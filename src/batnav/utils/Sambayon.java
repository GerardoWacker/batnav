package batnav.utils;

import batnav.instance.Game;
import batnav.notifications.Notification;
import batnav.notifications.NotificationManager;
import com.google.common.collect.Maps;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.Map;

/**
 * Geolocation module to improve updates in a server's URL.
 */
public class Sambayon
{
   private final String SAMBAYON_ENDPOINT = "https://sb.rar.vg/";
   private final String FALLBACK_SERVER = "https://batnav.glitch.me/";
   private final Map<String, String> serverPool = Maps.newHashMap();
   private boolean fallbackMode = false;

   /**
    * Get a server's URL based on location.
    *
    * @param serverName Name/ID to identify selected server.
    * @return The server's URL.
    */
   public String getServer(final String serverName)
   {
      // Check for fallback mode.
      if (fallbackMode)
      {
         try
         {
            if (serverName.equals("damas") || serverName.equals("damas_sock"))
            {
               Logger.log("Accedido a " + serverName + " usando los registros fallback.");
               return this.FALLBACK_SERVER;
            } else throw new SambayonException("No fue posible conectarse a Sambayón.");
         } catch (Exception e)
         {
            Game.getInstance().getNotificationManager().addNotification(
                 new Notification(Notification.Priority.CRITICAL,
                      "Ha ocurrido un error", "Hubo un error al conectarse con Sambayón.", a -> {
                 }
                 )
            );
            Logger.err(e.getLocalizedMessage());
         }
      }

      if (this.serverPool.containsKey(serverName))
      {
         Logger.log("Accedido a " + serverName + " desde los servidores en caché.");
         return this.serverPool.get(serverName);
      } else
      {
         Logger.log("Obteniendo " + serverName + " desde Sambayón...");
         try
         {
            HttpClient httpclient = HttpClients.createDefault();
            StringEntity requestEntity = new StringEntity("{\"client_id\": \"chocomint\", \"req\":\"" +
                 serverName + "\"}", ContentType.APPLICATION_JSON);

            HttpPost postMethod = new HttpPost(SAMBAYON_ENDPOINT);
            postMethod.setEntity(requestEntity);

            HttpResponse response = httpclient.execute(postMethod);
            HttpEntity entity = response.getEntity();

            if (entity != null)
            {
               JSONObject json = new JSONObject(EntityUtils.toString(entity));
               if (json.getBoolean("success"))
               {
                  this.serverPool.put(serverName, json.getString("response"));
                  return json.getString("response");
               } else
               {
                  throw new SambayonException(json.getString("response"));
               }
            }
         } catch (Exception e)
         {
            Game.getInstance().getNotificationManager().addNotification(
                 new Notification(
                      Notification.Priority.CRITICAL,
                      "Ha ocurrido un error",
                      "Hubo un error en la conexión con Sambayón",
                      a -> {
                      }
                 )
            );
            Logger.err("Hubo un error en la conexión con Sambayón.");
            e.printStackTrace();
         }

         return null;
      }
   }

   /**
    * Sets the fallback mode on Sambayon, using a hardcoded backup server allocated that will be available most of the
    * time.
    *
    * @param fallbackMode Is fallback.
    */
   public void setFallbackMode(boolean fallbackMode)
   {
      this.fallbackMode = fallbackMode;
   }

   /**
    * Checks if Sambayón is accessible by sending a ping packet.
    */
   public boolean isAccesible()
   {
      return this.getServer("ping") != null;
   }
}