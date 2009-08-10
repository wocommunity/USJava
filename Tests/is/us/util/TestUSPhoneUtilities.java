package is.us.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for USPhoneUtilities.
 * 
 * @author Hugi Thordarson
 */

public class TestUSPhoneUtilities {

	private static final String[] CORRECT_PHONE_NUMBERS = new String[] { "1234567", "0000000", "9999999", "5553845" };
	private static final String[] WRONG_PHONE_NUMBERS = new String[] { "12345678", "#", "asdf", "%", "&", "$", "/u0002", "All work and no play make Jack go..." };
	private static final String[] EMPTY_PHONE_NUMBERS = new String[] { "", null, " ", "   " };
	private static final String[] NEW_VARIATIONS_OF_PHONE_NUMBERS = new String[] { "693 3845", "666-6666", "999 9999", "555-3845" };

	private static final String[] CORRECT_MOBILE_NUMBERS = new String[] { "6933845", "6666666", "6000000", "6999999", "8000000", "8999999" };
	private static final String[] WRONG_MOBILE_NUMBERS = new String[] { "A", "1", "4999999", "1000000", "2995999", "9000000" };

	@Test
	public void validatePhoneNumber() {

		for( String next : EMPTY_PHONE_NUMBERS )
			assertFalse( USPhoneUtilities.validatePhoneNumber( next ) );

		for( String next : CORRECT_PHONE_NUMBERS )
			assertTrue( USPhoneUtilities.validatePhoneNumber( next ) );

		for( String next : WRONG_PHONE_NUMBERS )
			assertFalse( USPhoneUtilities.validatePhoneNumber( next ) );

		for( String next : NEW_VARIATIONS_OF_PHONE_NUMBERS )
			assertTrue( USPhoneUtilities.validatePhoneNumber( next ) );
	}

	@Test
	public void validateMobileNumber() {

		for( String next : EMPTY_PHONE_NUMBERS )
			assertEquals( false, USPhoneUtilities.validateMobileNumber( next ) );

		for( String next : CORRECT_MOBILE_NUMBERS )
			assertEquals( true, USPhoneUtilities.validateMobileNumber( next ) );

		for( String next : WRONG_MOBILE_NUMBERS )
			assertEquals( false, USPhoneUtilities.validateMobileNumber( next ) );
	}
}