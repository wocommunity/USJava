package is.us.util;

import java.util.*;

/**
 * @author Hugi Þórðarson
 * 
 * Icelandic IP-addresses need regular updates: http://www.rix.is/is-as-nets.html
 */

public class USIPRange {

	public static final String LF = "\n";
	private static final String DOT_PATTERN = "\\.";
	private static final String COMMA = ",";
	private List<SingleIPRange> ranges = new ArrayList<SingleIPRange>();
	private static USIPRange _icelandicRange;

	public USIPRange() {}

	public USIPRange( String ip1, String ip2 ) {
		this.addRange( ip1, ip2 );
	}

	public void addRange( String ip1, String ip2 ) {

		if( ip1 != null && ip2 != null ) {
			ranges.add( new SingleIPRange( ip1, ip2 ) );
		}
	}

	public void addRanges( String ip1, String ip2 ) {
		String[] firstIPs = ip1.split( COMMA );
		String[] lastIPs = ip2.split( COMMA );
		addRanges( firstIPs, lastIPs );
	}

	public void addRanges( String[] firstIPs, String[] lastIPs ) {
		for( int i = firstIPs.length - 1; i >= 0; i-- ) {
			addRange( firstIPs[i], lastIPs[i] );
		}
	}

	public boolean isInRange( String ipAddress ) {

		if( ranges != null && ranges.size() > 0 ) {

			for( SingleIPRange sipr : ranges ) {
				if( sipr.isInRange( ipAddress ) )
					return true;
			}
		}

		return false;
	}

	public static USIPRange icelandicRange() {

		if( _icelandicRange == null ) {
			_icelandicRange = new USIPRange();
			_icelandicRange.addRange( "62.145.128.1", "62.145.159.254" );
			_icelandicRange.addRange( "81.15.0.1", "81.15.127.254" );
			_icelandicRange.addRange( "213.181.96.1", "213.181.127.254" );
			_icelandicRange.addRange( "213.220.64.1", "213.220.127.254" );
			_icelandicRange.addRange( "193.4.58.1", "193.4.59.254" );
			_icelandicRange.addRange( "130.208.0.1", "130.208.255.254" );
			_icelandicRange.addRange( "155.91.74.1", "155.91.74.254" );
			_icelandicRange.addRange( "157.157.0.1", "157.157.255.254" );
			_icelandicRange.addRange( "157.157.139.1", "157.157.139.254" );
			_icelandicRange.addRange( "192.147.34.1", "192.147.34.254" );
			_icelandicRange.addRange( "194.105.224.1", "194.105.255.254" );
			_icelandicRange.addRange( "204.219.180.1", "204.219.183.254" );
			_icelandicRange.addRange( "204.219.220.1", "204.219.223.254" );
			_icelandicRange.addRange( "212.30.192.1", "212.30.223.254" );
			_icelandicRange.addRange( "213.167.128.1", "213.167.159.254" );
			_icelandicRange.addRange( "193.4.0.1", "193.4.255.254" );
			_icelandicRange.addRange( "194.144.0.1", "194.144.255.254" );
			_icelandicRange.addRange( "213.176.128.1", "213.176.159.254" );
			_icelandicRange.addRange( "213.213.128.1", "213.213.159.254" );
			_icelandicRange.addRange( "217.9.128.1", "217.9.143.254" );
			_icelandicRange.addRange( "217.151.160.1", "217.151.191.254" );
			_icelandicRange.addRange( "213.190.96.1", "213.190.127.254" );
			_icelandicRange.addRange( "193.109.16.1", "193.109.31.254" );
		}

		return _icelandicRange;
	}

	private static class SingleIPRange {

		private String[] arr1;
		private String[] arr2;
		private USRange first;
		private USRange second;
		private USRange third;
		private USRange fourth;
		private String firstIP;
		private String lastIP;

		public SingleIPRange( String ip1, String ip2 ) {

			firstIP = ip1;
			lastIP = ip2;

			arr1 = ip1.split( DOT_PATTERN );
			arr2 = ip2.split( DOT_PATTERN );

			first = rangeFromArrayIndex( 0 );
			second = rangeFromArrayIndex( 1 );
			third = rangeFromArrayIndex( 2 );
			fourth = rangeFromArrayIndex( 3 );
		}

		public boolean isInRange( String ipAddress ) {

			if( !USStringUtilities.stringHasValue( ipAddress ) )
				return false;

			String[] a = ipAddress.split( DOT_PATTERN );

			if( first.containsLocation( Integer.parseInt( a[0] ) ) )
				if( second.containsLocation( Integer.parseInt( a[1] ) ) )
					if( third.containsLocation( Integer.parseInt( a[2] ) ) )
						if( fourth.containsLocation( Integer.parseInt( a[3] ) ) )
							return true;

			return false;
		}

		private USRange rangeFromArrayIndex( int i ) {
			int addr1 = Integer.parseInt( arr1[i] );
			int addr2 = Integer.parseInt( arr2[i] );

			int length = addr2 - addr1 + 1;

			return new USRange( addr1, length );
		}

		public String toString() {
			return firstIP + " - " + lastIP;
		}
	}

	public String toString() {
		StringBuffer b = new StringBuffer();

		for( SingleIPRange s : ranges ) {
			b.append( s );
			b.append( LF );
		}

		return b.toString();
	}

	private static class USRange {

		public USRange( int newStart, int newLength ) {
			start = newStart;
			length = newLength;
		}

		int start;
		int length;

		public boolean containsLocation( int location ) {
			return USNumberUtilities.isBetween( location, start, start + length );
		}
	}
}