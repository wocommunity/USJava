package is.us.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 * @author Hugi Thordarson
 */

public class TestUSIPRange {

	@Test
	public void usIPRange() {
		String firstIP = "192.168.34.1";
		String lastIP = "192.168.34.254";
		String[] inRangeIPs = new String[] { "192.168.34.1", "192.168.34.30", "192.168.34.254" };
		String[] outOfRangeIPs = new String[] { "192.168.1.1", "192.165.34.30", "192.168.38.0", "0.0.0.0", "255.255.255.255", null };

		USIPRange range = new USIPRange( firstIP, lastIP );

		for( String nextIP : inRangeIPs ) {
			assertTrue( range.isInRange( nextIP ) );
		}

		for( String nextIP : outOfRangeIPs ) {
			assertFalse( range.isInRange( nextIP ) );
		}
	}
}
