package batnav.ui.screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsScreen extends JFrame implements ActionListener
{
   private CardLayout cl;
   private JButton logoutButton;
   private JPanel mainPanel, settingsPanel;

   public SettingsScreen()
   {
      this.cl = new CardLayout();
      this.setSize(500, 500);
      this.setLocationRelativeTo(null);

      this.settingsPanel = new JPanel();
      this.mainPanel = new JPanel();
      this.mainPanel.setLayout(cl);
      this.add(mainPanel);
      mainPanel.add(settingsPanel, "1");

      cl.show(mainPanel, "1");
      settingsPanel.setLayout(null);

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
