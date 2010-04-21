package is.us.util;

import java.util.List;

/**
 * Various utility methods for collections.
 * 
 * @author Hugi Thordarson
 */

public class USCollectionUtilities {

	/**
	 * @param list The list to use
	 * @return A human readable version of the list.
	 */
	public static String humanReadableList( List<?> list ) {
		StringBuilder b = new StringBuilder();

		for( int i = 0; i < list.size(); i++ ) {
			Object o = list.get( i );

			b.append( o );

			if( i == list.size() - 2 ) {
				b.append( " og " );
			}
			else if( i < list.size() - 2 ) {
				b.append( ", " );
			}

		}

		return b.toString();
	}
}
