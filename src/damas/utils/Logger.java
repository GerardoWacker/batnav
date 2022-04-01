package damas.utils;

public class Logger
{
   public static void log(final String text)
   {
      System.out.println("[LOG] " + text);
   }

   public static void warn(final String text)
   {
      System.out.println("[WARN] " + text);
   }

   public static void err(final String text)
   {
      System.out.println("[ERROR] " + text);
   }
}