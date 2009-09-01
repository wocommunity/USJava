package is.us.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

	@Test
	public void kilowattsToMetricHorsepower() {
		assertTrue( USStringUtilities.formatDouble( USNumberUtilities.kilowattsToMetricHorsepower( -200 ), 4, false ).equals( "-271,9243" ) );
		assertTrue( USStringUtilities.formatDouble( USNumberUtilities.kilowattsToMetricHorsepower( -1 ), 4, false ).equals( "-1,3596" ) );
		assertTrue( USStringUtilities.formatDouble( USNumberUtilities.kilowattsToMetricHorsepower( 0 ), 4, false ).equals( "0" ) );
		assertTrue( USStringUtilities.formatDouble( USNumberUtilities.kilowattsToMetricHorsepower( 1 ), 4, false ).equals( "1,3596" ) );
		assertTrue( USStringUtilities.formatDouble( USNumberUtilities.kilowattsToMetricHorsepower( 10 ), 4, false ).equals( "13,5962" ) );
		assertTrue( USStringUtilities.formatDouble( USNumberUtilities.kilowattsToMetricHorsepower( 20.5f ), 4, false ).equals( "27,8722" ) );
		assertTrue( USStringUtilities.formatDouble( USNumberUtilities.kilowattsToMetricHorsepower( -20.5f ), 4, false ).equals( "-27,8722" ) );
	}

}