package batnav.ui.screens;

import batnav.instance.Game;
import batnav.ui.components.GameButton;
import batnav.ui.components.GamePanel;
import batnav.utils.Colour;
import batnav.utils.Fonts;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RegisterScreen extends JFrame implements ActionListener
{

   JLabel usernameLabel, emailLabel, passwordLabel, repeatPasswordLabel, titleLabel, jumpToLoginScreen;
   JTextField usernameField, emailField, passwordField, repeatpasswordField;


   RegisterScreen()
   {

      this.setSize(315, 600);
      this.setResizable(false);

      final GamePanel contentPanel = new GamePanel();
      contentPanel.setAlternative(true);

      this.titleLabel = new JLabel("Registrarse", SwingConstants.CENTER);
      titleLabel.setFont(Fonts.displayTitle);
      titleLabel.setHorizontalTextPosition(JLabel.CENTER);
      titleLabel.setBounds(50, 10, 200, 80);

      this.usernameLabel = new JLabel("Usuario:");
      usernameLabel.setFont(Fonts.displayRegular);
      this.emailLabel = new JLabel("Correo electrónico:");
      emailLabel.setFont(Fonts.displayRegular);
      this.passwordLabel = new JLabel("Contraseña:");
      passwordLabel.setFont(Fonts.displayRegular);
      this.repeatPasswordLabel = new JLabel("Repetir contraseña:");
      repeatPasswordLabel.setFont(Fonts.displayRegular);

      this.usernameField = new JTextField(20);
      usernameField.setFont(Fonts.displayRegular);
      this.emailField = new JTextField(20);
      emailField.setFont(Fonts.displayRegular);
      this.passwordField = new JPasswordField(20);
      passwordField.setFont(Fonts.displayRegular);
      this.repeatpasswordField = new JPasswordField(20);
      repeatpasswordField.setFont(Fonts.displayRegular);

      final GameButton registerButton = new GameButton("Registrarse");
      registerButton.setAlternative(true);
      registerButton.setBounds(75, 290, 150, 30);
      registerButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Colour.Black, Colour.Black));

      this.jumpToLoginScreen = new JLabel("<html><u>¿Ya tenés una cuenta?</u></html>");
      this.jumpToLoginScreen.setForeground(Colour.Blue);
      this.jumpToLoginScreen.setBounds(50, 480, 200, 25);
      this.add(jumpToLoginScreen);
      this.jumpToLoginScreen.addMouseListener(new MouseListener()
      {
         @Override
         public void mouseClicked(MouseEvent e)
         {
            if (e.getSource() == jumpToLoginScreen)
            {
               Game.getInstance().getLoginScreen().setVisible(true);
               setVisible(false);
            }
         }

         @Override
         public void mousePressed(MouseEvent e)
         {

         }

         @Override
         public void mouseReleased(MouseEvent e)
         {

         }

         @Override
         public void mouseEntered(MouseEvent e)
         {

         }

         @Override
         public void mouseExited(MouseEvent e)
         {

         }
      });

      this.usernameLabel.setBounds(50, 100, 300, 25);
      this.usernameField.setBounds(50, 130, 200, 30);
      this.emailLabel.setBounds(50, 180, 300, 25);
      this.emailField.setBounds(50, 210, 200, 30);
      this.passwordLabel.setBounds(50, 260, 300, 25);
      this.passwordField.setBounds(50, 290, 200, 30);
      this.repeatPasswordLabel.setBounds(50, 340, 300, 25);
      this.repeatpasswordField.setBounds(50, 370, 200, 30);
      registerButton.setBounds(75, 430, 150, 30);

      contentPanel.setLayout(null);

      contentPanel.add(usernameLabel);
      contentPanel.add(usernameField);
      contentPanel.add(emailLabel);
      contentPanel.add(emailField);
      contentPanel.add(passwordLabel);
      contentPanel.add(passwordField);
      contentPanel.add(repeatPasswordLabel);
      contentPanel.add(repeatpasswordField);
      contentPanel.add(registerButton);
      contentPanel.add(titleLabel);

      this.add(contentPanel);

      this.setVisible(true);


   }


   @Override
   public void actionPerformed(ActionEvent e)
   {

   }

   public static void main(String[] args)
   {
      new RegisterScreen();
   }
}
