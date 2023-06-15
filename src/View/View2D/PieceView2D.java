/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jake Schaeffer
 * Section: 11am
 * Date: 11/13/19
 * Time: 11:34 AM
 *
 * Project: csci205finalproject
 * Package: SinglePlatform.View
 * Class: PieceView
 *
 * Description:
 *
 * ****************************************
 */
package View.View2D;

import View.PieceEnum;
import View.PieceView;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;

/**
 * 2D representation of a chess piece
 */
public class PieceView2D extends PieceView {

    ImageView view;

    /**
     * Constructor that sets the piece type and the color by creating a new imageview to represent the piece
     * @param pieceType - the piece type that should be represented
     * @param color - the color the image should be
     */
    public PieceView2D(PieceEnum pieceType, Color color)  {
        super(pieceType, color);

        setColor(color);

    }

    /**
     * Color a given image a specific color
     * @param image The image to color
     * @param color the color to make the image
     * @return the colored image
     */
    private static BufferedImage colorImage(BufferedImage image, Color color) {
        int width = image.getWidth();
        int height = image.getHeight();
        WritableRaster raster = image.getRaster();

        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                int[] pixels = raster.getPixel(xx, yy, (int[]) null);
                if (pixels.length >= 3) {
                    //colorize the pixels only if the pixel is opaque and white
                    if (pixels[0] > 50 && pixels[1] > 50 && pixels[2] > 50) {
                        pixels[0] = (int) (color.getRed() * 255);
                        pixels[1] = (int) (color.getGreen() * 255);
                        pixels[2] = (int) (color.getBlue() * 255);
                        raster.setPixel(xx, yy, pixels);
                    }
                }

            }
        }
        return image;
    }

    /**
     * @return the ImageView Object for the piece
     */
    public ImageView getView(){
        return view;
    }

    /** Override method to set the color of a 2D piece by creating a new image and coloring it and setting it as the view.
     *
     * @param newColor the color to color the new piece image
     */
    @Override
    public void setColor(Color newColor) {
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(new URL(this.pieceType.getFileName2D()));
            BufferedImage coloredImage = colorImage(bufferedImage,newColor);
            Image image = SwingFXUtils.toFXImage(coloredImage, null);
            view = new ImageView(image);

            //these are temporary
            view.setFitHeight(80);
            view.setFitWidth(80);

        } catch (IOException e) {
            System.err.println("Error with creating the buffered image");
            e.printStackTrace();
        }
    }
}
