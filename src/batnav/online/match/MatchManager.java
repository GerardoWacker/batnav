package batnav.online.match;

import batnav.online.session.SessionManager;
import batnav.online.socket.Connection;
import batnav.online.socket.JSONPacket;
import batnav.online.socket.Packet;
import com.google.common.collect.Lists;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

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
    *
    * @param connection Real-time connection manager.
    */
   public void joinRankedQueue(final Connection connection)
   {
      connection.sendPacket(new Packet("join-ranked-queue", this.sessionManager.getSessionId()));
   }

   /**
    * Sends a packet leaving the matchmaking queue.
    *
    * @param connection Real-time connection manager.
    */
   public void leaveRankedQueue(final Connection connection)
   {
      connection.sendPacket(new Packet("leave-ranked-queue", this.sessionManager.getSessionId()));
   }

   /**
    * Method executed when a bomb packet has been received. It updates the current match and the game's interface.
    *
    * @param response Packet from server.
    */
   public void receiveBomb(final Object[] response)
   {
      try
      {
         final JSONObject json = Connection.decodePacket(response);
         final JSONArray coordinates = json.getJSONArray("coordinates");

         this.getCurrentMatch().addOpponentBomb(new int[]{coordinates.getInt(0), coordinates.getInt(1)});

         // TODO: Update game interface
      } catch (JSONException e)
      {
         e.printStackTrace();
      }
   }

   /**
    * Method executed when a bomb packet has been received. It updates the current match and the game's interface.
    *
    * @param response Packet from server.
    */
   public void receiveShips(final Object[] response)
   {
      this.getCurrentMatch().hasReceivedOpponentShips();
   }

   /**
    * Method executed when the player's bomb has been thrown. It updates the current match and the game's interface.
    *
    * @param response Packet from server.
    */
   public void hasThrownBomb(final Object[] response)
   {
      try
      {
         final JSONObject json = Connection.decodePacket(response);

         if (json.getBoolean("success"))
         {
            final JSONObject content = json.getJSONObject("content");

            final int x = content.getJSONArray("coordinates").getInt(0),
                 y = content.getJSONArray("coordinates").getInt(1);

            final boolean hasHit = content.getBoolean("hasHit");

            this.getCurrentMatch().addPlayerBomb(new Bomb(
                 x, y, false, hasHit
            ));

            // TODO: Update Game UI
         }
      } catch (JSONException e)
      {
         e.printStackTrace();
      }
   }

   /**
    * Sets the current match.
    *
    * @param currentMatch Current Match object.
    */
   public void setCurrentMatch(Match currentMatch)
   {
      this.currentMatch = currentMatch;
   }

   public Match getCurrentMatch()
   {
      return currentMatch;
   }
}
