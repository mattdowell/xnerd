package org.dowell.xnerd.client.controller;

import java.util.List;

import org.dowell.xnerd.client.screen.GameListScreen;
import org.dowell.xnerd.client.service.GameService;
import org.dowell.xnerd.client.service.GameServiceAsync;
import org.dowell.xnerd.client.service.VoteService;
import org.dowell.xnerd.client.service.VoteServiceAsync;
import org.dowell.xnerd.client.util.Callback;
import org.dowell.xnerd.client.util.ValidationUtil;
import org.dowell.xnerd.shared.entities.Game;

import com.google.gwt.core.client.GWT;
import com.google.gwt.view.client.ListDataProvider;

/**
 * This game controls the display and user actions around "Wanted"
 * games. 
 * 
 * - Retrive and display games we dont own yet
 * - Mark games as owned
 * - Vote for games
 * 
 * 
 * @See Wanted Games use case. 
 * @author Matt
 * 
 */
public class WantedGamesController {

	GameListScreen myScreen = null;
	GameServiceAsync gameService = GWT.create(GameService.class);
	VoteServiceAsync voteService = GWT.create(VoteService.class);
	GameListScreen.Context myContext;
	String myTitle = "Games that we want!";

	public WantedGamesController(String inTitle) {
		myTitle = inTitle;
	}

	/**
	 * The show() method will async a call to the backend
	 * service and when the call finally returns, then it will
	 * build and show the screen.
	 */
	public void show() {
		gameService.getGamesWanted(new Callback<List<Game>>() {

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
	 * @param inList
	 *            List of Games
	 * @return populated GameListScreen.Context implementation
	 */
	private GameListScreen.Context buildContext(final List<Game> inList) {
		return new GameListScreen.Context() {

			final ListDataProvider<Game> p = new ListDataProvider<Game>(inList);

			@Override
			public boolean shouldShowVoteButton() {
				return true;
			}

			@Override
			public boolean shouldShowTotalVotes() {
				return true;
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
				checkVotingPriviledges(inGame);
			}

			@Override
			public boolean shouldShowMarkOwnedButton() {
				return true;
			}

			@Override
			public void onClickMarkOwnedButton(Game inGame) {
				markGameAsOwned(inGame);
			}

			@Override
			public String getTitleText() {
				return myTitle;
			}
		};
	}

	/**
	 * This method checks to make sure this user has not done the following:
	 * 
	 * 1) A user can only submit one vote OR submit one new game title per day.
	 * 
	 */
	private void checkVotingPriviledges(Game inGame) {
		// TODO: First perform edits
		if (ValidationUtil.hasUserActedToday()) {
			myTitle = "Sorry dude, you can only do something once a day. Get to work.";
			show();
		} else {
			// If they have passed
			voteForGame(inGame);
		}
	}

	/**
	 * Handles the server call to vote for the given game.
	 * @param inGame
	 */
	private void voteForGame(Game inGame) {
		voteService.submitVoteForGame(inGame, new Callback<Void>() {

			@Override
			public void onSuccess(Void result) {
				// Repaint the screen.
				show();
				ValidationUtil.userActedToday();
			}

		});
	}

	/**
	 * Handles the server call to mark the game
	 * @param inGame
	 */
	private void markGameAsOwned(final Game inGame) {
		gameService.markGameAsOwned(inGame, new Callback<Void>() {

			@Override
			public void onSuccess(Void result) {
				myTitle = "We now own the game: " + inGame.getTitle() + ". Love that game.";
				show();
			}
		});
	}

	public void setTitle(String myTitle) {
		this.myTitle = myTitle;
	}

}
