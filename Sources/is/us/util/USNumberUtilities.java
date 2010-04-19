package is.us.util;

import java.math.BigDecimal;
import java.text.*;

/**
 * Various numeric functions
 * 
 * @author Bjarni Sævarsson
 * @reviewedby Hugi Thordarson
 */

public class USNumberUtilities {

	public static final float METRIC_HP_IN_KW = 0.73549875f;// 1 metric horsepower = 735.49875 watts (0.73549875 Kw)

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

	/**
	 * See http://en.wikipedia.org/wiki/Horsepower#Metric_horsepower
	 * converts kilowatts to metric horsepowers
	 * @param kw kilowatts to be converted
	 * @reviewedby Atli Páll Hafsteinsson
	 */
	public static float kilowattsToMetricHorsepower( float kw ) {
		if( kw == 0 ) {
			return 0f;
		}
		return kw / METRIC_HP_IN_KW;
	}

	/**
	 * Converts a string to BigDecimal
	 * 
	 *  @param nr to convert, can be a formated number
	 *  @return a BigDecimal object if conversion is successful, else null is returned.
	 */
	public static BigDecimal stringToBigDecimal( String nr ) {
		DecimalFormat format = (DecimalFormat)NumberFormat.getInstance();
		format.setParseBigDecimal( true );
		BigDecimal number;
		try {
			number = (BigDecimal)format.parse( nr );
		}
		catch( ParseException e ) {
			return null;
		}
		return number;
	}
}