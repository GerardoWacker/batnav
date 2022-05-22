package batnav.ui.screens;

import batnav.utils.Colour;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class SplashScreen extends JFrame implements ActionListener
{
   private final JLabel logoContainerLabel;
   private final JLabel splashText;


   public SplashScreen()
   {

      super("batnav");
      this.setType(Type.UTILITY);
      this.setSize(300, 400);
      this.setLocationRelativeTo(null);
      this.setAlwaysOnTop(true);
      //this.setUndecorated(true);
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
      this.setResizable(false);

      //JPanel contentPane = (JPanel) this.getContentPane();
      //contentPane.setBackground(Colour.AliceBlue);
      this.setLayout(new GridLayout(3,1));
      this.logoContainerLabel = new JLabel("batnav", SwingConstants.CENTER);
      this.logoContainerLabel.setFont(new Font("San Francisco Display", Font.BOLD, 25));
      this.logoContainerLabel.setHorizontalTextPosition(JLabel.CENTER);
      this.splashText = new JLabel("Iniciando cliente");
      this.splashText.setFont(new Font("San Francisco Display", Font.PLAIN, 15));
      this.splashText.setHorizontalAlignment(JLabel.CENTER);

      JLabel loaderLabel = new JLabel();

      try {
         Image logo = ImageIO.read(new File("assets/textures/loadingLogo.png"));
         Image logoScaled = logo.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
         loaderLabel.setIcon(new ImageIcon(logoScaled));
      } catch (IOException e) {
         throw new RuntimeException(e);
      }

      loaderLabel.setHorizontalAlignment(JLabel.CENTER);
      this.add(this.logoContainerLabel, BorderLayout.CENTER);
      this.add(loaderLabel, BorderLayout.CENTER);
      this.add(this.splashText);
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

   public static void main(String[] args) {
      SplashScreen mysplashscreen = new SplashScreen();
   }
}
