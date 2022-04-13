package batnav.ui.screens;

import javax.swing.*;

public class LoginScreen extends JFrame
{
   public LoginScreen()
   {
      this.setSize(500, 600);

      this.setLocationRelativeTo(null);
      this.setAlwaysOnTop(true);

      this.add(new JLabel("Iniciar sesión"));

      this.setVisible(true);
   }
}
