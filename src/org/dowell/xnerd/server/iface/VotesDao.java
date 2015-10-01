package org.dowell.xnerd.server.iface;

import org.dowell.xnerd.shared.entities.Game;

/**
 * Interface for Managing votes for a particular game.
 * @author Matt
 *
 */
public interface VotesDao {
	
	public Integer getVotesForGame(Game in);
	public void submitVoteForGame(Game in);

}
