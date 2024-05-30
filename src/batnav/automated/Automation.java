package batnav.automated;

import batnav.online.socket.Connection;
import batnav.ui.screens.MainMenuScreen;
import batnav.ui.screens.ResultsScreen;

public interface Automation
{
   void injectLogin(final Connection connection);
   void injectMainMenu(final MainMenuScreen mainMenuScreen);
   void injectSetShips();
   void injectBombSequence();
   void injectMatchEnd(final ResultsScreen resultsScreen);
   String getDescriptor();
}
