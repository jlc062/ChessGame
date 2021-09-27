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
 * Class: Pawn
 *
 * Description:
 * A pawn implementation, will hold the team its on, and whether or not it has moved,
 * It has 4 movement types. Forward 1, its default moevement, forward 2 if it has not moved, and
 * it can move 1 diagonal forward if there is a chess piece it can kill.
 * ****************************************
 */
package Model.ChessPieces;

import Model.ChessBoard;
import Model.Square;
import Model.Team;
import View.PieceEnum;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends ChessPiece{

    private boolean hasMoved = false;

    public Pawn(Team team) {
        super(team);
        pieceType = PieceEnum.PAWN;
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
        ArrayList<Square> legalMoves = new ArrayList<>();
        //if it's the black team, movements should be down (-1)
        int direction = (team == Team.WHITE) ? 1 : -1;
        int row = currentSquare.getRow();
        int col = currentSquare.getCol();


        //check in front
        Square frontSquare = board.getSquareAt(row + direction, col);
        if(frontSquare != null){
            if(frontSquare.isEmpty()){ //only if it is empty can a pawn move onto the spot in front of them
                legalMoves.add(frontSquare);
            }
        }
        //check front left for enemy
        Square frontLeft = board.getSquareAt(row + direction, col - direction);
        if(frontLeft != null){
            //only if it occupied by the other team can a pawn move onto the spot diagonal to them
            if(!frontLeft.isEmpty()){
                if(frontLeft.getCurrentPiece().getTeam() != team) {
                    legalMoves.add(frontLeft);
                }
            }
        }

        //check front right for enemy
        Square frontRight = board.getSquareAt(row + direction, col + direction);
        if(frontRight != null){
            //only if it occupied by the other team can a pawn move onto the spot diagonal to them
            if(!frontRight.isEmpty()){
                if(frontRight.getCurrentPiece().getTeam() != team) {
                    legalMoves.add(frontRight);
                }
            }
        }

        // if it has not moved, add the extra 2 movement to possible moves
        if (!hasMoved){
            Square twoInFront = board.getSquareAt(row + direction*2, col);
            if(twoInFront != null && frontSquare.isEmpty()){
                //only if it is empty can a pawn move onto the spot in front of them
                if(twoInFront.isEmpty()){
                    legalMoves.add(twoInFront);
                }
            }
        }
        return legalMoves;
    }

    @Override
    public String toString() {
        return "P" + team.toString().substring(0,1);
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public boolean isHasMoved() {
        return hasMoved;
    }

}