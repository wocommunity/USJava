package is.us.formatters;

import is.us.util.USPersidnoUtilities;

import java.text.*;

/**
 * A formatter for formatting persidnos for display (inserts a dash after the sixth digit).
 * 
 * @author Hugi Þórðarson
 */

public class USPersidnoFormatter extends java.text.Format {

	/**
	 * Attempts to format a persidno to human readable format.
	 */
	public StringBuffer format( Object persidno, StringBuffer toAppendTo, FieldPosition pos ) {

		if( persidno instanceof String )
			toAppendTo.append( USPersidnoUtilities.formatPersidno( (String)persidno ) );

		return toAppendTo;
	}

	/**
	 * Parses a persidno and returns it in the standard format (no delimiters, just a string of numbers of the length 10).
	 */
	public Object parseObject( String source, ParsePosition status ) {
		String temp = source.substring( status.getIndex() );
		String result = USPersidnoUtilities.cleanupPersidno( temp );
		status.setIndex( source.length() );
		return result;
	}
}