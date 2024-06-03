package batnav.automated.variant.attack;

import batnav.instance.Game;
import batnav.utils.Logger;

public class RandomAttack extends Attack
{
   public boolean[][] bombMatrix = new boolean[10][10];

   public RandomAttack()
   {
      fillMatrix();
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
   public void receiveBomb()
   {
      int coordX = (int) (Math.random() * 10);
      int coordY = (int) (Math.random() * 10);
      if (!bombMatrix[coordX][coordY])
      {
         Game.getInstance().getMatchManager().throwBomb(Game.getInstance().getConnection(),
                 coordX, coordY);
         bombMatrix[coordX][coordY] = true;
         Logger.log("Se arrojó una bomba");
      }
   }
}
