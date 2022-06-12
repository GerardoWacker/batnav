package batnav.ui.screens;

import batnav.online.model.Ship;
import batnav.ui.boards.ShipSelectionBoard;
import com.google.common.collect.Lists;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ShipSelectionScreen extends JFrame implements ActionListener
{
   JPanel southPanel, centerPanel, eastPanel;
   JButton okButton, rotateButton, longButton;
   ShipSelectionBoard playerBoard;
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


      this.southPanel = new JPanel();
      this.eastPanel = new JPanel();
      this.centerPanel = new JPanel();

      this.rotateButton = new JButton("Rotar");
      this.playerBoard = new ShipSelectionBoard(this);
      this.setSize(580, 490);
      this.setLayout(new BorderLayout());
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setResizable(false);

      this.okButton = new JButton("Aceptar");

      playerBoard.setPreferredSize(new Dimension(380, 300));
      eastPanel.setPreferredSize(new Dimension(200, 300));
      this.southPanel.setBackground(Color.BLUE);
      this.eastPanel.setBackground(Color.red);
      this.add(southPanel, BorderLayout.SOUTH);


      this.add(eastPanel, BorderLayout.EAST);

      this.southPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
      this.southPanel.setBorder(new EmptyBorder(10,10,10,10));
      this.southPanel.add(okButton);

      for (int i = 0; i < ships.size(); i++)
      {
         ShipLabel jLabel = new ShipLabel(i, ships.get(i), eastPanel.getWidth());
         eastPanel.add(jLabel);
         jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
         //jLabel.addMouseListener(new MouseEvent());
      }


      this.eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
      this.eastPanel.setSize(300,100);
      this.eastPanel.setBorder(new EmptyBorder(10,10,10,10));
      rotateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
      this.eastPanel.add(rotateButton);

      this.centerPanel.setBackground(Color.pink);
      this.centerPanel.setLayout(new BorderLayout());
      this.centerPanel.add(playerBoard);
      this.setVisible(true);
   }


   @Override
   public void actionPerformed(ActionEvent e) {

   }

   public static void main(String[] args) throws IOException {
      new ShipSelectionScreen();
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
      private ShipLabel(int id, Ship ship, int panelWidth) throws IOException {
         int cubes = (int) (170 / 5);
         final BufferedImage icon = ImageIO.read(new File("assets/ships/ship" + ship.getSize() + ".png"));
         final int height = cubes;
         final int width = cubes * ship.getSize();
         super.setIcon(new ImageIcon(new ImageIcon(icon).getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT)));
         this.id = id;
         this.setOpaque(true);
         this.setBackground(Color.black);

      }

      public int getId()
      {
         return id;
      }
   }
}
