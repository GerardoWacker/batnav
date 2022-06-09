package batnav.utils;

import batnav.instance.Game;

import java.awt.*;

public class Fonts
{
   public static Font displayRegular = Game.getInstance().getFontUtil().createFont("Roboto-Regular").deriveFont(Font.PLAIN, 14);
   public static Font displayTitle = Game.getInstance().getFontUtil().createFont("Roboto-Black").deriveFont(Font.PLAIN, 25);
   public static Font displayMedium = displayRegular.deriveFont(17f);
}
