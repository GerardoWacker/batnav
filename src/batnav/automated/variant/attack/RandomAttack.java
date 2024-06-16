package batnav.automated.variant.attack;

import batnav.instance.Game;
import batnav.utils.Logger;

import java.util.ArrayList;
import java.util.List;

public class RandomAttack extends Attack
{
   private final PreferenceHandler preferenceHandler;
   public boolean[][] bombMatrix;
   private final List<int[]> bombList;

   public RandomAttack(final boolean preference)
   {
      this.preferenceHandler = new PreferenceHandler(preference);
      this.bombList = new ArrayList<>();
   }

   public void fillMatrix()
   {
      for (int i = 0; i < 10; i++)
      {
         for (int j = 0; j < 10; j++)
         {
            this.bombList.add(new int[]{i, j});
            bombMatrix[i][j] = false;
         }
      }
   }

   @Override
   public void startAttack()
   {
      this.bombList.clear();
      bombMatrix = new boolean[10][10];
      fillMatrix();
   }

   @Override
   public void receiveBomb()
   {
      int[] generatedCoordinates = generateCoordinates();
      int[] coordinates = this.preferenceHandler.handle(generatedCoordinates);
      this.bombMatrix[coordinates[0]][coordinates[1]] = true;
      Logger.log("Verificando si la lista contiene las coordenadas generadas: " + this.bombList.contains(coordinates));
      this.bombList.remove(coordinates);
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

   @Override
   public void matchEnd()
   {
      this.bombMatrix = new boolean[10][10];
      fillMatrix();
      this.preferenceHandler.destroy();
   }

   public int[] generateCoordinates()
   {
      int[] item = this.bombList.get((int) (Math.random() * this.bombList.size()));
      Logger.log("Se obtuvo la coordenada " + item[0] + ", " + item[1] + " de entre "
              + this.bombList.size() + " bombas disponibles.");
      int coordX = item[0], coordY = item[1];

      Logger.log("Se han generado las coordenadas " + coordX + ", " + coordY);
      if (this.bombMatrix[coordX][coordY])
         return generateCoordinates();
      else
      {
         return item;
      }
   }
}
