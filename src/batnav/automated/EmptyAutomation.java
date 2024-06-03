package batnav.automated;

import batnav.online.socket.Connection;
import batnav.ui.screens.MainMenuScreen;
import batnav.ui.screens.ResultsScreen;

public class EmptyAutomation implements Automation
{
   @Override
   public void injectLogin(final Connection connection)
   {

   }

   @Override
   public void injectMainMenu(final MainMenuScreen mainMenuScreen)
   {

   }

   @Override
   public void injectSetShips()
   {

   }

   @Override
   public void injectBombSequence()
   {

   }

   @Override
   public void injectMatchEnd()
   {

   }

   @Override
   public String getDescriptor()
   {
      return "Just an empty injection";
   }
}
