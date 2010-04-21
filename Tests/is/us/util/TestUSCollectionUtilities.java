package is.us.util;

import static org.junit.Assert.assertEquals;

import java.util.*;

import org.junit.Test;

public class TestUSCollectionUtilities {

	@Test
	public void humanReadableList() {
		String expected;
		String actual;

		expected = "Hugi";
		actual = USCollectionUtilities.humanReadableList( list( "Hugi" ) );
		assertEquals( expected, actual );

		expected = "Kjartan og Strumparnir";
		actual = USCollectionUtilities.humanReadableList( list( "Kjartan", "Strumparnir" ) );
		assertEquals( expected, actual );

		expected = "Hugi, Kjartan og Strumparnir";
		actual = USCollectionUtilities.humanReadableList( list( "Hugi", "Kjartan", "Strumparnir" ) );
		assertEquals( expected, actual );
	}

	/**
	 * Constructs a list for testing. 
	 */
	private static List<?> list( Object... objects ) {
		ArrayList<Object> a = new ArrayList<Object>();

		for( Object o : objects ) {
			a.add( o );
		}

		return a;
	}
}
