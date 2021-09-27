/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Ethan Dunne
 * Section: 11am
 * Date: 11/26/19
 * Time: 6:44 PM
 *
 * Project: csci205finalproject
 * Package: SinglePlatform.View
 * Class: SquareView
 *
 * Description:
 *
 * ****************************************
 */
package View;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public abstract class SquareView extends StackPane {

    //protected PieceView currentPiece;

    protected int row;
    protected int col;


    public SquareView(int row, int col) {
        super();
        this.row = row;
        this.col = col;
    }

    public abstract void putPiece(PieceEnum pieceType, Color color);

    public abstract PieceView removePiece();

    public abstract void setPieceColor(Color newColor);

    public abstract void highlight();

    public abstract void unHighlight();

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
