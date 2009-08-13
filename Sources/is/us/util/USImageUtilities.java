package is.us.util;

import is.us.thirdparty.ImageInfo;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

import javax.imageio.*;
import javax.imageio.stream.MemoryCacheImageOutputStream;

import org.slf4j.*;

/**
 * Various utility methods for dealing with image data.
 * 
 * @author Hugi Þórðarson
 */

public class USImageUtilities {

	private static final Logger logger = LoggerFactory.getLogger( USImageUtilities.class );

	public static enum CodecType {
		JPEG, PNG
	};

	/**
	 * No instances created, ever.
	 */
	private USImageUtilities() {}

	public static byte[] scale( byte[] imageData, int width, int height, int qualityPercent, CodecType codecType ) {
		return scale( bufferedImageFromData( imageData ), width, height, qualityPercent, codecType );
	}

	/**
	* Takes byte array containing an image and scales it to the given size
	* 
	 * @param width The new width
	 * @param height The new height
	 * @param qualityPercent The JPEG quality to write out (1-100)
	 * @param codecType
	 * @param aPicture The picture to scale
	 */

	public static byte[] scale( Image inImage, int width, int height, int qualityPercent, CodecType codecType ) {
		Image scaledImage = inImage.getScaledInstance( width, height, BufferedImage.SCALE_SMOOTH );
		BufferedImage outImage = new BufferedImage( width, height, BufferedImage.TYPE_INT_RGB );
		Graphics2D g2 = outImage.createGraphics();
		g2.drawImage( scaledImage, 0, 0, null );

		byte[] img = null;
		if( codecType == CodecType.JPEG )
			img = jpegEncodeBufferedImage( outImage, qualityPercent );
		else
			img = pngEncodeBufferedImage( outImage );

		return img;
	}

	public static byte[] createThumbnail( byte[] bytes, int maxWidth, int maxHeight, int qualityPercent, CodecType codecType ) {
		BufferedImage bi = bufferedImageFromData( bytes );
		return createThumbnail( bi, maxWidth, maxHeight, qualityPercent, codecType );
	}

	public static byte[] createThumbnail( BufferedImage b, int maxWidth, int maxHeight, int qualityPercent, CodecType codecType ) {
		byte[] img = null;
		if( b == null )
			return null;

		int height = b.getHeight();
		int width = b.getWidth();

		if( height > maxHeight || width > maxWidth ) {
			float proportions = calculateProportions( height, width, maxHeight, maxWidth );
			float newHeight = height * proportions;
			float newWidth = width * proportions;
			img = scale( b, (int)newWidth, (int)newHeight, qualityPercent, codecType );
		}
		else {
			if( codecType == CodecType.JPEG )
				img = jpegEncodeBufferedImage( b, qualityPercent );
			else if( codecType == CodecType.PNG )
				img = pngEncodeBufferedImage( b );

		}
		return img;
	}

	/**
	 * 
	 * @param height
	 * @param width
	 * @param maxHeight
	 * @param maxWidth
	 * @return
	 */
	private static float calculateProportions( int height, int width, int maxHeight, int maxWidth ) {
		float hprop = 1;
		float wprop = 1;

		if( height > maxHeight ) {
			hprop = (float)maxHeight / (float)height;
		}

		if( width > maxWidth ) {
			wprop = (float)maxWidth / (float)width;
		}

		return Math.min( wprop, hprop );
	}

	/**
	 * 
	 * @param imageData
	 * @return
	 */
	public static BufferedImage bufferedImageFromData( byte[] imageData ) {
		try {
			return ImageIO.read( new ByteArrayInputStream( imageData ) );
		}
		catch( Exception e ) {
			logger.error( "Error while creating buffered image", e );
			return null;
		}
	}

	/**
	 * 
	 * @param image
	 * @param qualityPercent
	 * @return
	 */
	public static byte[] jpegEncodeBufferedImage( BufferedImage image, float qualityPercent ) {

		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();

			try {
				Iterator iter = ImageIO.getImageWritersByFormatName( "jpeg" );

				float quality = qualityPercent / 100f;
				ImageWriter writer = (ImageWriter)iter.next();
				ImageWriteParam iwp = writer.getDefaultWriteParam();
				iwp.setCompressionMode( ImageWriteParam.MODE_EXPLICIT );
				iwp.setCompressionQuality( quality );

				MemoryCacheImageOutputStream output = new MemoryCacheImageOutputStream( os );
				writer.setOutput( output );
				IIOImage iioimage = new IIOImage( image, null, null );
				writer.write( null, iioimage, iwp );
				writer.dispose();
			}
			catch( Exception e ) {
				logger.error( "Error while jpeg encoding buffered image", e );
			}
			finally {
				os.close();
			}

			return os.toByteArray();
		}
		catch( IOException e ) {
			logger.error( "Error while jpeg encoding buffered image", e );
			return null;
		}
	}

	/**
	 * @author Atli Páll Hafsteinsson
	 */
	public static byte[] pngEncodeBufferedImage( BufferedImage image ) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();

			try {
				Iterator iter = ImageIO.getImageWritersByFormatName( "png" );

				ImageWriter writer = (ImageWriter)iter.next();
				ImageWriteParam iwp = writer.getDefaultWriteParam();

				MemoryCacheImageOutputStream output = new MemoryCacheImageOutputStream( os );
				writer.setOutput( output );
				IIOImage iioimage = new IIOImage( image, null, null );
				writer.write( null, iioimage, iwp );
				writer.dispose();
			}
			catch( IOException e ) {
				logger.error( "Error while png encoding buffered image", e );
			}
			finally {
				os.close();
			}

			return os.toByteArray();
		}
		catch( IOException e ) {
			logger.error( "Error while png encoding buffered image", e );
			return null;
		}
	}

	/**
	 * 
	 * @param data
	 * @return
	 */
	public static ImageInfo imageInfo( byte[] data ) {
		ImageInfo ii = new ImageInfo();
		ii.setInput( new ByteArrayInputStream( data ) );

		if( !ii.check() )
			return null;

		return ii;
	}
}