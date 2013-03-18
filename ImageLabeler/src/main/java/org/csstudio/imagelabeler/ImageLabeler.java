package org.csstudio.imagelabeler;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class ImageLabeler {
    
    private static Font loadFont(String name) {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, ImageLabeler.class.getResourceAsStream(name));
            return font.deriveFont(Font.PLAIN, 21f);
        } catch (FontFormatException ex) {
            Logger.getLogger(ImageLabeler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ImageLabeler.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new RuntimeException("Couldn't load");
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.out.println("Usage: [version] [template.bmp] [splash.bmp]");
            System.exit(-1);
        }
        
        String version = args[0];
        String fontName = "AGENCYR.TTF";
        String inFile = args[1];
        String outFile = args[2];
        int xPosition = 394;
        int yPosition = 53;
        BufferedImage image = ImageIO.read(new File(inFile));
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = loadFont(fontName);
        g.setFont(font);
        g.setTransform(AffineTransform.getTranslateInstance(0.3, 0));
        int length = g.getFontMetrics().stringWidth(version);
        g.drawString(version, xPosition - length, yPosition);
        ImageIO.write(image, "bmp", new File(outFile));
    }
}
