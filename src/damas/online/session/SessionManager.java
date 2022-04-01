package damas.online;

import damas.utils.RestUtils;

public class SessionManager
{
   private final RestUtils restUtils;

   /**
    * Handler used for sessions and authentication purposes
    * @param restUtils Rest utils.
    */
   public SessionManager(RestUtils restUtils)
   {
      this.restUtils = restUtils;
   }

   public void login(final String username, final String password)
   {

   }
}
