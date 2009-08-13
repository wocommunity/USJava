package is.us.util;

import static org.junit.Assert.*;
import is.us.util.USImageUtilities.CodecType;

import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

import org.junit.Test;

public class TestUSImageUtilities {
	private static final String CHECKSUM_TEST_IMAGE_ORIGINAL = "2bd7091ec2a5ffeaaf637c4cf141cbd2";
	private static final String TEST_IMAGE_ORIGINAL = "usimageutilities_original.jpg";

	private static final String TEST_IMAGE_QUALITY_10 = "usimageutilities_quality_10.jpg";
	private static final String TEST_IMAGE_QUALITY_50 = "usimageutilities_quality_50.jpg";
	private static final String TEST_IMAGE_SCALE_150X150 = "usimageutilities_scale_150x150.jpg";
	private static final String TEST_IMAGE_THUMBNAIL_100X100 = "usimageutilities_thumbnail_100x100.jpg";
	private static final String originalPath = USTestUtilities.documentPath( TestUSImageUtilities.class, TEST_IMAGE_ORIGINAL );

	@Test
	public void testOriginal() {
		String t1Path = USTestUtilities.documentPath( this.getClass(), TEST_IMAGE_ORIGINAL );
		try {
			assertEquals( CHECKSUM_TEST_IMAGE_ORIGINAL, USTestUtilities.streamDigest( t1Path ) );
		}
		catch( Exception e ) {
			fail( e.getMessage() );
		}
	}

	@Test
	public void encode() {
		String quality10Path = USTestUtilities.documentPath( this.getClass(), TEST_IMAGE_QUALITY_10 );
		String quality50Path = USTestUtilities.documentPath( this.getClass(), TEST_IMAGE_QUALITY_50 );

		try {
			// test 10% quality
			BufferedImage bi = ImageIO.read( new File( originalPath ) );
			byte[] bytes = USImageUtilities.encode( bi, 10, CodecType.JPEG );
			InputStream inStream1 = new ByteArrayInputStream( bytes );
			assertEquals( USTestUtilities.streamDigest( quality10Path ), USTestUtilities.streamDigest( inStream1 ) );

			// test 50% quality
			BufferedImage bi2 = ImageIO.read( new File( originalPath ) );
			byte[] bytes50 = USImageUtilities.encode( bi2, 50, CodecType.JPEG );
			InputStream inStream50 = new ByteArrayInputStream( bytes50 );
			assertEquals( USTestUtilities.streamDigest( quality50Path ), USTestUtilities.streamDigest( inStream50 ) );
		}
		catch( Exception e ) {
			fail( e.getMessage() );
		}
	}

	@Test
	public void testScale() {
		String testPath = USTestUtilities.documentPath( this.getClass(), TEST_IMAGE_SCALE_150X150 );
		try {
			byte[] input = USDataUtilities.readBytesFromFile( new File( originalPath ) );
			byte[] bytes = USImageUtilities.scale( input, 150, 150, 100, CodecType.JPEG );
			InputStream inStream = new ByteArrayInputStream( bytes );
			assertEquals( USTestUtilities.streamDigest( testPath ), USTestUtilities.streamDigest( inStream ) );
		}
		catch( Exception e ) {
			fail( e.getMessage() );
		}
	}

	@Test
	public void thumbnaleScale() {
		String testPath = USTestUtilities.documentPath( this.getClass(), TEST_IMAGE_THUMBNAIL_100X100 );
		try {
			byte[] input = USDataUtilities.readBytesFromFile( new File( testPath ) );
			byte[] bytes = USImageUtilities.createThumbnail( input, 50, 50, 100, CodecType.PNG );
			InputStream inStream = new ByteArrayInputStream( bytes );
			assertEquals( USTestUtilities.streamDigest( testPath ), USTestUtilities.streamDigest( inStream ) );
		}
		catch( Exception e ) {
			fail( e.getMessage() );
		}
	}

	public void saveFile( InputStream inStream, String fileName ) throws IOException {
		File file = new File( fileName );
		OutputStream out = new FileOutputStream( file );
		byte buf[] = new byte[8192];
		int len;
		while( (len = inStream.read( buf )) > 0 ) {
			out.write( buf, 0, len );
		}
		out.close();
	}
}
