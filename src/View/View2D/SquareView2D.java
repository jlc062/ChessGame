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
 * Class: SquareView
 *
 * Description:
 *
 * ****************************************
 */
package View.View2D;

import View.BoardView;
import View.PieceEnum;
import View.PieceView;
import View.SquareView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;


public class SquareView2D extends SquareView {

    private PieceView2D piece2D;

    /**Creates a StackPane object for a square that stores the square's location on the board
     *
     * @param row the square's row
     * @param col the square's column
     */
    SquareView2D(int row, int col) {
        super(7-row, col);
    }

    @Override
    public void putPiece(PieceEnum pieceType, Color color) {
        piece2D = new PieceView2D(pieceType, color);
        this.getChildren().add(piece2D.getView());
    }


    @Override
    public PieceView2D removePiece() {
        PieceView2D pieceToRemove = piece2D;
        this.getChildren().clear();
        this.piece2D = null;

        return pieceToRemove;
    }


    /**
     * sets size based off a pixel size
     * @param size the pixel size of the square
     */
    void setSize(int size) {
        this.setPrefSize(size, size);
    }

    /**
     * Adds a child to this square view
     * @param iv the ImageView node to be added
     */
    void addImageView(ImageView iv) { this.getChildren().add(iv); }


    /**
     * Highlights the selected square
     */
    @Override
    public void highlight() {
        if ((row + col) % 2 != 0) {
            setColor(BoardView.getSquare1Highlight());
        } else {
            setColor(BoardView.getSquare2Highlight());
        }
    }

    /**
     * unHighlights the selected square
     */
    @Override
    public void unHighlight(){
        if ((row + col) % 2 != 0) {
            setColor(BoardView.getSquare1Color());
        } else {
            setColor(BoardView.getSquare2Color());
        }
    }

    /**
     * grabs the imageview contained within the square
     * @return the ImageView node of the piece
     */
    public ImageView getPiece(){
        return (ImageView)this.getChildren().get(0);
    }

    public void setColor(Color color){
        this.setBackground(new Background(new BackgroundFill(color, null, null)));
    }

    @Override
    public void setPieceColor(Color newColor) {
        if(piece2D != null){
            this.getChildren().clear();
            putPiece(piece2D.getPieceType(), newColor);
        }
    }

}
