package org.dowell.xnerd.server.dao;

import junit.framework.Assert;

import java.util.List;

import org.dowell.xnerd.server.iface.GamesDao;
import org.dowell.xnerd.shared.entities.Game;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GamesDaoTest {

	GamesDao dao = null;
	
	@Before
	public void setUp() throws Exception {
		dao  = DaoFactory.getGamesDao();
	}

	@After
	public void tearDown() throws Exception {
		dao = null;
	}

	@Test
	public void testGetGamesCurrentlyOwned() {
		List<Game> games = dao.getGamesCurrentlyOwned();
		Assert.assertNotNull(games);
		
		for (Game g : games) {
			Assert.assertTrue(g.isOwned());
		}
	}

	@Test
	public void testGetGamesToBuy() {
		List<Game> games = dao.getGamesToBuy();
		Assert.assertNotNull(games);
		
		for (Game g : games) {
			Assert.assertFalse(g.isOwned());
		}
	}
	
	/**
	 * This test assumes the Test data has been loaded in to the test DB
	 */
	@Test
	public void testTitleExists() {
		Assert.assertTrue(dao.doesGameTitleExist("Halo"));
		Assert.assertFalse(dao.doesGameTitleExist("Unkbnown gaemez"));
	}

	@Test
	public void testGetGamesToBuyWithVotes() {
		List<Game> games = dao.getGamesToBuyWithVotes();
		Assert.assertNotNull(games);
		
		for (Game g : games) {
			Assert.assertFalse(g.isOwned());
			Assert.assertNotNull(g.getVoteCount());
		}
	}
	
	@Test
	public void testInsertGame() {
		Game g = new Game();
		String newTitle = "Test Game";
		g.setTitle(newTitle);
		
		dao.submitNewGame(g);
		
		// Should not throw an exception
		Assert.assertTrue(dao.doesGameTitleExist(newTitle));
		
		dao.deleteGame(newTitle);
	}
	
	@Test
	public void testUpdateGame() {
		Game old = dao.get(11);
		String newTitle = "New Title From Testing";
		old.setTitle(newTitle);
		dao.update(old);
		
		Game newGame = dao.get(11);
		Assert.assertEquals(newTitle, newGame.getTitle());
		
	}
}
