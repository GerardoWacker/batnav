package batnav.ui.components;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel
{
   @Override
   protected void paintComponent(Graphics g)
   {
      super.paintComponent(g);

      Graphics2D g2d = (Graphics2D) g;

      g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
      int w = getWidth(), h = getHeight();

      Color color1 = Color.RED;
      Color color2 = Color.GREEN;
      GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);

      g2d.setPaint(gp);
      g2d.fillRect(0, 0, w, h);
   }
}
