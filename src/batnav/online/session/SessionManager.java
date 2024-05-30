package batnav.online.session;

import batnav.instance.Game;
import batnav.notifications.Notification;
import batnav.notifications.NotificationManager;
import com.google.gson.JsonObject;
import batnav.config.ConfigManager;
import batnav.utils.Logger;
import batnav.utils.RestUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

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
   public boolean loadSession()
   {
      if (Game.getInstance().hasInjection())
      {
         Logger.log("No se cargó el archivo de sesión porque hay una inyección en curso.");
         return false;
      }
      try
      {
         // Read session file.
         final JSONObject sessionObject = this.configManager.loadJson("ses.json");

         // Check if the file exists.
         if (sessionObject != null)
         {
            // Save session id. into the global variable.
            this.setSessionId(sessionObject.getString("session"));
            return true;
         } else
         {
            Logger.log("El sistema no encontró un archivo de sesión.");
            return false;
         }
      } catch (Exception e)
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
      return false;
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
            // Save session unique identifier.
            this.setAndSaveSessionId(response.get("content").getAsString());

            returnValue.set(true);
         }
      });

      return returnValue.get();
   }

   public boolean register(final String username, final String password, final String email, Consumer<String> response) throws JSONException
   {
      AtomicBoolean returnValue = new AtomicBoolean(false);

      final JSONObject registerReqObj = new JSONObject();
      registerReqObj.put("username", username);
      registerReqObj.put("password", password);
      registerReqObj.put("email", email);

      this.restUtils.sendJSONRequest("register", registerReqObj, res -> {
         if (res.get("success").getAsBoolean())
         {
            returnValue.set(true);
            response.accept("El usuario fue creado con éxito.");
         } else {
            response.accept(res.get("content").getAsString());
         }
      });

      return returnValue.get();
   }

   /**
    * Saves the session unique identifier into local configuration.
    *
    * @param sessionId Unique session identifier (UUID).
    */
   public void saveSession(String sessionId)
   {
      final JsonObject sessionStore = new JsonObject();
      sessionStore.addProperty("session", sessionId);

      try
      {
         this.configManager.saveJson("ses.json", sessionStore);
      } catch (IOException e)
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

   public void setSessionId(String sessionId)
   {
      this.sessionId = sessionId;
   }

   /**
    * Sets the session unique identifier.
    *
    * @param sessionId Unique session identifier (UUID).
    */
   public void setAndSaveSessionId(String sessionId)
   {
      this.sessionId = sessionId;
      this.saveSession(Objects.requireNonNullElse(sessionId, ""));
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
