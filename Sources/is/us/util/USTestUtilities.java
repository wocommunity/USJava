package is.us.util;

import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.security.*;

/**
 * Utility functions for unit testing
 * 
 * @author Bjarni
 * @reviewedby Hugi Thordarson
 */
public class USTestUtilities {

	private static final int readBufferSize = 8192;
	private static final String DIGEST_ALGORITHM = "MD5";

	/**
	 * Resolves the document physical path for documents located in the class folder.
	 * Note, folder is always the same as the caller Class folder
	 * 
	 * @param class thats used to locate the file
	 * @param fileName name of the file
	 * @return full document path for file
	 */
	public static String documentPath( Class<?> caller, String fileName ) {
		return classPath( caller ) + fileName;
	}

	/**
	 * Retrieves the physical directory path of the Class
	 * 
	 * @param caller Class
	 * @return directory path of the class
	 */
	public static String classPath( Class<?> caller ) {
		final URL location;
		String loc = caller.getName().replace( '.', File.separatorChar ) + ".class";

		final ClassLoader loader = caller.getClassLoader();
		if( loader == null ) {
			return null;
		}
		else {
			location = loader.getResource( loc );
			loc = location.getPath();
		}
		int fileIdx = loc.lastIndexOf( File.separatorChar );
		if( fileIdx >= 0 ) {
			loc = loc.substring( 0, fileIdx + 1 );
		}
		return loc;
	}

	/**
	 * Calculates a digest from a file.
	 * 
	 * @param inputFile full path to file
	 * @return a digest string of the files contents
	 * @throws NoSuchAlgorithmException if the algorithm, used for digest calculations, is not found
	 * @throws IOException if an I/O error occurs
	 */
	public static String streamDigest( String inputFile ) throws NoSuchAlgorithmException, IOException {
		FileInputStream fis = new FileInputStream( inputFile );
		return streamDigest( fis );
	}

	/**
	 * Calculates a digest from an input stream.
	 * 
	 * @param inputFile full path to file
	 * @return a digest string of the files contents
	 * @throws NoSuchAlgorithmException if the algorithm, used for digest calculations, is not found
	 * @throws IOException if an I/O error occurs
	 */
	public static String streamDigest( InputStream inputStream ) throws NoSuchAlgorithmException, IOException {
		MessageDigest digest = MessageDigest.getInstance( DIGEST_ALGORITHM );
		byte[] buffer = new byte[readBufferSize];
		int read = 0;
		while( (read = inputStream.read( buffer )) > 0 ) {
			digest.update( buffer, 0, read );
		}
		byte[] md5sum = digest.digest();
		BigInteger bigInt = new BigInteger( 1, md5sum );
		return bigInt.toString( 16 );
	}
}
