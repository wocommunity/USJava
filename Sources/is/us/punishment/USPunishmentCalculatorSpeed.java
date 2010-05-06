package is.us.punishment;

import java.util.ArrayList;

/**
 * Calculates punishments for speeding.
 * Based on data tables from: {@link http://www.us.is/id/4501}
 */

public class USPunishmentCalculatorSpeed {

	/**
	 * These are the speeds allowed in Iceland.
	 */
	public static final Integer[] SPEED_LIMITS_IN_ICELAND = new Integer[] { 30, 35, 50, 60, 70, 80, 90 };

	/**
	 * Creates a data table for speeding over 30km 
	 */
	public static USPunishment[] punishmentsForMaxSpeed30() {
		ArrayList<USPunishment> p = new ArrayList<USPunishment>();
		p.add( USPunishment.create( 36.0, 5000, 0, 0 ) );
		p.add( USPunishment.create( 41.0, 10000, 0, 0 ) );
		p.add( USPunishment.create( 46.0, 15000, 0, 1 ) );
		p.add( USPunishment.create( 51.0, 20000, 0, 2 ) );
		p.add( USPunishment.create( 56.0, 25000, 0, 3 ) );
		p.add( USPunishment.create( 61.0, 45000, 3, 3 ) );
		p.add( USPunishment.create( 66.0, 55000, 3, 3 ) );
		p.add( USPunishment.create( 71.0, 70000, 3, 3 ) );
		p.add( USPunishment.create( 76.0, 0, 0, 4 ) );
		return p.toArray( new USPunishment[p.size()] );
	}

	/**
	 * Creates a data table for speeding over 35km 
	 */
	public static USPunishment[] punishmentsForMaxSpeed35() {
		ArrayList<USPunishment> p = new ArrayList<USPunishment>();
		p.add( USPunishment.create( 41.0, 5000, 0, 0 ) );
		p.add( USPunishment.create( 46.0, 10000, 0, 0 ) );
		p.add( USPunishment.create( 51.0, 15000, 0, 1 ) );
		p.add( USPunishment.create( 56.0, 20000, 0, 2 ) );
		p.add( USPunishment.create( 61.0, 25000, 0, 3 ) );
		p.add( USPunishment.create( 66.0, 45000, 0, 3 ) );
		p.add( USPunishment.create( 71.0, 50000, 3, 3 ) );
		p.add( USPunishment.create( 76.0, 55000, 3, 3 ) );
		p.add( USPunishment.create( 81.0, 70000, 3, 3 ) );
		p.add( USPunishment.create( 86.0, 0, 0, 4 ) );
		return p.toArray( new USPunishment[p.size()] );
	}

	/**
	 * Creates a data table for speeding over 50km 
	 */
	public static USPunishment[] punishmentsForMaxSpeed50() {
		ArrayList<USPunishment> p = new ArrayList<USPunishment>();
		p.add( USPunishment.create( 56.0, 5000, 0, 0 ) );
		p.add( USPunishment.create( 61.0, 10000, 0, 0 ) );
		p.add( USPunishment.create( 66.0, 15000, 0, 0 ) );
		p.add( USPunishment.create( 71.0, 20000, 0, 0 ) );
		p.add( USPunishment.create( 76.0, 25000, 0, 1 ) );
		p.add( USPunishment.create( 81.0, 30000, 0, 2 ) );
		p.add( USPunishment.create( 86.0, 40000, 0, 3 ) );
		p.add( USPunishment.create( 91.0, 50000, 0, 3 ) );
		p.add( USPunishment.create( 96.0, 60000, 0, 3 ) );
		p.add( USPunishment.create( 101.0, 90000, 3, 3 ) );
		p.add( USPunishment.create( 111.0, 110000, 3, 3 ) );
		p.add( USPunishment.create( 121.0, 130000, 3, 3 ) );
		p.add( USPunishment.create( 131.0, 0, 0, 4 ) );
		return p.toArray( new USPunishment[p.size()] );
	}

	/**
	 * Creates a data table for speeding over 60km 
	 */
	public static USPunishment[] punishmentsForMaxSpeed60() {
		ArrayList<USPunishment> p = new ArrayList<USPunishment>();
		p.add( USPunishment.create( 66.0, 5000, 0, 0 ) );
		p.add( USPunishment.create( 71.0, 10000, 0, 0 ) );
		p.add( USPunishment.create( 76.0, 15000, 0, 0 ) );
		p.add( USPunishment.create( 81.0, 20000, 0, 0 ) );
		p.add( USPunishment.create( 86.0, 30000, 0, 1 ) );
		p.add( USPunishment.create( 91.0, 40000, 0, 2 ) );
		p.add( USPunishment.create( 96.0, 50000, 0, 3 ) );
		p.add( USPunishment.create( 101.0, 60000, 0, 3 ) );
		p.add( USPunishment.create( 111.0, 80000, 1, 3 ) );
		p.add( USPunishment.create( 121.0, 110000, 3, 3 ) );
		p.add( USPunishment.create( 131.0, 130000, 3, 3 ) );
		p.add( USPunishment.create( 141.0, 0, 0, 4 ) );
		return p.toArray( new USPunishment[p.size()] );
	}

	/**
	 * Creates a data table for speeding over 70km 
	 */
	public static USPunishment[] punishmentsForMaxSpeed70() {
		ArrayList<USPunishment> p = new ArrayList<USPunishment>();
		p.add( USPunishment.create( 76.0, 5000, 0, 0 ) );
		p.add( USPunishment.create( 81.0, 10000, 0, 0 ) );
		p.add( USPunishment.create( 86.0, 15000, 0, 0 ) );
		p.add( USPunishment.create( 91.0, 30000, 0, 0 ) );
		p.add( USPunishment.create( 96.0, 40000, 0, 1 ) );
		p.add( USPunishment.create( 101.0, 50000, 0, 2 ) );
		p.add( USPunishment.create( 111.0, 60000, 0, 3 ) );
		p.add( USPunishment.create( 121.0, 80000, 1, 3 ) );
		p.add( USPunishment.create( 131.0, 110000, 2, 3 ) );
		p.add( USPunishment.create( 141.0, 140000, 3, 3 ) );
		p.add( USPunishment.create( 151.0, 0, 0, 4 ) );
		return p.toArray( new USPunishment[p.size()] );
	}

	/**
	 * Creates a data table for speeding over 90km 
	 */
	public static USPunishment[] punishmentsForMaxSpeed80() {
		ArrayList<USPunishment> p = new ArrayList<USPunishment>();
		p.add( USPunishment.create( 86.0, 10000, 0, 0 ) );
		p.add( USPunishment.create( 91.0, 20000, 0, 0 ) );
		p.add( USPunishment.create( 96.0, 30000, 0, 0 ) );
		p.add( USPunishment.create( 101.0, 50000, 0, 1 ) );
		p.add( USPunishment.create( 111.0, 60000, 0, 2 ) );
		p.add( USPunishment.create( 121.0, 80000, 0, 3 ) );
		p.add( USPunishment.create( 131.0, 110000, 1, 3 ) );
		p.add( USPunishment.create( 141.0, 140000, 2, 3 ) );
		p.add( USPunishment.create( 151.0, 150000, 3, 3 ) );
		p.add( USPunishment.create( 161.0, 0, 0, 4 ) );
		return p.toArray( new USPunishment[p.size()] );
	}

	/**
	 * Creates a data table for speeding over 90km 
	 */
	public static USPunishment[] punishmentsForMaxSpeed90() {
		ArrayList<USPunishment> p = new ArrayList<USPunishment>();
		p.add( USPunishment.create( 96.0, 10000, 0, 0 ) );
		p.add( USPunishment.create( 101.0, 30000, 0, 0 ) );
		p.add( USPunishment.create( 111.0, 50000, 0, 1 ) );
		p.add( USPunishment.create( 121.0, 70000, 0, 2 ) );
		p.add( USPunishment.create( 131.0, 90000, 0, 3 ) );
		p.add( USPunishment.create( 141.0, 130000, 1, 3 ) );
		p.add( USPunishment.create( 151.0, 140000, 2, 3 ) );
		p.add( USPunishment.create( 161.0, 150000, 3, 3 ) );
		p.add( USPunishment.create( 171.0, 0, 0, 4 ) );
		return p.toArray( new USPunishment[p.size()] );
	}

	/**
	 * @param speedLimit The speed limit we're fetching punishments for.
	 * @return A list of punishments for violations in a zone with the given speed limit.
	 */
	public static USPunishment[] punishments( int speedLimit ) {

		switch (speedLimit) {
			case 30:
				return punishmentsForMaxSpeed30();
			case 35:
				return punishmentsForMaxSpeed35();
			case 50:
				return punishmentsForMaxSpeed50();
			case 60:
				return punishmentsForMaxSpeed60();
			case 70:
				return punishmentsForMaxSpeed70();
			case 80:
				return punishmentsForMaxSpeed80();
			case 90:
				return punishmentsForMaxSpeed90();
		}

		throw new IllegalArgumentException( "An invalid speedlimit was specified, must be one of 30, 35, 50, 60, 70, 80 or 90" );
	}

	/**
	 * @param speedLimit the speed limit
	 * @param drivingSpeed Measured vehicle speed
	 * @return The appropriate punishment for drivingSpeed in a zone of the given speedLimit.
	 */
	public static USPunishment punishment( int speedLimit, Double drivingSpeed ) {

		if( drivingSpeed == null ) {
			throw new IllegalArgumentException( "Drivingspeed may not be null" );
		}

		return USPunishment.punishment( punishments( speedLimit ), drivingSpeed );
	}
}