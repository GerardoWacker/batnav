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
import java.sql.PseudoColumnUsage;
import java.text.DecimalFormat;

public class MatchScreen extends JFrame implements ActionListener
{
   private final Match match;

   private ShipSelectionScreen shipSelectionScreen;

   private OpponentBoard opponentBoard;
   private PlayerBoard playerBoard;
   private JPanel opponentInfo;
   private JPanel playerInfo;
   private JPanel middlePanel;
   private Icon opponentIcon;
   private Icon playerIcon;
   private JLabel opponentName;
   private JLabel playerName;
   private JLabel counterLabel;
   private Timer timer;
   private int second;
   private DecimalFormat dFormat;
   private String ddSecond;

   public MatchScreen(final Match match)
   {
      this.match = match;
      this.opponentBoard = new OpponentBoard(this.match);
      this.playerBoard = new PlayerBoard(this.match);
      this.opponentInfo = new JPanel();
      this.playerInfo = new JPanel();
      this.middlePanel = new JPanel();
      this.opponentName = new JLabel(match.getOpponent().getUsername());
      this.playerName = new JLabel("Yo");
      this.opponentIcon = new ImageIcon("assets/textures/red_icon.png");
      this.playerIcon = new ImageIcon("assets/textures/green_icon.png");
      this.counterLabel = new JLabel();
      this.dFormat = new DecimalFormat("00");

      this.opponentBoard.setPreferredSize(new Dimension(380, 390));
      this.playerBoard.setPreferredSize(new Dimension(380, 390));

      this.setSize(350, 720);
      this.setLayout(new BorderLayout());
      this.setResizable(false);
      this.setVisible(true);






      this.opponentInfo.setPreferredSize(new Dimension(350,50));
      this.playerInfo.setPreferredSize(new Dimension(350,50));

      this.opponentBoard.addMouseListener(new BoardMouseEvent());
      middlePanel.setLayout(new GridLayout(2,1));
      middlePanel.add(opponentBoard);
      middlePanel.add(playerBoard);

      this.opponentName.setIcon(opponentIcon);
      this.opponentInfo.setLayout(new BorderLayout());
      this.opponentInfo.add(opponentName, BorderLayout.WEST);

      this.playerName.setIcon(playerIcon);
      this.playerInfo.setLayout(new BorderLayout());
      this.playerInfo.add(playerName, BorderLayout.WEST);
      this.playerInfo.add(counterLabel, BorderLayout.EAST);
      this.startTimer();
      this.timer.start();

      this.add(opponentInfo, BorderLayout.NORTH);
      this.add(playerInfo, BorderLayout.SOUTH);
      this.add(middlePanel, BorderLayout.CENTER);
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

   private void startTimer() {
      timer = new Timer(1000, new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            second++;
            ddSecond = dFormat.format(30 - second);
            counterLabel.setText("00:" + ddSecond+"   ");
            if(second == 30){
               timer.stop();
            }
         }
      });
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
