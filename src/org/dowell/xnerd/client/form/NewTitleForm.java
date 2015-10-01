package org.dowell.xnerd.client.form;

import org.dowell.xnerd.client.controller.ContentContainer;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This form manages the submition of new titles. By using the MVP
 * pattern here, we could also use this form to manage the changing
 * of a title, if needed. This form is dumb, the controller controls
 * all the behavior.
 * 
 * @author Matt
 *
 */
public class NewTitleForm implements ClickHandler {

	public interface Context {

		String getErrorText();

		void onSubmitClick(ClickEvent event);
	}

	private Context myContext;

	public NewTitleForm(Context inCtx) {
		myContext = inCtx;
	}

	TextBox titleTextBox = null;

	/**
	 * @wbp.parser.entryPoint
	 */
	public void build() {

		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		Label title = new Label("New game title");
		title.setStyleName("title");
		
		verticalPanel.add(title);

		FlexTable flexTable = new FlexTable();
		verticalPanel.add(flexTable);

		Label lblEmail = new Label("Title:");
		flexTable.setWidget(0, 0, lblEmail);

		titleTextBox = new TextBox();
		flexTable.setWidget(0, 1, titleTextBox);
		titleTextBox.setFocus(true);
		titleTextBox.setCursorPos(0);
		titleTextBox.setTabIndex(0);

		Button btnOk = new Button("I Want it!");
		flexTable.setWidget(1, 1, btnOk);
		btnOk.addClickHandler(this);

		ContentContainer.setContent(verticalPanel);
		
		if (myContext.getErrorText() != null) {
			ContentContainer.showValidationErrors(myContext.getErrorText());
		}

	}

	@Override
	public void onClick(ClickEvent event) {
		myContext.onSubmitClick(event);
	}

	public TextBox getNewTitle() {
		return titleTextBox;
	}

}
