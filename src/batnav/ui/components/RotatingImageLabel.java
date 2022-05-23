package batnav.ui.components;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class RotatingImageLabel extends AnimatedLabel
{
   private final Image image;
   private int angle;
   private final int rotation;

   public RotatingImageLabel(Image image, int rotation)
   {
      super(60);
      this.rotation = rotation;
      this.image = image;
   }

   @Override
   public void paint(Graphics g)
   {
      super.paint(g);

      Graphics2D g2d = (Graphics2D) g;
      final AffineTransform affineTransform = new AffineTransform();

      affineTransform.translate((image.getWidth(null) * .5), (image.getHeight(null) * .5));
      affineTransform.rotate(Math.toRadians(angle));
      affineTransform.translate(-(image.getWidth(null) * .5), -(image.getHeight(null) * .5));

      g2d.drawImage(image, affineTransform, null);
   }

   @Override
   public void update()
   {
      angle += rotation;
   }
}
