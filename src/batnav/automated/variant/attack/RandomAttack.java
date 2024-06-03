package batnav.automated.variant.attack;

import batnav.instance.Game;
import batnav.utils.Logger;

public class RandomAttack extends Attack
{
   public boolean[][] bombMatrix;

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
      int[] coordinates = generateCoordinates();
      Game.getInstance().getMatchManager().throwBomb(Game.getInstance().getConnection(),
              coordinates[0], coordinates[1]);
      Logger.log("Se arrojó una bomba en " + coordinates[0] + ", " + coordinates[1]);
   }

   public int[] generateCoordinates()
   {
      int coordX = (int) (Math.random() * 10);
      int coordY = (int) (Math.random() * 10);

      Logger.log("Se han generado las coordenadas" + coordX + ", " + coordY);
      if (this.bombMatrix[coordX][coordY])
         return generateCoordinates();
      else {
         bombMatrix[coordX][coordY] = true;
         return new int[]{coordX, coordY};
      }
   }
}
