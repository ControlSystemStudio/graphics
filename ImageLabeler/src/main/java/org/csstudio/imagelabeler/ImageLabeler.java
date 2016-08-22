package org.csstudio.imagelabeler;


import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;


@SuppressWarnings( "ClassWithoutLogger" )
public class ImageLabeler {

	@SuppressWarnings( "UseOfSystemOutOrSystemErr" )
	public static void main( String[] args ) throws Exception {

		if ( ( args.length != 3 + 2 ) && ( args.length != 3 + 2 + 3 ) && ( args.length != 3 + 2 + 3 + 3 ) ) {
			System.out.println(
				"Usage: java -jar ImageLabeler-2.0.jar <version> <xPosition> <yPosition> <template.bmp> <splash.bmp>\n" +
				"                                     [<affiliation>  <xAffPosition> <yAffPosition>\n" +
				"                                     [<icon> <xIconPosition> <yIconPosition>]]\n" +
				"Where:\n" +
				"  <version>       is the version string,\n" +
				"  <xPosition>     is the right  X coordinate of the version string,\n" +
				"  <yPosition>     is the bottom Y coordinate of the version string,\n" +
				"  <template.bmp>  is the pathname of the template bitmap file,\n" +
				"  <splash.bmp>    is the pathname of the generated (annotated) splash-screen bitmap\n" +
				"  <affiliation>   is an optional affiliation text\n" +
				"  <xAffPosition>  is the left   X coordinate of the affiliation string,\n" +
				"  <yAffPosition>  is the bottom Y coordinate of the affiliation string,\n"
			);
			System.exit(- args.length);
		}

		String version = args[0];
		int xPosition = Integer.parseInt(args[1]); //394;
		int yPosition = Integer.parseInt(args[2]); // 53;
		String inFile = args[3];
		String outFile = args[4];
		BufferedImage image = ImageIO.read(new File(inFile));
		Graphics2D g = (Graphics2D) image.getGraphics();

		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		String fontName = "AGENCYR.TTF";
		Font font = loadFont(fontName);

		g.setFont(font);
		g.setTransform(AffineTransform.getTranslateInstance(0.3, 0));

		int length = g.getFontMetrics().stringWidth(version);
		
		g.drawString(version, xPosition - length, yPosition);

		if ( args.length >= 3 + 2 + 3 ) {

			String affiliation = args[5];
			int xAffPosition = Integer.parseInt(args[6]); // 19;
			int yAffPosition = Integer.parseInt(args[7]); //151;

			g.drawString(affiliation, xAffPosition, yAffPosition);



		}

		ImageIO.write(image, "bmp", new File(outFile));

	}

	private static Font loadFont( String name ) {

		try {

			Font font = Font.createFont(Font.TRUETYPE_FONT, ImageLabeler.class.getResourceAsStream(name));

			return font.deriveFont(Font.BOLD, 21f);

		} catch ( FontFormatException ex ) {
			Logger.getLogger(ImageLabeler.class.getName()).log(Level.SEVERE, null, ex);
		} catch ( IOException ex ) {
			Logger.getLogger(ImageLabeler.class.getName()).log(Level.SEVERE, null, ex);
		}

		throw new RuntimeException("Couldn't load");

	}

}
