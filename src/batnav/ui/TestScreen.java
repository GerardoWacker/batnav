package batnav.ui;

import batnav.online.match.Bomb;
import batnav.online.match.Match;
import batnav.online.match.Ship;
import batnav.online.session.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class TestScreen extends JFrame implements ActionListener
{
   private final PlayerBoard playerBoard;
   private final OpponentBoard opponentBoard;
   private final Match match;

   public TestScreen()
   {
      this.match = new Match("", new User("tu vieja", "AR", 0, 0, false));
      this.setBounds(100, 100, 450, 900);
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
      this.setFocusable(false);
      this.setResizable(false);
      this.add(this.playerBoard = new PlayerBoard(this.match), BorderLayout.NORTH);
      this.add(this.opponentBoard = new OpponentBoard(this.match), BorderLayout.SOUTH);

      this.opponentBoard.addActionListener(this);

      this.setLayout(new GridLayout(2, 1));
      this.setVisible(true);
   }

   @Override
   public void actionPerformed(ActionEvent e)
   {
      // Get the new mouse coordinates and handle the click
      Point point = TestScreen.this.getMousePosition();

      if (point != null)
      {
         // Get coordinates based on clicked point.
         int[] coordinates = opponentBoard.handleClick(point);

         // Add a null check before setting positions.
         if (coordinates != null)
            this.match.addPlayerBomb(new Bomb(coordinates[0], coordinates[1], false, false));

         this.playerBoard.update();
         this.opponentBoard.update();
      }
   }
}
