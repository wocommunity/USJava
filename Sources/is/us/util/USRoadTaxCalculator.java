package is.us.util;

/**
 * Calculates the amount of money you  pay the government for owning your car, twice a year.
 * The tax is based on the weight of the vehicle. Note that current regulations dictate that
 * vehicles < 400kgs in weight are not taxed. this calculator does not take that into account.
 * 
 * The basis for these calculations can be found at:
 * 
 * http://www.rsk.is/einstakl/skattar/bifreida
 * 
 * @author Hugi Thordarson
 */

public class USRoadTaxCalculator {

	/**
	 * The tax can never be lower than this amount.
	 */
	private static final int MIN_AMOUNT_DUE = 4650;

	/**
	 * The tax can never be higher than this amount.
	 */
	private static final int MAX_AMOUNT_DUE = 56074;

	/**
	 * Kilograms below or at this number cost FIRST_CATEGORY_PRICE.
	 */
	private static final double FIRST_CATEGORY_WEIGHT = 1000d;

	/**
	 * Kilograms below or at this number + FIRST_CATEGORY_WEIGHT cost SECOND_CATEGORY_PRICE.
	 */
	private static final double SECOND_CATEGORY_WEIGHT = 2000d;

	/**
	 * Amount paid for every kg under 1000 kg.
	 */
	private static final double FIRST_CATEGORY_PRICE = 9.30d;

	/**
	 * Amount paid for every kg between 1001 and 3000 kg.
	 */
	private static final double SECOND_CATEGORY_PRICE = 12.55d;

	/**
	 * Amount paid for every started ton of the vehicle's weight after 3 tons.
	 */
	private static final double THIRD_CATEGORY_PRICE = 3100d;

	/**
	 * The amount you pay is based on the weight of the vehicle.
	 */
	private Integer _weight;

	/**
	 * Constructs a new calculator instance. 
	 * 
	 * @param weight The weight of the vehicle.
	 */
	public USRoadTaxCalculator( int weight ) {
		setWeight( weight );
	}

	/**
	 * @param value the weight to use for calculations.
	 */
	private void setWeight( int value ) {
		_weight = value;
	}

	/**
	 * @return the weight of the vehicle.
	 */
	public int weight() {
		return _weight;
	}

	/**
	 * @return the total amount due.
	 */
	public int calculate() {
		double result = firstAmount() + secondAmount() + thirdAmount();

		if( result < MIN_AMOUNT_DUE ) {
			return MIN_AMOUNT_DUE;
		}

		if( result > MAX_AMOUNT_DUE ) {
			return MAX_AMOUNT_DUE;
		}

		return (int)Math.round( result );
	}

	/**
	 * @return The part of the tax paid for weight < FIRST_CATEGORY_WEIGHT.
	 */
	private double firstAmount() {
		if( weight() < FIRST_CATEGORY_WEIGHT ) {
			return weight() * FIRST_CATEGORY_PRICE;
		}

		return FIRST_CATEGORY_WEIGHT * FIRST_CATEGORY_PRICE;
	}

	/**
	 * @return The part of the tax paid for weight between FIRST_CATEGORY_WEIGHT and FIRST_CATEGORY_WEIGHT + SECOND_CATEGORY_WEIGHT
	 */
	private double secondAmount() {

		if( weight() <= FIRST_CATEGORY_WEIGHT ) {
			return 0d;
		}

		if( weight() < FIRST_CATEGORY_WEIGHT + SECOND_CATEGORY_WEIGHT ) {
			return (weight() - FIRST_CATEGORY_WEIGHT) * SECOND_CATEGORY_PRICE;
		}

		return SECOND_CATEGORY_WEIGHT * SECOND_CATEGORY_PRICE;
	}

	/**
	 * @return The part of the tax paid for weight above FIRST_CATEGORY_WEIGHT + SECOND_CATEGORY_WEIGHT
	 */
	private double thirdAmount() {

		if( weight() <= (FIRST_CATEGORY_WEIGHT + SECOND_CATEGORY_WEIGHT) ) {
			return 0d;
		}

		double weightInTons = weight() / 1000d;
		double tonsToUseForCalculation = weightInTons - (FIRST_CATEGORY_WEIGHT + SECOND_CATEGORY_WEIGHT) / 1000;
		tonsToUseForCalculation = Math.ceil( tonsToUseForCalculation );

		return tonsToUseForCalculation * THIRD_CATEGORY_PRICE;
	}
}