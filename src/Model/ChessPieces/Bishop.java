/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: James Campbell
 * Section: 11 am
 * Date: 11/10/19
 * Time: 12:11 PM
 *
 * Project: CSCI205FinalProject
 * Package: Model.ChessPieces
 * Class: Bishop
 *
 * Description:
 * A bishop chess piece representation
 * ****************************************
 */
package Model.ChessPieces;

import Model.ChessBoard;
import Model.Square;
import Model.Team;
import View.PieceEnum;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends ChessPiece{

    public Bishop(Team team) {
        super(team);
        pieceType = PieceEnum.BISHOP;
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
        return "B" + team.toString().substring(0,1);
    }
}