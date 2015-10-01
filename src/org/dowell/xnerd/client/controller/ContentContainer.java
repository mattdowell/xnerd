package org.dowell.xnerd.client.controller;

import java.util.List;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * This class manages the display of the content in the main panel. The main 
 * panel is broken down in to two section in the Xnerd.html file like so:
 * 
 * 	<div id="errorLabelContainer"></div>
 *	<div id="contentcontainer"></div>
 *
 * This class handles the mundane tasks of grabbing each div and adding content to
 * it.
 * 
 * @author Matt
 *
 */
public final class ContentContainer {

	private static final String CONTENT_DIV_TAG = "contentcontainer";
	private static final String ERROR_DIV_TAG = "errorLabelContainer";

	/**
	 * 
	 * @param inContent
	 */
	public static void setContent(Widget inContent) {
		clearAll();
		RootPanel contentPanel = RootPanel.get(CONTENT_DIV_TAG);
		contentPanel.insert(inContent, 0);
	}

	public static void addContentBelow(Widget inContent) {
		RootPanel contentPanel = RootPanel.get(CONTENT_DIV_TAG);
		contentPanel.insert(inContent, 1);
	}

	public static void showError(Widget inContent) {
		clearErrors();
		RootPanel contentPanel = RootPanel.get(ERROR_DIV_TAG);
		contentPanel.insert(inContent, 0);
	}
	public static void showErrorOnly(Widget inContent) {
		clearBody();
		showError(inContent);
	}	

	public static void showError(String inContent) {
		clearErrors();
		TextBox text = new TextBox();
		text.setWidth("90%");
		text.setText(inContent);
		text.setStyleName("error_text");
		showError(text);
	}

	public static void showValidationErrors(List<String> inContent) {
		
		String err = "";
		for (String s : inContent) {
			err += s + "\n";
		}
		showError(err);
	}
	
	public static void showValidationErrors(String inContent) {
		showError(inContent);
	}	

	public static void showError(Throwable caught) {
		showError(caught.getMessage());
	}

	private static void clearAll() {
		clearErrors();
		clearBody();
	}
	
	private static void clearErrors() {
		RootPanel errPanel = RootPanel.get(ERROR_DIV_TAG);
		errPanel.clear();
	}	
	private static void clearBody() {
		RootPanel contentPanel = RootPanel.get(CONTENT_DIV_TAG);
		contentPanel.clear();
	}

}
