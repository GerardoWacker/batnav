package batnav.instance;

import batnav.automated.Automation;
import batnav.automated.EmptyAutomation;
import batnav.config.ConfigManager;
import batnav.online.match.MatchManager;
import batnav.notifications.NotificationManager;
import batnav.online.session.SessionManager;
import batnav.online.socket.Connection;
import batnav.ui.screens.LoginScreen;
import batnav.ui.screens.MainMenuScreen;
import batnav.ui.screens.SplashScreen;
import batnav.utils.FontUtil;
import batnav.utils.Logger;
import batnav.utils.RestUtils;
import batnav.utils.Sambayon;

public class Game
{
   private final Sambayon sambayon;
   private final ConfigManager configManager;
   private final NotificationManager notificationManager;
   private RestUtils restUtils;
   private SessionManager sessionManager;
   private Connection connection;
   private MatchManager matchManager;
   private final FontUtil fontUtil;
   private SplashScreen splashScreen;
   private static Game instance;
   private MainMenuScreen mainMenuScreen;
   private LoginScreen loginScreen;
   private boolean done = false;
   private boolean authNeeded = false;
   private Automation injection;

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
      this.fontUtil = new FontUtil();
   }

   /**
    * Launches the game with no automations.
    */
   public void launch()
   {
      this.launch(new EmptyAutomation());
   }

   /**
    * Launches the game.
    */
   public void launch(final Automation automation)
   {
      // Check if an injection has been loaded.
      this.injection = automation;
      Logger.log((this.hasInjection() ? "Hay" : "No hay") + " una inyección en curso.");
      if (this.hasInjection()) Logger.log(this.getInjection().getClass().getName() + ": [" + this.getInjection().getDescriptor() + "]");

      this.splashScreen = new SplashScreen();
      this.splashScreen.setDisplayString("Conectando con Sambayón");

      // Verify if server is accesible
      if (!this.sambayon.isAccesible())
      {
         // Activate backup server instance.
         this.sambayon.setFallbackMode(true);
      }

      Logger.log("Se pudo establecer una conexión con Sambayón.");

      this.splashScreen.setDisplayString("Creando managers");

      // Create handlers.
      this.restUtils = new RestUtils(this.sambayon);
      this.sessionManager = new SessionManager(this.restUtils, this.configManager);
      this.matchManager = new MatchManager(this.sessionManager);
      this.connection = new Connection(this.sambayon, this.sessionManager, this.matchManager);

      this.splashScreen.setDisplayString("Cargando sesión");

      // Load session.
      this.sessionManager.loadSession();

      this.splashScreen.setDisplayString("Cargada sesión correctamente.");

      // Connect to real-time server.
      this.connection.connect(this.sessionManager.getSessionId());

      this.splashScreen.setDisplayString("Iniciando...");

      this.loginScreen = new LoginScreen();
      this.loginScreen.setVisible(false);

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

   public SplashScreen getSplashScreen()
   {
      return splashScreen;
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

   public MainMenuScreen getMainMenuScreen()
   {
      return mainMenuScreen;
   }

   public LoginScreen getLoginScreen()
   {
      return loginScreen;
   }

   public FontUtil getFontUtil()
   {
      return fontUtil;
   }

   public void setMainMenuScreen(MainMenuScreen mainMenuScreen)
   {
      this.mainMenuScreen = mainMenuScreen;
   }

   public static Game getInstance()
   {
      return instance == null ? instance = new Game() : instance;
   }

   public boolean isDone()
   {
      return done;
   }

   public boolean isAuthNeeded()
   {
      return authNeeded;
   }

   public void setAuthNeeded(boolean authNeeded)
   {
      this.authNeeded = authNeeded;
   }

   public Automation getInjection()
   {
      return injection;
   }

   public void setDone(boolean done)
   {
      this.done = done;
   }

   public boolean hasInjection()
   {
      return !(this.injection instanceof EmptyAutomation);
   }
}
