package batnav.automated.variant.attack;

import batnav.utils.Logger;

import java.util.LinkedList;
import java.util.List;

public class PreferenceHandler
{
   private int x, y;
   private Status status = Status.DISABLED;
   public List<int[]> coordinates;
   public int[] lastBomb;

   private final boolean enabled;

   public PreferenceHandler(final boolean enabled)
   {
      this.enabled = enabled;
   }

   public void begin(boolean[][] bombMap, final int x, final int y)
   {
      if (!this.enabled) return;
      this.coordinates = new LinkedList<>();
      this.setX(x);
      this.setY(y);

      Logger.log("Generando cruz en las coordenadas [" + this.getX() + ", " + this.getY() + "]");
      this.generateCross(bombMap, this.getX(), this.getY());

      Logger.log("Comenzado el polling desde las coordenadas [" + this.getX() + ", " + this.getY() + "]");

      this.setStatus(Status.POLLING);
   }

   public int[] handle(final int x, final int y)
   {
      if (!this.enabled) return new int[]{x, y};
      switch (this.getStatus())
      {
         default ->
         {
            final int[] coords = new int[]{x, y};
            this.lastBomb = coords;
            return coords;
         }
         case POLLING, FOUND_VERTICAL, FOUND_HORIZONTAL, UP, DOWN, LEFT, RIGHT ->
         {
            if(this.coordinates.isEmpty())
               this.setStatus(Status.DISABLED);
            final int[] coords = this.getAndRemove();
            this.lastBomb = new int[]{coords[0], coords[1]};
            Logger.log("Se devolvieron las coordenadas [" + coords[0] + ", " + coords[1] + "]");
            return coords;
         }
      }
   }

   public void setBombHit(boolean[][] bombMap, boolean hit)
   {
      if (!this.enabled) return;
      switch (this.getStatus())
      {
         case DISABLED ->
         {
            if (hit)
            {
               this.setStatus(Status.IDLE);
               this.begin(bombMap, this.lastBomb[0], this.lastBomb[1]);
            }
         }
         case POLLING ->
         {
            if (hit)
            {
               if (this.lastBomb[1] == this.getY())
               {
                  this.setStatus(Status.FOUND_HORIZONTAL);
                  this.coordinates.clear();
                  Logger.log("Limpiadas las coordenadas almacenadas.");
                  this.generateHorizontalLine(bombMap, this.getX(), this.getY());
               } else if (this.lastBomb[0] == this.getX())
               {
                  this.setStatus(Status.FOUND_VERTICAL);
                  this.coordinates.clear();
                  Logger.log("Limpiadas las coordenadas almacenadas.");
                  this.generateVerticalLine(bombMap, this.getX(), this.getY());
               }
            }
         }
         case FOUND_VERTICAL ->
         {
            if (!hit)
            {
               if (this.lastBomb[1] > this.getY())
               {
                  this.setStatus(Status.UP);
                  this.coordinates.clear();
                  Logger.log("Limpiadas las coordenadas almacenadas.");
                  this.generateUp(bombMap, this.getX(), this.getY());
               } else if (this.lastBomb[1] < this.getY())
               {
                  this.setStatus(Status.DOWN);
                  this.coordinates.clear();
                  Logger.log("Limpiadas las coordenadas almacenadas.");
                  this.generateDown(bombMap, this.getX(), this.getY());
               }
            }
         }
         case FOUND_HORIZONTAL ->
         {
            if (!hit)
            {
               if (this.lastBomb[0] > this.getX())
               {
                  this.setStatus(Status.LEFT);
                  this.coordinates.clear();
                  Logger.log("Limpiadas las coordenadas almacenadas.");
                  this.generateLeft(bombMap, this.getX(), this.getY());
               } else if (this.lastBomb[0] < this.getX())
               {
                  this.setStatus(Status.RIGHT);
                  this.coordinates.clear();
                  Logger.log("Limpiadas las coordenadas almacenadas.");
                  this.generateRight(bombMap, this.getX(), this.getY());
               }
            }
         }
         case UP, DOWN, LEFT, RIGHT ->
         {
            if (!hit)
            {
               this.destroy();
            }
         }
      }
   }

   public void destroy()
   {
      this.setStatus(Status.DISABLED);
      if(this.coordinates != null)
         this.coordinates.clear();
      this.setX(0);
      this.setY(0);
   }

   public int[] getAndRemove()
   {
      Logger.log("Hay " + this.coordinates.size() + " coordenadas en la lista.");

      int[] coords = this.coordinates.get(0);
      Logger.log("Sobreescribiendo valor y enviando [" + coords[0] + ", " + coords[1] + "]");
      this.coordinates.remove(0);
      return coords;
   }

   private void generateAvailableCoordinates(int x, int y)
   {
      if (checkBounds(x, y))
         this.coordinates.add(new int[]{x, y});
   }

   private void generateCross(boolean[][] bombMap, int x, int y)
   {
      if (bombMapContains(bombMap, x - 1, y))
         this.generateAvailableCoordinates(x - 1, y);
      if (bombMapContains(bombMap, x + 1, y))
         this.generateAvailableCoordinates(x + 1, y);
      if (bombMapContains(bombMap, x, y - 1))
         this.generateAvailableCoordinates(x, y - 1);
      if (bombMapContains(bombMap, x, y + 1))
         this.generateAvailableCoordinates(x, y + 1);
   }


   private void generateVerticalLine(boolean[][] bombMap, final int x, final int y)
   {
      for (int i = 1; i < 8; i++)
      {
         if (bombMapContains(bombMap, x, y + i))
            this.generateAvailableCoordinates(x, y + i);
         if (bombMapContains(bombMap, x, y - i))
            this.generateAvailableCoordinates(x, y - i);
      }
   }

   private void generateHorizontalLine(boolean[][] bombMap, final int x, final int y)
   {
      for (int i = 1; i < 8; i++)
      {
         if (bombMapContains(bombMap, x + i, y))
            this.generateAvailableCoordinates(x + i, y);
         if (bombMapContains(bombMap, x - i, y))
            this.generateAvailableCoordinates(x - i, y);
      }
   }

   private void generateDown(boolean[][] bombMap, final int x, final int y)
   {
      for (int i = 1; i < 8; i++)
      {
         if (bombMapContains(bombMap, x, y + i))
         {
            Logger.log("Se generaron las coordenadas [" + x + ", " + (y + i) + "]");
            this.generateAvailableCoordinates(x, y + i);
         }
      }
   }

   private void generateUp(boolean[][] bombMap, final int x, final int y)
   {
      for (int i = 1; i < 8; i++)
      {
         if (bombMapContains(bombMap, x, y - i))
         {
            Logger.log("Se generaron las coordenadas [" + x + ", " + (y - i) + "]");
            this.generateAvailableCoordinates(x, y - i);
         }
      }
   }

   private void generateLeft(boolean[][] bombMap, final int x, final int y)
   {
      for (int i = 1; i < 8; i++)
      {
         if (bombMapContains(bombMap, x - i, y))
         {
            Logger.log("Se generaron las coordenadas [" + (x - i) + ", " + y + "]");
            this.generateAvailableCoordinates(x - i, y);
         }
      }
   }

   private void generateRight(boolean[][] bombMap, final int x, final int y)
   {
      for (int i = 1; i < 8; i++)
      {
         if (bombMapContains(bombMap, x + i, y))
         {
            Logger.log("Se generaron las coordenadas [" + (x + i) + ", " + y + "]");
            this.generateAvailableCoordinates(x + i, y);
         }
      }
   }

   private boolean bombMapContains(boolean[][] bombMap, final int x, final int y)
   {
      return checkBounds(x, y) && (!bombMap[x][y]);
   }

   private boolean checkBounds(int x, int y)
   {
      return (x >= 0 && x < 10 && y >= 0 && y < 10);
   }

   public int getX()
   {
      return x;
   }

   public void setX(int x)
   {
      this.x = x;
   }

   public int getY()
   {
      return y;
   }

   public void setY(int y)
   {
      this.y = y;
   }

   public Status getStatus()
   {
      return status;
   }

   public void setStatus(Status status)
   {
      switch (status)
      {
         case FOUND_HORIZONTAL, FOUND_VERTICAL, UP, DOWN, LEFT, RIGHT:
            break;
      }
      Logger.log("Se cambió el status de preferencia a " + status.name());
      this.status = status;
   }

   public enum Status
   {
      DISABLED, IDLE, POLLING, FOUND_VERTICAL, FOUND_HORIZONTAL, UP, RIGHT, DOWN, LEFT;
   }
}
