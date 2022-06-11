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
   private Image background;
   private boolean alternative, extended;

   public GameButton(String text)
   {
      super(text);

      this.setBackground(Colour.Transparent);
      this.setBorder(null);
      this.setForeground(alternative ? Colour.AliceBlue : Colour.Black);
      this.setFont(Fonts.displayRegular.deriveFont(14f));

      try
      {
         this.background = ImageIO.read(new File("assets/textures/button" + (extended ? "_long" : "") +
              (alternative ? "_alternative" : "") + ".png"));
      } catch (IOException e)
      {
         Logger.err("Hubo un error leyendo una textura.");
         throw new RuntimeException(e);
      }
   }

   public GameButton()
   {
      this.setBackground(Colour.Transparent);
      this.setBorder(BorderFactory.createEmptyBorder());
      this.setForeground(alternative ? Colour.AliceBlue : Colour.Black);

      try
      {
         this.background = ImageIO.read(new File("assets/textures/button" + (extended ? "_long" : "") +
              (alternative ? "_alternative" : "") + ".png"));
      } catch (IOException e)
      {
         Logger.err("Hubo un error leyendo una textura.");
         throw new RuntimeException(e);
      }
   }

   @Override
   public void paint(Graphics g)
   {
      Graphics2D g2d = (Graphics2D) g;

      Colour grad1 = alternative ? new Colour(53, 110, 255) : new Colour(255, 226, 53);
      Colour grad2 = alternative ? new Colour(19, 59, 147) : new Colour(204, 140, 0);

      GradientPaint gp = new GradientPaint(0, 0, grad1, 0, getHeight(), grad2);

      g2d.setPaint(gp);

      g.fillRect(0, 0, this.getWidth(), this.getHeight());

      super.paint(g);
   }

   public void setExtended(boolean extended)
   {
      this.extended = extended;
      try
      {
         this.background = ImageIO.read(new File("assets/textures/button" + (extended ? "_long" : "") +
              (alternative ? "_alternative" : "") + ".png"));
      } catch (IOException e)
      {
         throw new RuntimeException(e);
      }
      repaint();
   }

   public void setAlternative(boolean alternative)
   {
      this.alternative = alternative;
      this.setForeground(alternative ? Colour.AliceBlue : Colour.Black);

      try
      {
         this.background = ImageIO.read(new File("assets/textures/button" + (extended ? "_long" : "") +
              (alternative ? "_alternative" : "") + ".png"));
      } catch (IOException e)
      {
         throw new RuntimeException(e);
      }
      repaint();
   }
}
