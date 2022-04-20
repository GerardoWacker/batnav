package batnav.instance;

import batnav.config.ConfigManager;
import batnav.online.match.MatchManager;
import batnav.notifications.NotificationManager;
import batnav.online.session.SessionManager;
import batnav.online.socket.Connection;
import batnav.ui.screens.LoginScreen;
import batnav.ui.screens.MainMenuScreen;
import batnav.ui.screens.TestScreen;
import batnav.utils.Logger;
import batnav.utils.RestUtils;
import batnav.utils.Sambayon;
import com.sun.tools.javac.Main;

import java.awt.event.WindowEvent;

public class Game
{
   private final Sambayon sambayon;
   private final ConfigManager configManager;
   private final NotificationManager notificationManager;
   private RestUtils restUtils;
   private SessionManager sessionManager;
   private Connection connection;
   private MatchManager matchManager;
   private MainMenuScreen mainMenuScreen;
   private static Game instance;

   /**
    * batnav, a naval battle simulator.
    *
    * @author Gerardo Wacker
    * @author Juan Ignacio Vecchio
    * @author Matías Mena Da Dalt
    */
   public Game()
   {
      this.notificationManager = new NotificationManager();
      this.sambayon = new Sambayon();
      this.configManager = new ConfigManager();
   }

   /**
    * Launches the game.
    */
   public void launch() throws Exception
   {
      this.mainMenuScreen = new MainMenuScreen();
      this.mainMenuScreen.setDisplayString("Conectando con Sambayón");

      // Verify if server is accesible
      if (this.sambayon.isAccesible())
      {
         Logger.log("Se pudo establecer una conexión con Sambayón.");

         this.mainMenuScreen.setDisplayString("Creando managers");

         // Create handlers.
         this.matchManager = new MatchManager(this.sessionManager);
         this.restUtils = new RestUtils(this.sambayon);
         this.sessionManager = new SessionManager(this.restUtils, this.configManager);
         this.connection = new Connection(this.sambayon, this.sessionManager, this.matchManager);

         this.mainMenuScreen.setDisplayString("Cargando sesión");

         // Load session.
         this.sessionManager.loadSession();

         this.mainMenuScreen.setDisplayString("Conectando con el servidor");

         // Connect to real-time server.
         this.connection.connect(this.sessionManager.getSessionId());

         this.mainMenuScreen.setDisplayString("Conectado correctamente al servidor.");

         if(this.sessionManager.getSessionId() == null || this.connection.getCurrentUser() == null)
         {
            new LoginScreen();
         }
      } else
      {
         this.mainMenuScreen.displayFailure("No se pudo conectar con el servidor");
      }
   }

   public Sambayon getSambayon()
   {
      return sambayon;
   }

   public ConfigManager getConfigManager()
   {
      return configManager;
   }

   public RestUtils getRestUtils()
   {
      return restUtils;
   }

   public SessionManager getSessionManager()
   {
      return sessionManager;
   }

   public Connection getConnection()
   {
      return connection;
   }

   public MatchManager getMatchManager()
   {
      return matchManager;
   }

   public NotificationManager getNotificationManager()
   {
      return notificationManager;
   }

   public static Game getInstance()
   {
      return instance == null ? instance = new Game() : instance;
   }
}
