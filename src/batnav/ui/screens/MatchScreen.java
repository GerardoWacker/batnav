package batnav.ui.screens;

import batnav.instance.Game;
import batnav.online.match.Match;
import batnav.online.model.User;
import batnav.ui.boards.OpponentBoard;
import batnav.ui.boards.PlayerBoard;
import batnav.utils.Colour;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;

public class MatchScreen extends JFrame implements ActionListener
{
   private final Match match;

   private ShipSelectionScreen shipSelectionScreen;

   private OpponentBoard opponentBoard;
   private PlayerBoard playerBoard;
   private JPanel opponentInfo, playerInfo, middlePanel;
   private Icon playerIcon, opponentIcon, playerFlagIcon, opponentFlagIcon;
   private JLabel playerName, opponentName, counterLabel, opponentflagPlaceholder, playerFlagPlaceholder;
   private Timer timer;
   private int second;
   private DecimalFormat dFormat;
   private String ddSecond;
   private BufferedImage playerFlagTexture, opponentFlagTexture;


   public MatchScreen(final Match match)
   {
      final boolean isOffline = Game.getInstance().getSessionManager() == null;

      this.match = match;

      this.opponentBoard = new OpponentBoard(this.match);
      this.playerBoard = new PlayerBoard(this.match);

      this.playerBoard.setFilled(true);
      this.opponentBoard.setFilled(true);

      this.opponentInfo = new JPanel();
      this.playerInfo = new JPanel();

      Colour backgroundColour = new Colour(165, 189, 242);

      opponentInfo.setBackground(backgroundColour);
      playerInfo.setBackground(backgroundColour);
      opponentBoard.setBackground(backgroundColour);
      playerBoard.setBackground(backgroundColour);

      this.middlePanel = new JPanel();

      Font displayFont = Game.getInstance().getFontUtil().createFont("Roboto-Regular").deriveFont(Font.PLAIN, 14);

      Font largeFont = new Font("Roboto", Font.PLAIN, 20);

      this.opponentName = new JLabel(match.getOpponent().getUsername() + " (" + match.getOpponent().getElo() + ")");
      this.playerName = new JLabel(isOffline ? "Yo (1000)" : (Game.getInstance().getConnection().getCurrentUser().getUsername() + " (" + Game.getInstance().getConnection().getCurrentUser().getElo() + ")"));

      opponentName.setFont(displayFont);
      playerName.setFont(displayFont);

      this.opponentIcon = new ImageIcon("assets/textures/red_icon.png");
      this.playerIcon = new ImageIcon("assets/textures/green_icon.png");

      this.counterLabel = new JLabel();
      this.dFormat = new DecimalFormat("00");

      counterLabel.setFont(largeFont);

      this.opponentflagPlaceholder = new JLabel();
      this.playerFlagPlaceholder = new JLabel();

      try {
         this.playerFlagTexture = ImageIO.read(new URL("https://raw.githubusercontent.com/gosquared/flags/master/flags/flags-iso/flat/64/" + "AR" + ".png"));
         this.opponentFlagTexture = ImageIO.read(new URL("https://raw.githubusercontent.com/gosquared/flags/master/flags/flags-iso/flat/64/" + match.getOpponent().getCountry() + ".png"));
      } catch (IOException e) {
         e.printStackTrace();
      }

      Image playerFlagTextureScaled = playerFlagTexture.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
      Image opponentFlagTextureScaled = opponentFlagTexture.getScaledInstance(15, 15, Image.SCALE_SMOOTH);

      this.playerFlagIcon = new ImageIcon(playerFlagTextureScaled);
      this.opponentFlagIcon = new ImageIcon(opponentFlagTextureScaled);

      this.opponentBoard.setPreferredSize(new Dimension(380, 390));
      this.playerBoard.setPreferredSize(new Dimension(380, 390));

      this.setSize(350, 720);
      this.setLayout(new BorderLayout());
      this.setResizable(false);

      this.opponentInfo.setPreferredSize(new Dimension(350, 50));
      this.opponentInfo.setBorder(new EmptyBorder(10, 10, 10, 10) );
      this.playerInfo.setPreferredSize(new Dimension(350, 50));
      this.playerInfo.setBorder(new EmptyBorder(10, 10, 10, 10) );;

      this.opponentBoard.addMouseListener(new BoardMouseEvent());
      middlePanel.setLayout(new GridLayout(2, 1));
      middlePanel.add(opponentBoard);
      middlePanel.add(playerBoard);

      this.opponentName.setIcon(opponentIcon);
      this.opponentflagPlaceholder.setIcon(opponentFlagIcon);
      this.opponentInfo.setLayout(new BorderLayout(10,0));
      this.opponentInfo.add(opponentName, BorderLayout.WEST);
      this.opponentInfo.add(opponentflagPlaceholder);

      this.playerName.setIcon(playerIcon);
      this.playerFlagPlaceholder.setIcon(playerFlagIcon);
      this.playerInfo.setLayout(new BorderLayout(10,0));
      this.playerInfo.add(playerName, BorderLayout.WEST);
      this.playerInfo.add(playerFlagPlaceholder);
      this.playerInfo.add(counterLabel, BorderLayout.EAST);

      this.add(opponentInfo, BorderLayout.NORTH);
      this.add(playerInfo, BorderLayout.SOUTH);
      this.add(middlePanel, BorderLayout.CENTER);

      try
      {
         if (this.match.getPlayerShips().size() <= 0)
         {
            this.shipSelectionScreen = new ShipSelectionScreen();
         } else
         {
            this.setVisible(true);
         }
      } catch (IOException e)
      {
         e.printStackTrace();
      }
   }

   public void resetTimer()
   {
      this.ddSecond = "30";
      counterLabel.setText("00:" + ddSecond);
      this.second = 0;
      timer.restart();
   }

   public void startTimer()
   {
      this.ddSecond = "30";
      this.second = 0;

      if (this.timer != null)
      {
         counterLabel.setText("00:" + ddSecond);
         this.timer.restart();
         return;
      }

      this.timer = new Timer(1000, e -> {
         second++;
         ddSecond = dFormat.format(30 - second);
         counterLabel.setText("00:" + ddSecond);
         if (second == 30)
         {
            timer.stop();
         }
      });

      this.timer.start();
   }


   public static void main(String[] args)
   {
      MatchScreen matchScreen = new MatchScreen(new Match("asdfghjkl", new User("AAAAAAAAA", "AR", 0, 1000, false)));
      matchScreen.setVisible(true);
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

   public OpponentBoard getOpponentBoard()
   {
      return opponentBoard;
   }

   public PlayerBoard getPlayerBoard()
   {
      return playerBoard;
   }
}
