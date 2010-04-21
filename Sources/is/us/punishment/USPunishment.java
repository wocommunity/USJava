package is.us.punishment;

import is.us.util.*;

import java.util.ArrayList;

/**
 * Container class for punishments.
 * 
 * Each punishment defines three variables:
 * 
 * - lowerBounds: The lowest possible value where this punishment will be applied.
 * - fine: The amount of the fine applied.
 * - monthsWithoutLicense: The number of months the driver will lose his/her license. 
 * - punishmentPoints: The number of points applied to your driver's license.
 * 
 * @author Hugi Þórðarson
 */

public class USPunishment {

	private Double _lowerBounds;
	private Integer _fine;
	private Integer _monthsWithoutLicense;
	private Integer _punishmentPoint;

	/**
	 * Please use the factory method "create()" to create instances.
	 */
	private USPunishment() {}

	/**
	 * Creates a new instance of Punishment.
	 * 
	 * @param lowerBounds			The lowest possible value where this punishment will be applied.
	 * @param fine 					The fine applied
	 * @param monthsWithoutLicense 	The number of months the driver will lose his/her license.
	 * @param punishmentPoint 		The number of points the driver will lose on his/her license.
	 */
	public static USPunishment create( Double lowerBounds, Integer fine, Integer monthsWithoutLicense, Integer punishmentPoint ) {
		USPunishment p = new USPunishment();
		p.setLowerBounds( lowerBounds );
		p.setFine( fine );
		p.setMonthsWithoutLicense( monthsWithoutLicense );
		p.setPunishmentPoint( punishmentPoint );
		return p;
	}

	/**
	 * The lower bounds that this punishment applies to. 
	 */
	public Double lowerBounds() {
		return _lowerBounds;
	}

	public void setLowerBounds( Double d ) {
		_lowerBounds = d;
	}

	/**
	 * Months that the driver will be without license
	 */
	public Integer monthsWithoutLicense() {
		return _monthsWithoutLicense;
	}

	public void setMonthsWithoutLicense( Integer _monthsWithoutLicense ) {
		this._monthsWithoutLicense = _monthsWithoutLicense;
	}

	/**
	 * Monetary fine in ISK.
	 */
	public Integer fine() {
		return _fine;
	}

	public void setFine( Integer _fine ) {
		this._fine = _fine;
	}

	/**
	 * punishment points
	 */
	public Integer punishmentPoints() {
		return _punishmentPoint;
	}

	/**
	 * punishment points
	 */
	public void setPunishmentPoint( Integer punishmentPoint ) {
		this._punishmentPoint = punishmentPoint;
	}

	/**
	 * @return A description of the punishment in human readable form (in Icelandic).
	 */
	public String humanReadableDescription() {

		if( punishmentPoints() > 3 ) {
			return "Þín bíður ákæra & dómur";
		}

		ArrayList<String> list = new ArrayList<String>();

		if( fine() != null && fine() > 0 ) {
			String template = USStringUtilities.stringWithFormat( "{} króna fjársekt", USStringUtilities.formatDouble( fine() ) );
			list.add( template );
		}

		if( monthsWithoutLicense() != null && monthsWithoutLicense() > 0 ) {
			String template = USStringUtilities.stringWithFormat( "svipting ökuleyfis í {} {}", USStringUtilities.formatDouble( monthsWithoutLicense() ), ((monthsWithoutLicense() > 1) ? "mánuði" : "mánuð") );
			list.add( template );
		}

		if( punishmentPoints() != null && punishmentPoints() > 0 ) {
			String template = USStringUtilities.stringWithFormat( "{} {} í ökuferilsskrá", USStringUtilities.formatDouble( punishmentPoints() ), ((punishmentPoints() > 1) ? "refsipunktar" : "refsipunktur") );
			list.add( template );
		}

		String result = "Þín bíður " + USCollectionUtilities.humanReadableList( list );
		return result;
	}

	@Override
	public String toString() {
		return USStringUtilities.stringWithFormat( "{} - lowerbounds: {} - fine: {} - monthsWithoutLicense: {}", getClass().getName(), lowerBounds(), fine(), monthsWithoutLicense() );
	}

	/**
	 * Punishments are considered equal if their variables are the same.
	 */
	@Override
	public boolean equals( Object o ) {

		if( !(o instanceof USPunishment) )
			return false;

		USPunishment p = (USPunishment)o;
		return eq( p.lowerBounds(), this.lowerBounds() ) && eq( p.fine(), this.fine() ) && eq( p.monthsWithoutLicense(), this.monthsWithoutLicense() ) && eq( p.punishmentPoints(), this.punishmentPoints() );
	}

	/**
	 * Calculate the correct punishment from the given array of punishments.
	 * Returns null if no punishment applies.
	 * 
	 * @param punishments Array of potential punishments.
	 * @param number Value to fetch an appropriate punishment for.
	 */
	public static USPunishment punishment( USPunishment[] punishments, Double number ) {

		if( number == null ) {
			return null;
		}

		for( int i = punishments.length - 1; i > -1; i-- ) {
			USPunishment p = punishments[i];

			if( number >= p.lowerBounds() ) {
				return p;
			}
		}

		return null;
	}

	/**
	 * Checks for equality of objects in a null safe manner.
	 */
	private static boolean eq( Object o1, Object o2 ) {
		if( o1 == null && o2 == null )
			return true;

		if( o1 != null )
			return o1.equals( o2 );

		return false;
	}
}