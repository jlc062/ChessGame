/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: James Campbell
 * Section: 11 am
 * Date: 11/10/19
 * Time: 12:12 PM
 *
 * Project: CSCI205FinalProject
 * Package: Model.ChessParts
 * Class: ChessPiece
 *
 * Description:
 * An abstract representation of a chess piece that all the different pieces can inherit.
 * ****************************************
 */
package Model.ChessPieces;

import Model.ChessBoard;
import Model.Square;
import Model.Team;
import View.PieceEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract representation of a chess piece in a game of chess.
 */
public abstract class ChessPiece implements Serializable {
    //Stores the team of the chess piece
    protected Team team;
    //All possible directions on a Chess Board
    protected static final int MAX_DISTANCE = 8;
    protected PieceEnum pieceType;
    //Holds whether or not the chess piece has moved, most useful for the pawn
    protected boolean hasMoved = false;
    public ChessPiece(Team team) {
        this.team = team;
    }

    /**
     * Will return an ArrayList with square positions of all the possible that
     * a specific chess piece is allowed to move to
     * @param currentSquare, the position the chess piece is on the board
     * @param  board, the board to check for it's legal positions on
     * @return ArrayList of all the possible moves
     */
    public abstract List<Square> getLegalMoves(Square currentSquare, ChessBoard board);

    /**
     * Checks whether the squares in a certain direction can be moved to by a piece
     *
     * @param currentPos the Square the piece is currently on
     * @param board the board that the piece is on
     * @param rowDirec whether the direction is up(1) down(-1) or neither(0) from current pos
     * @param colDirec whether the direction is right(1) left(-1) or neither(0) from current pos
     * @param distance the number of squares to check in the given direction
     * @return a list containing squares the piece could move to in that direction
     */
    protected List<Square> checkDirection(Square currentPos, ChessBoard board, int rowDirec, int colDirec, int distance){
        List<Square> adjacentSquares = new ArrayList<>();
        int col = currentPos.getCol();
        int row = currentPos.getRow();
        for (int i = 1; i < distance; i++) {
            Square nextSquare = board.getSquareAt(row + i*rowDirec, col + i*colDirec);

            //check if it's not null
            if (nextSquare == null){
                break;
            }
            //check if there's a piece there
            if(!nextSquare.isEmpty()){
                //if it is on the other team, include that space
                if(nextSquare.getCurrentPiece().getTeam() != this.team){
                    adjacentSquares.add(nextSquare);
                }
                break; //stop looking in this direction
            }
            adjacentSquares.add(nextSquare);

            if(nextSquare != null){ //if the square is on the board
                if(checkSquare(nextSquare)) { //if the square does not contain someone on in it
                    adjacentSquares.add(nextSquare);
                }
            }
        }
        return adjacentSquares;
    }

    /**
     * Returns true if the given square is not empty or occupied by the opposing team
     * @param square the square to check
     * @return whether or not this chess piece can occupy this square
     */
    public boolean checkSquare(Square square){
        if (!square.isEmpty()){
            ChessPiece piece = square.getCurrentPiece();
            return piece.team != this.team;
        }
        return true;
    }

    /**
     * Loops through the allMoves array list and makes a new list containing only the valid moves,
     * i.e is on the board and not blocked by a piece on the same team
     * @param allMoves
     * @return
     */
    public ArrayList<Square> getValidMoves(ArrayList<Square> allMoves) {
        ArrayList<Square> validMoves = new ArrayList<>(MAX_DISTANCE);
        for (Square square : allMoves) {
            if (square != null) {
                if (checkSquare(square)) {
                    validMoves.add(square);
                }
            }
            continue;
        }
        return validMoves;
    }

    public Team getTeam() {
        return team;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
    public boolean getHasMoved() {
        return hasMoved;
    }

    public PieceEnum getPieceType() {
        return pieceType;
    }
}