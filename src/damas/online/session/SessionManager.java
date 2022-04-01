package damas.online.session;

import com.google.gson.JsonObject;
import damas.config.ConfigManager;
import damas.utils.Logger;
import damas.utils.RestUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class SessionManager
{
   private String sessionId;
   private final RestUtils restUtils;
   private final ConfigManager configManager;

   /**
    * Manager used for sessions and authentication purposes.
    *
    * @param restUtils Rest utils.
    * @author Gerardo Wacker
    */
   public SessionManager(final RestUtils restUtils, final ConfigManager configManager)
   {
      this.restUtils = restUtils;
      this.configManager = configManager;
   }

   /**
    * Checks if a session file exists, and reads its value to login into the Socket Server.
    */
   public void loadSession()
   {
      try
      {
         // Read session file.
         final JSONObject sessionObject = this.configManager.loadJson("ses.json");

         // Check if the file exists.
         if (sessionObject != null)
         {
            // Save session id. into the global variable.
            this.setSessionId(sessionObject.getString("session"));
         } else
         {
            Logger.log("El sistema no encontró un archivo de sesión.");
         }
      } catch (Exception e)
      {
         e.printStackTrace();
      }
   }

   /**
    * Sends a request to login into the game.
    *
    * @param username Username.
    * @param password Password.
    * @return Login success.
    * @throws Exception exception
    */
   public boolean login(final String username, final String password) throws Exception
   {
      // Value to return
      AtomicBoolean returnValue = new AtomicBoolean(false);

      // Creates a JSON object that would be sent alongside the request.
      final JSONObject loginReqObject = new JSONObject();
      loginReqObject.put("username", username);
      loginReqObject.put("password", password);

      // Sends the JSON object through a request to the '/login' endpoint.
      this.restUtils.sendJSONRequest("login", loginReqObject, response -> {
         /* Response model: {success: boolean, content: string} */
         if (response.get("success").getAsBoolean())
         {
            // JSON object to store in local configuration.
            final JsonObject sessionStore = new JsonObject();
            sessionStore.addProperty("session", response.get("content").getAsString());

            // Save file.
            try
            {
               this.configManager.saveJson("ses.json", sessionStore);
            } catch (IOException e)
            {
               Logger.err("Hubo un problema al guardar la sesión.");
               e.printStackTrace();
            }

            // Save session unique identifier.
            this.setSessionId(response.get("content").getAsString());

            returnValue.set(true);
         }
      });

      return returnValue.get();
   }

   /**
    * Sets the session unique identifier.
    *
    * @param sessionId UUID String.
    */
   public void setSessionId(String sessionId)
   {
      this.sessionId = sessionId;
   }

   /**
    * @return If the user is logged in.
    */
   public boolean isLoggedIn()
   {
      return this.sessionId != null;
   }

   public String getSessionId()
   {
      return sessionId;
   }
}
