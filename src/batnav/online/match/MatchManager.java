package batnav.online.match;

import batnav.online.session.SessionManager;
import batnav.online.socket.Connection;
import batnav.online.socket.JSONPacket;
import batnav.online.socket.Packet;
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
    *
    * @param connection Real-time connection manager.
    * @param x X coordinates for the bomb.
    * @param y Y coordinates for the bomb.
    */
   public void throwBomb(final Connection connection, final int x, final int y)
   {
      try
      {
         final JSONObject objectToSend = new JSONObject();
         objectToSend.put("matchId", this.getCurrentMatch().getId());
         objectToSend.put("playerId", this.sessionManager.getSessionId());
         objectToSend.put("coordinates", new int[]{x, y});
         connection.sendPacket(new JSONPacket("match-throw-bomb", objectToSend));
      } catch (JSONException e)
      {
         e.printStackTrace();
      }
   }

   /**
    * Sends a packet to set ships.
    *
    * @param connection Real-time connection manager
    * @param shipList List containing every ship object.
    */
   public void setShips(final Connection connection, List<Ship> shipList)
   {
      final JSONArray shipArray = new JSONArray();

      for (Ship ship : shipList)
      {
         shipArray.put(new JSONArray().put(new int[]{ship.getX(), ship.getY()}));
      }

      connection.sendPacket(new Packet("match-set-ships", shipArray.toString()));
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
