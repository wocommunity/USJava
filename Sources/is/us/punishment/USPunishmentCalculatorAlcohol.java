package is.us.punishment;

import java.util.ArrayList;

/**
 * Calculates punishments for driving drunk.
 * 
 * Based on data from:
 * http://www.reglugerd.is/interpro/dkm/WebGuard.nsf/538c26748c8e2a9d00256a07003476bd/498600e7cbd349360025758b0047e04d?OpenDocument
 */

public class USPunishmentCalculatorAlcohol {

	public static final String BREATH = "Útöndunarlofti";
	public static final String BLOOD = "Blóði";

	/**
	 * The methods used to measure alcohol in the body.
	 */
	public static final String[] MEASUREMENT_TYPES = new String[] { BREATH, BLOOD };

	/**
	 * Data table: Drunk driving, first violation, alcohol in blood (‰)
	 */
	public static USPunishment[] punishmentsBloodFirstViolation() {
		ArrayList<USPunishment> p = new ArrayList<USPunishment>();
		p.add( USPunishment.create( 0.50, 70000, 2, 0 ) );
		p.add( USPunishment.create( 0.61, 70000, 4, 0 ) );
		p.add( USPunishment.create( 0.76, 90000, 6, 0 ) );
		p.add( USPunishment.create( 0.91, 100000, 8, 0 ) );
		p.add( USPunishment.create( 1.11, 110000, 10, 0 ) );
		p.add( USPunishment.create( 1.20, 140000, 12, 0 ) );
		p.add( USPunishment.create( 1.51, 160000, 18, 0 ) );
		p.add( USPunishment.create( 2.01, 160000, 24, 0 ) );
		return p.toArray( new USPunishment[p.size()] );
	}

	/**
	 * Data table: Drunk driving, first violation, alcohol in breath (mg/l)
	 */
	public static USPunishment[] punishmentsBreathFirstViolation() {
		ArrayList<USPunishment> p = new ArrayList<USPunishment>();
		p.add( USPunishment.create( 0.25, 70000, 2, 0 ) );
		p.add( USPunishment.create( 0.31, 70000, 4, 0 ) );
		p.add( USPunishment.create( 0.38, 90000, 6, 0 ) );
		p.add( USPunishment.create( 0.46, 100000, 8, 0 ) );
		p.add( USPunishment.create( 0.56, 110000, 10, 0 ) );
		p.add( USPunishment.create( 0.60, 140000, 12, 0 ) );
		p.add( USPunishment.create( 0.76, 160000, 18, 0 ) );
		p.add( USPunishment.create( 1.01, 160000, 24, 0 ) );
		return p.toArray( new USPunishment[p.size()] );
	}

	/**
	 * Data table: Drunk driving, first violation, alcohol in blood (‰)
	 */
	public static USPunishment[] punishmentsBloodSecondViolation() {
		ArrayList<USPunishment> p = new ArrayList<USPunishment>();
		p.add( USPunishment.create( 0.50, 180000, 24, 0 ) );
		p.add( USPunishment.create( 1.20, 200000, 36, 0 ) );
		p.add( USPunishment.create( 1.51, 220000, 42, 0 ) );
		p.add( USPunishment.create( 2.01, 240000, 48, 0 ) );
		return p.toArray( new USPunishment[p.size()] );
	}

	/**
	 * Data table: Drunk driving, first violation, alcohol in breath (mg/l)
	 */
	public static USPunishment[] punishmentsBreathSecondViolation() {
		ArrayList<USPunishment> p = new ArrayList<USPunishment>();
		p.add( USPunishment.create( 0.25, 180000, 24, 0 ) );
		p.add( USPunishment.create( 0.60, 200000, 36, 0 ) );
		p.add( USPunishment.create( 0.76, 220000, 42, 0 ) );
		p.add( USPunishment.create( 1.01, 240000, 48, 0 ) );
		return p.toArray( new USPunishment[p.size()] );
	}

	/**
	 * @param measurementType Indicates if we're measuring alcohol in blood or breath.
	 * @param firstOffence Indicates if this is the person's first violation.
	 * @return A list of punishments appropriate for the given parameters.
	 */
	public static USPunishment[] punishments( String measurementType, boolean firstOffence ) {
		if( measurementType.equals( BREATH ) ) {
			return firstOffence ? punishmentsBreathFirstViolation() : punishmentsBreathSecondViolation();
		}

		if( measurementType.equals( BLOOD ) ) {
			return firstOffence ? punishmentsBloodFirstViolation() : punishmentsBloodSecondViolation();
		}

		throw new IllegalArgumentException( "Invalid parameter for measurementType" );
	}

	/**
	 * @param measurement The measured alcohol levvel.
	 * @param measurementType Indicates if we're measuring alcohol in blood or breath.
	 * @param firstOffence Indicates if this is the person's first violation.
	 * @return The appropriate punishment.
	 */
	public static USPunishment punishment( Double value, String measurementType, boolean firstOffence ) {
		return USPunishment.punishment( punishments( measurementType, firstOffence ), value );
	}
}