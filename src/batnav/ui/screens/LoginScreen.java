package batnav.ui.screens;

import batnav.instance.Game;
import batnav.online.model.Packet;
import batnav.ui.components.GameButton;
import batnav.ui.components.GamePanel;
import batnav.ui.components.RoundedPasswordField;
import batnav.ui.components.RoundedTextField;
import batnav.utils.Colour;
import batnav.utils.Fonts;
import batnav.utils.Logger;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;

public class LoginScreen extends JFrame implements ActionListener, KeyListener
{
   private final JTextField userName, userPassword;
   private final JLabel userLabel, logoContainerLabel, passwordLabel, alert, loadingText, jumpToRegisterScreen;
   private JPanel loginPanel, loadingPanel;
   private GameButton tempButton, loginButton;
   private GamePanel mainPanel;
   private CardLayout cl;

   public LoginScreen()
   {
      this.cl = new CardLayout();
      this.setSize(315, 450);
      this.setLocationRelativeTo(null);
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);

      this.loginPanel = new JPanel();
      this.loadingPanel = new JPanel();

      loginPanel.setBackground(Colour.Transparent);
      loginPanel.setOpaque(false);
      loadingPanel.setBackground(Colour.Transparent);
      loadingPanel.setOpaque(false);

      this.mainPanel = new GamePanel();
      mainPanel.setAlternative(true);
      this.mainPanel.setLayout(cl);

      mainPanel.add(loginPanel, "1");
      mainPanel.add(loadingPanel, "2");

      this.add(mainPanel);
      cl.show(mainPanel, "1");

      loginPanel.setLayout(null);

      this.jumpToRegisterScreen = new JLabel("<html><u>¿No tenés una cuenta?</u></html");
      this.jumpToRegisterScreen.setForeground(Colour.BLUE);
      this.jumpToRegisterScreen.setBounds(50, 350, 200, 25);
      this.loginPanel.add(jumpToRegisterScreen);
      this.jumpToRegisterScreen.addMouseListener(new MouseListener()
      {
         @Override
         public void mouseClicked(MouseEvent e)
         {
            if (e.getSource() == jumpToRegisterScreen)
            {
               new RegisterScreen();
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

      this.logoContainerLabel = new JLabel("Iniciar sesión", SwingConstants.CENTER);
      logoContainerLabel.setFont(Fonts.displayTitle);
      logoContainerLabel.setHorizontalTextPosition(JLabel.CENTER);
      logoContainerLabel.setBounds(50, 10, 200, 80);
      logoContainerLabel.setBackground(Colour.Transparent);

      this.userLabel = new JLabel("Usuario");
      userLabel.setBounds(50, 100, 80, 25);
      userLabel.setFont(Fonts.displayRegular);

      this.userName = new JTextField(20);
      userName.setBounds(50, 130, 200, 30);
      userName.setFont(Fonts.displayRegular);

      this.passwordLabel = new JLabel("Contraseña");
      passwordLabel.setBounds(50, 200, 80, 25);
      passwordLabel.setFont(Fonts.displayRegular);

      this.userPassword = new JPasswordField(20);
      userPassword.setBounds(50, 230, 200, 30);
      userPassword.setFont(Fonts.displayRegular);

      this.loginButton = new GameButton("Iniciar sesión");
      loginButton.setAlternative(true);
      loginButton.setBounds(75, 290, 150, 30);
      loginButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Colour.Black, Colour.Black));
      loginButton.addActionListener(this);
      loginButton.setActionCommand("login");
      this.addKeyListener(this);


      this.alert = new JLabel("Contraseña");
      alert.setOpaque(true);
      alert.setForeground(Color.white);
      alert.setBackground(Color.red);
      alert.setBounds(50, 350, 200, 25);
      alert.setVisible(false);

      this.loadingText = new JLabel("Iniciando sesión");
      loadingText.setBounds(100, 120, 200, 80);

      this.tempButton = new GameButton("Cancelar");
      tempButton.setBounds(50, 300, 165, 25);
      tempButton.addActionListener(this);
      tempButton.setActionCommand("cancel");

      this.loginPanel.add(this.userName);
      this.loginPanel.add(this.logoContainerLabel);
      this.loginPanel.add(this.userPassword);
      this.loginPanel.add(this.userLabel);
      this.loginPanel.add(this.passwordLabel);
      this.loginPanel.add(this.loginButton);
      this.loginPanel.add(this.alert);

      this.loadingPanel.add(loadingText);
      this.loadingPanel.add(tempButton);

      this.setResizable(false);
      this.setAlwaysOnTop(true);
      this.setVisible(true);
   }

   private void createLoginThread()
   {
      Thread loginThread = new Thread(() -> {
         Logger.log("Creado hilo de inicio de sesión.");

         try
         {
            if (Game.getInstance().getSessionManager().login(userName.getText(), userPassword.getText()))
            {
               Game.getInstance().getConnection().sendPacket(
                    new Packet("authenticate", Game.getInstance().getSessionManager().getSessionId())
               );

               Game.getInstance().getMainMenuScreen().setVisible(true);
               this.setVisible(false);
            } else
            {
               this.alert.setText("Los datos ingresados son incorrectos.");
               this.alert.setVisible(true);
               cl.show(mainPanel, "1");
            }
         } catch (Exception e)
         {
            throw new RuntimeException(e);
         }
      });

      loginThread.start();
   }


   @Override
   public void actionPerformed(ActionEvent e)
   {
      final String action = e.getActionCommand();

      switch (action)
      {
         case "login" ->
         {
            cl.show(mainPanel, "2");
            try
            {
               this.createLoginThread();
            } catch (Exception ex)
            {
               ex.printStackTrace();
            }
         }
         case "cancel" -> cl.show(mainPanel, "1");
      }
   }

   public static void main(String[] args)
   {
      new LoginScreen();
   }

   @Override
   public void keyTyped(KeyEvent e)
   {

   }

   @Override
   public void keyPressed(KeyEvent e)
   {
      System.out.println("you pressed character " + e.getKeyCode());

   }

   @Override
   public void keyReleased(KeyEvent e)
   {

   }
}
