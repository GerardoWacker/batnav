package batnav.ui.components;

import batnav.utils.Logger;

import javax.swing.*;

public abstract class AnimatedLabel extends JLabel implements Runnable
{
   protected Thread animationThread;
   protected final int delay;

   /**
    * Created an animated component.
    *
    * @param frames Frames per second.
    */
   public AnimatedLabel(int frames)
   {
      this.delay = 1000 / frames;
   }

   /**
    * Starts the animation.
    */
   public void start()
   {
      this.animationThread = new Thread(this);
      this.animationThread.start();
   }

   /**
    * Updates the component every tick.
    */
   public abstract void update();

   /**
    * Stops the animation.
    */
   public void stop()
   {
      this.animationThread = null;
   }

   public Thread getAnimationThread()
   {
      return animationThread;
   }

   public void setAnimationThread(Thread animationThread)
   {
      this.animationThread = animationThread;
   }

   public int getDelay()
   {
      return delay;
   }

   @Override
   public void run()
   {
      while (Thread.currentThread() == this.animationThread)
      {
         try
         {
            Thread.sleep(this.delay);
         } catch (InterruptedException e)
         {
            Logger.err(e.getLocalizedMessage());
         }
         this.update();
         this.repaint();
      }
   }
}
