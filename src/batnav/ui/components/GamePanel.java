package batnav.ui.components;

import batnav.utils.Colour;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel
{
   private boolean alternative = false;

   @Override
   protected void paintComponent(Graphics g)
   {
      super.paintComponent(g);

      Graphics2D g2d = (Graphics2D) g;

      g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
      int w = getWidth(), h = getHeight();

      Color color1 = alternative ? new Colour(171, 202, 224) : new Colour(30, 66, 158);
      Color color2 = alternative ? new Colour(240, 248, 255) : new Colour(14, 75, 207);
      GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);

      g2d.setPaint(gp);
      g2d.fillRect(0, 0, w, h);
   }

   public void setAlternative(boolean alternative)
   {
      this.alternative = alternative;
   }
}
