package org.dowell.xnerd.client.service;

import org.dowell.xnerd.shared.entities.Game;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("votes")
public interface VoteService extends RemoteService {
	
	public void submitVoteForGame(Game in);

}
