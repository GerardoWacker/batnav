package batnav.online.match;

import com.google.common.collect.Lists;
import batnav.online.session.User;

import java.util.List;

public class Match
{
   private final String id;
   private final User opponent;

   private final List<Bomb> playerBombs, opponentBombs;

   public Match(String id, User opponent)
   {
      this.id = id;
      this.opponent = opponent;
      this.playerBombs = Lists.newArrayList();
      this.opponentBombs = Lists.newArrayList();
   }

}
