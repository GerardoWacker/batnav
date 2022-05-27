package batnav.ui.screens;

import batnav.instance.Game;
import org.apache.commons.logging.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JFrame implements ActionListener {
    private JTextField userName;
    private JTextField userPassword;
    private JLabel userLabel;
    private JLabel logoContainerLabel;
    private JLabel PasswordLabel;
    private JButton loginButton;
    private JLabel altert;
    private JPanel contentPanel;
    private JLabel loadingText;
    private JButton tempButton;

    public LoginScreen() {
        this.setSize(300, 500);
        this.setLocationRelativeTo(null);
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


        this.PasswordLabel = new JLabel("Contraseña");
        PasswordLabel.setBounds(50, 200, 80, 25);


        this.userPassword = new JPasswordField(20);
        userPassword.setBounds(50, 230, 165, 25);

        this.loginButton = new JButton("Login");
        loginButton.setBounds(50, 300, 165, 25);
        loginButton.addActionListener(this);
        loginButton.setActionCommand("login");

        this.altert = new JLabel("Contraseña");
        altert.setOpaque(true);
        altert.setForeground(Color.white);
        altert.setBackground(Color.red);
        altert.setBounds(50, 350, 80, 25);

        this.loadingText = new JLabel("Iniciando sesion");
        loadingText.setBounds(100, 120, 200, 80);

        this.tempButton = new JButton("Back");
        tempButton.setBounds(50, 300, 165, 25);
        tempButton.addActionListener(this);
        tempButton.setActionCommand("Back");


        this.setResizable(false);
        this.setAlwaysOnTop(true);
        this.setVisible(true);

        this.paintScreen();


    }

    private void showLoadingScreen() {
        this.contentPanel.removeAll();
        this.contentPanel.add(loadingText);
        this.contentPanel.add(tempButton);
        this.revalidate();
        this.repaint();
    }


    public static void main(String[] args) {
        new LoginScreen();
    }

    public void paintScreen() {
        this.contentPanel.removeAll();
        this.contentPanel.add(this.userName);
        this.contentPanel.add(this.logoContainerLabel);
        this.contentPanel.add(this.userPassword);
        this.contentPanel.add(this.userLabel);
        this.contentPanel.add(this.PasswordLabel);
        this.contentPanel.add(this.loginButton);
        this.contentPanel.add(this.altert);
        this.revalidate();
        this.repaint();

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        final String action = e.getActionCommand();
        switch (action) {
            case "login":
                this.showLoadingScreen();
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
                this.paintScreen();
                break;


        }
    }
}
