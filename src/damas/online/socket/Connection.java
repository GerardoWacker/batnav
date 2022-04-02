package damas.online.socket;

import damas.online.session.SessionManager;
import damas.online.session.User;
import damas.utils.Logger;
import damas.utils.Sambayon;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class Connection
{
   private Socket socket;
   private final String endpoint;
   private final Sambayon sambayon;
   private final SessionManager sessionManager;

   private User currentUser;

   /**
    * Socket connection Manager
    *
    * @param sambayon Sambayon geolocation server.
    */
   public Connection(final Sambayon sambayon, final SessionManager sessionManager)
   {
      this.sambayon = sambayon;
      this.sessionManager = sessionManager;
      this.endpoint = sambayon.getServer("damas_sock");
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
         // Creates the Socket object with the obtained endpoint.
         this.socket = IO.socket(this.endpoint);
         this.socket.on(Socket.EVENT_CONNECT, args -> {
            this.socket.emit("authenticate", this.sessionManager.getSessionId());
            this.socket.on("authentication", this::authentication);
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
   private void authentication(final Object json)
   {
      try
      {
         final JSONObject response = new JSONObject(json);
         if (response.getBoolean("success"))
         {
            // Create a User object based off JSON.
            final JSONObject userObject = response.getJSONObject("content");

            this.setCurrentUser(new User(
                 userObject.getString("username"),
                 userObject.getString("country"),
                 userObject.getString("created"),
                 userObject.getJSONObject("stats").getInt("plays"),
                 userObject.getJSONObject("stats").getInt("elo"),
                 userObject.getBoolean("developer")
            ));
            Logger.log("Iniciada sesión como " + this.getCurrentUser());
         } else
         {
            Logger.warn("Ha surgido un error iniciando sesión.");
            Logger.warn(response.getString("content"));
         }

      } catch (JSONException e)
      {
         e.printStackTrace();
      }
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
      this.currentUser = currentUser;
   }
}
