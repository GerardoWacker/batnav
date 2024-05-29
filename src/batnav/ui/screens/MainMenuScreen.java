package batnav.ui.screens;

import batnav.instance.Game;
import batnav.ui.components.GameButton;
import batnav.ui.components.GamePanel;
import batnav.utils.Colour;
import batnav.utils.Fonts;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;

// class extends JFrame
public class MainMenuScreen extends JFrame implements ActionListener
{

   private GamePanel menuScreenPanel, matchmakingPanel;
   private JPanel mainPanel;
   private CardLayout cl;

   public MainMenuScreen()
   {
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);

      this.setTitle("batnav");

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
         matchmakingPanel.setAlternative(false);
         matchmakingPanel.setLayout(null);

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
         JLabel userIconLabel = new JLabel();
         final BufferedImage userIcon = ImageIO.read(new File("assets/textures/green_icon.png"));
         userIconLabel.setIcon(new ImageIcon(userIcon.getScaledInstance(40, 40, Image.SCALE_SMOOTH)));

         // Top panel : User panel : Meta panel.
         final JPanel metaPanel = new JPanel();
         metaPanel.setOpaque(false);
         metaPanel.setBackground(Colour.Transparent);
         metaPanel.setLayout(new GridLayout(2, 1));

         // Top panel : User panel : Meta panel : Username label.
         final JLabel userNameLabel = new JLabel(Game.getInstance().getConnection().getCurrentUser().getUsername());
         userNameLabel.setFont(Fonts.displayRegular);

         // Top panel : User panel : Meta panel : Elo label.
         final JLabel eloUserLabel = new JLabel(String.valueOf(Game.getInstance().getConnection().getCurrentUser().getElo()));
         final BufferedImage cupIcon = ImageIO.read(new File("assets/textures/cup.png"));
         eloUserLabel.setIcon(new ImageIcon(cupIcon.getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
         eloUserLabel.setFont(Fonts.displayRegular.deriveFont(Font.BOLD));

         BufferedImage playerFlagTexture;

         // Top panel : User panel: User flag.
         try {
            playerFlagTexture = ImageIO.read(new URL("https://raw.githubusercontent.com/gosquared/flags/master/flags/flags-iso/flat/64/" + Game.getInstance().getConnection().getCurrentUser().getCountry() + ".png"));
         } catch (Exception e) {
            playerFlagTexture = ImageIO.read(new URL("https://raw.githubusercontent.com/gosquared/flags/master/flags/flags-iso/flat/64/AR.png"));
         }
         final JLabel userFlag = new JLabel();
         userFlag.setIcon(new ImageIcon(playerFlagTexture.getScaledInstance(25, 25, Image.SCALE_SMOOTH)));

         metaPanel.add(userNameLabel);
         metaPanel.add(eloUserLabel);

         userPanel.add(userIconLabel);
         userPanel.add(metaPanel);
         userPanel.add(userFlag);

         // Top panel: Settings button.
         final JPanel settingsPanel = new JPanel();
         settingsPanel.setLayout(null);
         settingsPanel.setBounds(310, 0, 50, 50);

         GameButton settingsButton = new GameButton();

         final BufferedImage settingsIcon = ImageIO.read(new File("assets/textures/settings.png"));
         final JLabel settingsIconLabel = new JLabel("", SwingConstants.CENTER);
         settingsIconLabel.setLayout(null);
         settingsIconLabel.setIcon(new ImageIcon(settingsIcon.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
         settingsIconLabel.setSize(50, 50);

         settingsButton.add(settingsIconLabel);
         settingsButton.setHorizontalTextPosition(JLabel.CENTER);
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
         rankedImage.setIcon(new ImageIcon(myRankedImage.getScaledInstance(250, 208, Image.SCALE_SMOOTH)));
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

         findMatchButton.addActionListener(e -> this.startMatchmaking());

         findMatchButtonContainer.add(findMatchButton);

         matchPanel.add(rankedImage);
         matchPanel.add(findMatchButtonContainer);

         // Begin with the "searching for match" screen.
         final JLabel findingMatchesLabel = new JLabel("Buscando oponentes...", SwingConstants.CENTER);
         findingMatchesLabel.setFont(Fonts.displayTitle);
         findingMatchesLabel.setForeground(Colour.AliceBlue);
         findingMatchesLabel.setVerticalTextPosition(JLabel.TOP);
         findingMatchesLabel.setHorizontalTextPosition(JLabel.CENTER);
         findingMatchesLabel.setIcon(new ImageIcon(myRankedImage.getScaledInstance(200, 166, Image.SCALE_SMOOTH)));
         findingMatchesLabel.setBounds(0, 0, 400, 300);

         final GameButton cancelMatchFindingButton = new GameButton("Cancelar");
         cancelMatchFindingButton.setCancel(true);
         cancelMatchFindingButton.addActionListener(this);
         cancelMatchFindingButton.setIconTextGap(30);
         cancelMatchFindingButton.setActionCommand("Cancelar");
         cancelMatchFindingButton.setFont(Fonts.displayMedium);
         cancelMatchFindingButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Colour.Black, Colour.Black));
         cancelMatchFindingButton.setBounds(120, 350, 160, 60);
         cancelMatchFindingButton.addActionListener(e -> {
            Game.getInstance().getMatchManager().leaveRankedQueue(Game.getInstance().getConnection());
            cl.show(mainPanel, "1");
         });

         final String[] advices = new String[]{
              "En las partidas clasificatorias, tanto vos como tu<br>oponente tendrán 8 barcos.",
              "Cada turno dura 45 segundos,<br>¡no malgastes el tiempo!",
              "El ELO (u copas) que ganes dependerá de tu<br>puntaje y el de tu oponente.",
              "¡Demostrá que sos el mejor jugador de batalla<br>naval jugando partidas clasificatorias!",
              "Si encontrás un bug, notificanos a través del link<br>en Configuración.",
              "En total, debés tirar 24 bombas como mínimo<br>para poder ganar.",
              "Si te desconectás durante una partida, se<br>tomará como una derrota."
         };

         final JButton adviceButton = new JButton();
         adviceButton.setText("<html>" + advices[(int) Math.floor(Math.random() * (7))] + "</html>");
         adviceButton.setBounds(50, 430, 300, 80);
         adviceButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Colour.Black, Colour.Black));
         adviceButton.addActionListener(e -> adviceButton.setText("<html>" + advices[(int) Math.floor(Math.random() * (7))] + "</html>"));

         this.menuScreenPanel.add(titleLabel);
         this.menuScreenPanel.add(topPanel);
         this.menuScreenPanel.add(matchPanel);

         this.matchmakingPanel.add(findingMatchesLabel);
         this.matchmakingPanel.add(cancelMatchFindingButton);
         this.matchmakingPanel.add(adviceButton);

      } catch (Exception e)
      {
         throw new RuntimeException(e);
      }

      this.add(mainPanel);
      cl.show(mainPanel, "1");

      this.setResizable(false);
      this.setVisible(true);

      Game.getInstance().getInjection().injectMainMenu();
   }

   public void displayMainMenu()
   {
      this.cl.show(mainPanel, "1");
   }

   public void showLoadingScreen()
   {
      this.cl.show(mainPanel, "2");
   }

   public static void main(String[] args)
   {
      new MainMenuScreen();
   }

   public void startMatchmaking()
   {
      Game.getInstance().getMatchManager().joinRankedQueue(Game.getInstance().getConnection());
      cl.show(mainPanel, "2");
   }

   @Override
   public void actionPerformed(ActionEvent e)
   {
   }
}
