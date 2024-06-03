package batnav.automated;

import batnav.online.socket.Connection;
import batnav.ui.screens.MainMenuScreen;

public interface Automation
{
   void injectLogin(final Connection connection);
   void injectMainMenu(final MainMenuScreen mainMenuScreen);
   void injectSetShips();
   void injectBombSequence();
   void injectMatchEnd();
   String getDescriptor();
}
