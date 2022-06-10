package batnav.ui.components;

import batnav.utils.Colour;
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
      this.setBorder(BorderFactory.createEmptyBorder());
      this.setForeground(Colour.Black);

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
      this.setForeground(Colour.Black);

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

      if (background != null)
      {
         g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);
      }

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
