package damas.online.match;

import damas.online.socket.Connection;

public class MatchManager
{
   private final Connection connection;

   public MatchManager(Connection connection)
   {
      this.connection = connection;
   }
}
