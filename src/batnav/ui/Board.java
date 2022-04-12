package batnav.ui;

import batnav.utils.Colour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Board extends JButton
{
   public Board()
   {
      // As the Board is a button, disabling the default values for styling is necessary.
      super.setBorderPainted(false);
      super.setFocusPainted(false);
      super.setContentAreaFilled(false);

      // Also, we set the same background as the board's.
      super.setBackground(Colour.AliceBlue);

      // Finally, add the action listener that handles clicking.
      this.addActionListener(new ClickListener());
   }

   @Override
   public void paint(Graphics g)
   {
      super.paint(g);

      // Set board position and size.
      final int tileSize = 50;
      final int boardSize = tileSize * 10;
      final int paddingX = (this.getWidth() - boardSize) / 2;
      final int paddingY = (this.getHeight() - boardSize) / 2;

      g.setColor(Colour.AliceBlue);
      g.fillRect(paddingX, paddingY, boardSize, boardSize);

      g.setColor(Colour.Black);
      for (int i = 0; i < 11; i++)
      {
         g.drawLine(paddingX + i * tileSize, paddingY, paddingX + i * tileSize, paddingY + boardSize);
         g.drawLine(paddingX, paddingY + i * tileSize, paddingX + boardSize, paddingY + i * tileSize);
      }
   }

   public void update()
   {
      this.repaint();
   }

   public void handleClick(int x, int y)
   {
      // Set board position and size.
      final int tileSize = 50;
      final int boardSize = tileSize * 10;
      final int paddingX = (this.getWidth() - boardSize) / 2;
      final int paddingY = (this.getHeight() - boardSize) / 2;

      // Check if the click was executed inside the board.
      if (x > paddingX && y > paddingY && x < paddingX + boardSize && y < paddingY + boardSize)
      {
         // Perform calculations to get coordinates.
         final int xPos = (x - paddingX) / 50;
         final int yPos = (y - paddingY) / 50;

         System.out.println(xPos + ":" + yPos);
      }
   }

   private class ClickListener implements ActionListener
   {
      @Override
      public void actionPerformed(ActionEvent e)
      {
         // Get the new mouse coordinates and handle the click
         Point point = Board.this.getMousePosition();

         if (point != null)
         {
            handleClick(point.x, point.y);
         }
      }
   }
}