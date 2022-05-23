package batnav.ui.screens;

import batnav.instance.Game;
import org.apache.commons.logging.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JFrame implements ActionListener
{
   private JTextField userName;
   private JTextField userPassword;

   public LoginScreen()
   {
      this.setSize(300, 500);
      this.setLocationRelativeTo(null);
      JPanel panel = new JPanel();
      this.add(panel);
      panel.setLayout(null);

      JLabel logoContainerLabel = new JLabel("batnav", SwingConstants.CENTER);
      logoContainerLabel.setFont(new Font("San Francisco Display", Font.BOLD, 25));
      logoContainerLabel.setHorizontalTextPosition(JLabel.CENTER);
      logoContainerLabel.setBounds(50,10,200,80);
      panel.add(logoContainerLabel);

      JLabel userLabel = new JLabel("Usuario");
      userLabel.setBounds(50,100,80,25);
      panel.add(userLabel);

      this.userName = new JTextField(20);
      userName.setBounds(50,130,165,25);
      panel.add(userName);

      JLabel PasswordLabel = new JLabel("Contraseña");
      PasswordLabel.setBounds(50,200,80,25);
      panel.add(PasswordLabel);

      this.userPassword = new JPasswordField(20);
      panel.add(userPassword);
      userPassword.setBounds(50,230,165,25);

      JButton button = new JButton("Login");
      button.setBounds(50,300,165,25);
      button.setActionCommand("login");
      panel.add(button);

      JLabel prueba = new JLabel("Contraseña");
      prueba.setOpaque(true);
      prueba.setForeground(Color.white);
      prueba.setBackground(Color.red);
      prueba.setBounds(50,350,80,25);
      panel.add(prueba);

      this.setResizable(false);
      this.setAlwaysOnTop(true);
      this.setVisible(true);

   }



   public static void main(String[] args) {
      new LoginScreen();
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      final String action = e.getActionCommand();
      switch (action)
      {
         case "login":
            try {
               if(Game.getInstance().getSessionManager().login(userName.getText(), userPassword.getText())){
                  new MainMenuScreen();
               }else{
                  System.out.println("lol");
               }
               break;
            } catch (Exception ex) {
               ex.printStackTrace();
            }

      }
   }
}
