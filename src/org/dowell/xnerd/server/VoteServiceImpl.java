package org.dowell.xnerd.server;

import org.dowell.xnerd.client.service.VoteService;
import org.dowell.xnerd.server.dao.DaoFactory;
import org.dowell.xnerd.server.iface.VotesDao;
import org.dowell.xnerd.shared.entities.Game;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Server-side service implementation to manange the use-case and 
 * eventual persistance of game votes.
 * 
 * @author Matt
 *
 */
@SuppressWarnings("serial")
public class VoteServiceImpl extends RemoteServiceServlet implements VoteService {
	
	private static final VotesDao voteDao = DaoFactory.getVotesDao();

	@Override
	public void submitVoteForGame(Game in) {
		voteDao.submitVoteForGame(in);
	}

}
