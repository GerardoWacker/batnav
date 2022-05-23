package batnav.online.model;

public class Packet
{
   private final String packetName;
   private final String packetContent;

   /**
    * Network packet. Contains a name and content, used to be sent as information to the Game's server.
    * @param packetName Name for the packet.
    * @param packetContent Contents of the packet.
    */
   public Packet(final String packetName, final String packetContent)
   {
      this.packetName = packetName;
      this.packetContent = packetContent;
   }

   public String getPacketName()
   {
      return packetName;
   }

   public String getPacketContent()
   {
      return packetContent;
   }
}
