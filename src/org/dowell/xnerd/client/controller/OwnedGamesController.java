package org.dowell.xnerd.client.controller;

import java.util.List;

import org.dowell.xnerd.client.screen.GameListScreen;
import org.dowell.xnerd.client.service.GameService;
import org.dowell.xnerd.client.service.GameServiceAsync;
import org.dowell.xnerd.client.util.Callback;
import org.dowell.xnerd.shared.entities.Game;

import com.google.gwt.core.client.GWT;
import com.google.gwt.view.client.ListDataProvider;

/**
 * This class will control the displaying of 
 * "owned" games. It can be called from anywhere. It 
 * fetches the data and builds the proper screen.
 * 
 * @author Matt
 *
 */
public class OwnedGamesController {
	
	GameListScreen myScreen = null;
	GameServiceAsync gameService = GWT.create(GameService.class);
	GameListScreen.Context myContext;
	String myTitle = "Games we currently own";
	
	public OwnedGamesController() {
	}
	
	/**
	 * The show() method will asynchronisly call the backend 
	 * service and when the call finally returns, then it will
	 * build and show the screen.
	 */
	public void show() {
		gameService.getGamesCurrentlyOwned(new Callback<List<Game>>() {

			@Override
			public void onSuccess(List<Game> result) {
				myContext = buildContext(result);
				buildScreen();
			}
		});
	}
	
	/**
	 * Builds the display screen
	 */
	private void buildScreen() {
		myScreen = new GameListScreen(myContext);
		myScreen.showAllGames();
	}
	
	/**
	 * This method builds the context needed for the display screen.
	 * 
	 * @param inList List of Games
	 * @return populated GameListScreen.Context implementation
	 */
	private GameListScreen.Context buildContext(final List<Game> inList) {
		return new GameListScreen.Context() {
			
			final ListDataProvider<Game> p = new ListDataProvider<Game>(inList);
			
			@Override
			public boolean shouldShowVoteButton() {
				return false;
			}
			
			@Override
			public boolean shouldShowTotalVotes() {
				return false;
			}
			
			@Override
			public int getTotalRows() {
				return (inList == null) ? 0 : inList.size();
			}
			
			@Override
			public int getRowsPerPage() {
				return 10;
			}
			
			@Override
			public ListDataProvider<Game> getDataProvider() {
				return p;
			}

			@Override
			public void onClickVoteButton(Game inGame) {
				// No op, since we dont vote on owned games
			}

			@Override
			public boolean shouldShowMarkOwnedButton() {
				return false;
			}

			@Override
			public void onClickMarkOwnedButton(Game inGame) {
				// No op
			}

			@Override
			public String getTitleText() {
				return myTitle;
			}
		};
	}

}
