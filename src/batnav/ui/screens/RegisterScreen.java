package batnav.ui.screens;

import batnav.utils.Colour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RegisterScreen extends JFrame implements ActionListener {

   JLabel usernameLabel, emailLabel, passwordLabel, repeatpasswordLabel, titleLabel, jumpToLoginScreen;
   JTextField usernameField, emailField, passwordField, repeatpasswordField;
   JButton registerButton;


   RegisterScreen() {
      this.setSize(300, 600);
      this.setResizable(false);

      this.titleLabel = new JLabel("Registrarse", SwingConstants.CENTER);
      titleLabel.setFont(new Font("San Francisco Display", Font.BOLD, 25));
      titleLabel.setHorizontalTextPosition(JLabel.CENTER);
      titleLabel.setBounds(30, 10, 200, 80);


      this.usernameLabel = new JLabel("Usuario:");
      this.emailLabel = new JLabel("e-mail");
      this.passwordLabel = new JLabel("contraseña");
      this.repeatpasswordLabel = new JLabel("repetir contraseña");

      this.usernameField = new JTextField();
      this.emailField = new JTextField();
      this.passwordField = new JPasswordField();
      this.repeatpasswordField = new JPasswordField();
      this.setLayout(null);

      this.registerButton = new JButton("Registrarse");

      this.jumpToLoginScreen = new JLabel("Registrarse");
      this.jumpToLoginScreen.setForeground(Colour.BLUE);
      this.jumpToLoginScreen.setBounds(50, 480, 80, 25);
      this.add(jumpToLoginScreen);
      this.jumpToLoginScreen.addMouseListener(new MouseListener() {
         @Override
         public void mouseClicked(MouseEvent e) {
            if(e.getSource()== jumpToLoginScreen){
               new LoginScreen();
               setVisible(false);
            }
         }

         @Override
         public void mousePressed(MouseEvent e) {

         }

         @Override
         public void mouseReleased(MouseEvent e) {

         }

         @Override
         public void mouseEntered(MouseEvent e) {

         }

         @Override
         public void mouseExited(MouseEvent e) {

         }
      });

      this.usernameLabel.setBounds(50, 100, 80, 25);
      this.usernameField.setBounds(50, 130, 165, 25);
      this.emailLabel.setBounds(50, 180, 80, 25);
      this.emailField.setBounds(50, 210, 165, 25);
      this.passwordLabel.setBounds(50, 260, 80, 25);
      this.passwordField.setBounds(50, 290, 165, 25);
      this.repeatpasswordLabel.setBounds(50, 340, 165, 25);
      this.repeatpasswordField.setBounds(50, 370, 165, 25);
      this.registerButton.setBounds(65, 430, 130, 25);

      this.add(usernameLabel);
      this.add(usernameField);
      this.add(emailLabel);
      this.add(emailField);
      this.add(passwordLabel);
      this.add(passwordField);
      this.add(repeatpasswordLabel);
      this.add(repeatpasswordField);
      this.add(registerButton);
      this.add(titleLabel);

      this.setVisible(true);


   }



   @Override
   public void actionPerformed(ActionEvent e) {

   }

   public static void main(String[] args) {
      new RegisterScreen();
   }
}
