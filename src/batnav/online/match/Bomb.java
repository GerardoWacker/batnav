package batnav.online.match;

public class Bomb
{
   private final int x, y;
   private final boolean opponent, hasHit;

   /**
    * Throwable bomb. Kaboom!
    *
    * @param x        Position in the X axis.
    * @param y        Position in the Y axis.
    * @param opponent Is the bomb from the opponent.
    * @param hasHit   Has the bomb hit a target.
    */
   public Bomb(int x, int y, boolean opponent, boolean hasHit)
   {
      this.x = x;
      this.y = y;
      this.opponent = opponent;
      this.hasHit = hasHit;
   }

   /**
    * Gets the bomb position. Will be used to send information to the server.
    *
    * @return The bomb's coordinates.
    */
   public int[] getAsRawData()
   {
      return new int[]{this.x, this.y};
   }
}
