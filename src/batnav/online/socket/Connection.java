package batnav.online.socket;

import batnav.notifications.Notification;
import batnav.notifications.NotificationManager;
import batnav.online.session.SessionManager;
import batnav.online.session.User;
import batnav.utils.Logger;
import batnav.utils.Sambayon;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class Connection
{
   private Socket socket;
   private final String endpoint;
   private final Sambayon sambayon;
   private final SessionManager sessionManager;
   private final NotificationManager notificationManager;

   private User currentUser;

   /**
    * Socket connection Manager
    *
    * @param sambayon Sambayon geolocation server.
    * @param notificationManager
    */
   public Connection(final Sambayon sambayon, final SessionManager sessionManager, final NotificationManager notificationManager)
   {
      this.sambayon = sambayon;
      this.sessionManager = sessionManager;
      this.endpoint = sambayon.getServer("damas_sock");
      this.notificationManager = notificationManager;
   }

   /**
    * Establishes a connection to the real-time game server,
    *
    * @param uuid Session unique identifier.
    */
   public void connect(final String uuid)
   {
      try
      {
         Logger.log("Intentando conectarse a servidor de juego...");
         // Creates the Socket object with the obtained endpoint.
         this.socket = IO.socket(this.endpoint).connect();
         this.socket.on(Socket.EVENT_CONNECT, args -> {
            Logger.log("Conectado a servidor de juego.");
            this.socket.emit("authenticate", uuid);
            this.socket.on("authentication", this::authentication);
            this.socket.on("match-found", this::matchFound);
         });

      } catch (Exception e)
      {
         e.printStackTrace();
      }
   }

   /**
    * Method used when the `authentication` packet is received.
    *
    * @param json Response String containing a JSON object. Structure: {success: boolean, content: string/object}
    */
   private void authentication(final Object[] json)
   {
      String r = Arrays.toString(json);
      r = r.substring(1, r.length() - 1);

      try
      {
         final JSONObject response = new JSONObject(r);
         if (response.getBoolean("success"))
         {
            // Create a User object based off JSON.
            final JSONObject userObject = response.getJSONObject("content").getJSONObject("content");

            this.setCurrentUser(new User(
                 userObject.getString("username"),
                 userObject.getString("country"),
                 userObject.getString("created"),
                 userObject.getJSONObject("stats").getInt("plays"),
                 userObject.getJSONObject("stats").getInt("elo"),
                 userObject.getBoolean("developer")
            ));
            Logger.log("Iniciada sesión como " + this.getCurrentUser().getUsername());
            this.socket.emit("ready", this.sessionManager.getSessionId());
            Logger.log("Enviado paquete de ready");
         } else
         {
            this.notificationManager.addNotification(
                 new Notification(
                      Notification.Priority.CRITICAL,
                      "Ha ocurrido un error",
                      "Error de sesión" + response.getString("content"),
                      a -> {}
                 )
            );
            Logger.warn("Ha surgido un error iniciando sesión.");
            Logger.warn(response.getString("content"));
         }

      } catch (JSONException e)
      {
         this.notificationManager.addNotification(
              new Notification(
                   Notification.Priority.CRITICAL,
                   "Ha ocurrido un error",
                   e.getLocalizedMessage(),
                   a -> {}
              )
         );
         e.printStackTrace();
      }
   }

   private void matchFound(final Object[] json)
   {
      Logger.log("Encontrada partida.");
      String r = Arrays.toString(json);
      r = r.substring(1, r.length() - 1);
      System.out.println(r);
   }

   /**
    * Sends a custom packet to the Game's server.
    *
    * @param packet Packet to be sent.
    * @return If the delivery was a success.
    */
   public boolean sendPacket(final Packet packet)
   {
      if (this.socket != null)
      {
         this.socket.emit(packet.getPacketName(), packet.getPacketContent());
         return true;
      }

      return false;
   }

   /**
    * Gets current User.
    *
    * @return User.
    */
   public User getCurrentUser()
   {
      return currentUser;
   }

   /**
    * Sets the current User.
    *
    * @param user User.
    */
   public void setCurrentUser(User user)
   {
      this.currentUser = user;
   }
}
