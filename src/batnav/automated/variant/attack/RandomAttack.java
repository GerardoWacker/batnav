package batnav.automated.variant.attack;

import batnav.instance.Game;
import batnav.utils.Logger;

public class RandomAttack extends Attack
{
   private final PreferenceHandler preferenceHandler;
   public boolean[][] bombMatrix;

   public RandomAttack(final boolean preference)
   {
      this.preferenceHandler = new PreferenceHandler(preference);
   }

   public void fillMatrix()
   {
      for (int i = 0; i < 10; i++)
      {
         for (int j = 0; j < 10; j++)
         {
            bombMatrix[i][j] = false;
         }
      }
   }

   @Override
   public void startAttack()
   {
      bombMatrix = new boolean[10][10];
      fillMatrix();
   }

   @Override
   public void receiveBomb()
   {
      int[] generatedCoordinates = generateCoordinates();
      int[] coordinates = this.preferenceHandler.handle(generatedCoordinates[0], generatedCoordinates[1]);
      this.bombMatrix[coordinates[0]][coordinates[1]] = true;
      Game.getInstance().getMatchManager().throwBomb(Game.getInstance().getConnection(),
              coordinates[0], coordinates[1]);
      Logger.log("Se arrojó una bomba en " + coordinates[0] + ", " + coordinates[1]);
   }

   @Override
   public void setBombHit(boolean hit)
   {
      preferenceHandler.setBombHit(this.bombMatrix, hit);
   }

   @Override
   public void setShipSunk()
   {
      this.preferenceHandler.destroy();
   }

   public int[] generateCoordinates()
   {
      int coordX = (int) (Math.random() * 10);
      int coordY = (int) (Math.random() * 10);

      Logger.log("Se han generado las coordenadas " + coordX + ", " + coordY);
      if (this.bombMatrix[coordX][coordY])
         return generateCoordinates();
      else
      {
         return new int[]{coordX, coordY};
      }
   }
}
