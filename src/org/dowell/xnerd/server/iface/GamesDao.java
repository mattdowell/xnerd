package org.dowell.xnerd.server.iface;

import java.util.List;

import org.dowell.xnerd.shared.entities.Game;

/**
 * Public interface for the Game dao.
 * 
 * @author Matt
 *
 */
public interface GamesDao {

	public List<Game> getGamesCurrentlyOwned();
	
	public List<Game> getGamesToBuy();
	
	/**
	 * Gets all the games not currently owned and
	 * returns them in a list with their votes in 
	 * descending vote order.
	 * 
	 * @return
	 */
	public List<Game> getGamesToBuyWithVotes();
	
	public boolean doesGameTitleExist(String inTitle);
	
	public void submitNewGame(Game inGame);
	
	// This is not part of the req. but needed for testing
	public void deleteGame(String inTitle);
	
	public void update(Game inGame);
	
	public Game get(Integer inGameId);
	
}
