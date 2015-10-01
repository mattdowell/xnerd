package org.dowell.xnerd.client.service;

import java.util.List;

import org.dowell.xnerd.shared.entities.Game;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GameServiceAsync {

	void getGamesCurrentlyOwned(AsyncCallback<List<Game>> callback);

	void doesGameTitleExist(String inTitle, AsyncCallback<Boolean> callback);

	void submitNewGame(Game inGame, AsyncCallback<Void> callback);

	void getGamesWanted(AsyncCallback<List<Game>> callback);

	void markGameAsOwned(Game inGame, AsyncCallback<Void> callback);

}
