package batnav.ui.screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsScreen extends JFrame implements ActionListener
{
   private CardLayout cl;
   private JButton logoutButton;
   private JButton lightModeButton;
   private JButton darkModeButton;
   private JButton customizeItYourselfButton;
   private JPanel mainPanel;
   private JPanel settingsPanel;

   public SettingsScreen()
   {
      this.cl = new CardLayout();
      this.setSize(500, 500);
      this.setLocationRelativeTo(null);

      this.settingsPanel = new JPanel();
      this.mainPanel = new JPanel();
      this.mainPanel.setLayout(cl);
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
      this.add(mainPanel);
      mainPanel.add(settingsPanel, "1");

      cl.show(mainPanel, "1");
      settingsPanel.setLayout(null);

      this.lightModeButton = new JButton("Light Mode");
      lightModeButton.setBounds(100, 10, 300, 100);
      lightModeButton.setFont(new Font("San Francisco Display", Font.PLAIN, 20));
      lightModeButton.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            new lightMode();
         }
      });

      this.darkModeButton = new JButton("Dark Mode");
      darkModeButton.setBounds(100, 120, 300, 100);
      darkModeButton.setFont(new Font("San Francisco Display", Font.PLAIN, 20));
      darkModeButton.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            new darkMode();
         }
      });
      this.customizeItYourselfButton = new JButton("Customize it yourself");
      customizeItYourselfButton.setBounds(100, 230, 300, 100);
      customizeItYourselfButton.setFont(new Font("San Francisco Display", Font.PLAIN, 20));
      customizeItYourselfButton.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            new customizeItYourself();
         }
      });

      this.logoutButton = new JButton("Logout");
      logoutButton.setBounds(100, 350, 300, 50);
      logoutButton.setFont(new Font("San Francisco Display", Font.PLAIN, 18));
      logoutButton.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            getDefaultCloseOperation();
         }
      });

      this.settingsPanel.add(this.lightModeButton);
      this.settingsPanel.add(this.darkModeButton);
      this.settingsPanel.add(this.customizeItYourselfButton);
      this.settingsPanel.add(this.logoutButton);


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
