package org.dowell.xnerd.client.util;

import org.dowell.xnerd.client.Xnerd;
import org.dowell.xnerd.client.controller.ContentContainer;
import org.dowell.xnerd.shared.exceptions.DatabaseException;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Concrete implementation of AsyncCallback that handles the errors in a unified format.
 * 
 * @author Matt
 * 
 * @param <T>
 */
public abstract class Callback<T> implements AsyncCallback<T> {

	/**
	 * Any exceptions we might throw from the Service layer can be caught
	 * and handled individually here.
	 * 
	 * (non-Javadoc)
	 * @see com.google.gwt.user.client.rpc.AsyncCallback#onFailure(java.lang.Throwable)
	 */
	@Override
	public void onFailure(Throwable caught) {

		if (caught instanceof DatabaseException) {
			caught.printStackTrace();
			ContentContainer.showError("There is an error with the database. Has it been configured? Users / Schema created? Permissions? " + caught.getMessage());
			// Lets not show the welcome screen if we get a DB error, it could cause a loop.
		} else {
			caught.printStackTrace();
			ContentContainer.showError("There has been an unknown error. " + caught.getMessage());
			Xnerd.showWelcomeScreen();
		}
	}
}
