package damas.instance;

import damas.config.ConfigManager;
import damas.online.session.SessionManager;
import damas.utils.Logger;
import damas.utils.RestUtils;
import damas.utils.Sambayon;

public class Damas
{
   private final Sambayon sambayon;
   private final RestUtils restUtils;
   private final SessionManager sessionManager;
   private final ConfigManager configManager;

   /**
    * Damas, a checkers game.
    * Made as a final project for Programming class.
    * @author Gerardo Wacker
    * @author Juan Ignacio Vecchio
    */
   public Damas()
   {
      this.sambayon = new Sambayon();
      this.configManager = new ConfigManager();
      this.restUtils = new RestUtils(this.sambayon);
      this.sessionManager = new SessionManager(this.restUtils, this.configManager);
   }

   /**
    * Launches the game.
    */
   public void launch()
   {
      // Verify if server is accesible
      if(this.sambayon.isAccesible())
      {
         Logger.log("Se pudo establecer una conexión con Sambayón.");

         // Load session
         this.sessionManager.loadSession();

      }
   }
}
