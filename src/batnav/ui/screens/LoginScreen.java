package batnav.ui.screens;

import batnav.instance.Game;
import batnav.utils.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class LoginScreen extends JFrame implements ActionListener
{
   private final JTextField userName, userPassword;
   private final JLabel userLabel, logoContainerLabel, passwordLabel, alert, loadingText, counterLabel;
   private final JButton loginButton, tempButton;
   private final JPanel contentPanel;
   private Timer timer;
   private int second;
   private final DecimalFormat dFormat = new DecimalFormat("00");
   String ddSecond;

   public LoginScreen()
   {
      this.setSize(300, 500);
      this.setLocationRelativeTo(null);
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);

      this.contentPanel = new JPanel();
      this.add(contentPanel);
      contentPanel.setLayout(null);

      this.logoContainerLabel = new JLabel("batnav", SwingConstants.CENTER);
      logoContainerLabel.setFont(new Font("San Francisco Display", Font.BOLD, 25));
      logoContainerLabel.setHorizontalTextPosition(JLabel.CENTER);
      logoContainerLabel.setBounds(50, 10, 200, 80);


      this.userLabel = new JLabel("Usuario");
      userLabel.setBounds(50, 100, 80, 25);


      this.userName = new JTextField(20);
      userName.setBounds(50, 130, 165, 25);


      this.passwordLabel = new JLabel("Contraseña");
      passwordLabel.setBounds(50, 200, 80, 25);


      this.userPassword = new JPasswordField(20);
      userPassword.setBounds(50, 230, 165, 25);

      this.loginButton = new JButton("Iniciar sesión");
      loginButton.setBounds(50, 300, 165, 25);
      loginButton.addActionListener(this);
      loginButton.setActionCommand("login");

      this.alert = new JLabel("Contraseña");
      alert.setOpaque(true);
      alert.setForeground(Color.white);
      alert.setBackground(Color.red);
      alert.setBounds(50, 350, 200, 25);
      alert.setVisible(false);

      this.loadingText = new JLabel("Iniciando sesión");
      loadingText.setBounds(100, 120, 200, 80);

      this.tempButton = new JButton("Cancelar");
      tempButton.setBounds(50, 300, 165, 25);
      tempButton.addActionListener(this);
      tempButton.setActionCommand("cancel");

      this.counterLabel = new JLabel();
      counterLabel.setBounds(50, 400, 165, 25);

      this.second = 0;
      iniciarTimer();
      timer.start();


      this.setResizable(false);
      this.setAlwaysOnTop(true);
      this.setVisible(true);

      this.paintScreen();

   }

   private void iniciarTimer()
   {
      timer = new Timer(1000, e -> {
         second++;
         ddSecond = dFormat.format(30 - second);
         if (second == 0)
         {
            timer.stop();
         }

         counterLabel.setText("00:" + ddSecond);

      });
   }

   private void resetTimer()
   {
      this.ddSecond = "30";
      counterLabel.setText("00:" + ddSecond);
      this.second = 0;
      timer.restart();
   }

   private void showLoadingScreen()
   {
      this.contentPanel.removeAll();
      this.contentPanel.add(loadingText);
      this.contentPanel.add(tempButton);
      this.revalidate();
      this.repaint();
   }


   public static void main(String[] args)
   {
      new LoginScreen();
   }

   public void paintScreen()
   {
      this.contentPanel.removeAll();
      this.contentPanel.add(this.userName);
      this.contentPanel.add(this.logoContainerLabel);
      this.contentPanel.add(this.userPassword);
      this.contentPanel.add(this.userLabel);
      this.contentPanel.add(this.passwordLabel);
      this.contentPanel.add(this.loginButton);
      this.contentPanel.add(this.alert);
      this.contentPanel.add(counterLabel);
      this.revalidate();
      this.repaint();
   }

   private void createLoginThread()
   {
      Thread loginThread = new Thread(() -> {
         Logger.log("Creado hilo de inicio de sesión.");

         try
         {
            if (Game.getInstance().getSessionManager().login(userName.getText(), userPassword.getText()))
            {
               new MainMenuScreen();
               this.setVisible(false);
            } else
            {
               this.alert.setText("Los datos ingresados son incorrectos.");
               this.alert.setVisible(true);
               this.paintScreen();
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
            this.showLoadingScreen();
            this.createLoginThread();
         }
         case "cancel" -> this.paintScreen();
      }
   }
}
