package batnav.ui;

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
   private final Board board;
   private final Match match;

   public TestScreen()
   {
      this.setBounds(100, 100, 800, 800);
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
      this.setFocusable(false);
      this.setResizable(false);
      this.add(this.board = new Board(
           this.match = new Match("id", new User("", "", 0, 0, false))
      ));
      this.board.addActionListener(this);
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
         int[] coordinates = board.handleClick(point);
         this.match.getPlayerShips().get(0).setPosition(coordinates[0], coordinates[1], true);
      }
   }
}
