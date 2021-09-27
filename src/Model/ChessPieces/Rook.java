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
 * Package: Model.ChessParts
 * Class: Rooke
 *
 * Description:
 * A rooke piece that is allowed to move in 4 lateral directions as many spaces as it wants
 * ****************************************
 */
package Model.ChessPieces;

import Model.ChessBoard;
import Model.Square;
import Model.Team;
import View.PieceEnum;

import java.util.ArrayList;
import java.util.List;

public class Rook extends ChessPiece {
//    private int[] movement = {1,1};
//    private boolean lateralMovementOnly = true;
//    private boolean diagonalMovementOnly = false;
//    private boolean canExtrapolateMovement = true;
//    private static final int POSSIBLEMOVES = 8;

    public Rook(Team team) {
        super(team);
        pieceType = PieceEnum.ROOK;
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


        legalMoves.addAll(checkDirection(currentSquare, board, 0, 1, MAX_DISTANCE)); //check forward
        legalMoves.addAll(checkDirection(currentSquare, board, 1, 0, MAX_DISTANCE)); //check right
        legalMoves.addAll(checkDirection(currentSquare, board, 0, -1, MAX_DISTANCE)); //check backward
        legalMoves.addAll(checkDirection(currentSquare, board, -1, 0, MAX_DISTANCE)); //check left


        return legalMoves;
    }




    @Override
    public String toString() {

        return "R" + team.toString().substring(0,1);
    }
}