package damas;

import damas.instance.Damas;

public class Start
{
   // Creates a new instance for the main game.
   private static final Damas damas = new Damas();

   public static void main(String[] args)
   {
      // Launches the game
      damas.launch();
   }
}
