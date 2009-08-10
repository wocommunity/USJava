package is.us.util;

import java.io.*;
import java.net.*;

import org.slf4j.*;

/**
* Contains various utility methods for handling data,
* reading and writing data to and from disks or over a network connection etc.
*
* @author Hugi Þórðarson
*/

public class USDataUtilities {

	private static final Logger logger = LoggerFactory.getLogger( USDataUtilities.class );

	private USDataUtilities() {}

	/**
	 * Reads a file and returns a byte array.
	 *
	 * @param sourceFile The file to read from
	 */
	public static byte[] readBytesFromFile( File sourceFile ) {
		byte[] bytes = null;

		try {
			bytes = getBytesFromFile( sourceFile );
		}
		catch( IOException e ) {
			logger.debug( "Failed to read data from file: " + sourceFile, e );
		}

		return bytes;
	}

	/**
	 * Writes a byte array to a file.
	 *
	 * @param destination The file to write to
	 */
	public static void writeBytesToFile( byte[] bytes, File destination ) {

		try {
			FileOutputStream fos = new FileOutputStream( destination );
			fos.write( bytes );
			fos.close();
		}
		catch( Exception e ) {
			logger.error( "Failed to write data to file", e );
		}
	}

	/**
	 * Downloads a document from the given URL. 
	 * 
	 * @param sourceURL The URL to download data from.
	 */
	public static byte[] readBytesFromURL( String sourceURL ) {
		OutputStream out = null;
		URLConnection conn = null;
		InputStream in = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {
			URL url = new URL( sourceURL );
			out = new BufferedOutputStream( bos );
			conn = url.openConnection();
			in = conn.getInputStream();
			byte[] buffer = new byte[1024];
			int numRead;
			long numWritten = 0;

			while( (numRead = in.read( buffer )) != -1 ) {
				out.write( buffer, 0, numRead );
				numWritten += numRead;
			}
		}
		catch( Exception exception ) {
			exception.printStackTrace();
		}
		finally {
			try {
				if( in != null ) {
					in.close();
				}
				if( out != null ) {
					out.close();
				}
			}
			catch( IOException ioe ) {}
		}

		return bos.toByteArray();
	}

	// Returns the contents of the file in a byte array.
	private static byte[] getBytesFromFile( File file ) throws IOException {
		InputStream is = new FileInputStream( file );
		long length = file.length();

		if( length > Integer.MAX_VALUE ) {
			// File is too large
		}

		byte[] bytes = new byte[(int)length];

		int offset = 0;
		int numRead = 0;
		while( offset < bytes.length && (numRead = is.read( bytes, offset, bytes.length - offset )) >= 0 ) {
			offset += numRead;
		}

		if( offset < bytes.length ) {
			throw new IOException( "Could not completely read file " + file.getName() );
		}

		is.close();
		return bytes;
	}
}