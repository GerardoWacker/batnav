package batnav.ui.screens;

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

      this.setLocationRelativeTo(null);
      this.setResizable(false);

      final GamePanel container = new GamePanel();
      container.setAlternative(true);

      container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

      this.setSize(350, 150);
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
         // TODO: Set icon to :cup:
         difference.setText((victory ? "+" : "-") + elo);
         difference.setFont(Fonts.displayRegular);
         difference.setHorizontalAlignment(SwingConstants.CENTER);

         final JButton matchInfoButton = new JButton("Ver más información sobre la partida");
         matchInfoButton.setBorder(BorderFactory.createEmptyBorder());
         matchInfoButton.setBackground(Colour.Transparent);
         matchInfoButton.setHorizontalAlignment(SwingConstants.CENTER);
         matchInfoButton.addActionListener(this);
         matchInfoButton.setActionCommand("matchInfo");

         final JButton returnButton = new JButton("Volver al menú principal");
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
      }
   }
}
