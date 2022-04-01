package gerar.clase4.agenda;

public class Logger
{
   private final boolean enabled;

   public Logger(final boolean enabled)
   {
      this.enabled = enabled;
   }

   public void log(final String texto)
   {
      if(enabled) System.out.println("[LOG] " + texto);
   }

   public void warn(final String texto)
   {
      System.out.println("[WARN] " + texto);
   }

   public void err(final String texto)
   {
      System.out.println("[ERROR] " + texto);
   }
}