package batnav.ui.boards;

import batnav.online.model.Ship;
import batnav.ui.screens.ShipSelectionScreen;

import java.awt.*;

public class ShipSelectionBoard extends Board
{

   private final ShipSelectionScreen parent;

   public ShipSelectionBoard(ShipSelectionScreen parent)
   {
      this.parent = parent;
   }

   @Override
   public void paint(Graphics g)
   {
      /*
      super.paint(g);

      for (Ship ship : this.parent.getShips())
      {
         this.drawShip(g, ship);
      }

       */
   }
}
