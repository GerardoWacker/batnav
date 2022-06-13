package batnav.ui.components;

import batnav.utils.Colour;
import batnav.utils.Fonts;
import batnav.utils.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GameButton extends JButton
{
   private boolean alternative, extended, cancel;

   public GameButton(String text)
   {
      super(text);

      this.setBackground(Colour.Transparent);
      this.setBorder(null);
      this.setForeground(alternative || cancel ? Colour.White : Colour.Black);
      this.setFont(Fonts.displayRegular.deriveFont(14f));
   }

   public GameButton()
   {
      this.setBackground(Colour.Transparent);
      this.setBorder(BorderFactory.createEmptyBorder());
      this.setForeground(alternative || cancel ? Colour.White : Colour.Black);
      this.setFont(Fonts.displayRegular.deriveFont(14f));
   }

   @Override
   public void paint(Graphics g)
   {
      Graphics2D g2d = (Graphics2D) g;

      Colour grad1 = cancel ? new Colour(255, 0, 0) : (alternative ? new Colour(53, 110, 255) : new Colour(255, 226, 53));
      Colour grad2 = cancel ? new Colour(120, 0, 0) : (alternative ? new Colour(19, 59, 147) : new Colour(204, 140, 0));

      GradientPaint gp = new GradientPaint(0, 0, grad1, 0, getHeight(), grad2);

      g2d.setPaint(gp);

      g.fillRect(0, 0, this.getWidth(), this.getHeight());

      super.paint(g);
   }

   public void setCancel(boolean cancel)
   {
      this.cancel = cancel;
      this.setForeground(alternative || cancel ? Colour.White : Colour.Black);
   }

   public void setAlternative(boolean alternative)
   {
      this.alternative = alternative;
      this.setForeground(alternative || cancel ? Colour.White : Colour.Black);
      repaint();
   }
}
