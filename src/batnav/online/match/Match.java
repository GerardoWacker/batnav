package batnav.online.match;

import batnav.ui.screens.MatchScreen;
import batnav.online.model.Bomb;
import batnav.online.model.Ship;
import com.google.common.collect.Lists;
import batnav.online.model.User;

import java.util.List;

public class Match
{
   private final String id;
   private final User opponent;

   private final List<Bomb> playerBombs, opponentBombs;
   private List<Ship> playerShips;

   private MatchScreen matchScreen;

   private boolean hasReceivedOpponentShips = false;

   public Match(String id, User opponent)
   {
      this.id = id;
      this.opponent = opponent;
      this.playerBombs = Lists.newArrayList();
      this.opponentBombs = Lists.newArrayList();
      this.playerShips = Lists.newArrayList();
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

   public void setPlayerShips(final List<Ship> shipList)
   {
      this.playerShips = shipList;
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

   public void setMatchScreen(MatchScreen matchScreen)
   {
      this.matchScreen = matchScreen;
   }

   public MatchScreen getMatchScreen()
   {
      return matchScreen;
   }

   public boolean hasReceivedOpponentShips()
   {
      this.getMatchScreen().setOpponentReady();
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
