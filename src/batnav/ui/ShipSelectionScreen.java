package batnav.ui;

import batnav.online.match.Bomb;
import batnav.online.match.Ship;
import com.google.common.collect.Lists;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ShipSelecionScreen extends JFrame implements ActionListener
{
   protected List<Ship> ships;
   private Ship selectedShip;
   private final ShipSelectionBoard shipSelectionBoard;

   public ShipSelecionScreen()
   {
      this.ships = Lists.newArrayList();
      this.shipSelectionBoard = new ShipSelectionBoard(this);

      // Add ships.
      this.ships.add(new Ship(2));
      this.ships.add(new Ship(2));
      this.ships.add(new Ship(2));
      this.ships.add(new Ship(3));
      this.ships.add(new Ship(3));
      this.ships.add(new Ship(3));
      
   }

   // When a ship is selected in the East JPanel, this::selectedShip should be set with its value.

   @Override
   public void actionPerformed(ActionEvent e)
   {
      // Get the new mouse coordinates and handle the click.
      Point point = ShipSelecionScreen.this.getMousePosition();

      if (point != null)
      {
         // Get coordinates based on clicked point.
         int[] coordinates = shipSelectionBoard.handleClick(point);

         // Add a null check before setting positions.
         if (coordinates != null)
         {
            // TODO: set this::selectedShip position based on the coordinates specified above.
         }

         this.shipSelectionBoard.update();
      }
   }
}
