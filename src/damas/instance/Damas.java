package damas.instance;

import damas.config.ConfigManager;
import damas.online.session.SessionManager;
import damas.online.session.User;
import damas.online.socket.Connection;
import damas.utils.Logger;
import damas.utils.RestUtils;
import damas.utils.Sambayon;

public class Damas
{
   private final Sambayon sambayon;
   private final ConfigManager configManager;
   private RestUtils restUtils;
   private SessionManager sessionManager;
   private Connection connection;

   /**
    * Damas, a checkers game.
    * Made as a final project for Programming class.
    *
    * @author Gerardo Wacker
    * @author Juan Ignacio Vecchio
    */
   public Damas()
   {
      this.sambayon = new Sambayon();
      this.configManager = new ConfigManager();
   }

   /**
    * Launches the game.
    */
   public void launch() throws Exception
   {
      // Verify if server is accesible
      if (this.sambayon.isAccesible())
      {
         Logger.log("Se pudo establecer una conexión con Sambayón.");

         // Create handlers.
         this.restUtils = new RestUtils(this.sambayon);
         this.sessionManager = new SessionManager(this.restUtils, this.configManager);
         this.connection = new Connection(this.sambayon, this.sessionManager);

         // Load session.
         this.sessionManager.loadSession();

         // Connect to real-time server.
         this.connection.connect(this.sessionManager.getSessionId());
      }
   }
}
