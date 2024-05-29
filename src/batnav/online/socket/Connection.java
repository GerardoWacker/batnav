package batnav.online.socket;

import batnav.instance.Game;
import batnav.online.match.Match;
import batnav.online.match.MatchManager;
import batnav.notifications.Notification;
import batnav.online.model.Packet;
import batnav.online.session.SessionManager;
import batnav.ui.screens.MainMenuScreen;
import batnav.ui.screens.MatchScreen;
import batnav.online.model.User;
import batnav.ui.screens.ResultsScreen;
import batnav.utils.Logger;
import batnav.utils.Sambayon;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.util.Arrays;

public class Connection
{
   private Socket socket;
   private final String endpoint;
   private final SessionManager sessionManager;
   private final MatchManager matchManager;

   private boolean disconnectionWasIssued = false;

   private User currentUser;

   /**
    * Socket connection Manager
    *
    * @param sambayon       Sambayon geolocation server.
    * @param sessionManager Session manager.
    * @param matchManager   Match manager.
    */
   public Connection(final Sambayon sambayon, final SessionManager sessionManager, final MatchManager matchManager)
   {
      this.sessionManager = sessionManager;
      this.endpoint = sambayon.getServer("damas_sock");
      this.matchManager = matchManager;
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

            this.disconnectionWasIssued = false;

            this.socket.emit("authenticate", uuid);

            // Authentication.
            this.socket.on("authentication", this::authentication);

            // Match.
            this.socket.on("match", this::match);
            this.socket.on("match-bomb-thrown", this.matchManager::hasThrownBomb);
            this.socket.on("match-bomb-receive", this.matchManager::receiveBomb);
            this.socket.on("match-ships-set", data -> this.matchManager
                 .getCurrentMatch().getMatchScreen().setPlayerReady());
            this.socket.on("match-ships-receive", this.matchManager::receiveShips);
            this.socket.on("match-turn", this.matchManager::turn);
            this.socket.on("match-end", this.matchManager::end);
            this.socket.on("disconnect", this::onDisconnect);

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
      try
      {
         final JSONObject response = Connection.decodePacket(json);
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
//            this.sendPacket(new Packet("join-ranked-queue", this.sessionManager.getSessionId()));
            Game.getInstance().setMainMenuScreen(new MainMenuScreen());
            Game.getInstance().getSplashScreen().setVisible(false);
            Game.getInstance().getMainMenuScreen().setVisible(true);
            Game.getInstance().setDone(true);
         } else
         {
            Game.getInstance().getNotificationManager().addNotification(
                 new Notification(
                      Notification.Priority.CRITICAL,
                      "Ha ocurrido un error",
                      "Error de sesión" + response.getString("content"),
                      a -> {
                      }
                 )
            );
            Logger.warn("Ha surgido un error iniciando sesión.");
            Logger.warn(response.getString("content"));

            this.sessionManager.setAndSaveSessionId(null);

            Game.getInstance().getSplashScreen().setVisible(false);
            Game.getInstance().getLoginScreen().showLoginPanel();
            Game.getInstance().getLoginScreen().setVisible(true);
         }

      } catch (JSONException e)
      {
         Game.getInstance().getNotificationManager().addNotification(
              new Notification(
                   Notification.Priority.CRITICAL,
                   "Ha ocurrido un error",
                   e.getLocalizedMessage(),
                   a -> {
                   }
              )
         );
         e.printStackTrace();
      }
   }

   /**
    * Method used when the `match` packet is received.
    *
    * @param json Response String containing a JSON object. Structure: {success: boolean, content: object}
    */
   private void match(final Object[] json)
   {
      Logger.log("Encontrada partida.");

      try
      {
         final JSONObject response = Connection.decodePacket(json);
         if (response.getBoolean("success"))
         {
            // Create JSON Objects based on response.
            final JSONObject matchObject = response.getJSONObject("content");
            final JSONObject opponentObject = matchObject.getJSONObject("opponent");

            // Set current match.
            this.matchManager.setCurrentMatch(
                 new Match(
                      matchObject.getString("matchId"),
                      new User(
                           opponentObject.getString("username"),
                           opponentObject.getString("country"),
                           opponentObject.getJSONObject("stats").getInt("plays"),
                           opponentObject.getJSONObject("stats").getInt("elo"),
                           opponentObject.getBoolean("developer")
                      )
                 )
            );

            Game.getInstance().getMainMenuScreen().setVisible(false);
            this.matchManager.getCurrentMatch().setMatchScreen(new MatchScreen(this.matchManager.getCurrentMatch()));
         } else
         {
            final JSONObject matchFailObject = response.getJSONObject("content");

            if (matchFailObject.getBoolean("retry"))
            {
               Logger.err("Reintentando matchmaking: " + matchFailObject.getString("message"));
               this.matchManager.joinRankedQueue(this);
            } else
            {
               Logger.log("El usuario abandonó la búsqueda de partidos.");
               // TODO: Display menu interface
            }
         }

      } catch (JSONException e)
      {
         e.printStackTrace();
      }
   }

   /**
    * Method that executed once a disconnection has occurred.
    *
    * @param json Packet received.
    */
   private void onDisconnect(final Object[] json)
   {
      if (!this.disconnectionWasIssued)
      {
         Game.getInstance().getMainMenuScreen().setVisible(false);
         if(Game.getInstance().getMatchManager().getCurrentMatch() != null)
            Game.getInstance().getMatchManager().getCurrentMatch().getMatchScreen().setVisible(false);
         JOptionPane.showMessageDialog(null,
              "Fuiste desconectado del servidor",
              "Advertencia", JOptionPane.ERROR_MESSAGE);
      }
   }

   /**
    * Disconnects from the server.
    */
   public void disconnect()
   {
      this.disconnectionWasIssued = true;
      this.socket.disconnect();
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

   /**
    * Converts a socket response type into a parseable JSON object.
    *
    * @param content Packet content.
    * @return JSON Object with the packet content.
    */
   public static JSONObject decodePacket(final Object[] content) throws JSONException
   {
      String r = Arrays.toString(content);
      r = r.substring(1, r.length() - 1);

      return new JSONObject(r);
   }
}
