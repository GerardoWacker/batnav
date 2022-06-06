package batnav.ui.screens;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ResultsScreen extends JFrame
{
   private final boolean victory;
   private final int elo;

   public ResultsScreen(final boolean victory, final int elo)
   {
      this.victory = victory;
      this.elo = elo;

      this.setLocationRelativeTo(null);
      this.setResizable(false);

      this.setSize(350, 150);
      this.setLayout(new GridLayout(4, 1));

      try
      {
         final JLabel iconLabel = new JLabel();
         final Image icon = ImageIO.read(new File("assets/textures/" + (victory ? "green" : "red") + "_icon.png"));
         icon.getScaledInstance(100, 100, Image.SCALE_SMOOTH);

         iconLabel.setIcon(new ImageIcon(icon));
         iconLabel.setHorizontalAlignment(SwingConstants.CENTER);

         final JLabel title = new JLabel(victory ? "¡Has ganado!" : "¡Has perdido!");
         title.setFont(new Font("San Francisco Display", Font.BOLD, 25));
         title.setHorizontalAlignment(SwingConstants.CENTER);

         final JLabel difference = new JLabel();
         // TODO: Set icon to :cup:
         difference.setText((victory ? "+" : "-") + elo);
         difference.setHorizontalAlignment(SwingConstants.CENTER);

         final JButton returnButton = new JButton("Volver al menú principal");

         this.add(iconLabel);
         this.add(title);
         this.add(difference);
         this.add(returnButton);
      } catch (Exception e)
      {
         e.printStackTrace();
      }

      this.setVisible(true);
   }

   public static void main(String[] args)
   {
      new ResultsScreen(true, 30);
   }
}
