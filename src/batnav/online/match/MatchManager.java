package batnav.online.match;

import batnav.instance.Game;
import batnav.notifications.Notification;
import batnav.online.model.Bomb;
import batnav.online.model.Ship;
import batnav.online.session.SessionManager;
import batnav.online.socket.Connection;
import batnav.online.model.JSONPacket;
import batnav.online.model.Packet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
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
    * @param connection Real-time connection manager.
    * @param x          X coordinates for the bomb.
    * @param y          Y coordinates for the bomb.
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
    * @param shipList   List containing every ship object.
    */
   public void setShips(final Connection connection, List<Ship> shipList)
   {
      try
      {
         final JSONArray shipArray = new JSONArray();

         for (Ship ship : shipList)
         {
            final JSONArray coordinatesArray = new JSONArray();

            for (int[] coordinates : ship.getAsRawData())
            {
               coordinatesArray.put(coordinates);
            }

            shipArray.put(coordinatesArray);
         }

         final JSONObject object = new JSONObject();

         object.put("matchId", this.getCurrentMatch().getId());
         object.put("playerId", this.sessionManager.getSessionId());
         object.put("coordinates", shipArray);

         connection.sendPacket(new Packet("match-set-ships", object.toString()));
         this.getCurrentMatch().setPlayerShips(shipList);

      } catch (JSONException e)
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
      }
   }

   /**
    * Handles turn setting for the players in a match.
    *
    * @param response Contains a boolean with the turn's value.
    */
   public void turn(final Object[] response)
   {
      try
      {
         final JSONObject json = Connection.decodePacket(response);

         // If the "content" value for this packet is true, then it's the user's turn.
         if (json.getBoolean("content"))
         {
            this.getCurrentMatch().getMatchScreen().startTimer();
            this.getCurrentMatch().getMatchScreen().getPlayerBoard().setDisabled(false);
            this.getCurrentMatch().getMatchScreen().getOpponentBoard().setDisabled(true);
         } else
         {
            this.getCurrentMatch().getMatchScreen().startTimer();
            this.getCurrentMatch().getMatchScreen().getPlayerBoard().setDisabled(true);
            this.getCurrentMatch().getMatchScreen().getOpponentBoard().setDisabled(false);
         }
      } catch (JSONException e)
      {
         throw new RuntimeException(e);
      }

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

         this.currentMatch.getMatchScreen().repaint();
      } catch (JSONException e)
      {
         e.printStackTrace();
      }
   }

   /**
    * Method executed when the ships packet has been received. It updates the current match and the game's interface.
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
            final boolean hasSunk = content.getBoolean("hasSunk");

            if (hasSunk)
            {
               JOptionPane.showMessageDialog(null,
                    "¡Hundiste un barco!",
                    "Notificación", JOptionPane.INFORMATION_MESSAGE);
            }

            this.getCurrentMatch().addPlayerBomb(new Bomb(
                 x, y, false, hasHit
            ));

            this.getCurrentMatch().getMatchScreen().repaint();
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
