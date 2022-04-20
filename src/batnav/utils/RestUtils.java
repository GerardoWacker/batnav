package batnav.utils;

import batnav.instance.Game;
import batnav.notifications.Notification;
import batnav.notifications.NotificationManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.function.Consumer;

public class RestUtils
{

   private final String REST_ENDPOINT;
   private final Sambayon sambayon;
   private final NotificationManager notificationManager;

   /**
    * Helps handle requests to the static server more efficiently.
    *
    * @param sambayon Sambayón geolocation manager.
    */
   public RestUtils(final Sambayon sambayon)
   {
      this.sambayon = sambayon;
      this.REST_ENDPOINT = sambayon.getServer("damas") + "/";
      this.notificationManager = Game.getInstance().getNotificationManager();
   }

   /**
    * Sends a request including a JSON object to the specified endpoint.
    *
    * @param endpoint Requested endpoint to access.
    * @param obj      JSON object to send.
    * @param callback Response from server (String).
    */
   public void sendJsonRequest(String endpoint, JSONObject obj, Consumer<String> callback)
   {
      try
      {
         HttpClient httpclient = HttpClients.createDefault();
         StringEntity requestEntity = new StringEntity(obj.toString(), ContentType.APPLICATION_JSON);

         HttpPost postMethod = new HttpPost(this.REST_ENDPOINT + endpoint);
         postMethod.setEntity(requestEntity);

         HttpResponse response = httpclient.execute(postMethod);
         HttpEntity entity = response.getEntity();

         if (entity != null)
         {
            String responseString = EntityUtils.toString(entity);
            callback.accept(responseString);
         }
      } catch (Exception e)
      {
         this.notificationManager.addNotification(
              new Notification(
                   Notification.Priority.CRITICAL,
                   "Ha ocurrido un error",
                   e.getLocalizedMessage(),
                   a -> {
                   }
              )
         );
         Logger.err("Ha ocurrido un error en la solicitud a " + endpoint);
         e.printStackTrace();
      }
   }

   /**
    * Sends a request including a String to the specified endpoint.
    *
    * @param endpoint Requested endpoint to access.
    * @param obj      JSON string to send.
    * @param callback Response from server (JsonObject).
    */
   public void sendJSONRequest(String endpoint, JSONObject obj, Consumer<JsonObject> callback)
   {
      try
      {
         HttpClient httpclient = HttpClients.createDefault();
         StringEntity requestEntity = new StringEntity(obj.toString(), ContentType.APPLICATION_JSON);

         HttpPost postMethod = new HttpPost(this.REST_ENDPOINT + endpoint);
         postMethod.setEntity(requestEntity);

         HttpResponse response = httpclient.execute(postMethod);
         HttpEntity entity = response.getEntity();

         if (entity != null)
         {
            String responseString = EntityUtils.toString(entity);
            callback.accept(new Gson().fromJson(responseString, JsonObject.class));
         }
      } catch (Exception e)
      {
         this.notificationManager.addNotification(
              new Notification(
                   Notification.Priority.CRITICAL,
                   "Ha ocurrido un error",
                   e.getLocalizedMessage(),
                   a -> {
                   }
              )
         );
         Logger.err("Ha ocurrido un error en la solicitud a " + endpoint);
         e.printStackTrace();
      }
   }

   /**
    * Sends a request including a String to the specified endpoint.
    *
    * @param endpoint Requested endpoint to access.
    * @param obj      JSON object to send.
    * @param callback Response from server (String).
    */
   public void sendJsonRequest(String endpoint, String obj, Consumer<String> callback)
   {
      try
      {
         HttpClient httpclient = HttpClients.createDefault();
         StringEntity requestEntity = new StringEntity(obj, ContentType.APPLICATION_JSON);

         HttpPost postMethod = new HttpPost(this.REST_ENDPOINT + endpoint);
         postMethod.setEntity(requestEntity);

         HttpResponse response = httpclient.execute(postMethod);
         HttpEntity entity = response.getEntity();

         if (entity != null)
         {
            String responseString = EntityUtils.toString(entity);
            callback.accept(responseString);
         }
      } catch (Exception e)
      {
         this.notificationManager.addNotification(
              new Notification(
                   Notification.Priority.CRITICAL,
                   "Ha ocurrido un error",
                   e.getLocalizedMessage(),
                   a -> {
                   }
              )
         );
         Logger.err("Ha ocurrido un error en la solicitud a " + endpoint);
         e.printStackTrace();
      }
   }
}