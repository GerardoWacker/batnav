package batnav.ui.screens;

import batnav.online.match.Ship;
import batnav.ui.boards.ShipSelectionBoard;
import batnav.utils.Colour;
import com.google.common.collect.Lists;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ShipSelectionScreen extends JFrame implements ActionListener
{
   protected List<Ship> ships;
   private Ship selectedShip;
   private final ShipSelectionBoard shipSelectionBoard;

   public ShipSelectionScreen() throws IOException
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
      this.ships.add(new Ship(4));
      this.ships.add(new Ship(5));

      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setResizable(false);

      this.setSize(580, 490);
      this.setLayout(new BorderLayout(0, 40));
      JPanel panelEast = new JPanel();
      JPanel panelSouth = new JPanel();

      JButton okButton = new JButton();
      okButton.setText("Aceptar");
      okButton.setPreferredSize(new Dimension(100, 30));
      okButton.addActionListener(this);
      okButton.setActionCommand("setShips");

      JButton rotateButton = new JButton();
      rotateButton.setSize(new Dimension(100, 10));
      rotateButton.setText("Rotar");
      rotateButton.addActionListener(this);
      rotateButton.setActionCommand("rotateShip");

      shipSelectionBoard.setPreferredSize(new Dimension(380, 300));
      panelEast.setPreferredSize(new Dimension(150, 300));
      panelSouth.setSize(new Dimension(600, 65));

      this.shipSelectionBoard.addMouseListener(new BoardMouseEvent());

      this.add(shipSelectionBoard, BorderLayout.CENTER);
      this.add(panelSouth, BorderLayout.SOUTH);

      panelEast.setLayout(new GridLayout(9, 1));
      panelSouth.setLayout(new BorderLayout());

      for (int i = 0; i < ships.size(); i++)
      {
         ShipLabel jLabel = new ShipLabel(i, ships.get(i));
         panelEast.add(jLabel);
         jLabel.addMouseListener(new MouseEvent());
      }

      panelEast.add(rotateButton);
      this.add(panelEast, BorderLayout.EAST);
      panelSouth.add(okButton, BorderLayout.EAST);


      this.setVisible(true);
   }

   @Override
   public void actionPerformed(ActionEvent e)
   {
      final String action = e.getActionCommand();
      switch (action)
      {
         case "rotateShip":
            if (this.selectedShip.isVertical())
            {
               if (this.selectedShip.getX() >= 10 - this.selectedShip.getSize())
               {
                  this.selectedShip.setPosition(10 - this.selectedShip.getSize(), this.selectedShip.getY());
               }
               this.selectedShip.setVertical(false);
            } else
            {
               if (this.selectedShip.getY() >= 10 - this.selectedShip.getSize())
               {
                  this.selectedShip.setPosition(this.selectedShip.getX(), 10 - this.selectedShip.getSize());
               }
               this.selectedShip.setVertical(true);
            }
            this.shipSelectionBoard.update();
      }
   }

   private class ShipLabel extends JLabel
   {
      private final int id;

      private ShipLabel(int id, Ship ship) throws IOException
      {

         final BufferedImage icon = ImageIO.read(new File("assets/ships/ship" + ship.getSize() + ".png"));
         final int height = (150 / (icon.getWidth() / icon.getHeight()));

         super.setIcon(new ImageIcon(new ImageIcon(icon).getImage().getScaledInstance(150, height, Image.SCALE_DEFAULT)));
         this.id = id;
      }

      public int getId()
      {
         return id;
      }
   }

   public class MouseEvent implements MouseListener
   {

      @Override
      public void mouseClicked(java.awt.event.MouseEvent mouseEvent)
      {
         int id = ((ShipLabel) mouseEvent.getSource()).getId();
         ShipSelectionScreen.this.setSelectedShip(ships.get(id));
         System.out.println("Se ha seleccionado el barco " + id);
      }

      @Override
      public void mousePressed(java.awt.event.MouseEvent mouseEvent)
      {

      }

      @Override
      public void mouseReleased(java.awt.event.MouseEvent mouseEvent)
      {

      }

      @Override
      public void mouseEntered(java.awt.event.MouseEvent mouseEvent)
      {

      }

      @Override
      public void mouseExited(java.awt.event.MouseEvent mouseEvent)
      {

      }
   }

   public class BoardMouseEvent implements MouseListener
   {
      @Override
      public void mouseClicked(java.awt.event.MouseEvent e)
      {
         // Check if a ship has already been selected.
         if (ShipSelectionScreen.this.selectedShip == null)
         {
            JOptionPane.showMessageDialog(null, "¡Tenés que seleccionar un barco!",
                 "Advertencia", JOptionPane.INFORMATION_MESSAGE);
            return;
         }

         // Get the new mouse coordinates and handle the click.
         Point point = e.getPoint();

         // Get coordinates based on clicked point.
         int[] coordinates = shipSelectionBoard.handleClick(point);

         // Add a null check before setting positions.
         if (coordinates != null)
         {
            System.out.println(Arrays.toString(coordinates));
            selectedShip.setPosition(coordinates[0], coordinates[1]);
         }

         shipSelectionBoard.update();
      }

      @Override
      public void mousePressed(java.awt.event.MouseEvent e)
      {

      }

      @Override
      public void mouseReleased(java.awt.event.MouseEvent e)
      {

      }

      @Override
      public void mouseEntered(java.awt.event.MouseEvent e)
      {

      }

      @Override
      public void mouseExited(java.awt.event.MouseEvent e)
      {

      }
   }

   public List<Ship> getShips()
   {
      return ships;
   }

   public Ship getSelectedShip()
   {
      return selectedShip;
   }

   public void setSelectedShip(Ship selectedShip)
   {
      this.selectedShip = selectedShip;
   }

   public static void main(String args[])
   {
      try
      {
         new ShipSelectionScreen();
      } catch (IOException e)
      {
         e.printStackTrace();
      }
   }
}
