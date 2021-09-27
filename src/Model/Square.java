/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Ethan Dunne
 * Section: 11am
 * Date: 11/10/19
 * Time: 11:58 AM
 *
 * Project: csci205finalproject
 * Package: model
 * Class: Square
 *
 * Description:
 * A square class that can hold a chess piece, and has a specific row and column,
 * that corresponds to a position in the arrayList in the board.
 * ****************************************
 */
package Model;

import Model.ChessPieces.ChessPiece;

import java.io.Serializable;
import java.util.Objects;

/**
 * Representation of a single space on a chess board
 */
public class Square implements Serializable {

    /** The current piece located on this square */
    private ChessPiece currentPiece;
    /** Row that this square is in */
    private final int row;
    /** Column that this square is in */
    private final int col;

    /**
     * Explicit Constructor to initialize the row and column of this square
     * @param row row
     * @param col column
     */
    public Square(int row, int col) {
        this.row = row;
        this.col = col;
    }


    public ChessPiece getCurrentPiece() {
        return currentPiece;
    }
    public void setCurrentPiece(ChessPiece currentPiece) {
        this.currentPiece = currentPiece;
    }

    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }

    public boolean isEmpty(){
        return currentPiece == null;
    }

    /**
     * @return a string of the Chess format of this square's position
     */
    @Override
    public String toString() {
        final int ASCII_a_OFFSET = 97;
        return String.format("%c%d", (char) col +ASCII_a_OFFSET, row +1);
    }

    /**
     * @return the current piece if there is one, otherwise return an empty looking space
     */
    public String getStringPiece(){
        if (currentPiece != null){
            return currentPiece.toString();
        }
        return "_|";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return row == square.row && col == square.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
