package is.us.util;

/**
 * Calculates the amount of money you have to pay the government for owning your car.
 * You pay this twice a year.
 * 
 * http://www.rsk.is/einstakl/skattar/bifreida
 * 
 * @author Hugi Thordarson
 */

public class USRoadTaxCalculator {

	/**
	 * The tax can never be lower than this amount.
	 */
	private static final int MIN_AMOUNT_DUE = 4227;

	/**
	 * The tax can never be higher than this amount.
	 */
	private static final int MAX_AMOUNT_DUE = 50976;

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
	private static final double FIRST_CATEGORY_PRICE = 8.45d;

	/**
	 * Amount paid for every kg between 1001 and 3000 kg.
	 */
	private static final double SECOND_CATEGORY_PRICE = 11.40d;

	/**
	 * Amount paid for every started ton of the vehicle's weight after 3 tons.
	 */
	private static final double THIRD_CATEGORY_PRICE = 2818d;

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

		return (int)Math.floor( result );
	}

	/**
	 * @return The part of the tax paid for weight < FIRST_CATEGORY_WEIGHT.
	 */
	public double firstAmount() {
		if( weight() < FIRST_CATEGORY_WEIGHT ) {
			return weight() * FIRST_CATEGORY_PRICE;
		}

		return FIRST_CATEGORY_WEIGHT * FIRST_CATEGORY_PRICE;
	}

	/**
	 * @return The part of the tax paid for weight between FIRST_CATEGORY_WEIGHT and FIRST_CATEGORY_WEIGHT + SECOND_CATEGORY_WEIGHT
	 */
	public double secondAmount() {

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
	public double thirdAmount() {

		if( weight() <= (FIRST_CATEGORY_WEIGHT + SECOND_CATEGORY_WEIGHT) ) {
			return 0d;
		}

		double weightInTons = weight() / 1000d;
		double tonsToUseForCalculation = weightInTons - (FIRST_CATEGORY_WEIGHT + SECOND_CATEGORY_WEIGHT) / 1000;
		tonsToUseForCalculation = Math.ceil( tonsToUseForCalculation );

		return tonsToUseForCalculation * THIRD_CATEGORY_PRICE;
	}
}