package is.us.util;

import java.io.*;
import java.net.*;
import java.util.*;

import org.slf4j.*;

/**
 * Contains various utility methods for handling data, reading and writing data to and from disks or over a network connection etc.
 * 
 * @author Hugi Þórðarson
 */

public class USDataUtilities {

	/**
	 * Logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger( USDataUtilities.class );

	/**
	 * No instances created.
	 */
	private USDataUtilities() {}

	/**
	 * Reads a file and returns a byte array.
	 * 
	 * @param sourceFile The file to read from
	 */
	public static byte[] readBytesFromFile( File sourceFile ) {

		try {
			InputStream is = new FileInputStream( sourceFile );
			long length = sourceFile.length();

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
				throw new IOException( "Could not completely read file " + sourceFile.getName() );
			}

			is.close();
			return bytes;
		}
		catch( IOException e ) {
			logger.debug( "Failed to read data from file: " + sourceFile, e );
			return null;
		}
	}

	public static byte[] readBytesFromStream( InputStream in ) {
		List<Byte> bytes = new ArrayList<Byte>();

		int b = 0;
		try {
			while( (b = in.read()) != -1 ) {
				bytes.add( new Byte( (byte)b ) );
			}
		}
		catch( IOException e ) {
			logger.debug( "Failed to read data from input stream", e );
			return null;
		}

		byte[] rslt = new byte[bytes.size()];
		for( int i = 0; i < bytes.size(); i++ ) {
			rslt[i] = bytes.get( i );
		}
		return rslt;
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
}