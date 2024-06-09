package batnav.automated.variant;

import batnav.automated.Automation;
import batnav.automated.variant.attack.Attack;
import batnav.automated.variant.attack.CrossAttack;
import batnav.automated.variant.attack.CrossRandomAttack;
import batnav.automated.variant.attack.RandomAttack;
import batnav.instance.Game;
import batnav.online.model.Packet;
import batnav.online.model.Ship;
import batnav.online.socket.Connection;
import batnav.ui.screens.MainMenuScreen;
import batnav.utils.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AutomationGenerator
{
   public static Automation generate(final String username, final DefenceType defenceType, AttackType attackType)
   {
      final Attack attack = switch (attackType)
      {
         case CROSS -> new CrossAttack();
         case RANDOM -> new RandomAttack(false);
         case RANDOM_PREF -> new RandomAttack(true);
         case CROSS_PREF -> new CrossRandomAttack();
      };
      return new Automation()
      {
         @Override
         public void injectLogin(final Connection connection)
         {
            Logger.log("Se inyectó secuencia de inicio de sesión.");
            Game.getInstance().getSplashScreen().setVisible(false);
            try
            {
               if (Game.getInstance().getSessionManager().login(username, "asdfghjkl"))
               {
                  Game.getInstance().getConnection().sendPacket(
                          new Packet("authenticate", Game.getInstance().getSessionManager().getSessionId())
                  );
                  Game.getInstance().getLoginScreen().setVisible(false);
               }
            } catch (Exception e)
            {
               e.printStackTrace();
            }
         }

         @Override
         public void injectMainMenu(final MainMenuScreen mainMenuScreen)
         {
            mainMenuScreen.startMatchmaking();
         }

         @Override
         public void injectSetShips()
         {
            List<Ship> ships;
            switch (defenceType)
            {
               case MIDDLE -> ships = Defences.getMiddleDefence();
               case CORNER -> ships = Defences.getCornerDefence();
               case ROW -> ships = Defences.getRowDefence();
               case RANDOM -> ships = Defences.getRandomDefence();
               default -> ships = new ArrayList<>();
            }

            System.out.println(Arrays.toString(ships.toArray()));
            Game.getInstance().getMatchManager().setShips(
                    Game.getInstance().getConnection(),
                    ships
            );

            attack.startAttack();
            Game.getInstance().getMatchManager().getCurrentMatch().getMatchScreen().setVisible(true);
         }

         @Override
         public void injectBombSequence()
         {
            Logger.log("Se recibió una bomba");
            attack.receiveBomb();
         }

         @Override
         public void injectBombHit(boolean hit)
         {
            attack.setBombHit(hit);
         }

         @Override
         public void injectShipSunk()
         {
            attack.setShipSunk();
         }

         @Override
         public void injectMatchEnd()
         {
            Game.getInstance().getMainMenuScreen().displayMainMenu();
            Game.getInstance().getMainMenuScreen().setVisible(true);
         }

         @Override
         public void injectStartMatch()
         {
            attack.receiveBomb();
         }

         @Override
         public String getDescriptor()
         {
            return "Inicio de sesión: " + username + ". Estrategia de defensa: " + defenceType.name() + ". Estrategia de ataque: " + attackType.name() + ".";
         }
      };
   }

   public enum DefenceType
   {
      RANDOM, CORNER, MIDDLE, ROW
   }

   public enum AttackType
   {
      RANDOM, RANDOM_PREF, CROSS, CROSS_PREF
   }
}
