package is.us.util;

import static org.junit.Assert.*;

import java.io.*;

import org.junit.Test;

/**
 * Tests for USDataUtilities.
 * 
 * @author Hugi Thordarson
 */

public class TestUSDataUtilities {

	@Test
	public void readBytesFromURL() {
		byte[] bytes = USDataUtilities.readBytesFromURL( "http://www.google.com/" );
		String s = USStringUtilities.stringFromDataUsingEncoding( bytes, "UTF-8" );
		String expected = "<!doctype html>";
		assertTrue( s.startsWith( expected ) );
	}

	@Test
	public void readWrite() throws IOException {
		File testFile = File.createTempFile( "abc", "def" );

		String testString = "testString þjóðarþýðingin";
		byte[] testBytes = testString.getBytes( "UTF-8" );

		USDataUtilities.writeBytesToFile( testBytes, testFile );

		byte[] readBytes = USDataUtilities.readBytesFromFile( testFile );
		String readString = new String( readBytes, "UTF-8" );

		assertEquals( readString, testString );

		testFile.delete();
	}
}