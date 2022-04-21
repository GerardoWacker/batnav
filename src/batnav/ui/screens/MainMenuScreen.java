package batnav.ui.screens;

import batnav.utils.Colour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuScreen extends JFrame implements ActionListener
{
   private final JLabel logoContainerLabel;
   private final JLabel splashText;

   public MainMenuScreen()
   {
      super("batnav");

      this.setSize(500, 600);

      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
      this.setResizable(false);

      JPanel contentPane = (JPanel) this.getContentPane();
      contentPane.setBackground(Colour.AliceBlue);

      this.logoContainerLabel = new JLabel("batnav", SwingConstants.CENTER);
      this.logoContainerLabel.setFont(new Font("San Francisco Display", Font.BOLD, 25));

      this.splashText = new JLabel("Iniciando cliente");
      this.splashText.setFont(new Font("San Francisco Display", Font.PLAIN, 14));

      contentPane.add(logoContainerLabel);
      contentPane.add(splashText);

      this.setVisible(true);
   }

   @Override
   public void actionPerformed(ActionEvent e)
   {

   }

   public void displayFailure(String reason)
   {
      this.logoContainerLabel.setText(":(");
      this.splashText.setText(reason);
   }

   public void setDisplayString(String displayString)
   {
      this.splashText.setText(displayString);
      this.repaint();
   }
}
