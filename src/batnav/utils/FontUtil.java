package batnav.utils;

import java.awt.*;
import java.io.File;
import java.io.InputStream;

public class FontUtil
{
   /**
    * Loads a font into the local graphics environment.
    *
    * @param fontName Font file name.
    */
   public Font createFont(final String fontName)
   {
      GraphicsEnvironment graphicsEnvironment = null;
      try
      {
         graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
         File fontFile = new File("assets/fonts/" + fontName + ".ttf");
         Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
         graphicsEnvironment.registerFont(font);
         return font;
      } catch (Exception e)
      {
         e.printStackTrace();
      }
      return new Font("Arial", Font.PLAIN, 14);
   }
}
