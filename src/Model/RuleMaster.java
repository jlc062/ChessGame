/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: James Campbell
 * Section: 11 am
 * Date: 11/18/19
 * Time: 12:24 PM
 *
 * Project: CSCI205FinalProject
 * Package: Controller.Controller.Model
 * Class: RuleMaster
 *
 * Description:
 * A class made to hold the rules for chess,
 * this is mostly for figuring out if the king is in check or not,
 * and if a piece is allowed to capture the king
 * ****************************************
 */
package Model;

import Model.ChessPieces.ChessPiece;
import Model.ChessPieces.King;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.ArrayList;

public class RuleMaster {

    //Variable that holds if the White King is in Check
    private SimpleBooleanProperty isCheckedWhite = new SimpleBooleanProperty(false);
    //Variable that holds if the Black King is in Check
    private SimpleBooleanProperty isCheckedBlack = new SimpleBooleanProperty(false);

    //ArrayList that holds all the squares that have pieces that have a potential move to check the Black King
    private ArrayList<Square> squaresThatHaveBlackPiecesThatAreCheckingWhiteKing = new ArrayList<>();
    //ArrayList that holds all the squares that have pieces that have a potential move to check the White King
    private ArrayList<Square> squaresThatHaveWhitePiecesThatAreCheckingBlackKing = new ArrayList<>();


    public boolean isIsCheckedBlack() {
        return isCheckedBlack.get();
    }
    public boolean isIsCheckedWhite() {
        return isCheckedWhite.get();
    }
    //Simple boolean properties that hold whether or not a black king or white king is in check, respectively
    public SimpleBooleanProperty isCheckedBlackProperty() {
        return isCheckedBlack;
    }
    public SimpleBooleanProperty isCheckedWhiteProperty() {
        return isCheckedWhite;
    }

    /**
     * Capture king function, you can only capture a king in chess if the king has
     * already been checked, so therefore it will only return true if the king is checked
     * @param king
     * @return
     */
    public boolean captureKing(ChessPiece king){
        if (king.getTeam() == Team.BLACK){
            if (isCheckedBlack.getValue()){
                return true;
            }
            isCheckedBlack.setValue(true);
        }
        if (king.getTeam() == Team.WHITE){
            if(isCheckedWhite.getValue()){
                return true;
            }
            isCheckedWhite.setValue(true);
        }
        return false;
    }

    /**
     * Uses methods in the board class to get all the squares of the team that might be
     * checking the other King, then it looks to see if there is a possible move of this
     * team that checks the King, if so report to the variable isChecked-THEOPPOSITETEAM-,
     * than the team specified, that their King is in Check.
     * @param board Takes in the Chessboard that the user is playing on
     * @param team, the team that we want to see if is checking the other teams King
     */
    public void checkAllPiecesIfCheckKing(ChessBoard board, Team team){

        //Verifies that there is a king in check, assume that there is not
        boolean checkedKing = false;
        //Get all the Squares that have pieces of the team specified
        ArrayList<Square> allAlivePieces = board.getAllAlivePiecesOfATeam(team);
        //Loop through these squares
        for (Square currentSquare: allAlivePieces){
            //Loop through the possible moves of the pieces on the squares
            for (Square possibleMove: currentSquare.getCurrentPiece().getLegalMoves(currentSquare, board)){
                ChessPiece currentPiece = possibleMove.getCurrentPiece();
                //If it can possibly capture a king, and the king is on the other team
                //Then check the King of that team
                if (currentPiece instanceof King && currentPiece.getTeam() != team) {
                    if( team == Team.BLACK){
                        isCheckedWhite.setValue(true);
                        checkedKing = true;
                        squaresThatHaveBlackPiecesThatAreCheckingWhiteKing.add(currentSquare);
                    } else {
                        isCheckedBlack.setValue(true);
                        checkedKing = true;
                        squaresThatHaveWhitePiecesThatAreCheckingBlackKing.add(currentSquare);
                    }
                }
            }
            //If there is a King in Check Break from the loop
            if (checkedKing == true) {break;}
        }
        //If there is no piece checking the King than we need to uncheck the King if
        //it was checked from a move before
        if (checkedKing != true){
            uncheckKing(team);
        }
    }

    /**
     * unchecks the King of the other team that is specified
     * @param team, the team that has no move that is checking the king
     */
    private void uncheckKing(Team team) {
        if( team == Team.BLACK){
            isCheckedWhite.setValue(false);
        } else {
            isCheckedBlack.setValue(false);
        }
    }


}