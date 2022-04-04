package batnav.online.socket;

import org.json.JSONObject;

public class JSONPacket extends Packet
{
   private final JSONObject packetContent;

   /**
    * Network packet, constructed with a name and a JSON content.
    * @param packetName Name of the packet
    * @param packetContent JSON object to be sent as content.
    */
   public JSONPacket(String packetName, JSONObject packetContent)
   {
      super(packetName, packetContent.toString());
      this.packetContent = packetContent;
   }

   /**
    * @return The raw JSON object.
    */
   public JSONObject getJSONPacketContent()
   {
      return packetContent;
   }
}
