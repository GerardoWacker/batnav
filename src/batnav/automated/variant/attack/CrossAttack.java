package batnav.automated.variant.attack;

import batnav.instance.Game;
import batnav.utils.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CrossAttack extends Attack
{
   public List<int[]> bombsToThrow;

   public CrossAttack()
   {
      this.bombsToThrow = new ArrayList<>();
      for (int i = 0; i < 10; i++)
      {
         this.bombsToThrow.add(new int[]{i, i});
      }

      for (int i = 0; i < 10; i++)
      {
         this.bombsToThrow.add(new int[]{i, 9 - i});
      }

      for (int x = 0; x < 9; x++)
      {
         for (int y = 0; y <= x; y++)
         {
            if (x - y != y)
               this.bombsToThrow.add(new int[]{x - y, y});
         }
      }
   }

   @Override
   public void receiveBomb()
   {
      if (!bombsToThrow.isEmpty())
      {
         Game.getInstance().getMatchManager().throwBomb(Game.getInstance().getConnection(),
              this.bombsToThrow.get(0)[0], this.bombsToThrow.get(0)[1]);
         this.bombsToThrow.remove(0);
         Logger.log("Se arrojó una bomba");
      }
   }
}
