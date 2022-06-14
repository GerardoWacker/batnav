package batnav.ui.screens;

import batnav.instance.Game;
import batnav.ui.components.GameButton;
import batnav.ui.components.GamePanel;
import batnav.utils.Colour;
import batnav.utils.Fonts;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class SettingsScreen extends JFrame implements ActionListener
{
   private CardLayout cl;
   private GameButton logoutButton;
   private JPanel settingsPanel;

   public SettingsScreen()
   {
      try
      {
         this.cl = new CardLayout();
         this.setSize(500, 500);
         this.setLocationRelativeTo(null);

         this.settingsPanel = new JPanel();
         GamePanel mainPanel = new GamePanel();
         mainPanel.setAlternative(true);
         mainPanel.setLayout(cl);
         this.add(mainPanel);
         mainPanel.add(settingsPanel, "1");

         cl.show(mainPanel, "1");
         settingsPanel.setLayout(null);
         this.settingsPanel.setOpaque(false);
         this.settingsPanel.setBackground(Colour.Transparent);
         this.logoutButton = new GameButton("Cerrar sesión");
         logoutButton.setCancel(true);
         logoutButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Colour.Black, Colour.Black));
         logoutButton.setBounds(100, 30, 300, 50);
         logoutButton.setFont(Fonts.displayMedium);
         logoutButton.addActionListener(e -> {
            Game.getInstance().getSessionManager().setAndSaveSessionId(null);
            Game.getInstance().getConnection().disconnect();
         });

         JPanel userInfo = new JPanel();
         userInfo.setLayout(new GridLayout(5, 1));
         userInfo.setOpaque(false);
         userInfo.setBackground(Colour.Transparent);

         JLabel userIconLabel = new JLabel();
         final BufferedImage userIcon = ImageIO.read(new File("assets/textures/green_icon.png"));
         userIconLabel.setIcon(new ImageIcon(userIcon.getScaledInstance(48, 48, Image.SCALE_SMOOTH)));
         userIconLabel.setHorizontalAlignment(JLabel.CENTER);

         JLabel username = new JLabel(Game.getInstance().getConnection().getCurrentUser().getUsername());
         username.setFont(Fonts.displayTitle.deriveFont(20f));
         username.setHorizontalAlignment(JLabel.CENTER);

         Instant createdAt = Instant.parse(Game.getInstance().getConnection().getCurrentUser().getCreated());
         String createdMonth = ZonedDateTime.ofInstant(createdAt, ZoneId.of("America/Argentina/Buenos_Aires")).format(DateTimeFormatter.ofPattern("MMM"));
         String createdYear = ZonedDateTime.ofInstant(createdAt, ZoneId.of("America/Argentina/Buenos_Aires")).format(DateTimeFormatter.ofPattern("uuuu"));

         JLabel creationDate = new JLabel("Se unió en " + createdMonth + " de " + createdYear);
         creationDate.setFont(Fonts.displayMedium.deriveFont(14f));
         creationDate.setHorizontalAlignment(JLabel.CENTER);

         JLabel plays = new JLabel(Game.getInstance().getConnection().getCurrentUser().getPlays() + " partidas jugadas.");
         plays.setFont(Fonts.displayMedium.deriveFont(14f));
         plays.setHorizontalAlignment(JLabel.CENTER);

         JLabel isDev = new JLabel("El usuario " + (Game.getInstance().getConnection().getCurrentUser().isDeveloper() ? "" : "no ") + "es un desarrollador");
         isDev.setForeground((Game.getInstance().getConnection().getCurrentUser().isDeveloper() ? Colour.Teal : Colour.Black));
         isDev.setFont(Fonts.displayMedium.deriveFont(14f));
         isDev.setHorizontalAlignment(JLabel.CENTER);

         userInfo.add(userIconLabel);
         userInfo.add(username);
         userInfo.add(creationDate);
         userInfo.add(plays);
         userInfo.add(isDev);
         userInfo.setBounds(100, 130, 300, 200);

         JLabel signature = new JLabel("batnav");
         signature.setFont(Fonts.displayTitle.deriveFont(18f));
         signature.setBounds(0, 400, 500, 20);
         signature.setHorizontalAlignment(JLabel.CENTER);
         signature.setHorizontalTextPosition(JLabel.CENTER);
         JLabel version = new JLabel("2022.613.5");
         version.setFont(Fonts.displayMedium.deriveFont(14f));
         version.setBounds(0, 420, 500, 20);
         version.setHorizontalAlignment(JLabel.CENTER);
         version.setHorizontalTextPosition(JLabel.CENTER);

         this.settingsPanel.add(this.logoutButton);
         this.settingsPanel.add(userInfo);
         this.settingsPanel.add(signature);
         this.settingsPanel.add(version);
      } catch (Exception e)
      {
         throw new RuntimeException(e);
      }

      this.setResizable(false);
      this.setAlwaysOnTop(true);
      this.setVisible(true);
   }

   public static void main(String[] args)
   {
      new SettingsScreen();
   }

   @Override
   public void actionPerformed(ActionEvent e)
   {

   }
}
