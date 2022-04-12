package batnav.online.match;

import com.google.common.collect.Lists;
import batnav.online.session.User;
import org.json.JSONArray;

import java.util.List;

public class Match
{
   private final String id;
   private final User opponent;

   private final List<Bomb> playerBombs, opponentBombs;
   private final List<Ship> playerShips;

   private boolean hasReceivedOpponentShips = false;

   public Match(String id, User opponent)
   {
      this.id = id;
      this.opponent = opponent;
      this.playerBombs = Lists.newArrayList();
      this.opponentBombs = Lists.newArrayList();
      this.playerShips = Lists.newArrayList();
      this.playerShips.add(new Ship(2).setPosition(0, 0, false));
   }

   /**
    * Add a bomb to the player bomb list.
    *
    * @param bomb Bomb object.
    */
   public void addPlayerBomb(final Bomb bomb)
   {
      this.playerBombs.add(bomb);
   }

   /**
    * Add a bomb to the opponent bomb list.
    *
    * @param bomb Bomb object.
    */
   public void addOpponentBomb(final Bomb bomb)
   {
      this.opponentBombs.add(bomb);
   }

   /**
    * Add a bomb to the player bomb list.
    *
    * @param coordinates Bomb coordinates.
    */
   public void addPlayerBomb(final int[] coordinates, boolean hasHit)
   {
      this.playerBombs.add(new Bomb(coordinates[0], coordinates[1], false, hasHit));
   }

   /**
    * Add a bomb to the opponent bomb list.
    *
    * @param coordinates Bomb coordinates.
    */
   public void addOpponentBomb(final int[] coordinates)
   {
      this.opponentBombs.add(
           new Bomb(
                coordinates[0],
                coordinates[1],
                true,
                this.getShipCoordinates().contains(new int[]{coordinates[0], coordinates[1]})
           )
      );
   }

   public void addPlayerShip(final Ship ship)
   {
      this.getPlayerShips().add(ship);
   }

   public void addPlayerShips(final List<Ship> shipList)
   {
      // TODO: Player ships implementation.
   }

   /**
    * @return A list containing every point that a ship is occupying.
    */
   public List<int[]> getShipCoordinates()
   {
      final List<int[]> coordinates = Lists.newArrayList();

      for (Ship ship : this.playerShips)
      {
         coordinates.add(new int[]{ship.getX(), ship.getY()});
      }

      return coordinates;
   }

   /**
    * Set if player has received opponent ships
    */
   public void setReceivedShips()
   {
      this.hasReceivedOpponentShips = true;
   }

   public boolean hasReceivedOpponentShips()
   {
      return hasReceivedOpponentShips;
   }

   public String getId()
   {
      return id;
   }

   public User getOpponent()
   {
      return opponent;
   }

   public List<Bomb> getPlayerBombs()
   {
      return playerBombs;
   }

   public List<Bomb> getOpponentBombs()
   {
      return opponentBombs;
   }

   public List<Ship> getPlayerShips()
   {
      return playerShips;
   }
}
