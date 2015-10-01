package org.dowell.xnerd.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dowell.xnerd.server.iface.GamesDao;
import org.dowell.xnerd.shared.entities.Game;

/**
 * Implementation of the GamesDao. This class handles
 * all the database interaction and is an OLD SCHOOL
 * SQL specific implementation. Feel free to replace
 * with a Hibernate or IBatis impl.
 * 
 * @see GamesDao
 * 
 * @author Matt
 * 
 */
public class GamesDaoImpl extends AbstractDao implements GamesDao {

	/* These are the column names in the db */
	enum Columns {
		game_id, name, owned, create_ts, vote_count
	}

	/**
	 * @see GamesDao.getGamesCurrentlyOwned
	 */
	@Override
	public List<Game> getGamesCurrentlyOwned() {
		String sql = "select * from games where owned=1 order by " + Columns.name;
		return processQuery(sql);
	}

	/**
	 * @see GamesDao.getGamesToBuy
	 */
	@Override
	public List<Game> getGamesToBuy() {
		String sql = "select * from games where owned=0 order by " + Columns.name;
		return processQuery(sql);
	}

	private List<Game> processQuery(String inQuery) {
		List<Game> theReturn = new ArrayList<Game>();
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			rs = conn.prepareStatement(inQuery).executeQuery();
			theReturn = processResultSet(rs);
		} catch (Exception e) {
			// TODO: Should log and send a admin msg here
			e.printStackTrace();
			throw new IllegalStateException("Error getting list of games: " + e.toString());
		} finally {
			finishResultSet(rs);
			finishConnection(conn);
		}
		return theReturn;
	}
	
	/**
	 * Turns a ResultSet in to a List<Game>
	 * @param inRs
	 * @return
	 */
	private List<Game> processResultSet(ResultSet inRs) {
		List<Game> theReturn = new ArrayList<Game>();
		try {
			while (inRs.next()) {
				Game g = buildGame(inRs);
				theReturn.add(g);
			}
		} catch (Exception e) {
			// TODO: Should log and send a admin msg here
			e.printStackTrace();
			throw new IllegalStateException("Error getting list of games: " + e.toString());
		} finally {
			finishResultSet(inRs);
		}
		return theReturn;		
	}

	/**
	 * Builds a Game object from the given row in the ResultSet.
	 * 
	 * @param inRs
	 *            ResultSet
	 * @return Populated Game
	 * @throws SQLException
	 */
	private Game buildGame(ResultSet inRs) throws SQLException {
		Game g = new Game();
		g.setId(inRs.getInt(Columns.game_id.name()));
		g.setTitle(inRs.getString(Columns.name.name()));
		g.setOwned(inRs.getBoolean(Columns.owned.name()));
		g.setCreated(inRs.getTimestamp(Columns.create_ts.name()));
		try {
			g.setVoteCount(inRs.getInt(Columns.vote_count.name()));
		} catch (Exception e) {
			// This is an acceptable situation. Some of the queries
			// do not join to the votes table to get the vote count.
			// TODO: Log a INFO 
		}
		return g;
	}

	/**
	 * Checks to see if a title exists in the DB already or not
	 * Returns TRUE of the title exists
	 */
	@Override
	public boolean doesGameTitleExist(String inTitle) {
		String sql = "select * from games where "+Columns.name.name()+"=?";
		List<Game> games = null;
		Connection conn =  null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, inTitle);
			games = processResultSet(stmt.executeQuery());

		} catch (Exception e) {
			// TODO: handle exception Logging/email
			e.printStackTrace();
			throw new IllegalStateException("Error checking title exists: " + e.toString());
		} finally {
			finishStatement(stmt);
			super.finishConnection(conn);
		}
		// true if title exists
		return (!games.isEmpty());
		
	}

	@Override
	public List<Game> getGamesToBuyWithVotes() {
		String sql = "SELECT g."+Columns.game_id+", " +
		" g."+Columns.name+", " +
		" g."+Columns.owned+", " +
        " g."+Columns.create_ts+", " +
        " count(v."+Columns.game_id+") as " + Columns.vote_count +
        " FROM " +
        " xnerd.games g " +
        " left outer join xnerd.votes v on g."+Columns.game_id+"=v."+Columns.game_id +
        " where g."+Columns.owned+" = 0 " +
        " group by g." + Columns.name +
        " order by "+Columns.vote_count+" desc";
		
		return processQuery(sql);
	}

	@Override
	public void submitNewGame(Game inGame) {
		final String sql = "insert into games (name, owned) values (?, ?)";
		Connection conn =  null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, inGame.getTitle());
			stmt.setBoolean(2, inGame.isOwned());
			stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception Logging/email
			e.printStackTrace();
			throw new IllegalStateException("Error submitting new game: " + e.toString());
		} finally {
			finishStatement(stmt);
			finishConnection(conn);
		}
	}

	@Override
	public void deleteGame(String inTitle) {
		// TODO: Check for null title
		final String sql = "delete from xnerd.games where "+Columns.name+"=?";
		Connection conn =  null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, inTitle);
			stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception Logging/email
			e.printStackTrace();
			throw new IllegalStateException("Error deleting game: " + e.toString());
		} finally {
			finishStatement(stmt);
			finishConnection(conn);
		}		
	}

	@Override
	public void update(Game inGame) {
		String sql = "update games set "+Columns.name+"=?, "+Columns.owned+"=? where "+Columns.game_id+"=?";
		Connection conn =  null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, inGame.getTitle());
			stmt.setBoolean(2, inGame.isOwned());
			stmt.setInt(3, inGame.getId());
			stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception Logging/email
			e.printStackTrace();
			throw new IllegalStateException("Error updating game: " + e.toString());
		} finally {
			finishStatement(stmt);
			finishConnection(conn);
		}				
	}

	@Override
	public Game get(Integer inGameId) {
		String sql = "select * from games where "+Columns.game_id+"=" + inGameId;
		List<Game> games = processQuery(sql);
		if (games == null) {
			return null;
		}
		return games.get(0);
	}
}
