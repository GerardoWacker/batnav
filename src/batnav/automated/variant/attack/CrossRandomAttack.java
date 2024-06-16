package batnav.automated.variant.attack;

import batnav.instance.Game;
import batnav.utils.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CrossRandomAttack extends Attack
{
   public List<int[]> bombsToThrow;
   private final PreferenceHandler preferenceHandler;
   public boolean[][] bombMatrix;
   private final Random random;

   public CrossRandomAttack()
   {
      this.random = new Random();
      this.preferenceHandler = new PreferenceHandler(true);
   }

   @Override
   public void startAttack()
   {
      this.bombMatrix = new boolean[10][10];
      this.fillMatrix();

      this.bombsToThrow = new ArrayList<>();

      for (int i = 0; i < 10; i++)
      {
         this.bombsToThrow.add(new int[]{i, i});
      }

      for (int i = 0; i < 10; i++)
      {
         this.bombsToThrow.add(new int[]{i, 9 - i});
      }
   }

   @Override
   public void receiveBomb()
   {
      int[] generatedCoordinates;

      if (!bombsToThrow.isEmpty())
      {
         generatedCoordinates = getLatestBombToThrow();
      } else
      {
         generatedCoordinates = generateCoordinates();
      }

      int[] coordinates = this.preferenceHandler.handle(generatedCoordinates);
      this.bombMatrix[coordinates[0]][coordinates[1]] = true;
      Game.getInstance().getMatchManager().throwBomb(Game.getInstance().getConnection(),
              coordinates[0], coordinates[1]);
      if (generatedCoordinates == coordinates)
         this.bombsToThrow.remove(0);
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

   @Override
   public void matchEnd()
   {
      this.preferenceHandler.destroy();
   }

   public int[] getLatestBombToThrow()
   {
      if(this.bombsToThrow.isEmpty())
         return this.generateCoordinates();

      final int index = random.nextInt(this.bombsToThrow.size());
      int[] generatedCoordinates = this.bombsToThrow.get(index);

      if (this.bombMatrix[generatedCoordinates[0]][generatedCoordinates[1]])
      {
         this.bombsToThrow.remove(index);
         Logger.log("La bomba [" + generatedCoordinates[0] + ", " + generatedCoordinates[1] +
                 "] ya fue arrojada. Obteniendo siguiente...");
         return getLatestBombToThrow();
      } else
      {
         Logger.log("La bomba [" + generatedCoordinates[0] + ", " + generatedCoordinates[1] +
                 "] fue arrojada correctamente.");
         return generatedCoordinates;
      }
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
}