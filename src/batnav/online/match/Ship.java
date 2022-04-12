package batnav.online.match;

import com.google.common.collect.Lists;

import java.util.List;

public class Ship
{
   private final int size;
   private int x, y;
   private boolean vertical;
   private Status status;

   /**
    * A ship. Will also be used for UI purposes, and to facilitate data handling.
    *
    * @param size Size of the ship to be created
    */
   public Ship(int size)
   {
      this.size = size;
      this.status = Status.ALIVE;
   }

   /**
    * Sets the ship position;
    *
    * @param x        Position in the X axis.
    * @param y        Position in the Y axis.
    * @param vertical Is the ship vertically positioned
    */
   public Ship setPosition(final int x, final int y, final boolean vertical)
   {
      this.x = x;
      this.y = y;
      this.vertical = vertical;
      return this;
   }

   /**
    * Gets the ship position. Will be used to send information to the server.
    *
    * @return A list of several coordinates.
    */
   public List<int[]> getAsRawData()
   {
      final List<int[]> rawData = Lists.newArrayList();

      // Populates `rawData` accordingly.
      if (this.vertical)
      {
         for (int i = 0; i < this.size; i++)
         {
            rawData.add(new int[]{this.x, this.y + i});
         }
      } else
      {
         for (int i = 0; i < this.size; i++)
         {
            rawData.add(new int[]{this.x + i, this.y});
         }
      }
      return rawData;
   }

   /**
    * Sets the ship as dead.
    */
   public void setDead()
   {
      this.status = Status.DEAD;
   }

   public int getSize()
   {
      return size;
   }

   public int getX()
   {
      return x;
   }

   public int getY()
   {
      return y;
   }

   public boolean isVertical()
   {
      return vertical;
   }

   public Status getStatus()
   {
      return status;
   }

   /**
    * Ship status.
    */
   enum Status
   {
      ALIVE, DEAD
   }
}
