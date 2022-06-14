package batnav.ui.screens;

import batnav.instance.Game;
import batnav.online.model.Ship;
import batnav.ui.boards.ShipSelectionBoard;
import batnav.ui.components.GameButton;
import batnav.ui.components.GamePanel;
import batnav.utils.Colour;
import batnav.utils.Fonts;
import com.google.common.collect.Lists;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
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
   protected List<Ship> ships;
   private Ship selectedShip;
   private final ShipSelectionBoard shipSelectionBoard;
   protected ShipLabel currentLabel;


   public ShipSelectionScreen() throws IOException
   {
      this.setSize(580, 520);
      this.setLayout(new BorderLayout());
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setResizable(false);
      this.setLocationRelativeTo(null);

      this.setTitle("Posicioná tus barcos — batnav");

      this.ships = Lists.newArrayList();
      this.shipSelectionBoard = new ShipSelectionBoard(this);
      this.setPreferredSize(new Dimension(380, 300));

      // Add ships.
      this.ships.add(new Ship(2));
      this.ships.add(new Ship(2));
      this.ships.add(new Ship(2));
      this.ships.add(new Ship(3));
      this.ships.add(new Ship(3));
      this.ships.add(new Ship(3));
      this.ships.add(new Ship(4));
      this.ships.add(new Ship(5));

      final GamePanel gamePanel = new GamePanel();
      JPanel southPanel = new JPanel();
      JPanel eastPanel = new JPanel();

      gamePanel.setAlternative(true);

      GameButton rotateButton = new GameButton("Rotar");
      rotateButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Colour.Black, Colour.Black));
      rotateButton.setMaximumSize(new Dimension(170, 30));
      rotateButton.setFont(Fonts.displayMedium);

      rotateButton.addActionListener(this);
      rotateButton.setActionCommand("rotateShip");

      JButton okButton = new JButton("Aceptar");
      okButton.setFont(Fonts.displayMedium);
      okButton.addActionListener(this);
      okButton.setActionCommand("setShips");

      eastPanel.setPreferredSize(new Dimension(200, 300));

      southPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
      southPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
      southPanel.add(okButton);

      JPanel eastPanelTitleContainer = new JPanel();
      eastPanelTitleContainer.setBackground(Colour.Transparent);
      eastPanelTitleContainer.setOpaque(false);
      eastPanelTitleContainer.setLayout(new BorderLayout(10, 10));
      eastPanelTitleContainer.setSize(170, 20);

      JLabel eastPanelTitle = new JLabel("<html></u>Barcos disponibles:</u></html>");
      eastPanelTitle.setFont(Fonts.displayRegular.deriveFont(Font.BOLD));
      eastPanelTitle.setHorizontalAlignment(JLabel.CENTER);
      eastPanelTitle.setHorizontalTextPosition(JLabel.CENTER);

      eastPanelTitleContainer.add(eastPanelTitle);

      eastPanel.add(Box.createVerticalStrut(10));
      eastPanel.add(eastPanelTitleContainer);
      eastPanel.add(Box.createVerticalStrut(10));

      for (int i = 0; i < ships.size(); i++)
      {
         ShipLabel jLabel = new ShipLabel(i, ships.get(i));
         eastPanel.add(jLabel);
         jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
         jLabel.addMouseListener(new MouseEvent());
         eastPanel.add(Box.createVerticalStrut(6));
      }
      eastPanel.add(Box.createVerticalStrut(10));

      eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
      eastPanel.setSize(300, 100);
      eastPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
      rotateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
      eastPanel.add(rotateButton);

      eastPanel.setOpaque(false);
      eastPanel.setBackground(Colour.Transparent);
      southPanel.setOpaque(false);
      southPanel.setBackground(Colour.Transparent);

      this.shipSelectionBoard.addMouseListener(new BoardMouseEvent());

      gamePanel.setLayout(new BorderLayout());
      gamePanel.add(this.shipSelectionBoard, BorderLayout.CENTER);
      gamePanel.add(southPanel, BorderLayout.SOUTH);
      gamePanel.add(eastPanel, BorderLayout.EAST);
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


   private static class ShipLabel extends JLabel
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

         final int width = cubes * ship.getSize();

         super.setIcon(new ImageIcon(new ImageIcon(icon).getImage().getScaledInstance(width, cubes, Image.SCALE_SMOOTH)));

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

         int cubes = 170 / 5;

         try
         {
            if (ShipSelectionScreen.this.getCurrentLabel() != null)
            {
               final int width = cubes * ships.get(getCurrentLabel().id).getSize();
               final BufferedImage icon = ImageIO.read(new File("assets/ships/ship" +
                    ships.get(ShipSelectionScreen.this.getCurrentLabel().getId()).getSize() + ".png"));

               getCurrentLabel().setIcon(new ImageIcon(new ImageIcon(icon).getImage().getScaledInstance(width, cubes, Image.SCALE_SMOOTH)));
            }
         } catch (IOException e)
         {
            throw new RuntimeException(e);
         }

         // Set the selected ship.
         ShipSelectionScreen.this.setSelectedShip(ships.get(id));

         try
         {
            final int width = cubes * ships.get(shipLabel.id).getSize();
            final BufferedImage icon = ImageIO.read(new File("assets/ships/ship" + ships.get(id).getSize() + "_selected.png"));

            shipLabel.setIcon(new ImageIcon(new ImageIcon(icon).getImage().getScaledInstance(width, cubes, Image.SCALE_SMOOTH)));
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
      this.currentLabel = selectedShip;
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
