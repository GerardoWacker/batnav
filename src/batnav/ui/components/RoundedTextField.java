package batnav.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedTextField extends JTextField
{
   private final int horizontal, vertical;
   private Shape shape;

   /**
    * Creates a rounded text field.
    *
    * @param size       Field size.
    * @param vertical   Vertical radius.
    * @param horizontal Horizontal radius.
    */
   public RoundedTextField(int horizontal, int vertical, int size)
   {
      super(size);

      this.horizontal = horizontal;
      this.vertical = vertical;

      this.setOpaque(false);
   }

   /**
    * Creates a rounded text field.
    *
    * @param radius Border radius.
    * @param size   Field size.
    */
   public RoundedTextField(int radius, int size)
   {
      this(radius, radius, size);
   }

   @Override
   protected void paintComponent(Graphics g)
   {
      g.setColor(getBackground());
      g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
      super.paintComponent(g);
   }

   @Override
   protected void paintBorder(Graphics g)
   {
      g.setColor(getForeground());
      g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
   }

   @Override
   public boolean contains(int x, int y)
   {
      if (shape == null || !shape.getBounds().equals(getBounds()))
      {
         shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, horizontal, vertical);
      }
      return shape.contains(x, y);
   }
}
