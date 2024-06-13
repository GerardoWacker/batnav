package batnav.automated.variant;

import batnav.online.model.Ship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Defences
{
   public static List<Ship> getMiddleDefence()
   {
      final List<Ship> ships = new ArrayList<Ship>();

      ships.add(new Ship(2));
      ships.add(new Ship(2));
      ships.add(new Ship(2));
      ships.add(new Ship(3));
      ships.add(new Ship(3));
      ships.add(new Ship(3));
      ships.add(new Ship(4));
      ships.add(new Ship(5));

      ships.get(0).setPosition(3, 2);
      ships.get(1).setPosition(5, 2);
      ships.get(2).setPosition(4, 6);
      ships.get(3).setPosition(4, 3);
      ships.get(4).setPosition(5, 3);
      ships.get(5).setPosition(6, 3);
      ships.get(6).setVertical(true);
      ships.get(6).setPosition(3, 3);
      ships.get(7).setVertical(true);
      ships.get(7).setPosition(2, 2);

      return ships;
   }

   public static List<Ship> getCornerDefence()
   {
      final List<Ship> ships = new ArrayList<Ship>();

      ships.add(new Ship(2));
      ships.add(new Ship(2));
      ships.add(new Ship(2));
      ships.add(new Ship(3));
      ships.add(new Ship(3));
      ships.add(new Ship(3));
      ships.add(new Ship(4));
      ships.add(new Ship(5));

      ships.get(0).setVertical(true);
      ships.get(0).setPosition(0, 1);
      ships.get(1).setVertical(true);
      ships.get(1).setPosition(9, 1);
      ships.get(2).setVertical(true);
      ships.get(2).setPosition(0, 7);
      ships.get(3).setPosition(0, 0);
      ships.get(4).setPosition(7, 0);
      ships.get(5).setPosition(0, 9);
      ships.get(6).setVertical(true);
      ships.get(6).setPosition(9, 5);
      ships.get(7).setPosition(5, 9);

      return ships;
   }


   public static List<Ship> getRandomDefence()
   {
      Random random = new Random();

      final List<Ship> ships = new ArrayList<Ship>();

      ships.add(new Ship(2));
      ships.add(new Ship(2));
      ships.add(new Ship(2));
      ships.add(new Ship(3));
      ships.add(new Ship(3));
      ships.add(new Ship(3));
      ships.add(new Ship(4));
      ships.add(new Ship(5));

      for (Ship ship : ships)
      {
         int[] coordinates = generateCoordinates(random, ships, ship);
         ship.setVertical(coordinates[2] == 1);
         ship.setPosition(coordinates[0], coordinates[1]);
      }

      return ships;
   }

   private static int[] generateCoordinates(final Random random, final List<Ship> ships, final Ship ship)
   {
      int x = random.nextInt(10);
      int y = random.nextInt(10);
      boolean vertical = random.nextBoolean();
      if (!coordinatesHasShip(ships, new int[]{x, y}, vertical, ship))
      {
         System.out.println("Se pudo agregar el barco de " + ship.getSize() + " en " + Arrays.toString(new int[]{x, y, vertical ? 1 : 0}));
         return new int[]{x, y, vertical ? 1 : 0};
      } else
      {
         System.out.println("No se pudo agregar el barco de " + ship.getSize() + " en " + Arrays.toString(new int[]{x, y, vertical ? 1 : 0}));
         return generateCoordinates(random, ships, ship);
      }
   }

   @SuppressWarnings ("DuplicatedCode")
   public static boolean coordinatesHasShip(final List<Ship> ships, int[] coordinates, boolean vertical, Ship currentShip)
   {
      if (vertical)
      {
         if ((currentShip.getSize() + coordinates[1]) > 9)
            return true;

         for (Ship ship : ships)
         {
            if (ship != currentShip)
            {
               for (int i = 0; i < currentShip.getSize(); i++)
               {
                  int y = coordinates[1] + i;
                  boolean has = Arrays.stream(ship.getAsRawData()).anyMatch(c -> c != null && c[0] == coordinates[0] && c[1] == y);
                  if (has)
                  {
                     return true;
                  }
               }
            }
         }
      } else
      {
         if ((currentShip.getSize() + coordinates[0]) > 9)
            return true;

         for (Ship ship : ships)
         {
            if (ship != currentShip)
            {
               for (int i = 0; i < currentShip.getSize(); i++)
               {
                  int x = coordinates[0] + i;
                  boolean has = Arrays.stream(ship.getAsRawData()).anyMatch(c -> c != null && c[0] == x && c[1] == coordinates[1]);
                  if (has)
                  {
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

}
