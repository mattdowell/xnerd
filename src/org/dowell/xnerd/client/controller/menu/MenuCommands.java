package org.dowell.xnerd.client.controller.menu;

import org.dowell.xnerd.client.controller.NewTitleController;
import org.dowell.xnerd.client.controller.OwnedGamesController;
import org.dowell.xnerd.client.controller.WantedGamesController;

import com.google.gwt.user.client.Command;

/**
 * This is a single binding class for all the menu commands.
 * 
 * @author Matt
 *
 */
public class MenuCommands {

	@Deprecated
	public static Command getNullCommand() {
	
		return new Command() {
			@Override
			public void execute() {
			}			
		};		
	}
	
	/**
	 * @return Command to executed the owned games controller
	 */
	public static Command getShowOwnedGamesCommand() {
		return new Command() {
			@Override
			public void execute() {
				OwnedGamesController c = new OwnedGamesController();
				c.show();
			}			
		};		
	}	
	
	/**
	 * @return Command to executed the owned games controller
	 */
	public static Command getShowWantedGamesCommand() {
		return new Command() {
			@Override
			public void execute() {
				WantedGamesController c = new WantedGamesController("Games we want!");
				c.show();
			}			
		};		
	}		
	
	/**
	 * @return Command to executed the owned games controller
	 */
	public static Command getNewTitleCommand() {
		return new Command() {
			@Override
			public void execute() {
				NewTitleController c = new NewTitleController();
				c.begin();
			}			
		};		
	}			
}
