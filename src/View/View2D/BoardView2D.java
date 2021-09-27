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
 * Class: BoardView
 *
 * Description:
 *
 * ****************************************
 */
package View.View2D;

import Model.ChessBoard;
import Model.ChessPieces.ChessPiece;
import Model.Team;
import View.BoardView;
import javafx.scene.paint.Color;

/**
 * This class is the container for the TilePane that contains all the squares on the board.
 */
public class BoardView2D extends BoardView {


    /**
     * creates a board that is a tile pane and puts a SquareView object in each spot
     * @param size pixel width and height of the board
     */
    public BoardView2D(int size, ChessBoard modelBoard){
        //the board will be a grid of squares
        super();
        this.setPrefSize(size,size);
        //for every one of the 64 squares
        for (int row = 0; row < SIDE_LENGTH; row++) {
            for (int col = 0; col < SIDE_LENGTH; col++) {
                initSquare(size, row, col);
            }
        }

        initPiecesFromBoard(modelBoard);
    }

    /**Creates a SquareView at the specified spot on the board
     *
     * @param size the size of the board
     * @param row the row on the board
     * @param col the column on the board
     */
    private void initSquare(int size, int row, int col) {
        //make each square a SquareView
        SquareView2D square = new SquareView2D(row,col);
        //pick color of square
        if ((row + col) % 2 == 0) {
            square.setColor(getSquare1Color());
        } else {
            square.setColor(getSquare2Color());
        }
        this.getChildren().add(square);
    }




    @Override
    public PieceView2D removePieceFromBoard(int row, int col) {
        return this.getSquareAt(row, col).removePiece();
    }



    /**
     * Grabs the SquareView at the specified spot in the board
     *
     * @param row the square's row
     * @param col the square's column
     * @return the SquareView
     */
    @Override
    public SquareView2D getSquareAt(int row, int col) {
        return (SquareView2D)this.getChildren().get((7-row) * SIDE_LENGTH + col);
    }
}
