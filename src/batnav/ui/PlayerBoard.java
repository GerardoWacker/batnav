package batnav.ui;

import batnav.online.match.Bomb;
import batnav.online.match.Match;
import batnav.online.match.Ship;
import batnav.utils.Colour;

import java.awt.*;

public class PlayerBoard extends Board
{
   private final Match match;

   public PlayerBoard(Match match)
   {
      this.match = match;
   }

   @Override
   public void paint(Graphics g)
   {
      super.paint(g);

      for (Ship ship : this.match.getPlayerShips())
      {
         this.drawShip(g, ship);
      }

      for (Bomb bomb : this.match.getOpponentBombs())
      {
         this.drawBomb(g, bomb);
      }
   }
}
