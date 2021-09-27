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
 * Class: Knight
 *
 * Description:
 * A Knight implementation, can move in an L shape around the board
 * ****************************************
 */
package Model.ChessPieces;

import Model.ChessBoard;
import Model.Square;
import Model.Team;
import View.PieceEnum;

import java.util.ArrayList;
import java.util.List;

public class Knight extends ChessPiece{
//    private boolean lateralMovementOnly = true;
//    private boolean diagonalMovementOnly = false;
//    private boolean canExtrapolateMovement = true;

    public Knight(Team team) {
        super(team);
        pieceType = PieceEnum.KNIGHT;
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
        ArrayList<Square> allMoves = getAllMoves(currentSquare, board);
        ArrayList<Square> validMoves = getValidMoves(allMoves);
        return  validMoves;
    }
    /**
     * Method to return all the squares that a piece could potentially move to
     * not minding the bounds of the board or the prescence of other pieces
     *  @param currentSquare
     *  @param board
     *  @return
     */
    private ArrayList<Square> getAllMoves(Square currentSquare, ChessBoard board) {
        ArrayList<Square> allMoves = new ArrayList<>(MAX_DISTANCE);
        int row = currentSquare.getRow();
        int col = currentSquare.getCol();
        // Options based off the knights movement patterns
        //Can move in 1 vertical and 2 horizontal directions or 1 horizontal and 2 vertical
        allMoves.add(board.getSquareAt(row + 1, col + 2));
        allMoves.add(board.getSquareAt(row + 2, col + 1));
        allMoves.add(board.getSquareAt(row + 1, col - 2));
        allMoves.add(board.getSquareAt(row + 2, col - 1));
        allMoves.add(board.getSquareAt(row - 1, col + 2));
        allMoves.add(board.getSquareAt(row - 2, col + 1));
        allMoves.add(board.getSquareAt(row - 1, col - 2));
        allMoves.add(board.getSquareAt(row - 2, col - 1));
        //Add each option to the collection of all ideally possible moves
        return allMoves;
    }


    @Override
    public String toString() {
        return "N" + team.toString().substring(0,1);
    }
}