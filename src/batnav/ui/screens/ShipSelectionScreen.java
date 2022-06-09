package batnav.ui.screens;

import batnav.online.model.Ship;
import batnav.ui.boards.ShipSelectionBoard;
import com.google.common.collect.Lists;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ShipSelectionScreen extends JFrame implements ActionListener
{
   JPanel southPanel, centerPanel, eastPanel;
   JButton okButton, rotateButton, longButton;
   ShipSelectionBoard playerBoard;
   /**
   protected List<Ship> ships;
   private Ship selectedShip;
   private final ShipSelectionBoard shipSelectionBoard;
    */


   public ShipSelectionScreen() throws IOException
   {
      /**
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
       */

      this.southPanel = new JPanel();
      this.centerPanel = new JPanel();
      this.eastPanel = new JPanel();
      this.okButton = new JButton("Aceptar");
      this.rotateButton = new JButton("Rotar");
      this.longButton = new JButton("Very ver ver long button");
      this.playerBoard = new ShipSelectionBoard(this);
      this.setSize(580, 490);
      this.setLayout(new BorderLayout());
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setResizable(false);

      this.southPanel.setBackground(Color.BLUE);
      this.centerPanel.setBackground(Color.YELLOW);
      this.eastPanel.setBackground(Color.red);
      this.add(southPanel, BorderLayout.SOUTH);
      this.add(centerPanel, BorderLayout.CENTER);
      this.add(eastPanel, BorderLayout.EAST);

      this.southPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
      this.southPanel.setBorder(new EmptyBorder(10,10,10,10));
      this.southPanel.add(okButton);
      this.setVisible(true);

      this.eastPanel.setLayout(new GridLayout(9, 1));

      this.eastPanel.setSize(300,100);
      this.eastPanel.setBorder(new EmptyBorder(10,10,10,10));

      this.centerPanel.setLayout(new GridLayout(1,1));
      this.centerPanel.add(playerBoard);
   }


   @Override
   public void actionPerformed(ActionEvent e) {

   }

   public static void main(String[] args) throws IOException {
      new ShipSelectionScreen();
   }
}
