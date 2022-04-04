package batnav.online.match;

import batnav.online.session.SessionManager;
import batnav.online.socket.Connection;
import batnav.online.socket.JSONPacket;
import batnav.online.socket.Packet;
import org.json.JSONObject;

public class MatchManager
{
   private final SessionManager sessionManager;

   private Match currentMatch;

   public MatchManager(final SessionManager sessionManager)
   {
      this.sessionManager = sessionManager;
   }

   /**
    * Sends a packet joining the matchmaking queue.
    * @param connection Real-time connection manager.
    */
   public void joinRankedQueue(final Connection connection)
   {
      connection.sendPacket(new Packet("join-ranked-queue", this.sessionManager.getSessionId()));
   }

   /**
    * Sends a packet leaving the matchmaking queue.
    * @param connection Real-time connection manager.
    */
   public void leaveRankedQueue(final Connection connection)
   {
      connection.sendPacket(new Packet("leave-ranked-queue", this.sessionManager.getSessionId()));
   }

   public void setCurrentMatch(Match currentMatch)
   {
      this.currentMatch = currentMatch;
   }

   public Match getCurrentMatch()
   {
      return currentMatch;
   }
}
