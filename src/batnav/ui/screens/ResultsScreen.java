package batnav.ui.screens;

import batnav.instance.Game;
import batnav.ui.components.GameButton;
import batnav.ui.components.GamePanel;
import batnav.utils.Colour;
import batnav.utils.Fonts;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ResultsScreen extends JFrame implements ActionListener
{
   private final boolean victory;
   private final int elo;
   private final String matchId;

   public ResultsScreen(final boolean victory, final int elo, final String matchId)
   {
      this.victory = victory;
      this.elo = elo;
      this.matchId = matchId;

      this.setTitle("Resultados del juego");
      this.setLocationRelativeTo(null);
      this.setResizable(false);

      final GamePanel container = new GamePanel();
      container.setAlternative(true);

      container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

      this.setSize(350, 180);
      container.setLayout(new GridLayout(4, 1));

      try
      {
         final JLabel iconLabel = new JLabel();
         final Image icon = ImageIO.read(new File("assets/textures/" + (victory ? "green" : "red") + "_icon.png"));
         icon.getScaledInstance(100, 100, Image.SCALE_SMOOTH);

         iconLabel.setIcon(new ImageIcon(icon));
         iconLabel.setHorizontalAlignment(SwingConstants.CENTER);

         final JLabel title = new JLabel(victory ? "¡Has ganado!" : "¡Has perdido!");
         title.setFont(Fonts.displayTitle);
         title.setHorizontalAlignment(SwingConstants.CENTER);

         final JLabel difference = new JLabel();
         Image image = ImageIO.read(new File("assets/textures/cup.png"));
         Image imageScaled = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
         difference.setIcon(new ImageIcon(imageScaled));
         difference.setText((victory ? "+" : "-") + elo);
         difference.setFont(Fonts.displayRegular);
         difference.setHorizontalAlignment(SwingConstants.CENTER);

         final GameButton matchInfoButton = new GameButton("Ver más información sobre la partida");
         matchInfoButton.setAlternative(true);
         matchInfoButton.setBorder(BorderFactory.createEmptyBorder());
         matchInfoButton.setBackground(Colour.Transparent);
         matchInfoButton.setHorizontalAlignment(SwingConstants.CENTER);
         matchInfoButton.addActionListener(this);
         matchInfoButton.setActionCommand("matchInfo");

         final GameButton returnButton = new GameButton("Volver al menú principal");
         returnButton.addActionListener(this);
         returnButton.setActionCommand("return");

         container.add(iconLabel);
         container.add(title);
         container.add(difference);
         container.add(returnButton);
      } catch (Exception e)
      {
         e.printStackTrace();
      }

      this.add(container);

      this.setVisible(true);
   }

   public static void main(String[] args)
   {
      new ResultsScreen(true, 30, "asdfghjkl");
   }

   @Override
   public void actionPerformed(ActionEvent e)
   {
      switch (e.getActionCommand())
      {
         case "matchInfo":
            // TODO: Implement MatchReviewScreen.
            break;
         case "return":
            this.setVisible(false);
            Game.getInstance().getMainMenuScreen().setVisible(true);
      }
   }
}
