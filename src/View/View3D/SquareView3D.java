/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Ryan Bailis
 * Section: MWF 11am
 * Instructor: Professor Brian King
 * Date: 11/24/19
 * Time: 12:51 PM
 *
 * Project: csci205FinalProject
 * Package: SinglePlatform.View.View3D.STLParser
 * Class: BoardSquare3DView
 *
 * Description:
 *
 * ****************************************
 */
package View.View3D;

import View.BoardView;
import View.PieceEnum;
import View.SquareView;

import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class SquareView3D extends SquareView {

    public static int SQUARE_SIZE = 100;
    public static int SQUARE_DEPTH = 10;

    private static double SELECTED_HUE_SHIFT = 1.0;
    private static double SELECTED_SATURATION_FACTOR = 1.0;
    private static double SELECTED_BRIGHTNESS_FACTOR = 1.0;
    private static double SELECTED_OPACITY_FACTOR = 0.3;

    private boolean hasPiece;
    private Color color;
    private PieceView3D piece3D;
    private Box square;

    SquareView3D(int row, int col, Color color) {
        super(7-row, col);
        this.setAlignment(Pos.CENTER);
        this.color = color;
        this.piece3D = null;

        this.square = new Box(SQUARE_SIZE, SQUARE_SIZE,SQUARE_DEPTH);
        square.setMaterial(new PhongMaterial(color));

        this.getChildren().addAll(square);

        this.hasPiece = false;

        //changeColor();
    }

    @Override
    public void putPiece(PieceEnum pieceType, Color color) {
        if (!this.hasPiece) {
            piece3D = new PieceView3D(pieceType, color);
            this.getChildren().add(piece3D.getPieceMesh());
            this.hasPiece = true;
        }
        else {
            System.out.println("There is already a piece here.");
        }

    }

    @Override
    public PieceView3D removePiece() {
        if (this.hasPiece) {
            PieceView3D pieceToRemove = this.piece3D;
            this.getChildren().remove(pieceToRemove.getPieceMesh());
            this.piece3D = null;
            this.hasPiece = false;
            return pieceToRemove;
        }
        else {
            System.out.println("No piece to remove.");
            return null;
        }
    }

    @Override
    public void setPieceColor(Color newColor) {
        if(hasPiece){
            piece3D.setColor(newColor);
        }
    }

    @Override
    public void highlight() {

        if ((row + col) % 2 == 0) {
            this.square.setMaterial(new PhongMaterial(BoardView.getSquare1Highlight()));
        } else {
            this.square.setMaterial(new PhongMaterial(BoardView.getSquare2Highlight()));
        }

        if (hasPiece) {
            this.piece3D.selectPiece();
        }
    }

    @Override
    public void unHighlight(){
        this.square.setMaterial(new PhongMaterial(color));

        if (hasPiece) {
            this.piece3D.deselectPiece();
        }
    }


}