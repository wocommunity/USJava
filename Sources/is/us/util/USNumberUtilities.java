package is.us.util;

/**
 * Various numeric functions
 * 
 * @author Bjarni Sævarsson
 * @reviewedby Hugi Thordarson
 */

public class USNumberUtilities {

	/**
	 * Checks if a number is between two the two given numbers, inclusive.
	 * 
	 *  @param number The number to check.
	 *  @param from Lower bounds of the check
	 *  @param to Upper bounds of the check.
	 */
	public static boolean isBetween( Number number, Number from, Number to ) {

		if( number == null || from == null || to == null ) {
			return false;
		}

		double doubleNumber = number.doubleValue();
		double floor = from.doubleValue();
		double ceiling = to.doubleValue();

		if( (doubleNumber >= floor) && (doubleNumber <= ceiling) ) {
			return true;
		}

		return false;
	}
}