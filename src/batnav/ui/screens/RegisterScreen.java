package batnav.ui.screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterScreen extends JFrame implements ActionListener {

   JLabel usernameLabel, emailLabel, passwordLabel, repeatpasswordLabel, titleLabel;
   JTextField usernameField, emailField, passwordField, repeatpasswordField;
   JButton registerButton;


   RegisterScreen() {
      this.setSize(300, 600);
      this.setResizable(false);

      this.titleLabel = new JLabel("Registrarse", SwingConstants.CENTER);
      titleLabel.setFont(new Font("San Francisco Display", Font.BOLD, 25));
      titleLabel.setHorizontalTextPosition(JLabel.CENTER);
      titleLabel.setBounds(50, 10, 200, 80);


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

      this.usernameLabel.setBounds(100, 100, 80, 25);
      this.usernameField.setBounds(100, 130, 80, 25);
      this.emailLabel.setBounds(100, 180, 80, 25);
      this.emailField.setBounds(100, 210, 80, 25);
      this.passwordLabel.setBounds(100, 260, 80, 25);
      this.passwordField.setBounds(100, 290, 80, 25);
      this.repeatpasswordLabel.setBounds(100, 340, 80, 25);
      this.repeatpasswordField.setBounds(100, 370, 80, 25);
      this.registerButton.setBounds(90, 450, 100, 25);

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
