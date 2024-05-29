package batnav.automated.variant;

import batnav.automated.Automation;
import batnav.instance.Game;
import batnav.online.model.Packet;
import batnav.online.model.Ship;

import java.util.ArrayList;
import java.util.List;

public class AutomationGenerator
{

   public Automation generate(final DefenceType defenceType, AttackType attackType)
   {
      return new Automation()
      {
         @Override
         public void injectLogin()
         {
            try
            {
               if (Game.getInstance().getSessionManager().login("usuario1", "asdfghjkl"))
               {
                  Game.getInstance().getConnection().sendPacket(
                          new Packet("authenticate", Game.getInstance().getSessionManager().getSessionId())
                  );
               }
            } catch (Exception e)
            {
               e.printStackTrace();
            }
         }

         @Override
         public void injectMainMenu()
         {
            Game.getInstance().getMainMenuScreen().startMatchmaking();
         }

         @Override
         public void injectSetShips()
         {
            List<Ship> ships;
            switch (defenceType)
            {
               case MIDDLE -> ships = Defences.getMiddleDefence();
               case CORNER -> ships = Defences.getCornerDefence();
               case RANDOM -> ships = Defences.getRowDefence();
               default -> ships = new ArrayList<>();
            }

            Game.getInstance().getMatchManager().setShips(
                    Game.getInstance().getConnection(),
                    ships
            );
            Game.getInstance().getMatchManager().getCurrentMatch().getMatchScreen().setVisible(true);
         }

         @Override
         public void injectBombSequence()
         {

         }
      };
   }

   public enum DefenceType
   {
      RANDOM, CORNER, MIDDLE,
   }

   public enum AttackType
   {
      RANDOM, RANDOM_PREF, CROSS, CROSS_PREF
   }
}
