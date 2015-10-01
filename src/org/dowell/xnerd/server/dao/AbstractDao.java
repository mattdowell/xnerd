package org.dowell.xnerd.server.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.dowell.xnerd.shared.exceptions.DatabaseException;

import com.mchange.v2.c3p0.DataSources;

/**
 * All Dao's must inherit from this class. It provides basic dao functionality
 * like connection pooling.
 * 
 * Note: I would normally use Hibernate here but it is overkill for a two table
 * schema. It is more efficient to just write the Dao's/SQL manually.
 * 
 * @author Matt
 * 
 * @param <T>
 *            Dao type
 */
public class AbstractDao {

	/**
	 * Below are the attributes needed to connect to the data source TODO: These
	 * should be acquired via a container managed datasource ( or maybe stored
	 * in a properties file, shhh..)
	 */
	private static final String SCHEMA_NAME = "xnerd";
	private static final String USER_NAME = "xuser";
	private static final String PASSWORD = "password";

	private static DataSource pooled = null;

	public AbstractDao() {
		initPool();
	}

	/**
	 * Inits the connection pool using the default 3CPO settings. To tune the
	 * settings, create and manage a 3CPO properties file.
	 * 
	 */
	private void initPool() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			// Acquire the DataSource...
			DataSource unpooled = DataSources.unpooledDataSource("jdbc:mysql://localhost:3306/" + SCHEMA_NAME, USER_NAME, PASSWORD);
			pooled = DataSources.pooledDataSource(unpooled);
		} catch (Exception e) {
			// TODO: Logging
			e.printStackTrace();
			throw new DatabaseException("Could not initPool. Check the DB state and settings: " + e.toString());
		}
	}

	/**
	 * Gets a connection from the pool.
	 * 
	 * @return
	 */
	Connection getConnection() {
		try {
			return pooled.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Could not get connection. Check the DB state and settings: " + e.toString());
		}
	}

	/**
	 * Commits and puts the connection back in the pool.
	 * 
	 * @param inConn
	 */
	void finishConnection(Connection inConn) {
		try {
			// TODO: Cannot call commit if Autocommit=true
			// c.commit();
			inConn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Could not close connection. Check the DB state and settings: " + e.toString());
		}
	}

	void finishResultSet(ResultSet inRs) {
		try {
			if (inRs != null) {
				inRs.close();
				inRs = null;
			}
		} catch (Exception e) {
			// This should NEVER! EVER! Happen. Never.
			// Actually, now that I think about it, it can happen if 
			// the parent STMT or Connection has been closed.
			e.printStackTrace();
			throw new DatabaseException("There was an exception while closing a ResulSet. Check your code. We could be leaking cursors: " + e.toString());
		}
	}
	
	void finishStatement(Statement inStmt) {
		try {
			if (inStmt != null) {
				inStmt.close();
				inStmt = null;
			}
		} catch (Exception e) {
			// This should NEVER! EVER! Happen. Never.
			// See Above ^
			e.printStackTrace();
			throw new DatabaseException("There was an exception while closing a query statement. Check your code. We could be leaking cursors: " + e.toString());
		}		
	}

}
