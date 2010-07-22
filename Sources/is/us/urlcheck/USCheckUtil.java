package is.us.urlcheck;

import is.us.util.USStringUtilities;

import java.net.*;
import java.util.*;
import java.util.regex.*;

/**
 * Utility classes pertaining to the URL checking.
 * 
 * @author Hugi Thordarson
 */

public class USCheckUtil {

	static String domainFromURL( String url ) {
		try {
			int beginningIndex = url.indexOf( "//" );
			int endIndex = url.indexOf( "/", beginningIndex + 2 );
			return url.substring( beginningIndex + 2, endIndex );
		}
		catch( Exception e ) {
			System.out.println( "Failed to get host from url: " + url );
			return null;
		}
	}

	static String protocolFromURL( String refererURL ) {
		if( refererURL.startsWith( "http://" ) )
			return "http";

		if( refererURL.startsWith( "https://" ) )
			return "https";

		throw new RuntimeException( "The referer url is not an absolute HTTP URL" );
	}

	static boolean isAnchor( String url ) {
		return url.startsWith( "#" );
	}

	/**
	 * Checks to see if an URL is absolute or relative. 
	 */
	static boolean isAbsolute( String url ) {
		if( url == null ) {
			return false;
		}

		return url.startsWith( "http://" ) || url.startsWith( "https:/" );
	}

	static boolean isHTTP( String url ) {
		if( url == null ) {
			return false;
		}

		return url.startsWith( "http://" ) || url.startsWith( "https:/" );
	}

	public static List<String> httpOnly( List<String> urls ) {
		List<String> http = new ArrayList<String>();

		for( String url : urls ) {
			if( isHTTP( url ) ) {
				http.add( url );
			}
		}

		return http;
	}

	public static List<String> anchorsOnly( List<String> urls ) {
		List<String> anchors = new ArrayList<String>();

		for( String url : urls ) {
			if( isAnchor( url ) ) {
				anchors.add( url );
			}
		}

		return anchors;
	}

	/**
	 * Make the given URL absolute in the context of the given referer. 
	 */
	public static String makeAbsolute( String refererURL, String url ) {
		String protocol = protocolFromURL( refererURL );
		String domain = domainFromURL( refererURL );
		return protocol + "://" + domain + url;
	}

	public static List<String> makeAbsolute( String refererURL, List<String> urls ) {
		List<String> absoluteUrls = new ArrayList<String>();

		for( String url : urls ) {
			if( !isAbsolute( url ) ) {
				url = makeAbsolute( refererURL, url );
			}

			absoluteUrls.add( url );
		}

		return absoluteUrls;
	}

	public static List<String> matchesInString( String string, String pattern ) {

		if( string == null || pattern == null ) {
			return Collections.<String> emptyList();
		}

		Pattern p = Pattern.compile( pattern, Pattern.DOTALL );
		Matcher m = p.matcher( string );

		List<String> matches = new ArrayList<String>();

		while( m.find() ) {
			String nextMatch = m.group( 1 );
			matches.add( nextMatch );
		}

		return matches;
	}

	/**
	 * Fetch all links on the page.
	 */
	public static List<String> linksOnPage( String url ) {
		String content = USStringUtilities.readStringFromURLUsingEncoding( url, "UTF-8" );
		String pattern = "href=\"(.*?)\"";
		return matchesInString( content, pattern );
	}

	public static int statusForURL( String urlString ) {

		if( urlString == null ) {
			return -1;
		}

		if( !isHTTP( urlString ) ) {
			return -1;
		}

		try {
			URL url = new URL( urlString );
			URLConnection connection = url.openConnection();

			connection.connect();

			if( connection instanceof HttpURLConnection ) {
				HttpURLConnection httpConnection = (HttpURLConnection)connection;
				return httpConnection.getResponseCode();
			}
		}
		catch( Exception e ) {
			System.out.println( e );
		}

		return -1;
	}

	public static String contentTypeForURL( String urlString ) {

		if( urlString == null ) {
			return null;
		}

		if( !isHTTP( urlString ) ) {
			return null;
		}

		try {
			URL url = new URL( urlString );
			URLConnection connection = url.openConnection();

			connection.connect();

			if( connection instanceof HttpURLConnection ) {
				HttpURLConnection httpConnection = (HttpURLConnection)connection;
				return httpConnection.getHeaderField( "Content-Type" );
			}
		}
		catch( Exception e ) {
			System.out.println( e );
		}

		return null;
	}
}