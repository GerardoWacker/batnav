package damas.utils;

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
   private final Map<String, String> serverPool = Maps.newHashMap();

   /**
    * Get a server's URL based on location.
    *
    * @param serverName Name/ID to identify selected server.
    * @return The server's URL.
    */
   public String getServer(final String serverName)
   {
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
            Logger.err("Hubo un error en la conexión con Sambayón.");
            e.printStackTrace();
         }

         return null;
      }
   }

   /**
    * Checks if Sambayón is accessible by sending a ping packet.
    */
   public boolean isAccesible()
   {
      return this.getServer("ping") != null;
   }
}