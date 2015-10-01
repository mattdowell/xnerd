package org.dowell.xnerd.client;

import org.dowell.xnerd.client.controller.WantedGamesController;
import org.dowell.xnerd.client.controller.menu.MenuCommands;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * GWT Entry class. This is the first class that is called.
 * 
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Xnerd implements EntryPoint {

	/**
	 * This is the entry point method. It builds the background screen
	 * (menu) and directs us to our first screen, which is the 
	 * "Wanted" games.
	 * 
	 * @wbp.parser.entryPoint
	 */
	@Override
	public void onModuleLoad() {
		
		// Configure and clear the panel
		RootPanel menuPanel = RootPanel.get("menubar");
		menuPanel.clear();
		menuPanel.setSize("100%", "100%");

		// Build the menu bar(s)
		MenuBar menuBar = new MenuBar(false);
		MenuBar menuBar_2 = new MenuBar(true);

		MenuItem mnuGames = new MenuItem("Games", false, menuBar_2);
		mnuGames.setHTML("Games");

		MenuItem mnuWantdGames = new MenuItem("Wanted Games", false, MenuCommands.getShowWantedGamesCommand());
		menuBar_2.addItem(mnuWantdGames);

		MenuItem mnuOwnedGame = new MenuItem("Owned Games", false, MenuCommands.getShowOwnedGamesCommand());
		menuBar_2.addItem(mnuOwnedGame);

		menuBar.addItem(mnuGames);
		menuPanel.add(menuBar);

		MenuBar menuBar_3 = new MenuBar(true);
		MenuItem mntmAdmin = new MenuItem("Admin", false, menuBar_3);
		MenuItem mnuAddGame = new MenuItem("Add New Game", false, MenuCommands.getNewTitleCommand());
		menuBar_3.addItem(mnuAddGame);

		menuBar.addItem(mntmAdmin);
		
		showWelcomeScreen();
	}
	
	/**
	 * This is the default, opening screen
	 */
	public static void showWelcomeScreen() {
		// Show the first screen, which is "wanted games"
		WantedGamesController startHere = new WantedGamesController("Games we want, with voting goodness!");
		startHere.show();		
	}
}
