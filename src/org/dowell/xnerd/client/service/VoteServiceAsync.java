package org.dowell.xnerd.client.service;

import org.dowell.xnerd.shared.entities.Game;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface VoteServiceAsync {

	void submitVoteForGame(Game in, AsyncCallback<Void> callback);

}
