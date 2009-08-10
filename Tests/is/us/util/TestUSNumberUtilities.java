package is.us.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for the USNumberUtilitiesclass
 * 
 * @author Hugi Thordarson
 */

public class TestUSNumberUtilities {

	@Test
	public void isBetween() {
		assertTrue( USNumberUtilities.isBetween( 2, 1, 3 ) );
		assertTrue( USNumberUtilities.isBetween( 200000000l, 1f, 30000000000000d ) );
		assertTrue( USNumberUtilities.isBetween( 20000l, 1l, 30000000000000l ) );
		assertFalse( USNumberUtilities.isBetween( null, 1, 3 ) );
		assertFalse( USNumberUtilities.isBetween( 2, null, 3 ) );
		assertFalse( USNumberUtilities.isBetween( 2, 1, null ) );
	}
}