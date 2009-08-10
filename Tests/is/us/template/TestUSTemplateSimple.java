package is.us.template;

import static org.junit.Assert.assertEquals;

import java.util.*;

import org.junit.Test;

/**
 * Tests for USTemplateSimple
 * 
 * @author Hugi Þórðarson
 * @issue INT-128
 * @reviewedBy Bjarni Sævarsson
 */

public class TestUSTemplateSimple {

	@Test
	public void parse() {
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put( "nafn-1", "Hugi Þórðarson" );
		vars.put( "nafn-2", "Chuck Norris" );
		testOne( "Halló ${nafn-1}, hvernig hefur þú það í dag?", vars, "Halló Hugi Þórðarson, hvernig hefur þú það í dag?" );
		testOne( "${nafn-1} borðar ${nafn-2} í morgunmat.", vars, "Hugi Þórðarson borðar Chuck Norris í morgunmat." );
	}

	private void testOne( String templateString, Map<String, Object> vars, String expectedResult ) {
		USTemplate template = new USTemplateSimple();
		template.setTemplateString( templateString );
		template.putAll( vars );
		String result = template.parse();
		assertEquals( result, expectedResult );
	}
}
