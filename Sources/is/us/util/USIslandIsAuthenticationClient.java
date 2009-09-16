package is.us.util;

import java.io.*;
import java.util.*;

import javax.net.ssl.*;

import nu.xom.*;

import org.slf4j.*;

import sun.misc.BASE64Encoder;

/**
 * Handle communications with the island.is authentication service.
 * 
 * @author Bjarni Sævarsson <bjarnis@us.is>
 * @author Atli Páll Hafsteinsson <atlip@us.is>
 * @reviewedby Logi Helgu at Sep 16, 2009( see JIRA issue INN-728 )
 */
public class USIslandIsAuthenticationClient {

	private static final Logger logger = LoggerFactory.getLogger( USIslandIsAuthenticationClient.class );

	private static final String CRLF = "\r\n";
	private static final String USER_AGENT_NAME = "is.us.island.authenticate";
	private static final String CONTENT_TYPE = "text/xml";
	private static final String SERVER = "egov.webservice.is";
	private static final int PORT = 443;
	private static final int SOCKET_TIMEOUT_MS = 4000;

	private static final String PATH = "/sst/runtime.asvc/com.actional.soapstation.eGOVDKM_AuthConsumer.AccessPoint";
	private static final String SOAP_METHOD = "generateSAMLFromToken";

	private String _token;
	private String _userIp;
	private String _username;
	private String _password;

	private Map<String, String> _samlInfo;

	/**
	 * Never construct this class using the default constructor, certain parameters required.
	 */
	@SuppressWarnings( "unused" )
	private USIslandIsAuthenticationClient() {}

	/**
	 * @param token the login token received from island.is
	 * @param userIp the users IP address
	 * @param username the island.is authentication service username
	 * @param password the island.is authentication service password
	 */
	public USIslandIsAuthenticationClient( String token, String userIp, String username, String password ) {
		_token = token;
		_userIp = userIp;
		_username = username;
		_password = password;
	}

	// TODO Shouldn't all the keys be constants
	// TODO (samlInfo.containsKey( "status.code" ) && !samlInfo.get( "status.code" ).equals( "0" )) is being called also in persidno, this should be a method
	// TODO What is a "saml" response?
	/**
	 * @return The sysid (identifier of the system that handled the login) from the saml response.
	 */
	public String sysid() {
		Map<String, String> samlInfo = samlInfo();

		if( (samlInfo.containsKey( "status.code" ) && !samlInfo.get( "status.code" ).equals( "0" )) || !samlInfo.containsKey( "SYSID" ) ) {
			return null;
		}

		return samlInfo.get( "SYSID" );
	}

	// TODO There is not request parameter here as stated in the JavaDoc
	/**
	 * @param request the request to fetch the persidno from.
	 * @return The user persidno from the saml response
	 * @throws USIslandIsAuthenticationException if there is an error getting the persidno
	 */
	public String persidno() {
		Map<String, String> samlInfo = samlInfo();

		if( (samlInfo == null) || !samlInfo.containsKey( "SSN" ) || (samlInfo.containsKey( "status.code" ) && !samlInfo.get( "status.code" ).equals( "0" )) ) {
			String errorMessage;

			if( samlInfo == null ) {
				errorMessage = "samlInfo == null";
			}
			else {
				errorMessage = "SSN = " + samlInfo.get( "SSN" ) + " status.code = " + samlInfo.get( "status.code" );
			}
			logger.error( errorMessage );
			throw new USIslandIsAuthenticationException( errorMessage );
		}

		return samlInfo.get( "SSN" );
	}

	/**
	 * Parses the SAML message, from the soap response, and extracts the status code/message and user ssn.
	 * @return the information from the SAML message
	 * @throws USIslandIsAuthenticationException if there is an error getting the SAML info
	 */
	private Map<String, String> samlInfo() {

		if( _samlInfo != null ) {
			return _samlInfo;
		}

		Map<String, String> info = new HashMap<String, String>();
		info.put( "TOKEN", _token );
		info.put( "IP", _userIp );

		Builder parser = new Builder();
		Document docXML = null;

		try {
			docXML = parser.build( sendSoapRequest(), "" );
			logger.debug( "Island.is xml response body: {}", docXML.toXML() );

			// parse the whole soap message
			Element body = null;
			body = getSoapBody( docXML );
			checkSoapForFaults( info, body );

			// fetch the soap function response from the soap body
			// TODO constants?
			Element soapResponseCertSaml = firstChild( body, "generateSAMLFromTokenResponse", "http://www.kogun.is/eGov/eGovSAMLGenerator.webServices" );

			// Check for SAML error
			checkSamlForErrors( info, soapResponseCertSaml );

			// fetch the saml message from the soap function response
			Element saml = soapResponseCertSaml.getFirstChildElement( "saml" );

			// if saml is html encoded we need to re-parse it
			Element assertion = parseAssertation( parser, saml );

			insertAttributesInMap( info, assertion );
		}
		// TODO This is being done all over, you could write a method that takes in the msg and e, logs it and throws USIslandIsAuthenticationException
		catch( ValidityException e ) {
			String msg = "Error validating island.is XML";
			logger.error( msg );
			throw new USIslandIsAuthenticationException( msg, e );
		}
		catch( ParsingException e ) {
			String msg = "Error parsing island.is XML";
			logger.error( msg );
			throw new USIslandIsAuthenticationException( msg, e );
		}
		catch( IOException e ) {
			String msg = "Error reading island.is XML";
			logger.error( msg );
			throw new USIslandIsAuthenticationException( msg, e );
		}

		_samlInfo = info;
		return _samlInfo;
	}

	// TODO soapResponseCertSaml is what?
	/**
	 * Checks for error status codes in the SAML and sets them in the information dictionary
	 * @param info the dictionary to set the error messages in
	 * @param soapResponseCertSaml
	 * @throws USIslandIsAuthenticationException if the are errors
	 */
	private void checkSamlForErrors( Map<String, String> info, Element soapResponseCertSaml ) {
		Element status = soapResponseCertSaml.getFirstChildElement( "status" );

		String type = status.getChildElements( "type" ).get( 0 ).getValue();
		String code = status.getChildElements( "code" ).get( 0 ).getValue();
		String msg = status.getChildElements( "message" ).get( 0 ).getValue();
		info.put( "status.type", type );
		info.put( "status.code", code );
		info.put( "status.message", msg );
		if( !code.equals( "0" ) ) {
			String m = "SAML message is not valid!";
			logger.error( m );
			throw new USIslandIsAuthenticationException( m );
		}
	}

	/**
	 * @param soapDocument the SOAP document to the body from
	 * @return the SOAP body from the given document
	 */
	private Element getSoapBody( Document soapDocument ) {
		return firstChild( soapDocument.getRootElement(), "Body", "http://schemas.xmlsoap.org/soap/envelope/" );
	}

	/**
	 * Gets the attributes from the assertion and puts them in a map
	 * @param info the map to put the attributes in
	 * @param assertion the assertion
	 */
	private void insertAttributesInMap( Map<String, String> info, Element assertion ) {
		Element AttributeStatement = firstChild( assertion, "AttributeStatement", "urn:oasis:names:tc:SAML:1.0:assertion" );
		Elements attributes = AttributeStatement.getChildElements();

		// Insert all the attributes, from the Attributestatement tag, into the info dictionary
		for( int i = 0; i < attributes.size(); i++ ) {
			Element child = attributes.get( i );

			if( child.getLocalName().equals( "Attribute" ) ) {
				String key = child.getAttribute( "AttributeName" ).getValue();
				String val = child.getChildElements().get( 0 ).getValue();
				info.put( key, val );
			}
		}
	}

	/**
	 * Parses the assertion element from the SAML message
	 * @param parser the xml parser to use
	 * @param saml the SAML message
	 * @return the assertion element from the SAML message
	 * @throws USIslandIsAuthenticationException if there is an error
	 */
	private Element parseAssertation( Builder parser, Element saml ) {
		Document docXML;
		Element assertion = null;
		// TODO I don't get this check
		if( saml.getChildElements().size() == 0 ) {
			try {
				docXML = parser.build( saml.getValue(), "" );
				assertion = docXML.getRootElement();

				// in case Assertion tag is not the root element we dig deeper for it
				if( !assertion.getLocalName().equals( "Assertion" ) ) {
					Elements samlChildren = assertion.getChildElements();
					boolean assertFound = false;
					for( int i = 0; i < samlChildren.size(); i++ ) {
						Element child = samlChildren.get( i );
						if( child.getLocalName().equals( "Assertion" ) ) {
							assertion = child;
							assertFound = true;
							break;
						}
					}
					if( !assertFound ) {
						String m = "island.is SAML message is not valid!";
						logger.error( m );
						throw new USIslandIsAuthenticationException( m );
					}
				}
			}
			catch( ValidityException e ) {
				String msg = "Error validating island.is XML";
				logger.error( msg );
				throw new USIslandIsAuthenticationException( msg, e );
			}
			catch( ParsingException e ) {
				String msg = "Error parsing island.is XML";
				logger.error( msg );
				throw new USIslandIsAuthenticationException( msg, e );
			}
			catch( IOException e ) {
				String msg = "Error reading island.is XML";
				logger.error( msg );
				throw new USIslandIsAuthenticationException( msg, e );
			}
		}

		if( assertion == null ) {
			assertion = firstChild( saml, "Assertion", "urn:oasis:names:tc:SAML:1.0:assertion" );
		}

		if( assertion == null ) {
			String m = "island.is SAML message is not valid, Assertion tag not found!";
			logger.error( m );
			throw new USIslandIsAuthenticationException( m );
		}

		return assertion;
	}

	/**
	 * Check for faults in the SOAP messages and sets them in the information dictionary
	 * @param info the dictionary to set the fault messages in
	 * @param body the {@link nu.xom.Element} (SOAP message) to check for faults
	 */
	private void checkSoapForFaults( Map<String, String> info, Element body ) {
		Element fault = body.getChildElements().get( 0 );

		if( (fault != null) && (fault.getLocalName().equals( "Fault" )) ) {
			Elements faultAttributes = fault.getChildElements();

			for( int i = 0; i < faultAttributes.size(); i++ ) {
				Element child = faultAttributes.get( i );
				String key = child.getLocalName();
				String val = child.getValue();
				info.put( key, val );
				logger.error( key + " = " + val );
			}

			String m = "SOAP message is not valid!";
			logger.error( m );
			throw new USIslandIsAuthenticationException( m );
		}
	}

	/**
	 * Handles the SAML request/response through a SSL socket
	 * 
	 * @return the response from the island.is authentication service
	 */
	private String sendSoapRequest() {

		SSLSocketFactory sf = (SSLSocketFactory)SSLSocketFactory.getDefault();
		SSLSocket socket = null;
		StringBuffer response = new StringBuffer();

		try {
			socket = (SSLSocket)sf.createSocket( SERVER, PORT );
			socket.setSoTimeout( SOCKET_TIMEOUT_MS );

			socket.startHandshake();
			PrintWriter dataOut = new PrintWriter( socket.getOutputStream() );
			BufferedReader dataIn = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
			dataOut.write( soapMessageHeaders( _token, _userIp, _username, _password ) );
			dataOut.write( soapMessageBody( _token, _userIp ) );
			dataOut.flush();

			StringBuffer xmlResponseHeaders = new StringBuffer();

			// Read the response headers
			String line;

			while( (line = dataIn.readLine()) != null ) {
				if( line.length() == 0 ) {
					break;
				}
				xmlResponseHeaders.append( line + CRLF );
			}

			logger.debug( "island.is xml response headers: " + xmlResponseHeaders.toString() );

			// Read the response body
			while( (line = dataIn.readLine()) != null ) {
				response.append( line + CRLF );
			}
		}
		catch( NoClassDefFoundError e ) {
			String msg = "SSLSocket handshake error";
			logger.error( msg, e );
			throw new USIslandIsAuthenticationException( msg, e );
		}
		catch( IOException e ) {
			String msg = "IOException when communicating with island.is";
			logger.error( msg, e );
			throw new USIslandIsAuthenticationException( msg, e );
		}
		finally {
			try {
				socket.close();
			}
			catch( Exception e ) {
				logger.error( "Failed to close island.is communications socket", e );
			}
		}

		return response.toString();
	}

	// TODO: Missing description for parameters 
	/**
	 * @return The SOAP request headers
	 */
	private String soapMessageHeaders( String token, String userIp, String username, String password ) {
		StringBuilder headers = new StringBuilder();

		headers.append( "POST " + PATH + " HTTP/1.0" + CRLF );
		headers.append( "User-Agent: " + USER_AGENT_NAME + CRLF );
		headers.append( "Host: us.is" + CRLF );
		headers.append( "Content-Type: " + CONTENT_TYPE + CRLF );
		headers.append( "Authorization: " + "Basic " + new BASE64Encoder().encode( new String( username + ":" + password ).getBytes() ) + CRLF );
		headers.append( "Content-Length: " + soapMessageBody( token, userIp ).length() + CRLF );
		headers.append( "Soapaction: " + SOAP_METHOD + CRLF );
		headers.append( CRLF );

		return headers.toString();
	}

	// TODO: Missing description for parameters 
	/**
	 * @return The soap request body.
	 */
	private String soapMessageBody( String token, String userIp ) {
		StringBuilder body = new StringBuilder();

		body.append( "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" + CRLF );
		body.append( "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" + CRLF );
		body.append( " xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"" + CRLF );
		body.append( " xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"" + CRLF );
		body.append( " xmlns:egov=\"http://www.kogun.is/eGov/eGovSAMLGenerator.webServices\">" + CRLF );
		body.append( "<soapenv:Header/>" + CRLF );
		body.append( "<soapenv:Body>" + CRLF );
		body.append( "<egov:generateSAMLFromToken soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">" + CRLF );
		body.append( "<token xsi:type=\"xsd:string\">" + token + "</token>" + CRLF );
		body.append( "<ipAddress xsi:type=\"xsd:string\">" + userIp + "</ipAddress>" + CRLF );
		body.append( "</egov:generateSAMLFromToken>" + CRLF );
		body.append( "</soapenv:Body>" + CRLF );
		body.append( "</soapenv:Envelope>" + CRLF );

		return body.toString();
	}

	/**
	 * Returns the first child element, from parent, with given name. Attempts to search in a new namespace if parent namespace returns null.
	 * 
	 * @param parent Element to search in.
	 * @param name Name of the child element to return.
	 * @param namespace Namespace of the child element.
	 */
	private Element firstChild( Element parent, String name, String namespace ) {
		Element childElement = parent.getFirstChildElement( name );

		if( (childElement == null) && (namespace != null) ) {
			childElement = parent.getFirstChildElement( name, namespace );
		}

		return childElement;
	}
}
