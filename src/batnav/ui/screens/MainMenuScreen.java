package batnav.ui.screens;

import batnav.ui.components.GameButton;
import batnav.ui.components.GamePanel;
import batnav.utils.Colour;
import batnav.utils.Fonts;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;

// class extends JFrame
public class MainMenuScreen extends JFrame implements ActionListener
{

   private JLabel findingMatchesLabel;
   private JLabel userIconLabel;
   private JButton previousMatchesButton, settingsButton, cancelMatchFindingButton;

   private GamePanel menuScreenPanel, matchmakingPanel;
   private JPanel mainPanel;
   private CardLayout cl;

   public MainMenuScreen()
   {
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);

      this.cl = new CardLayout();

      this.setSize(400, 600);
      this.setLocationRelativeTo(null);

      try
      {
         // Create main (parent) panel.
         this.mainPanel = new JPanel();
         this.mainPanel.setLayout(cl);

         // Main menu panel.
         this.menuScreenPanel = new GamePanel();
         menuScreenPanel.setAlternative(false);
         menuScreenPanel.setLayout(null);

         // Matchmaking panel
         this.matchmakingPanel = new GamePanel();
         matchmakingPanel.setAlternative(true);

         // Add child panels into main panel.
         mainPanel.add(menuScreenPanel, "1");
         mainPanel.add(matchmakingPanel, "2");

         // Begin with main menu panel.

         // Main title.
         final JLabel titleLabel = new JLabel("batnav", SwingConstants.CENTER);
         final BufferedImage titleIcon = ImageIO.read(new File("assets/ships/ship2.png"));
         titleLabel.setIcon(new ImageIcon(titleIcon.getScaledInstance(50, 25, Image.SCALE_SMOOTH)));
         titleLabel.setForeground(Colour.AliceBlue);
         titleLabel.setFont(Fonts.displayTitle.deriveFont(25f));
         titleLabel.setVerticalTextPosition(JLabel.BOTTOM);
         titleLabel.setHorizontalTextPosition(JLabel.CENTER);
         titleLabel.setBounds(0, 0, 400, 80);

         // Top panel.
         final JPanel topPanel = new JPanel();
         topPanel.setOpaque(false);
         topPanel.setBackground(Colour.Transparent);
         topPanel.setSize(300, 50);
         topPanel.setBounds(10, 90, 380, 50);
         topPanel.setLayout(null);

         // Top panel : User panel.
         final GamePanel userPanel = new GamePanel();
         userPanel.setAlternative(true);
         userPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Colour.Black, Colour.Black));
         userPanel.setBounds(0, 0, 300, 50);

         // Top panel : User panel : Icon.
         this.userIconLabel = new JLabel();
         final BufferedImage userIcon = ImageIO.read(new File("assets/textures/green_icon.png"));
         userIconLabel.setIcon(new ImageIcon(userIcon.getScaledInstance(40, 40, Image.SCALE_SMOOTH)));

         // Top panel : User panel : Meta panel.
         final JPanel metaPanel = new JPanel();
         metaPanel.setOpaque(false);
         metaPanel.setBackground(Colour.Transparent);
         metaPanel.setLayout(new GridLayout(2, 1));

         // Top panel : User panel : Meta panel : Username label.
         final JLabel userNameLabel = new JLabel("Usuario");
         userNameLabel.setFont(Fonts.displayRegular);

         // Top panel : User panel : Meta panel : Elo label.
         final JLabel eloUserLabel = new JLabel("1000");
         final BufferedImage cupIcon = ImageIO.read(new File("assets/textures/cup.png"));
         eloUserLabel.setIcon(new ImageIcon(cupIcon.getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
         eloUserLabel.setFont(Fonts.displayRegular.deriveFont(Font.BOLD));

         metaPanel.add(userNameLabel);
         metaPanel.add(eloUserLabel);

         userPanel.add(userIconLabel);
         userPanel.add(metaPanel);

         // Top panel: Settings button.
         final JPanel settingsPanel = new JPanel();
         settingsPanel.setLayout(null);
         settingsPanel.setBounds(310, 0, 50, 50);

         this.settingsButton = new GameButton();

         final BufferedImage settingsIcon = ImageIO.read(new File("assets/mainmenu/SettingsEngine.png"));
         final JLabel settingsIconLabel = new JLabel(new ImageIcon(settingsIcon));

         settingsButton.add(settingsIconLabel);
         settingsButton.setSize(50, 50);
         settingsButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Colour.Black, Colour.Black));
         settingsButton.addActionListener(e -> new SettingsScreen());

         settingsPanel.add(settingsButton);

         topPanel.add(userPanel);
         topPanel.add(settingsPanel);

         // Match panel.
         final JPanel matchPanel = new JPanel();
         matchPanel.setOpaque(false);
         matchPanel.setBackground(Colour.Transparent);
         matchPanel.setBounds(0, 150, 400, 400);
         matchPanel.setLayout(null);

         // Match panel : Ranked image panel.
         final JLabel rankedImage = new JLabel("Clasificatoria", SwingConstants.CENTER);
         rankedImage.setFont(Fonts.displayTitle.deriveFont(20f));
         rankedImage.setForeground(Colour.AliceBlue);

         rankedImage.setHorizontalTextPosition(JLabel.CENTER);
         rankedImage.setVerticalTextPosition(JLabel.TOP);

         final BufferedImage myRankedImage = ImageIO.read(new File("assets/textures/ranked.png"));
         rankedImage.setIcon(new ImageIcon(myRankedImage.getScaledInstance(250, 250, Image.SCALE_SMOOTH)));
         rankedImage.setBounds(0, 0, 400, 300);

         // Match panel : Find match button.
         final JPanel findMatchButtonContainer = new JPanel();
         findMatchButtonContainer.setBackground(Colour.Transparent);
         findMatchButtonContainer.setBounds(0, 300, 400, 110);
         findMatchButtonContainer.setLayout(null);

         final JButton findMatchButton = new GameButton("Buscar partida");
         findMatchButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Colour.Black, Colour.Black));
         findMatchButton.setFont(Fonts.displayMedium);
         findMatchButton.setBounds(120, 0, 160, 60);

         findMatchButton.addActionListener(e -> cl.show(mainPanel, "2"));

         findMatchButtonContainer.add(findMatchButton);

         matchPanel.add(rankedImage);
         matchPanel.add(findMatchButtonContainer);

         this.findingMatchesLabel = new JLabel("Buscando oponentes...", SwingConstants.CENTER);
         findingMatchesLabel.setFont(Fonts.displayMedium);
         findingMatchesLabel.setHorizontalTextPosition(JLabel.CENTER);

         // Begin with the "searching for match" screen.
         this.cancelMatchFindingButton = new GameButton("Cancelar");
         cancelMatchFindingButton.addActionListener(this);
         cancelMatchFindingButton.setActionCommand("Cancelar");

         this.previousMatchesButton = new JButton("Previas Partidas");
         previousMatchesButton.setFont(new Font("San Francisco Display", Font.PLAIN, 18));

         this.menuScreenPanel.add(titleLabel);
         this.menuScreenPanel.add(topPanel);
         this.menuScreenPanel.add(matchPanel);

         this.matchmakingPanel.add(findingMatchesLabel);
         this.matchmakingPanel.add(cancelMatchFindingButton);

      } catch (Exception e)
      {
         throw new RuntimeException(e);
      }

      this.add(mainPanel);
      cl.show(mainPanel, "1");

      this.setResizable(false);
      this.setVisible(true);
   }

   public static void main(String[] args)
   {

      {
         new MainMenuScreen();
      }
   }

   @Override
   public void actionPerformed(ActionEvent e)
   {
   }
}
