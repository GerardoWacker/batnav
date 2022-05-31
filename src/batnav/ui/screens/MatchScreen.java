package batnav.ui.screens;

import batnav.instance.Game;
import batnav.online.match.Match;
import batnav.online.model.User;
import batnav.ui.boards.OpponentBoard;
import batnav.ui.boards.PlayerBoard;
import batnav.utils.Colour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class MatchScreen extends JFrame implements ActionListener
{
   private final Match match;

   private ShipSelectionScreen shipSelectionScreen;

   private OpponentBoard opponentBoard;
   private PlayerBoard playerBoard;

   public MatchScreen(final Match match)
   {
      this.match = match;

      this.opponentBoard = new OpponentBoard(this.match);
      this.playerBoard = new PlayerBoard(this.match);

      this.opponentBoard.setPreferredSize(new Dimension(380, 390));
      this.playerBoard.setPreferredSize(new Dimension(380, 390));

      this.setSize(400, 820);
      this.setLayout(new BorderLayout());
      this.setResizable(false);

      JPanel divisionPanel = new JPanel();
      divisionPanel.setSize(new Dimension(400, 20));
      divisionPanel.setBackground(Colour.Gray);

      this.opponentBoard.addMouseListener(new BoardMouseEvent());

      this.add(opponentBoard, BorderLayout.NORTH);
      this.add(divisionPanel, BorderLayout.CENTER);
      this.add(playerBoard, BorderLayout.SOUTH);

      try
      {
         if (this.match.getPlayerShips().size() <= 0)
            this.shipSelectionScreen = new ShipSelectionScreen();
      } catch (IOException e)
      {
         e.printStackTrace();
      }

      this.setVisible(true);
   }

   public static void main(String[] args)
   {
      new MatchScreen(new Match("asdfghjkl", new User("tu vieja", "AR", 0, 0, false)));
   }

   @Override
   public void actionPerformed(ActionEvent e)
   {

   }

   public void setOpponentReady()
   {
      System.out.println("El oponente está listo");
   }

   public void setPlayerReady()
   {
      this.shipSelectionScreen.setVisible(false);
      this.repaint();
   }

   public class BoardMouseEvent implements MouseListener
   {

      @Override
      public void mouseClicked(MouseEvent e)
      {
         // Get the new mouse coordinates and handle the click.
         Point point = e.getPoint();

         // Get coordinates based on clicked point.
         int[] coordinates = opponentBoard.handleClick(point);
         if (coordinates != null)
         {
            Game.getInstance().getMatchManager().throwBomb(
                 Game.getInstance().getConnection(),
                 coordinates[0],
                 coordinates[1]
            );
            System.out.println("Tirada bomba en " + coordinates);
         }
      }

      @Override
      public void mousePressed(MouseEvent e)
      {

      }

      @Override
      public void mouseReleased(MouseEvent e)
      {

      }

      @Override
      public void mouseEntered(MouseEvent e)
      {

      }

      @Override
      public void mouseExited(MouseEvent e)
      {

      }
   }
}
