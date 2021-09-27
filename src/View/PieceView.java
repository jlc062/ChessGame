/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Ethan Dunne
 * Section: 11am
 * Date: 12/8/19
 * Time: 9:04 PM
 *
 * Project: csci205finalproject
 * Package: View
 * Class: PieceView
 *
 * Description:
 *
 * ****************************************
 */
package View;

import javafx.scene.paint.Color;

public abstract class PieceView {

    protected PieceEnum pieceType;
    protected Color pieceColor;

    public PieceView(PieceEnum pieceType, Color pieceColor) {
        this.pieceType = pieceType;
        this.pieceColor = pieceColor;
    }

    public abstract void setColor(Color newColor);

    public Color getPieceColor() {
        return pieceColor;
    }

    public PieceEnum getPieceType() {
        return pieceType;
    }

}
