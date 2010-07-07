package is.us.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author Hugi Thordarson
 */

public class TestUSRoadTaxCalculator {

	@Test
	public void annualCost() {
		USRoadTaxCalculator calc = null;

		calc = new USRoadTaxCalculator( 500 );
		assertEquals( 4650, calc.calculate() );

		calc = new USRoadTaxCalculator( 999 );
		assertEquals( 9291, calc.calculate() );

		calc = new USRoadTaxCalculator( 1000 );
		assertEquals( 9300, calc.calculate() );

		calc = new USRoadTaxCalculator( 1001 );
		assertEquals( 9313, calc.calculate() );

		calc = new USRoadTaxCalculator( 1999 );
		assertEquals( 21837, calc.calculate() );

		calc = new USRoadTaxCalculator( 2000 );
		assertEquals( 21850, calc.calculate() );

		calc = new USRoadTaxCalculator( 2001 );
		assertEquals( 21863, calc.calculate() );

		calc = new USRoadTaxCalculator( 2999 );
		assertEquals( 34387, calc.calculate() );

		calc = new USRoadTaxCalculator( 3000 );
		assertEquals( 34400, calc.calculate() );

		calc = new USRoadTaxCalculator( 3001 );
		assertEquals( 37500, calc.calculate() );

		calc = new USRoadTaxCalculator( 3999 );
		assertEquals( 37500, calc.calculate() );

		calc = new USRoadTaxCalculator( 4000 );
		assertEquals( 37500, calc.calculate() );

		calc = new USRoadTaxCalculator( 4001 );
		assertEquals( 40600, calc.calculate() );

		calc = new USRoadTaxCalculator( 5999 );
		assertEquals( 43700, calc.calculate() );

		calc = new USRoadTaxCalculator( 6000 );
		assertEquals( 43700, calc.calculate() );

		calc = new USRoadTaxCalculator( 6001 );
		assertEquals( 46800, calc.calculate() );

		calc = new USRoadTaxCalculator( 9236 );
		assertEquals( 56074, calc.calculate() );
	}
}
