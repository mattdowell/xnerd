package org.dowell.xnerd.client.service;

import java.util.List;

import org.dowell.xnerd.shared.entities.Game;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("games")
public interface GameService extends RemoteService {

	public void submitNewGame(Game inGame);
	
	public void markGameAsOwned(Game inGame);

	public boolean doesGameTitleExist(String inTitle);
	
	public List<Game> getGamesCurrentlyOwned();
	
	public List<Game> getGamesWanted();

}
