package is.us.punishment;

import java.util.ArrayList;

/**
 * Calculates punishments driving under the influence of drugs
 * Based on data tables from: {@link http://www.reglugerd.is/interpro/dkm/WebGuard.nsf/538c26748c8e2a9d00256a07003476bd/498600e7cbd349360025758b0047e04d?OpenDocument}
 */

public class USPunishmentCalculatorDrugs {

	public final static String AMPHETAMINES = "Amfetamín";
	public final static String CANNABIS = "Kannabis";
	public final static String MDMA = "MDMA";
	public final static String COCAINE = "Kókaín";

	/**
	 * A list of drugs that this calculator can work with.
	 */
	public static final String[] DRUGS = new String[] { AMPHETAMINES, CANNABIS, MDMA, COCAINE };

	/**
	 * Data table: Amphetamines in blood
	 */
	public static USPunishment[] punishmentsForAmphetamineInBlood() {
		ArrayList<USPunishment> p = new ArrayList<USPunishment>();
		p.add( USPunishment.create( 0.01, 70000, 4, 0 ) );
		p.add( USPunishment.create( 170.0, 140000, 12, 0 ) );
		return p.toArray( new USPunishment[p.size()] );
	}

	/**
	 * Data table: Cannabis in blood
	 */
	public static USPunishment[] punishmentsForCannabisInBlood() {
		ArrayList<USPunishment> p = new ArrayList<USPunishment>();
		p.add( USPunishment.create( 0.01, 70000, 4, 0 ) );
		p.add( USPunishment.create( 2.0, 140000, 12, 0 ) );
		return p.toArray( new USPunishment[p.size()] );
	}

	/**
	 * Data table: MDMA(meth) in blood
	 */
	public static USPunishment[] punishmentsForMdmaInBlood() {
		ArrayList<USPunishment> p = new ArrayList<USPunishment>();
		p.add( USPunishment.create( 220.0, 140000, 12, 0 ) );
		return p.toArray( new USPunishment[p.size()] );
	}

	/**
	 * Data table: Cocaine in blood 
	 */
	public static USPunishment[] punishmentsForCocaineInBlood() {
		ArrayList<USPunishment> p = new ArrayList<USPunishment>();
		p.add( USPunishment.create( 0.01, 70000, 4, 0 ) );
		p.add( USPunishment.create( 30.0, 140000, 12, 0 ) );
		return p.toArray( new USPunishment[p.size()] );
	}

	/**
	 * Data table: Any drugs in urine
	 */
	public static USPunishment[] punishmentsForAnyInUrine() {
		ArrayList<USPunishment> p = new ArrayList<USPunishment>();
		p.add( USPunishment.create( 0.01, 70000, 3, 0 ) );
		return p.toArray( new USPunishment[p.size()] );
	}

	/**
	 * @param drug the drug to look at.
	 * @return A list of appropriate punishments.
	 */
	public static USPunishment[] punishments( String drug ) {
		if( drug.equalsIgnoreCase( CANNABIS ) ) {
			return punishmentsForCannabisInBlood();
		}
		if( drug.equalsIgnoreCase( MDMA ) ) {
			return punishmentsForMdmaInBlood();
		}
		if( drug.equalsIgnoreCase( COCAINE ) ) {
			return punishmentsForCocaineInBlood();
		}
		if( drug.equalsIgnoreCase( AMPHETAMINES ) ) {
			return punishmentsForAmphetamineInBlood();
		}

		throw new IllegalArgumentException( "An invalid drug was specified, must be one of CANNABIS, MDMA, COCAINE or AMPHETAMINE" );
	}

	/**
	 * The appropriate punishment for amount of drugs measured in blood
	 * 
	 * @param drug The drug found in blood
	 * @param value The measured blood level of the given drug (ng/ml)
	 */
	public static USPunishment punishment( String drug, Double value ) {
		return USPunishment.punishment( punishments( drug ), value );
	}
}