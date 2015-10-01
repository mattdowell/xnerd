package org.dowell.xnerd.client.util;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.Cookies;

/**
 * This method checks local users cookies for business validation rules.
 * 
 * @author Matt
 * 
 */
public class ValidationUtil {

	private static final DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT);
	private static final String USER_LAST_VOTED_OR_SUBMITTED_TITLE = "ulvost";

	/**
	 * 
	 * @return true if this user has voted in this 24 hour period.
	 */
	public static boolean hasUserActedToday() {
		String cookie = Cookies.getCookie(USER_LAST_VOTED_OR_SUBMITTED_TITLE);
		
		if (cookie == null) {
			return false;
		}
		
		Date d = getDate(cookie);
		Date today = new Date(System.currentTimeMillis());
		return (today.getDay() == d.getDay());
	}

	/**
	 * Set a DATE in the users cookies that represents TODAY
	 */
	public static void userActedToday() {
		Cookies.setCookie(USER_LAST_VOTED_OR_SUBMITTED_TITLE, getTodayAsString());
	}

	/**
	 * @param inVal
	 *            is a Date represented as a String
	 * @return a Date object parsed from the String
	 */
	private static Date getDate(String inVal) {
		return DATE_FORMAT.parse(inVal);
	}

	/**
	 * Get NOW formatted as a String
	 * 
	 * @return
	 */
	private static String getTodayAsString() {
		return DATE_FORMAT.format(new Date(System.currentTimeMillis()));
	}

}
