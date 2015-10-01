package org.dowell.xnerd.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.dowell.xnerd.server.iface.VotesDao;
import org.dowell.xnerd.shared.entities.Game;

/**
 * Implementation of the VotesDao. This class handles
 * all the database interaction and is an OLD SCHOOL
 * SQL specific implementation. Feel free to replace
 * with a Hibernate or IBatis impl. This Dao is a little
 * different in that there is no concrete entity it is
 * managing. Just meta data related to the Game entity.
 * 
 * @see VotesDao
 * 
 * @author Matt
 *
 */
public class VotesDaoImpl extends AbstractDao implements VotesDao {

	public enum Columns {
		game_id,
		create_ts
	}
	
	@Override
	public Integer getVotesForGame(Game in) {
		final String sql = "select count(v.game_id) as vote_count from votes v where v.game_id=?";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, in.getId());
			rs = stmt.executeQuery();
			rs.next();
			return rs.getInt("vote_count");
		} catch (Exception e) {
			// TODO: Should log and send a admin msg here
			e.printStackTrace();
			throw new IllegalStateException("Error getting list of votes: " + e.toString());
		} finally {
			finishResultSet(rs);
			finishStatement(stmt);
			finishConnection(conn);
		}		
	}

	@Override
	public void submitVoteForGame(Game in) {
		final String sql = "insert into votes (game_id) values (?)";
		Connection conn =  null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, in.getId());
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException("Error submitin vote for Game: " + in + " Err: " + e.toString());
		} finally {
			finishStatement(stmt);
			finishConnection(conn);
		}			
	}

}
