package batnav;

import batnav.instance.Game;

public class Start
{
   // Creates a new instance for the main game.
   private static final Game damas = new Game();

   public static void main(String[] args)
   {
      System.out.println("===============================================================================");
      System.out.println("-- Damas --");
      System.out.println("El juego se está iniciando...");
      System.out.println("Para más información, visitá https://www.github.com/gerardowacker/damas.");
      System.out.println("===============================================================================");
      // Launches the game
      try
      {
         damas.launch();
      } catch (Exception e)
      {
         e.printStackTrace();
      }
   }
}
