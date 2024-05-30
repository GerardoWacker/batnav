package batnav.automated.variant;

import batnav.online.model.Ship;

import java.util.ArrayList;
import java.util.List;

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

   public static List<Ship> getRowDefence()
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

      ships.get(0).setPosition(2, 1);
      ships.get(1).setPosition(2, 3);
      ships.get(2).setPosition(1, 7);
      ships.get(3).setPosition(4, 1);
      ships.get(4).setPosition(4, 3);
      ships.get(5).setPosition(1, 5);
      ships.get(6).setPosition(4, 5);
      ships.get(7).setPosition(3, 7);

      return ships;
   }
}
