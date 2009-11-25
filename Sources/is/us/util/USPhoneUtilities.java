package is.us.util;


/**
 * Validation utilities for phone numbers.
 * 
 * @author Logi Helgu
 */

public class USPhoneUtilities {

	/**
	 * No instances created, ever.
	 */
	private USPhoneUtilities() {}

	/**
	 * Checks to see if the given string consists of 7 digits.
	 * 
	 * @returns true if the number is valid.
	 */
	public static boolean validatePhoneNumber( String phoneNumber ) {

		phoneNumber = cleanPhoneNumber( phoneNumber );

		if( !USStringUtilities.stringHasValue( phoneNumber ) )
			return false;

		if( phoneNumber.length() != 7 )
			return false;

		if( !USStringUtilities.isDigitsOnly( phoneNumber ) )
			return false;

		return true;
	}

	/**
	 * Checks to see if the given number is a valid mobile number.
	 * 
	 * According to http://pta.is/displayer.asp?cat_id=23 all mobile numbers start with 6, 7 or 8
	 *
	 * @returns true if the number is valid.
	 */
	public static boolean validateMobileNumber( String phoneNumber ) {

		if( !validatePhoneNumber( phoneNumber ) )
			return false;

		String firstLetterInPhoneNumber = phoneNumber.substring( 0, 1 );

		return "6".equals( firstLetterInPhoneNumber ) || "7".equals( firstLetterInPhoneNumber ) || "8".equals( firstLetterInPhoneNumber );
	}

	/**
	 * Cleans up phone number by trimming, removing white spaces and - sign
	 * */
	public static String cleanPhoneNumber( String phoneNumber ) {
		if( USStringUtilities.stringHasValue( phoneNumber ) ) {
			phoneNumber = phoneNumber.trim();
			phoneNumber = USStringUtilities.replace( phoneNumber, " ", "" );
			phoneNumber = USStringUtilities.replace( phoneNumber, "-", "" );
		}
		return phoneNumber;
	}
}
