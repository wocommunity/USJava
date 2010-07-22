package is.us.urlcheck;

import java.util.List;

/**
 * @author Hugi Thordarson
 */

public class USDeadLinkSniffer {

	public static void main( String[] argv ) {
		String sourceURL = "http://www.us.is/";

		USCheckedURL url = new USCheckedURL();
		url.setURL( sourceURL );

		//		System.out.println( url.status() );
		//		/*
		List<USCheckedURL> list = url.checkLinks();

		for( USCheckedURL listItem : list ) {
			System.out.println( listItem );
		}
		//		*/
	}
}
