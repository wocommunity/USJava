package is.us.formatters;

import static org.junit.Assert.assertEquals;
import is.us.formatters.USPersidnoFormatter;

import java.text.ParseException;

import org.junit.Test;

/**
 * Tests for USPersidnoFormatter.
 * 
 * @author Hugi Thordarson
 */

public class TestUSPersidnoFormatter {

	@Test
	public void parsing() throws ParseException {
		String input = "091179-4829";
		String expected = "0911794829";

		USPersidnoFormatter formatter = new USPersidnoFormatter();
		assertEquals( formatter.parseObject( input ), expected );
	}

	@Test
	public void formatting() {
		String input = "0911794829";
		String expected = "091179-4829";

		USPersidnoFormatter formatter = new USPersidnoFormatter();
		assertEquals( formatter.format( input ), expected );
		assertEquals( formatter.format( null ), "" );
	}
}
