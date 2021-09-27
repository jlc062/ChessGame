package Model;/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Ethan Dunne
 * Section: 11am
 * Date: 11/11/19
 * Time: 11:22 AM
 *
 * Project: csci205finalproject
 * Package: PACKAGE_NAME
 * Class: Controller.Controller.Model.GameManager
 *
 * Description:
 *
 * ****************************************
 */

import Model.ChessPieces.ChessPiece;

import java.util.List;

/**
 * Class to control the flow fo the game
 */
public class GameManager {

    /** Whichever team's turn it is */
    private Team currentTurn = Team.WHITE;

    /** Current board being played on */
    private ChessBoard board;

    /**  */
    private RuleMaster ruleMaster;

    public GameManager() {
        resetGame();
    }

    public void resetGame(){
        currentTurn = Team.WHITE;
        this.board = new ChessBoard();
        this.ruleMaster = new RuleMaster();
    }

    /**
     * Move a game piece from its current location to a new location
     * @param currentLocation the Square where the piece that should be moved is located
     * @param newLocation the location of where to move the piece in current location
     * @return the piece that is killed in the new location, if there is one
     */
    public ChessPiece movePiece(Square currentLocation, Square newLocation) {
        //make sure the squarwes are from this game's board
        currentLocation = board.getSquareAt(currentLocation.getRow(), currentLocation.getCol());
        newLocation = board.getSquareAt(newLocation.getRow(), newLocation.getCol());

        //if there is a piece in the new location, kill it
        ChessPiece pieceKilled = newLocation.getCurrentPiece();


        //put the piece moved in the new location
        newLocation.setCurrentPiece(currentLocation.getCurrentPiece());


        //remove piece from old location
        currentLocation.setCurrentPiece(null);

        System.out.println(board.toString());

        //add killed piece to pieces captured
        if(pieceKilled != null) {
            board.capturePiece(pieceKilled);
        }

        //tell the piece it has moved
        if(!newLocation.isEmpty()){
            newLocation.getCurrentPiece().setHasMoved(true);
        }
        ruleMaster.checkAllPiecesIfCheckKing(board,Team.BLACK);
        ruleMaster.checkAllPiecesIfCheckKing(board,Team.WHITE);
        switchTurn();
        return pieceKilled;
    }

    /**
     * Override method to take a Movement object instead of 2 Square objects
     * @param move the movement that should be made on the board containing the start location and end location of a move
     * @return the piece that was killed/captured by the movement
     */
    public ChessPiece movePiece(Movement move){
        return movePiece(move.getInitialSquare(), move.getFinalSquare());
    }


    /**
     *
     * @param pieceLocation the square chosen to get the moves of the piece located there
     * @return a list of possible move locations for the piece at that location
     * @throws NullPointerException if there is no piece at that location
     */
    public List<Square> getLegalMoves(Square pieceLocation) throws NullPointerException{
        //get the possible moves of the piece at this location
        List<Square> squareList = pieceLocation.getCurrentPiece().getLegalMoves(pieceLocation, this.board);


        return squareList;
    }

    /**
     * Helper function to toggle whose turn it is
     * @return the team whose turn it is
     */
    private Team switchTurn(){
        if (currentTurn == Team.WHITE){
            currentTurn = Team.BLACK;
        }else{
            currentTurn = Team.WHITE;
        }
        return currentTurn;
    }

    public ChessBoard getBoard() {
        return board;
    }

    public Team getCurrentTurn() {
        return currentTurn;
    }

    public RuleMaster getRuleMaster() {
        return ruleMaster;
    }
}
