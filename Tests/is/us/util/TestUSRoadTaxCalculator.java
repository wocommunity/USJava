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
		assertEquals( 4227, calc.calculate() );

		calc = new USRoadTaxCalculator( 999 );
		assertEquals( 8441, calc.calculate() );

		calc = new USRoadTaxCalculator( 1000 );
		assertEquals( 8450, calc.calculate() );

		calc = new USRoadTaxCalculator( 1001 );
		assertEquals( 8461, calc.calculate() );

		calc = new USRoadTaxCalculator( 1999 );
		assertEquals( 19838, calc.calculate() );

		calc = new USRoadTaxCalculator( 2000 );
		assertEquals( 19850, calc.calculate() );

		calc = new USRoadTaxCalculator( 2001 );
		assertEquals( 19861, calc.calculate() );

		calc = new USRoadTaxCalculator( 2999 );
		assertEquals( 31238, calc.calculate() );

		calc = new USRoadTaxCalculator( 3000 );
		assertEquals( 31250, calc.calculate() );

		calc = new USRoadTaxCalculator( 3001 );
		assertEquals( 34068, calc.calculate() );

		calc = new USRoadTaxCalculator( 3999 );
		assertEquals( 34068, calc.calculate() );

		calc = new USRoadTaxCalculator( 4000 );
		assertEquals( 34068, calc.calculate() );

		calc = new USRoadTaxCalculator( 4001 );
		assertEquals( 36886, calc.calculate() );

		calc = new USRoadTaxCalculator( 5999 );
		assertEquals( 39704, calc.calculate() );

		calc = new USRoadTaxCalculator( 6000 );
		assertEquals( 39704, calc.calculate() );

		calc = new USRoadTaxCalculator( 6001 );
		assertEquals( 42522, calc.calculate() );

		calc = new USRoadTaxCalculator( 9236 );
		assertEquals( 50976, calc.calculate() );
	}
}
