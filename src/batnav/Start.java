package batnav;

import batnav.instance.Game;

public class Start
{
   public static void main(String[] args)
   {
      System.out.println("===============================================================================");
      System.out.println("-- batnav --");
      System.out.println("El juego se está iniciando...");
      System.out.println("Para más información, visitá https://www.github.com/gerardowacker/batnav.");
      System.out.println("===============================================================================");
      // Launches the game
      try
      {
         Game.getInstance().launch();
      } catch (Exception e)
      {
         e.printStackTrace();
      }
   }
}
