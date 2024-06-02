package batnav.automated.variant;

import batnav.online.model.Bomb;
import batnav.online.socket.Connection;

import java.util.ArrayList;
import java.util.List;

public class CrossAttack
{
   public List<int[]> bombsToThrow;

   public CrossAttack()
   {
      this.bombsToThrow = new ArrayList<>();
      this.bombsToThrow.add(new int[]{0, 0});
      this.bombsToThrow.add(new int[]{1, 1});
      this.bombsToThrow.add(new int[]{2, 2});
      this.bombsToThrow.add(new int[]{3, 3});
      this.bombsToThrow.add(new int[]{4, 4});
      this.bombsToThrow.add(new int[]{5, 5});
      this.bombsToThrow.add(new int[]{6, 6});
      this.bombsToThrow.add(new int[]{7, 7});
      this.bombsToThrow.add(new int[]{8, 8});
      this.bombsToThrow.add(new int[]{9, 9});
      this.bombsToThrow.add(new int[]{0, 9});
      this.bombsToThrow.add(new int[]{1, 8});
      this.bombsToThrow.add(new int[]{2, 7});
      this.bombsToThrow.add(new int[]{3, 6});
      this.bombsToThrow.add(new int[]{4, 5});
      this.bombsToThrow.add(new int[]{5, 4});
      this.bombsToThrow.add(new int[]{6, 3});
      this.bombsToThrow.add(new int[]{7, 2});
      this.bombsToThrow.add(new int[]{8, 1});
      this.bombsToThrow.add(new int[]{9, 9});
   }

   public void receiveBomb(final Connection connection)
   {

   }
}
