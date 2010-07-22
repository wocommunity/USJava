package is.us.urlcheck;

import java.util.*;

import org.slf4j.*;

/**
 * 
 * @author Hugi Thordarson
 */

public class USCheckedURL {

	private static final Logger logger = LoggerFactory.getLogger( USCheckedURL.class );
	private Integer _status;
	private String _url;
	private List<String> _links;

	/**
	 * @return The URL to check.
	 */
	public String url() {
		return _url;
	}

	public void setURL( String value ) {
		_url = value;
	}

	/**
	 * @return The status of the page.
	 */
	public int status() {
		if( _status == null ) {
			logger.debug( "Checking URL: {}", url() );
			_status = USCheckUtil.statusForURL( url() );
		}

		return _status;
	}

	public List<String> links() {
		if( _links == null ) {
			_links = USCheckUtil.linksOnPage( url() );
		}

		return _links;
	}

	public List<String> linksToCheck() {
		List<String> links = links();
		links.removeAll( anchors() );
		return links;
	}

	public List<String> anchors() {
		List<String> anchors = new ArrayList<String>();

		for( String link : links() ) {
			if( USCheckUtil.isAnchor( link ) ) {
				anchors.add( link );
			}
		}

		return anchors;
	}

	public List<USCheckedURL> checkLinks() {
		List<String> links = linksToCheck();
		List<USCheckedURL> checked = new ArrayList<USCheckedURL>();

		for( String link : links ) {

			if( !USCheckUtil.isAbsolute( link ) ) {
				link = USCheckUtil.makeAbsolute( url(), link );
			}

			USCheckedURL url = new USCheckedURL();
			url.setURL( link );
			checked.add( url );
		}

		return checked;
	}

	public boolean isHTML() {
		String s = USCheckUtil.contentTypeForURL( url() );

		if( s == null ) {
			return false;
		}

		return s.contains( "html" );
	}

	@Override
	public String toString() {
		StringBuffer b = new StringBuffer();
		b.append( "URL: " + url() );
		b.append( "\n" );
		b.append( "STATUS: " + status() );
		if( isHTML() ) {
			b.append( "\n" );
			b.append( "NUMBER OF LINKS: " + links().size() );
			b.append( "\n" );
			b.append( "LINKS: " + links() );
			b.append( "\n" );
			b.append( "NUMBER OF ANCHORS: " + anchors().size() );
			b.append( "\n" );
			b.append( "ANCHORS: " + anchors() );
			b.append( "\n" );
			b.append( "NUMBER OF HTTP LINKS: " + linksToCheck().size() );
			b.append( "\n" );
			b.append( "ABSOLUTE: " + linksToCheck() );
			b.append( "\n" );
		}
		return b.toString();
	}
}
