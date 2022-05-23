package batnav.ui.boards;

import batnav.online.model.Bomb;
import batnav.online.match.Match;

import java.awt.*;

public class OpponentBoard extends Board
{
   private final Match match;

   public OpponentBoard(Match match)
   {
      this.match = match;
   }

   @Override
   public void paint(Graphics g)
   {
      super.paint(g);

      for (Bomb bomb : this.match.getPlayerBombs())
      {
         this.drawBomb(g, bomb);
      }
   }
}
