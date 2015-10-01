package org.dowell.xnerd.server.dao;

import org.dowell.xnerd.server.iface.GamesDao;
import org.dowell.xnerd.server.iface.VotesDao;

/**
 * Factory class for the Dao's. We want to control the creation of the Dao's
 * since their implementation (underlying DB, etc..) could change.
 * 
 * Since we are not holding state, except for the pooled connections since we
 * are using singletons here controls the DB conn's and saves memory.
 * 
 * @author Matt
 * 
 */
public final class DaoFactory {

	private static final GamesDao GAMESDAO = new GamesDaoImpl();
	private static final VotesDao VOTESDAO = new VotesDaoImpl();

	public static GamesDao getGamesDao() {
		return GAMESDAO;
	}

	public static VotesDao getVotesDao() {
		return VOTESDAO;
	}

}
