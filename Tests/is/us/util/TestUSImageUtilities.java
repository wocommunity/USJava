package is.us.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import is.us.thirdparty.ImageInfo;
import is.us.util.USImageUtilities.CodecType;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.junit.Test;

public class TestUSImageUtilities {
	private static final String TEST_IMAGE_NAME = "usimageutilities_original.jpg";
	private static final String TEST_IMAGE_PATH = USTestUtilities.documentPath( TestUSImageUtilities.class, TEST_IMAGE_NAME );

	@Test
	public void testOriginal() {
		try {
			BufferedImage bi2 = ImageIO.read( new File( TEST_IMAGE_PATH ) );
			byte[] bytes50 = USImageUtilities.encode( bi2, 50, CodecType.JPEG );
			ImageInfo info = USImageUtilities.imageInfo( bytes50 );
			assertEquals( info.getFormat(), ImageInfo.FORMAT_JPEG );
			assertEquals( info.getWidth(), 126 );
		}
		catch( Exception e ) {
			fail( e.getMessage() );
		}
	}

	@Test
	public void encode() {
		try {
			// test 10% quality
			BufferedImage bi = ImageIO.read( new File( TEST_IMAGE_PATH ) );
			byte[] bytes10 = USImageUtilities.encode( bi, 10, CodecType.JPEG );
			ImageInfo info = USImageUtilities.imageInfo( bytes10 );
			assertEquals( info.getFormat(), ImageInfo.FORMAT_JPEG );

			// test 50% quality
			BufferedImage bi2 = ImageIO.read( new File( TEST_IMAGE_PATH ) );
			byte[] bytes50 = USImageUtilities.encode( bi2, 50, CodecType.JPEG );
			info = USImageUtilities.imageInfo( bytes50 );
			assertEquals( info.getFormat(), ImageInfo.FORMAT_JPEG );
		}
		catch( Exception e ) {
			fail( e.getMessage() );
		}
	}

	@Test
	public void testScale() {
		try {
			byte[] input = USDataUtilities.readBytesFromFile( new File( TEST_IMAGE_PATH ) );
			byte[] bytes = USImageUtilities.scale( input, 150, 150, 100, CodecType.JPEG );
			ImageInfo info = USImageUtilities.imageInfo( bytes );
			assertEquals( info.getWidth(), 150 );
			assertEquals( info.getHeight(), 150 );
		}
		catch( Exception e ) {
			fail( e.getMessage() );
		}
	}

	@Test
	public void thumbnaleScale() {
		try {
			byte[] input = USDataUtilities.readBytesFromFile( new File( TEST_IMAGE_PATH ) );
			byte[] bytes = USImageUtilities.createThumbnail( input, 50, 50, 100, CodecType.PNG );
			ImageInfo info = USImageUtilities.imageInfo( bytes );
			assertEquals( info.getWidth(), 50 );
			assertEquals( info.getHeight(), 37 );
		}
		catch( Exception e ) {
			fail( e.getMessage() );
		}
	}

}
