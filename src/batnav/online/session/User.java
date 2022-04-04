package batnav.online.session;

public class User
{
   private final String username, country, created;
   private final int plays, elo;
   private final boolean developer;

   /**
    * Object that represents a user. Is used for identification, session and network playing functionalities.
    *
    * @param username  Name of the user. Will ALWAYS be lower case.
    * @param country   ISO country code representing the country from registration.
    * @param created   ISO date format String representing momento of account creation.
    * @param plays     Number of played matches.
    * @param elo       ELO/Ranking score for the player.
    * @param developer Is the user a developer for the game.
    */
   public User(String username, String country, String created, int plays, int elo, boolean developer)
   {
      this.username = username;
      this.country = country;
      this.created = created;
      this.plays = plays;
      this.elo = elo;
      this.developer = developer;
   }
   /**
    * Object that represents a user. Is used for identification, session and network playing functionalities.
    *
    * @param username  Name of the user. Will ALWAYS be lower case.
    * @param country   ISO country code representing the country from registration.
    * @param plays     Number of played matches.
    * @param elo       ELO/Ranking score for the player.
    * @param developer Is the user a developer for the game.
    */
   public User(String username, String country, int plays, int elo, boolean developer)
   {
      this.username = username;
      this.country = country;
      this.created = null;
      this.plays = plays;
      this.elo = elo;
      this.developer = developer;
   }

   public String getUsername()
   {
      return username;
   }

   public String getCountry()
   {
      return country;
   }

   public String getCreated()
   {
      return created;
   }

   public int getPlays()
   {
      return plays;
   }

   public int getElo()
   {
      return elo;
   }

   public boolean isDeveloper()
   {
      return developer;
   }
}
