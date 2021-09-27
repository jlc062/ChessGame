/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: James Campbell
 * Section: 11 am
 * Date: 11/10/19
 * Time: 1:42 PM
 *
 * Project: CSCI205FinalProject
 * Package: Model.ChessParts.ChessPieces
 * Class: Queen
 *
 * Description:
 * A Queen piece class that holds the team of queen and can calculate the moves. Queen can move diagnolly as many pieces
 * and horizontally or vertically as many pieces.
 * ****************************************
 */
package Model.ChessPieces;

import Model.ChessBoard;
import Model.Square;
import Model.Team;
import View.PieceEnum;

import java.util.ArrayList;
import java.util.List;

public class Queen extends ChessPiece {
    public Queen(Team team) {
        super(team);
        pieceType = PieceEnum.QUEEN;
    }

    /**
     * Will return an ArrayList with square positions of all the possible that
     * a specific chess piece is allowed to move to
     * @param currentSquare, the position the chess piece is on the board
     * @param  board, the board to check for it's legal positions on
     * @return ArrayList of all the possible moves
     */
    @Override
    public List<Square> getLegalMoves(Square currentSquare, ChessBoard board) {
        List<Square> legalMoves = new ArrayList<>();

        //Get horizontal and vertical directions
        legalMoves.addAll(checkDirection(currentSquare, board, 0, 1, MAX_DISTANCE)); //check forward
        legalMoves.addAll(checkDirection(currentSquare, board, 1, 0, MAX_DISTANCE)); //check right
        legalMoves.addAll(checkDirection(currentSquare, board, 0, -1, MAX_DISTANCE)); //check backward
        legalMoves.addAll(checkDirection(currentSquare, board, -1, 0, MAX_DISTANCE)); //check left

        //Get Diagonal Directions
        //check up and to the right
        legalMoves.addAll(checkDirection(currentSquare, board, 1,1, MAX_DISTANCE));
        //check down and to the right
        legalMoves.addAll(checkDirection(currentSquare, board, -1,1, MAX_DISTANCE));
        //check down and to the left
        legalMoves.addAll(checkDirection(currentSquare, board, -1,-1, MAX_DISTANCE));
        //check up and to the left
        legalMoves.addAll(checkDirection(currentSquare, board, 1,-1, MAX_DISTANCE));

        return legalMoves;
    }
    @Override
    public String toString() {
        return "Q" + team.toString().substring(0,1);
    }
}