package batnav.ui.screens;

import batnav.instance.Game;
import batnav.online.model.Ship;
import batnav.ui.boards.ShipSelectionBoard;
import batnav.ui.components.GamePanel;
import batnav.utils.Colour;
import com.google.common.collect.Lists;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ShipSelectionScreen extends JFrame implements ActionListener
{
   JPanel southPanel, centerPanel, eastPanel, gamePanel;
   JButton okButton, rotateButton;
   protected List<Ship> ships;
   private Ship selectedShip;
   private final ShipSelectionBoard shipSelectionBoard;
   protected ShipLabel currentLabel;


   public ShipSelectionScreen() throws IOException
   {

      this.ships = Lists.newArrayList();
      this.shipSelectionBoard = new ShipSelectionBoard(this);
      this.setPreferredSize(new Dimension(380,300));

      // Add ships.
      this.ships.add(new Ship(2));
      this.ships.add(new Ship(2));
      this.ships.add(new Ship(2));
      this.ships.add(new Ship(3));
      this.ships.add(new Ship(3));
      this.ships.add(new Ship(3));
      this.ships.add(new Ship(4));
      this.ships.add(new Ship(5));


      this.southPanel = new JPanel();
      this.eastPanel = new JPanel();
      this.centerPanel = new JPanel();
      this.gamePanel = new GamePanel();

      this.rotateButton = new JButton("Rotar");
      this.setSize(580, 490);
      this.setLayout(new BorderLayout());
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.rotateButton.setMaximumSize(new Dimension(170,30));
      this.setResizable(false);

      this.okButton = new JButton("Aceptar");

      eastPanel.setPreferredSize(new Dimension(200, 300));
      //this.add(southPanel, BorderLayout.SOUTH);

      //this.add(eastPanel, BorderLayout.EAST);

      this.southPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
      this.southPanel.setBorder(new EmptyBorder(10,10,10,10));
      this.southPanel.add(okButton);

      for (int i = 0; i < ships.size(); i++)
      {
         ShipLabel jLabel = new ShipLabel(i, ships.get(i));
         eastPanel.add(jLabel);
         jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
         jLabel.addMouseListener(new MouseEvent());
         eastPanel.add(Box.createVerticalStrut(6));
      }
      eastPanel.add(Box.createVerticalStrut(6));

      this.eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
      this.eastPanel.setSize(300,100);
      this.eastPanel.setBorder(new EmptyBorder(10,10,10,10));
      rotateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
      this.eastPanel.add(rotateButton);

      this.eastPanel.setOpaque(false);
      this.eastPanel.setBackground(Colour.Transparent);
      this.southPanel.setOpaque(false);
      this.southPanel.setBackground(Colour.Transparent);



      this.centerPanel.setBackground(Color.pink);
      this.centerPanel.setLayout(new BorderLayout());
      //this.centerPanel.add(this.shipSelectionBoard);
      //this.shipSelectionBoard.setOpaque(true);
      //this.shipSelectionBoard.setBackground(Colour.Tomato);
      //this.add(this.shipSelectionBoard, BorderLayout.CENTER);
      this.gamePanel.setLayout(new BorderLayout());
      this.gamePanel.add(this.shipSelectionBoard, BorderLayout.CENTER);
      this.gamePanel.add(this.southPanel, BorderLayout.SOUTH);
      this.gamePanel.add(this.eastPanel, BorderLayout.EAST);
      this.add(gamePanel);
      this.setVisible(true);
   }

   @Override
   public void actionPerformed(ActionEvent e)
   {
      final String action = e.getActionCommand();
      switch (action)
      {
         case "rotateShip" ->
                 {
                    if (this.selectedShip.isVertical())
                    {
                       if (this.selectedShip.getX() >= 10 - this.selectedShip.getSize())
                       {
                          if (!coordinatesHasShip(
                                  new int[]{10 - this.selectedShip.getSize(), this.selectedShip.getY()},
                                  false, this.selectedShip))
                          {
                             this.selectedShip.setPosition(10 - this.selectedShip.getSize(), this.selectedShip.getY());
                             this.selectedShip.setVertical(false);
                          } else
                          {
                             JOptionPane.showMessageDialog(null,
                                     "No se puede rotar a esa posición: ¡ya hay un barco!",
                                     "Advertencia", JOptionPane.WARNING_MESSAGE);
                          }
                       } else
                       {
                          if (!coordinatesHasShip(
                                  new int[]{this.selectedShip.getX(), this.selectedShip.getY()},
                                  false, this.selectedShip))
                          {
                             this.selectedShip.setVertical(false);
                          } else
                          {
                             JOptionPane.showMessageDialog(null,
                                     "No se puede rotar a esa posición: ¡ya hay un barco!",
                                     "Advertencia", JOptionPane.WARNING_MESSAGE);
                          }
                       }
                    } else
                    {
                       if (this.selectedShip.getY() >= 10 - this.selectedShip.getSize())
                       {
                          if (!coordinatesHasShip(
                                  new int[]{this.selectedShip.getX(), 10 - this.selectedShip.getSize()},
                                  true, this.selectedShip))
                          {
                             this.selectedShip.setPosition(this.selectedShip.getX(), 10 - this.selectedShip.getSize());
                             this.selectedShip.setVertical(true);
                          } else
                          {
                             JOptionPane.showMessageDialog(null,
                                     "No se puede rotar a esa posición: ¡ya hay un barco!",
                                     "Advertencia", JOptionPane.WARNING_MESSAGE);
                          }
                       } else
                       {
                          if (!coordinatesHasShip(
                                  new int[]{this.selectedShip.getX(), this.selectedShip.getY()},
                                  true, this.selectedShip))
                          {
                             this.selectedShip.setVertical(true);
                          } else
                          {
                             JOptionPane.showMessageDialog(null,
                                     "No se puede rotar a esa posición: ¡ya hay un barco!",
                                     "Advertencia", JOptionPane.WARNING_MESSAGE);
                          }
                       }
                    }
                    this.shipSelectionBoard.update();
                 }
         case "setShips" ->
                 {
                    for (Ship ship : this.ships)
                    {
                       if (ship.getX() == null || ship.getY() == null)
                       {
                          JOptionPane.showMessageDialog(null,
                                  "¡Todos los barcos deben tener posición!",
                                  "Advertencia", JOptionPane.WARNING_MESSAGE);
                          return;
                       }
                    }
                    Game.getInstance().getMatchManager().setShips(
                            Game.getInstance().getConnection(),
                            this.ships
                    );
                    Game.getInstance().getMatchManager().getCurrentMatch().getMatchScreen().setVisible(true);
                 }
      }
   }


   private class ShipLabel extends JLabel
   {
      private final int id;

      /**
       * A label containing an Id. Intended for ship drawing use only.
       *
       * @param id   Ship Id. Should be the same as the list's Id.
       * @param ship Ship object.
       */
      private ShipLabel(int id, Ship ship) throws IOException
      {
         int cubes = 170 / 5;
         final BufferedImage icon = ImageIO.read(new File("assets/ships/ship" + ship.getSize() + ".png"));
         final int height = cubes;
         final int width = cubes * ship.getSize();
         super.setIcon(new ImageIcon(new ImageIcon(icon).getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT)));
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
         // Note: It's expected that ONLY ShipLabels trigger this event.
         // If the mouseEvent was not triggered by a ShipLabel, then something is waaaay off.

         // Get the ShipLabel.
         ShipLabel shipLabel = ((ShipLabel) mouseEvent.getSource());

         // Get the Id. from the ShipLabel.
         int id = shipLabel.getId();

         try
         {
            if (ShipSelectionScreen.this.getCurrentLabel() != null)
            {
               int cubes = 170 / 5;
               final int height = cubes;
               final int width = cubes * ships.get(shipLabel.id).getSize();
               final BufferedImage icon = ImageIO.read(new File("assets/ships/ship" +
                    ships.get(ShipSelectionScreen.this.getCurrentLabel().getId()).getSize() + ".png"));


               getCurrentLabel().setIcon(new ImageIcon(new ImageIcon(icon).getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT)));
            }
         } catch (IOException e)
         {
            throw new RuntimeException(e);
         }

         // Set the selected ship.
         ShipSelectionScreen.this.setSelectedShip(ships.get(id));

         try
         {
            int cubes = 170 / 5;
            final int height = cubes;
            final int width = cubes * ships.get(shipLabel.id).getSize();
            final BufferedImage icon = ImageIO.read(new File("assets/ships/ship" + ships.get(id).getSize() + "_selected.png"));
            //final int height = (150 / (icon.getWidth() / icon.getHeight()));

            shipLabel.setIcon(new ImageIcon(new ImageIcon(icon).getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT)));
         } catch (IOException e)
         {
            throw new RuntimeException(e);
         }

         ShipSelectionScreen.this.setCurrentLabel(shipLabel);

         repaint();
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
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
         }

         // Get the new mouse coordinates and handle the click.
         Point point = e.getPoint();

         // Get coordinates based on clicked point.
         int[] coordinates = shipSelectionBoard.handleClick(point);

         // Add a null check before setting positions.
         if (coordinates != null)
         {
            if (!coordinatesHasShip(coordinates, selectedShip.isVertical(), selectedShip))
               selectedShip.setPosition(coordinates[0], coordinates[1]);
            else
            {
               JOptionPane.showMessageDialog(null, "¡Ya hay un barco ocupando esa posición!",
                       "Advertencia", JOptionPane.WARNING_MESSAGE);
               return;
            }

         }

         // Finally, update the board's content.
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

   /**
    * Checks if the ship is being set on top of another ship.
    *
    * @param coordinates New set of coordinates.
    * @param vertical    New rotation.
    * @param currentShip Current ship object to be moved.
    */
   public boolean coordinatesHasShip(int[] coordinates, boolean vertical, Ship currentShip)
   {
      if (vertical)
      {
         for (Ship ship : this.getShips())
         {
            if (ship != currentShip)
            {
               for (int i = 0; i < currentShip.getSize(); i++)
               {
                  int y = coordinates[1] + i;
                  boolean has = Arrays.stream(ship.getAsRawData()).anyMatch(c -> c != null && c[0] == coordinates[0] && c[1] == y);
                  if (has)
                  {
                     return true;
                  }
               }
            }
         }
      } else
      {
         for (Ship ship : this.getShips())
         {
            if (ship != currentShip)
            {
               for (int i = 0; i < currentShip.getSize(); i++)
               {
                  int x = coordinates[0] + i;
                  boolean has = Arrays.stream(ship.getAsRawData()).anyMatch(c -> c != null && c[0] == x && c[1] == coordinates[1]);
                  if (has)
                  {
                     return true;
                  }
               }
            }
         }
      }

      return false;
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
      repaint();
   }

   public void setCurrentLabel(ShipLabel selectedShip)
   {
      this.currentLabel = currentLabel;
   }

   public ShipLabel getCurrentLabel()
   {
      return currentLabel;
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
