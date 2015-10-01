package org.dowell.xnerd.server.dao;

import junit.framework.Assert;

import org.dowell.xnerd.server.iface.VotesDao;
import org.dowell.xnerd.shared.entities.Game;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class VotesDaoTest {
	
	VotesDao dao = null;
	Game testGame = null;
	Game voterGame = null;

	@Before
	public void setUp() throws Exception {
		dao = DaoFactory.getVotesDao();
		testGame = new Game();
		testGame.setId(1);
		testGame.setTitle("Leisure Suit Larry");
		testGame.setOwned(true);
		
		voterGame = new Game();
		voterGame.setId(3);
		voterGame.setTitle("Halo");
		voterGame.setOwned(false);		
	}

	@After
	public void tearDown() throws Exception {
		dao = null;
	}

	@Test
	public void testGetVotesForGame() {
		Integer votes = dao.getVotesForGame(testGame);
		
		// NOTE: This is dependant on the given test data
		Assert.assertEquals(votes, new Integer(6));
	}

	@Test
	public void testSubmitVoteForGame() {
		Integer oldVoteCount = dao.getVotesForGame(voterGame);
		dao.submitVoteForGame(voterGame);
		Integer newVoteCount = dao.getVotesForGame(voterGame);
		
		Assert.assertEquals(oldVoteCount.intValue() + 1, newVoteCount.intValue());
	}

}
