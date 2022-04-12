package batnav.ui;

import batnav.online.match.Ship;

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
      super.paint(g);

      for (Ship ship : this.parent.ships)
      {
         this.drawShip(g, ship);
      }
   }
}
