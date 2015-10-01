package org.dowell.xnerd.shared.exceptions;

/**
 * This exception is thrown from the service layer if we are trying
 * to insert a new game title that already exists in the database.
 * 
 * @author mjdowell
 *
 */
public class TitleExistsException extends UnsupportedOperationException {

	public TitleExistsException() {
		super();
	}

	public TitleExistsException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public TitleExistsException(String arg0) {
		super(arg0);
	}

	public TitleExistsException(Throwable arg0) {
		super(arg0);
	}

}
