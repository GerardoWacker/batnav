package batnav.ui.screens;

import batnav.instance.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class LoginScreen extends JFrame implements ActionListener {
   private JTextField userName;
   private JTextField userPassword;
   private JLabel userLabel;
   private JLabel logoContainerLabel;
   private JLabel PasswordLabel;
   private JButton loginButton;
   private JLabel alert;
   private JPanel loginPanel;
   private JPanel loadingPanel;
   private JLabel loadingText;
   private JButton tempButton;
   private JPanel mainPanel;
   private CardLayout cl;


   public LoginScreen() {
      this.cl = new CardLayout();
      this.setSize(300, 500);
      this.setLocationRelativeTo(null);
      this.loginPanel = new JPanel();
      this.loadingPanel = new JPanel();
      this.mainPanel = new JPanel();
      this.mainPanel.setLayout(cl);
      this.add(mainPanel);
      mainPanel.add(loginPanel, "1");
      mainPanel.add(loadingPanel, "2");

      cl.show(mainPanel, "1");

      loginPanel.setLayout(null);

      this.logoContainerLabel = new JLabel("batnav", SwingConstants.CENTER);
      logoContainerLabel.setFont(new Font("San Francisco Display", Font.BOLD, 25));
      logoContainerLabel.setHorizontalTextPosition(JLabel.CENTER);
      logoContainerLabel.setBounds(50, 10, 200, 80);


      this.userLabel = new JLabel("Usuario");
      userLabel.setBounds(50, 100, 80, 25);


      this.userName = new JTextField(20);
      userName.setBounds(50, 130, 165, 25);


      this.PasswordLabel = new JLabel("Contraseña");
      PasswordLabel.setBounds(50, 200, 80, 25);


      this.userPassword = new JPasswordField(20);
      userPassword.setBounds(50, 230, 165, 25);

      this.loginButton = new JButton("Login");
      loginButton.setBounds(50, 300, 165, 25);
      loginButton.addActionListener(this);
      loginButton.setActionCommand("login");

      this.alert = new JLabel("Contraseña");
      alert.setOpaque(true);
      alert.setForeground(Color.white);
      alert.setBackground(Color.red);
      alert.setBounds(50, 350, 80, 25);

      this.loadingText = new JLabel("Iniciando sesion");
      loadingText.setBounds(100, 120, 200, 80);

      this.tempButton = new JButton("Back");
      tempButton.setBounds(50, 300, 165, 25);
      tempButton.addActionListener(this);
      tempButton.setActionCommand("Back");


      this.loginPanel.add(this.userName);
      this.loginPanel.add(this.logoContainerLabel);
      this.loginPanel.add(this.userPassword);
      this.loginPanel.add(this.userLabel);
      this.loginPanel.add(this.PasswordLabel);
      this.loginPanel.add(this.loginButton);
      this.loginPanel.add(this.alert);


      this.loadingPanel.add(loadingText);
      this.loadingPanel.add(tempButton);

      this.setResizable(false);
      this.setAlwaysOnTop(true);
      this.setVisible(true);


   }


   @Override
   public void actionPerformed(ActionEvent e) {


      final String action = e.getActionCommand();
      switch (action) {
         case "login":
            cl.show(mainPanel, "2");
            try {
               if (Game.getInstance().getSessionManager().login(userName.getText(), userPassword.getText())) {
                  new MainMenuScreen();
               } else {
                  System.out.println("lol");
               }
               break;
            } catch (Exception ex) {
               ex.printStackTrace();
            }
            break;
         case "Back":
            cl.show(mainPanel, "1");
            break;


      }
   }

   public static void main(String[] args) {
      new LoginScreen();
   }
}
