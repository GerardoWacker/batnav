package batnav.ui.screens;

import batnav.online.model.User;
import com.sun.tools.javac.Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import javax.imageio.ImageIO;
import javax.swing.*;

// class extends JFrame
public class MainMenuScreen extends JFrame implements ActionListener {

   private JLabel FindMatchButtonc;
   private JPanel settingsPanel;
   private JLabel batNavTextLabel;
   private JButton findMatchButton;
   private JLabel idUserLabel;
   private JLabel eloUserLabel;
   private JPanel userImageLabel;
   private JLabel rankedImage;
   private JButton previousMatchesButton;
   private JButton settingsButton;
   private JPanel mainPanel;
   private JPanel menuScreenPanel;
   private JPanel loadingToPlayPanel;
 //  private JButton gerardoWackerButton;
  // private JButton juanIgnacioVecchioButton;
   //private JButton matiasMenaDaDaltButton;
   private JLabel alert;
   private JButton cancelButton;
   private CardLayout cl;

   public MainMenuScreen() {
      this.cl = new CardLayout();
      this.setSize(500,725);
      this.setLocationRelativeTo(null);

      this.menuScreenPanel = new JPanel();
      this.loadingToPlayPanel = new JPanel();
      this.mainPanel = new JPanel();
      this.mainPanel.setLayout(cl);
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
      this.add(mainPanel);
      mainPanel.add(menuScreenPanel,"1");
      mainPanel.add(loadingToPlayPanel, "2");
      cl.show(mainPanel,"1");
      menuScreenPanel.setLayout(null);

      this.cancelButton = new JButton("Cancel");
      cancelButton.setBounds(400, 400, 100, 100);
      cancelButton.setFont(new Font("San Francisco Display", Font.PLAIN, 20));
      cancelButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            new MainMenuScreen();
         }
      });

      this.cancelButton = new JButton("Cancelar");
      cancelButton.setBounds(50,300,165,25);
      cancelButton.addActionListener(this);
      cancelButton.setActionCommand("Cancelar");

      this.FindMatchButtonc = new JLabel("Encontrando partida . . .", SwingConstants.CENTER);
      FindMatchButtonc.setFont(new Font("San Francisco Display", Font.BOLD, 20));
      FindMatchButtonc.setHorizontalTextPosition(JLabel.CENTER);
      FindMatchButtonc.setBounds(50, 200, 250, 100);

      this.batNavTextLabel = new JLabel("batnav", SwingConstants.CENTER);
      batNavTextLabel.setFont(new Font("San Francisco Display", Font.BOLD, 20));
      batNavTextLabel.setHorizontalTextPosition(JLabel.CENTER);
      batNavTextLabel.setBounds(50, 10, 375, 40);

      this.settingsButton = new JButton("Settings");
      settingsButton.setBounds(445, 10, 55, 40);
      settingsButton.addActionListener(new ActionListener() {


         @Override
         public void actionPerformed(ActionEvent e) {
         new SettingsScreen();

         }
      });
      BufferedImage mySettingsImage;

      this.idUserLabel = new JLabel("AAA");
      idUserLabel.setBounds(85,50,425,25);
      idUserLabel.setFont(new Font("San Francisco Display", Font.TRUETYPE_FONT, 16));

      this.userImageLabel = new JPanel();
      userImageLabel.setBounds(0,50,75,75);
      BufferedImage myUserImage;

      this.eloUserLabel = new JLabel("4500");
      eloUserLabel.setFont(new Font("San Francisco Display", Font.ITALIC, 16));
      eloUserLabel.setBounds(85,75,425,25);

      this.rankedImage = new JLabel();
      rankedImage.setBounds(0,150,500,400);
      BufferedImage myRankedImage;

      try {
         mySettingsImage = ImageIO.read(new File("assets/mainmenu/SettingsEngine.png"));
         myUserImage = ImageIO.read(new File("assets/mainmenu/imagesOfUsers/Sth.png"));
         myRankedImage = ImageIO.read(new File("assets/mainmenu/ranked.png"));
      } catch (IOException e) {
         throw new RuntimeException(e);
      }

      JLabel SettingsImage_ = new JLabel(new ImageIcon(mySettingsImage));
      settingsButton.add(SettingsImage_);

      JLabel UserImage_ = new JLabel(new ImageIcon(myUserImage));
      userImageLabel.add(UserImage_);

      JLabel RankedImage_ = new JLabel(new ImageIcon(myRankedImage));
      rankedImage.add(RankedImage_);

      this.FindMatchButtonc = new JLabel("Encontrando partida . . .", SwingConstants.CENTER);
      FindMatchButtonc.setFont(new Font("San Francisco Display", Font.BOLD, 20));
      FindMatchButtonc.setHorizontalTextPosition(JLabel.CENTER);
      FindMatchButtonc.setBounds(50, 200, 250, 100);

      this.findMatchButton = new JButton("Buscar Partida");
      findMatchButton.setBounds(0, 525, 500, 75);
      findMatchButton.setFont(new Font("San Francisco Display", Font.PLAIN, 16));
      findMatchButton.addActionListener(this);
      findMatchButton.setActionCommand("Finding Matches");
      this.alert = new JLabel("Trying to find a match");
      alert.setOpaque(true);
      alert.setForeground(Color.white);
      alert.setBackground(Color.CYAN);
      alert.setBounds(50, 350, 200, 25);
      findMatchButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            loadingToPlayPanel.add(FindMatchButtonc);
            loadingToPlayPanel.add(cancelButton);
            cl.show(loadingToPlayPanel, "2");
            //problema del actionListener o el Performed

         }
      });

      this.previousMatchesButton = new JButton("Previas Partidas");
      previousMatchesButton.setBounds(0,600, 500, 100);
      previousMatchesButton.setFont(new Font("San Francisco Display", Font.PLAIN, 18));

    /*  this.gerardoWackerButton = new JButton("Gerardo Wacker");
      gerardoWackerButton.setFont(new Font("San Francisco Display", Font.ITALIC, 10));
      gerardoWackerButton.setBounds(15,  705, 150, 50);
      gerardoWackerButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            try {
               Desktop.getDesktop().browse(URI.create("https://github.com/GerardoWacker"));
            } catch (IOException ex) {
               throw new RuntimeException(ex);
            }
         }
      });

      this.juanIgnacioVecchioButton = new JButton("Juan Ignacio Vecchio");
      juanIgnacioVecchioButton.setFont(new Font("San Francisco Display", Font.ITALIC, 10));
      juanIgnacioVecchioButton.setBounds(175, 705, 150, 50);
      juanIgnacioVecchioButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            try {
               Desktop.getDesktop().browse(URI.create("https://github.com/juanvecchio"));
            } catch (IOException ex) {
               throw new RuntimeException(ex);
            }
         }
      });

      this.matiasMenaDaDaltButton = new JButton("Matias Mena Da Dalt");
      matiasMenaDaDaltButton.setFont(new Font("San Francisco Display", Font.ITALIC, 10));
      matiasMenaDaDaltButton.setBounds(335, 705, 150, 50);
      matiasMenaDaDaltButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            try {
               Desktop.getDesktop().browse(URI.create("https://github.com/Matias0-git"));
            } catch (IOException ex) {
               throw new RuntimeException(ex);
            }
         }
      });
*/
      this.menuScreenPanel.add(this.batNavTextLabel);
      this.menuScreenPanel.add(this.settingsButton);
      this.menuScreenPanel.add(this.userImageLabel);
      this.menuScreenPanel.add(this.idUserLabel);
      this.menuScreenPanel.add(this.eloUserLabel);
      this.menuScreenPanel.add(this.rankedImage);
      this.menuScreenPanel.add(this.findMatchButton);
      this.menuScreenPanel.add(this.previousMatchesButton);
/*
      this.loadingToPlayPanel.add(this.cancelButton);
      this.loadingToPlayPanel.add(this.alert);
*/
      this.setResizable(false);
      this.setVisible(true);
   }
   public static void main (String[]args){

      {
         new MainMenuScreen();
      }
   }
   @Override
   public void actionPerformed(ActionEvent e) {
   }
}
