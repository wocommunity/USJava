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
 * @reviewedBy Bjarni Sævarsson
 */

public class USImageUtilities {

	/**
	 * Logger for the class
	 */
	private static final Logger logger = LoggerFactory.getLogger( USImageUtilities.class );

	/**
	 * Codecs supported by 
	 */
	public static enum CodecType {
		JPEG, PNG
	}

	/**
	 * No instances created, ever.
	 */
	private USImageUtilities() {}

	/**
	 * Scales the image in imageData to the size given in width and height.
	 * 
	 * @param imageData The image data to scale.
	 * @param scale Of the new height and width.
	 * @param qualityPercent quality percentage, if the given codec is JPEG.
	 * @param codecType The Codec to use.
	 */
	public static byte[] scale( byte[] imageData, int percents, int qualityPercent, CodecType codecType ) {
		BufferedImage inImage = bufferedImageFromData( imageData );
		double p = percents / 100.0;
		int width = (int)((double)inImage.getWidth() * p);
		int height = (int)((double)inImage.getHeight() * p);

		BufferedImage outImage = scale( inImage, width, height );
		return encode( outImage, qualityPercent, codecType );
	}

	/**
	 * Scales the image in imageData to the size given in width and height.
	 * 
	 * @param imageData The image data to scale.
	 * @param width The new width.
	 * @param height The new height.
	 * @param qualityPercent quality percentage, if the given codec is JPEG.
	 * @param codecType The Codec to use.
	 */
	public static byte[] scale( byte[] imageData, int width, int height, int qualityPercent, CodecType codecType ) {
		BufferedImage inImage = bufferedImageFromData( imageData );
		BufferedImage outImage = scale( inImage, width, height );
		return encode( outImage, qualityPercent, codecType );
	}

	/**
	 * Scales the given Image to the specified size.
	 * 
	 * @param image The image to scale.
	 * @param width The new width
	 * @param height The new height
	 */
	private static BufferedImage scale( BufferedImage image, int width, int height ) {
		if( image == null ) {
			return null;
		}
		Image scaledImage = image.getScaledInstance( width, height, BufferedImage.SCALE_SMOOTH );
		BufferedImage outImage = new BufferedImage( width, height, BufferedImage.TYPE_INT_RGB );
		Graphics2D g2 = outImage.createGraphics();
		g2.drawImage( scaledImage, 0, 0, null );
		return outImage;
	}

	/**
	 * Rotates the given image according to <b>rotateDegrees</b>
	 * 
	 * @param imageData the image data to rotate
	 * @param rotateDegrees rotate angle in degrees
	 * @param qualityPercent if quality compression is not available in selected <b>codecType</b> then this parameter is ignored
	 * @param codecType image codec to use
	 * @return a byte array containing the result image data
	 */
	public static byte[] rotate( byte[] imageData, int rotateDegrees, int qualityPercent, CodecType codecType ) {
		BufferedImage inImage = bufferedImageFromData( imageData );
		BufferedImage outImage = rotate( inImage, rotateDegrees );
		inImage.flush();
		return encode( outImage, qualityPercent, codecType );
	}

	/**
	 * Rotates the given image according to <b>rotateDegrees</b>
	 * @param image {@link BufferedImage} to rotate
	 * @param rotateDegrees degrees of rotation
	 * @return a new rotated {@link BufferedImage} object
	 */
	public static BufferedImage rotate( BufferedImage image, int rotateDegrees ) {
		int width = image.getWidth();
		int height = image.getHeight();
		double angleRadians = Math.toRadians( rotateDegrees );

		// calculate new image widths
		double sin = Math.abs( Math.sin( angleRadians ) );
		double cos = Math.abs( Math.cos( angleRadians ) );
		int newWidth = (int)Math.floor( width * cos + height * sin );
		int newHeight = (int)Math.floor( height * cos + width * sin );

		BufferedImage result = new BufferedImage( newWidth, newHeight, BufferedImage.TYPE_INT_RGB );
		Graphics2D g = result.createGraphics();

		// white background
		g.setBackground( Color.white );
		g.fillRect( 0, 0, newWidth, newHeight );

		// rotate old image on image center point
		g.translate( (newWidth - width) / 2, (newHeight - height) / 2 );
		g.rotate( angleRadians, width / 2, height / 2 );

		g.drawRenderedImage( image, null );
		image.flush();
		return result;
	}

	/**
	 * Resizes the given image, if it  does not fit within the box specified by maxWidth and maxHeight.
	 * 
	 * @param imageData The image to scale.
	 * @param maxWidth The maximum width of the resulting image.
	 * @param maxHeight The maximum height of the resulting image.
	 * @param qualityPercent Quality percentage. Ignored if the codec is not JPEG.
	 * @param codecType The codec to use.
	 */
	public static byte[] createThumbnail( byte[] imageData, int maxWidth, int maxHeight, int qualityPercent, CodecType codecType ) {
		BufferedImage inImage = bufferedImageFromData( imageData );
		BufferedImage outImage = createThumbnail( inImage, maxWidth, maxHeight );
		return encode( outImage, qualityPercent, codecType );
	}

	/**
	 * Resizes the given image, if it  does not fit within the box specified by maxWidth and maxHeight.
	 * 
	 * @param image The image to scale.
	 * @param maxWidth The maximum width of the resulting image.
	 * @param maxHeight The maximum height of the resulting image.
	 */
	private static BufferedImage createThumbnail( BufferedImage image, int maxWidth, int maxHeight ) {

		if( image == null ) {
			return null;
		}

		int height = image.getHeight();
		int width = image.getWidth();

		if( height <= maxHeight && width <= maxWidth ) {
			return image;
		}

		float proportions = calculateProportions( height, width, maxHeight, maxWidth );
		float newHeight = height * proportions;
		float newWidth = width * proportions;

		return scale( image, (int)newWidth, (int)newHeight );
	}

	/**
	 * Calculates the proportional size of a box, given maximum dimensions.
	 * 
	 * @param height Original height.
	 * @param width Original width.
	 * @param maxHeight Maximum height.
	 * @param maxWidth Maximum width.
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
	 * Attempts to construct a buffered image from reading the bytes in a byte array.
	 * 
	 * @param imageData the data to read.
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
	 * Encodes the given image.
	 * 
	 * @param image The image to encode.
	 * @param qualityPercent Quality percentage. Ignored if the codec is not JPEG.
	 * @param codecType The codec to use.
	 */
	public static byte[] encode( BufferedImage image, int qualityPercent, CodecType codecType ) {

		if( codecType == CodecType.JPEG ) {
			return encodeJPEG( image, qualityPercent );
		}

		return encodePNG( image );
	}

	/**
	 * Encodes the given image as JPEG, with the given quality.
	 *  
	 * @param image the image to encode.
	 * @param qualityPercent The quality of the resulting image.
	 */
	private static byte[] encodeJPEG( BufferedImage image, float qualityPercent ) {

		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();

			try {
				Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName( "jpeg" );

				float quality = qualityPercent / 100f;
				ImageWriter writer = iter.next();
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
			byte[] arr = os.toByteArray();
			os.flush();
			os = null;
			return arr;
		}
		catch( IOException e ) {
			logger.error( "Error while jpeg encoding buffered image", e );
			return null;
		}
	}

	/**
	 * Encodes the given image as PNG.
	 *  
	 * @param image the image to encode.
	 */
	private static byte[] encodePNG( BufferedImage image ) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();

			try {
				Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName( "png" );

				ImageWriter writer = iter.next();
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
	 * Returns an instance of the 3rd party class "Image"Info, that reads and provides various metadata on the image.
	 * See ImageInfo.java for information on provided data and a list of formats it handles.
	 * 
	 * @param imageData The image data.
	 */
	public static ImageInfo imageInfo( byte[] imageData ) {
		ImageInfo ii = new ImageInfo();
		ii.setInput( new ByteArrayInputStream( imageData ) );

		if( !ii.check() ) {
			return null;
		}

		return ii;
	}
}