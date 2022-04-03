package batnav.online.match;

public class Bomb
{
   private final int x, y;

   /**
    * Throwable bomb. Kaboom!
    * @param x Position in the X axis.
    * @param y Position in the Y axis.
    */
   public Bomb(int x, int y)
   {
      this.x = x;
      this.y = y;
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
