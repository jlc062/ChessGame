package Main;/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Ethan Dunne
 * Section: 11am
 * Date: 11/14/19
 * Time: 9:45 PM
 *
 * Project: csci205finalproject
 * Package: PACKAGE_NAME
 * Class: SinglePlatform.ConsoleInterface
 *
 * Description:
 *
 * ****************************************
 */

import Model.ChessPieces.ChessPiece;
import Model.Square;
import Model.GameManager;

import java.util.List;
import java.util.Scanner;

public class ChessConsoleMain {

    /**The game manager that runs the game */
    private GameManager gm;
    /** the Scanner that gets user input from the console */
    private Scanner in;

    public static void main(String[] args) {
        ChessConsoleMain game = new ChessConsoleMain();
        game.runChessGame();
    }

    /**
     * using input from the console, two users can play chess using a string representation of the board
     * by alternating inputting piece movements.
     * used FOR DEBUGGING PURPOSES ONLY. players cannot win.
     */
    public void runChessGame(){
        boolean gameIsRunning = true;
        gm = new GameManager();

        in = new Scanner(System.in);

        while(gameIsRunning){
            //print the board
            System.out.println(gm.getBoard().toString());

            System.out.printf("It is %s team's turn\n", gm.getCurrentTurn().toString());

            while (true) {
                try {
                    Square squareToMoveFrom = getSquareAtPosition();
                    List<Square> legalMoves = getSquareMoves(squareToMoveFrom);

                    while(true) {
                        if (movePiece(squareToMoveFrom, legalMoves)) break;

                    }

                    break;
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid chess position.");
                } catch (NullPointerException e){
                    System.err.println("There is no piece at that location.");
                }
            }

        }

    }

    /**
     * Moves a piece from its old location to a new one
     *
     * @param squareToMoveFrom the square containing the piece to be moved
     * @param legalMoves the moves that the player is allowed to do with the piece selected
     * @return if the player has selected a valid position
     */
    private boolean movePiece(Square squareToMoveFrom, List<Square> legalMoves) {
        System.out.println("Choose one of the following positions to move this piece to:");
        //print all legal moves for this piece
        System.out.println(legalMoves);
        try {

            Square squareToMoveTo = movePieceTo(legalMoves);

            //move the piece
            ChessPiece killed = gm.movePiece(squareToMoveFrom, squareToMoveTo);
            gm.getBoard().capturePiece(killed);

            return true;
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid chess position to move to.");
        }
        return false;
    }

    /**
     * gets user input of position to move to
     *
     * @param legalMoves the allowed Squares to move to
     * @return the Square to move to
     */
    private Square movePieceTo(List<Square> legalMoves) {
        String posToMoveTo = in.next();
        Square squareToMoveTo = gm.getBoard().getSquareAt(posToMoveTo);

        //check if squareToMoveTo is in valid moves
        if(!legalMoves.contains(squareToMoveTo)){
            throw new IllegalArgumentException("That position is not in legal moves.");
        }
        return squareToMoveTo;
    }

    /**
     *Gets all possible moves for a piece and lets the user know if there are none
     *
     * @param squareToMoveFrom
     * @return
     */
    private List<Square> getSquareMoves(Square squareToMoveFrom) {
        //check if piece is there and get the moves from that piece
        List<Square> legalMoves = gm.getLegalMoves(squareToMoveFrom);
        if(legalMoves.isEmpty()){
            System.err.println("That piece has no legal moves.");
            throw new IllegalArgumentException("No Legal Moves");
        }
        return legalMoves;
    }

    /**
     * Gets user input of a desired square to move a piece from by getting a coordinate from user.
     *
     * @return the Square where the piece is located
     */
    private Square getSquareAtPosition() {
        //get position of piece to move
        System.out.println("Specify the position of the piece you would like to move (such as f5)");
        String posToMove = in.next();
        return gm.getBoard().getSquareAt(posToMove);
    }
}
