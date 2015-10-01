package org.dowell.xnerd.shared.exceptions;

/**
 * Thrown from the DAO layer when there is an unrecoverable Database exception
 * 
 * @author mjdowell
 *
 */
public class DatabaseException extends IllegalStateException {

	public DatabaseException() {
	}

	public DatabaseException(String arg0) {
		super(arg0);
	}

	public DatabaseException(Throwable arg0) {
		super(arg0);
	}

	public DatabaseException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
