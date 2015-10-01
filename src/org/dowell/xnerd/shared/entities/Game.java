package org.dowell.xnerd.shared.entities;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * This class represents a Game entity used within our application. It is also a
 * DTO so its objects will not necessarily be stored in the DB.
 * 
 * @author Matt
 * 
 */
public class Game implements Serializable {

	private static final long serialVersionUID = 5939660788246530700L;
	
	private Integer id;
	private String title;
	private boolean owned = false;
	private Timestamp created;
	private Integer voteCount;

	public Game() {
		super();
	}

	public Game(Integer id, String title, boolean owned, Timestamp created) {
		super();
		this.id = id;
		this.title = title;
		this.owned = owned;
		this.created = created;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isOwned() {
		return owned;
	}

	public void setOwned(boolean owned) {
		this.owned = owned;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Integer getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(Integer voteCount) {
		this.voteCount = voteCount;
	}

}
