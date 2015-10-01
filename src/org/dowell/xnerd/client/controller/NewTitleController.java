package org.dowell.xnerd.client.controller;

import org.dowell.xnerd.client.form.NewTitleForm;
import org.dowell.xnerd.client.service.GameService;
import org.dowell.xnerd.client.service.GameServiceAsync;
import org.dowell.xnerd.client.util.Callback;
import org.dowell.xnerd.client.util.ValidationUtil;
import org.dowell.xnerd.shared.entities.Game;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;

/**
 * This controller manages the creation of new Game titles. It handles the building
 * of the screen, the validation of the input and calling the back-end service to
 * persist the title.
 *  
 * @author mjdowell
 *
 */
public class NewTitleController {

	NewTitleForm myForm = null;
	GameServiceAsync gameService = GWT.create(GameService.class);
	String myError = null;

	public NewTitleController() {

	}

	/**
	 * The caller calls this method to "begin" the use-case.
	 */
	public void begin() {
		myForm = new NewTitleForm(buildContext());
		myForm.build();
	}

	/**
	 *This method builds the form's context
	 */
	private NewTitleForm.Context buildContext() {
		return new NewTitleForm.Context() {

			@Override
			public void onSubmitClick(ClickEvent event) {
				validateNewTitle();
			}

			@Override
			public String getErrorText() {
				return myError;
			}
		};
	}

	/**
	 * Checks to make sure this user has not submitted a title
	 * yet today, or has not voted already today. IF they pass
	 * these edits, then perform the submit.
	 */
	private void submitNewTitle() {
		// submit
		Game game = new Game();
		game.setTitle(myForm.getNewTitle().getText());
		
		gameService.submitNewGame(game, new Callback<Void>() {

			@Override
			public void onSuccess(Void result) {
				// show the Wanted titles page
				WantedGamesController c = new WantedGamesController("Your title has been added!");
				c.show();
				ValidationUtil.userActedToday();
			}
			
		});
	}

	/**
	 * This method is called first and checks to see if the title
	 * exists or not. If it does, it adds an error to the form
	 * and repaints it. If it does not, it calls the submit method
	 */
	private void validateNewTitle() {
		
		// First validate
		final String title = myForm.getNewTitle().getText();
		
		if (title == null || title.equals("")) {
			myError = "An actual title is required";
			myForm.build();	
			return;
		}
		
		if (ValidationUtil.hasUserActedToday()) {
			WantedGamesController w = new WantedGamesController("Sorry dude, you can only do something once a day. Get to work.");
			w.show();
			return;
		}		

		gameService.doesGameTitleExist(title, new Callback<Boolean>() {

			@Override
			public void onSuccess(Boolean result) {

				if (result) {
					// Game title exists
					myError = "The game " + title + " already exists in the database";
					myForm.build();

				} else {
					submitNewTitle();
				}
			}

		});
	}

}
