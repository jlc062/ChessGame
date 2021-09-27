/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: James Campbell
 * Section: 11 am
 * Date: 11/10/19
 * Time: 12:10 PM
 *
 * Project: CSCI205FinalProject
 * Package: Model.ChessParts
 * Class: King
 *
 * Description:
 * A king piece implementation, the king can move to any adjacent block, any of the 8 directions.
 * ****************************************
 */
package Model.ChessPieces;

import Model.ChessBoard;
import Model.Square;
import Model.Team;
import View.PieceEnum;

import java.util.ArrayList;
import java.util.List;

public class King extends ChessPiece{
//    //How much the King can move on the chess Board
//    private int[] movement = {1,1};
//    //Says if the King can move anything more special than laterally
//    private boolean lateralMovementOnly = true;
//    //Says if the Piece can multiply movement
//    private boolean canExtrapolateMovement = false;
//    private boolean diagonalMovementOnly = false;

    public King(Team team) {
        super(team);
        pieceType = PieceEnum.KING;
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
     * @param currentSquare
     * @param board
     * @return
     */
    private ArrayList<Square> getAllMoves(Square currentSquare, ChessBoard board) {
        ArrayList<Square> allMoves = new ArrayList<>(MAX_DISTANCE);
        int row = currentSquare.getRow();
        int col = currentSquare.getCol();
        //Get all diagonals
        Square option1 = board.getSquareAt(row+1,col+1);
        Square option2 = board.getSquareAt(row+1, col-1);
        Square option3 = board.getSquareAt(row-1,col+1);
        Square option4 = board.getSquareAt(row-1,col-1);
        //Add options to arraList
        allMoves.add(option1);
        allMoves.add(option2);
        allMoves.add(option3);
        allMoves.add(option4);
        //Get all non-Diagnols
        Square option5 = board.getSquareAt(row+1,col);
        Square option6 = board.getSquareAt(row-1, col);
        Square option7 = board.getSquareAt(row,col+1);
        Square option8 = board.getSquareAt(row,col-1);
        //Add options to arrayList
        allMoves.add(option5);
        allMoves.add(option6);
        allMoves.add(option7);
        allMoves.add(option8);
        return allMoves;
    }




    @Override
    public String toString() {
        return "K" + team.toString().substring(0,1);
    }
}