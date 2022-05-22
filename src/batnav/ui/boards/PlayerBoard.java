package batnav.ui.boards;

import batnav.online.model.Bomb;
import batnav.online.match.Match;
import batnav.online.model.Ship;

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
