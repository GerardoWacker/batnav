package batnav.ui;

import batnav.online.match.Ship;
import batnav.utils.Colour;
import com.google.common.collect.Lists;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ShipSelectionScreen extends JFrame implements ActionListener {
   protected List<Ship> ships;
   private Ship selectedShip;
   private final ShipSelectionBoard shipSelectionBoard;

   public ShipSelectionScreen() throws IOException {
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
      okButton.setText("OK");
      okButton.setPreferredSize(new Dimension( 100, 30));

      JButton rotateButton = new JButton();
      rotateButton.setSize(new Dimension( 100, 10));
      rotateButton.setText("Rotar");

      shipSelectionBoard.setPreferredSize(new Dimension (380, 300) );
      panelEast.setPreferredSize(new Dimension (150, 300));
      panelSouth.setSize(new Dimension (600, 65));

      this.shipSelectionBoard.addActionListener(this);

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

   private class ShipLabel extends JLabel
   {
      private final int id;

      private ShipLabel(int id, Ship ship) throws IOException {

         final BufferedImage icon = ImageIO.read(new File("assets/ships/barco" + ship.getSize() + ".png"));
         final int height = (150 / (icon.getWidth() / icon.getHeight()));

         super.setIcon(new ImageIcon(new ImageIcon(icon).getImage().getScaledInstance(150, height, Image.SCALE_DEFAULT)));
         super.getIcon();
         this.id = id;
      }

      public int getId() {
         return id;
      }
   }

   public class MouseEvent implements MouseListener
   {

      @Override
      public void mouseClicked(java.awt.event.MouseEvent mouseEvent) {
         int id = ((ShipLabel)mouseEvent.getSource()).getId();
         ShipSelectionScreen.this.setSelectedShip(ships.get(id));
         System.out.println("Se ha seleccionado el barco " + id);
      }

      @Override
      public void mousePressed(java.awt.event.MouseEvent mouseEvent) {

      }

      @Override
      public void mouseReleased(java.awt.event.MouseEvent mouseEvent) {

      }

      @Override
      public void mouseEntered(java.awt.event.MouseEvent mouseEvent) {

      }

      @Override
      public void mouseExited(java.awt.event.MouseEvent mouseEvent) {

      }
   }

   // When a ship is selected in the East JPanel, this::selectedShip should be set with its value.
   // For every ship added into this::ships, draw an Image into the East JPanel for selection.
   // When it has been clicked, set the selected ship as ships.get(JLabel Id.).
   // Handle rotation and clicking on board is yet to be implemented, however, it's just a button.

   @Override
   public void actionPerformed(ActionEvent e)
   {
      // Get the new mouse coordinates and handle the click.
      Point point = ShipSelectionScreen.this.getMousePosition();

      if (point != null)
      {
         // Get coordinates based on clicked point.
         int[] coordinates = shipSelectionBoard.handleClick(point);

         // Add a null check before setting positions.
         if (coordinates != null)
         {
            System.out.println(Arrays.toString(coordinates));
            this.selectedShip.setPosition(coordinates[0], coordinates[1]);
         }

         this.shipSelectionBoard.update();
      }
   }

   public void setSelectedShip(Ship selectedShip) {
      this.selectedShip = selectedShip;
   }

   public static void main(String args[]){
      try {
         new ShipSelectionScreen();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
