package org.dowell.xnerd.server;

import java.util.List;

import org.dowell.xnerd.client.service.GameService;
import org.dowell.xnerd.server.dao.DaoFactory;
import org.dowell.xnerd.server.iface.GamesDao;
import org.dowell.xnerd.shared.entities.Game;
import org.dowell.xnerd.shared.exceptions.TitleExistsException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * This is the GameService remove service serlet. The GWT client asynchronisly
 * calls the methods in this service. 
 * 
 * This is the service interface for the application. This interface provides
 * a smallish wrapper around the dao supplimenting the dao methods with additional
 * functionality usefully to the application client layer.
 * 
 * 
 * @author Matt
 *
 */
@SuppressWarnings("serial")
public class GameServiceImpl extends RemoteServiceServlet implements GameService {

	private static final GamesDao gamesDao = DaoFactory.getGamesDao();

	public List<Game> getGamesToBuyWithVotes() {
		return gamesDao.getGamesToBuyWithVotes();
	}

	public boolean doesGameTitleExist(String inTitle) {
		return gamesDao.doesGameTitleExist(inTitle);
	}

	/**
	 * This method: 1) Checks to see if a title exists already, and if not 2)
	 * Submits the game to the DAO
	 * 
	 * @param inGame
	 */
	public void submitNewGame(Game inGame) {

		if (!doesGameTitleExist(inGame.getTitle())) {
			gamesDao.submitNewGame(inGame);
		} else {
			// handle an error
			throw new TitleExistsException("The game title: " + inGame.getTitle() + " already exists in the database.");
		}
	}

	@Override
	public List<Game> getGamesCurrentlyOwned() {
		return gamesDao.getGamesCurrentlyOwned();
	}

	@Override
	public List<Game> getGamesWanted() {
		return gamesDao.getGamesToBuyWithVotes();
	}

	@Override
	public void markGameAsOwned(Game inGame) {
		inGame.setOwned(true);
		gamesDao.update(inGame);
	}
	

}
