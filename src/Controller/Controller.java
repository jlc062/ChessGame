/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Ethan Dunne
 * Section: 11am
 * Date: 11/13/19
 * Time: 11:34 AM
 *
 * Project: csci205finalproject
 * Package: Controller.Controller
 * Class: Controller.Controller
 *
 * Description:
 * The Controller for the MVC
 * ****************************************
 */
package Controller;

import Model.ChessPieces.ChessPiece;
import Model.Movement;
import Model.ChessPieces.King;
import Model.Square;
import Model.Team;
import Model.GameManager;
import Model.RuleMaster;
import Networking.Player.ClientPlayer;
import Networking.Player.HostPlayer;
import Networking.Player.Player;
import View.GameView;
import View.NetworkingPopUps.HostGamePopUp;
import View.NetworkingPopUps.JoinGamePopUp;
import View.SquareView;
import Model.*;
import Model.ChessPieces.ChessPiece;
import Model.ChessPieces.King;
import View.GameView;
import View.SquareView;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.util.List;

public class Controller {

    /** The view */
    protected GameView theView;
    /** The model */
    protected GameManager theModel;

    protected MenuBarController MBC;

    /**The square the mouse most recently clicked*/
    protected Square currentSquareSelected;
    //Boolean that says whether or not this is a multiplayer game
    protected boolean isMultiplayer;

    /**
     * Creates a chess game controller, allowing the user interaction with the view to be connected with the model
     *
     * @param theView the GameView that will display a GUI using javafx
     * @param theModel the GameManager that runs the game by making moves
     */
    public Controller(GameView theView, GameManager theModel) {
        this.theView = theView;
        this.theModel = theModel;
        this.MBC = new MenuBarController(theView,this,theModel);

        //display text if the user is in check
        displayIfInCheck();
        //enable the controller to know when a user has clicked a square on the board.
        makeSquaresClickable();
    }


    /**
     * Sets up event handlers for when squares in the view are clicked
     */
    protected void makeSquaresClickable() {
        if (isMultiplayer){
            for (Node child : theView.getBoard().getChildren()){
                SquareView squareView = (SquareView) child;
                squareView.setOnMouseClicked(event -> {
                    try {
                        squareWasClickedNetwork((SquareView) event.getSource());
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
            }
        } else {
            for (Node child : theView.getBoard().getChildren()) {
                SquareView squareView = (SquareView) child;
                squareView.setOnMouseClicked(event -> squareWasClicked((SquareView) event.getSource()));
            }
        }
    }

    /**
     * Function to handle event for when a SquareView is clicked
     * @param squareSelected - the square that was clicked
     */
    protected Movement squareWasClicked(SquareView squareSelected){
        System.out.println("Square was clicked");

        //get model's corresponding square
        Square thisSquare = theModel.getBoard().getSquareAt(squareSelected.getRow(), squareSelected.getCol());
        System.out.println(thisSquare.toString());

        Movement moveMade = null;

        //if it has a piece in it in the model (it's not empty)
        if (!thisSquare.isEmpty()) {
            //check if this piece is on the team whose turn it is
            if(thisSquare.getCurrentPiece().getTeam() == theModel.getCurrentTurn()) {
                //select it the piece at this square and highlight its moves
                selectPieceAt(thisSquare);
            }else{
                moveMade = attemptMovementTo(thisSquare);
            }
        }else{
            //else if there is already a square selected
             moveMade = attemptMovementTo(thisSquare);
        }
        return moveMade;
    }


    /**
     * Helper method to show that a piece at a square is selected by highlighting its legal moves
     * @param thisSquare the square to select
     */
    protected void selectPieceAt(Square thisSquare){
        if(currentSquareSelected != null){ //de-highlight current moves
            highlightSquares(theModel.getLegalMoves(currentSquareSelected), false);
        }
        //highlight new square's moves
        highlightSquares(theModel.getLegalMoves(thisSquare), true);

        currentSquareSelected = thisSquare;
    }


    /**
     * Helper method to attempt to move the piece at the currently selected square to a new square location.
     * If there is no currently selected square, do nothing.
     * @param newSquare is the new location to move the currently selected piece to
     */
    protected Movement attemptMovementTo(Square newSquare){
        Movement move = null;
        //if there's currently a square selected
        if(currentSquareSelected != null) {
            //get the legal moves of the current square
            List<Square> legalMoves = theModel.getLegalMoves(currentSquareSelected);
            // if this new square clicked is a legal move, move the chess piece
            if (legalMoves.contains(newSquare)) {

                //move piece to the new square "thisSquare"
                ChessPiece killedPiece = theModel.movePiece(currentSquareSelected, newSquare);

                //if there is a piece at the new location(thisSquare), kill it
                if (killedPiece != null) {
                    if (killedPiece.getTeam() == Team.WHITE) {
                        killPiece(newSquare, killedPiece, Team.BLACK, theView.getDeadPieceHolderWhite());
                    } else {
                        //if king is killed go to end game screen where White won
                        killPiece(newSquare, killedPiece, Team.WHITE, theView.getDeadPieceHolderBlack());
                    }
                    //if its a king. the game's over.
                }
                //move piece to the new square in the view
                theView.getBoard().movePiece(currentSquareSelected.getRow(), currentSquareSelected.getCol(), newSquare.getRow(), newSquare.getCol());
                //Create a movement object, that has the initial and final positions of the piece moved
                move = new Movement(currentSquareSelected, newSquare);
            }
            //deselect the square and unhighlight the previous square's legal moves
            highlightSquares(legalMoves, false);
            currentSquareSelected = null;
        }
        return move;
    }

    private void killPiece(Square newSquare, ChessPiece killedPiece, Team team, FlowPane deadPieceHolder) {
        //if king is killed, go to end game screen where Black won
        if (killedPiece instanceof King) {
            theView.createEndGameWindow(team);
            theView.getQuitButton().setOnAction((ActionEvent e) -> {
                System.exit(0);
            });
            theView.getRestartButton().setOnAction((ActionEvent e) -> {
                restartGame();
            });

        }
        //put piece in captured pieces
        else {
          theView.killPiece(newSquare.getRow(), newSquare.getCol(), deadPieceHolder);
        }
    }

    /**
     * Restarts the game, puts all the pieces to their starting spots and resets the model and view.
     */
    protected void restartGame() {
            theModel.resetGame();
            MBC.reloadGameViewAndResetBindings();
            if (theView.getEndGameWindow() != null) theView.getEndGameWindow().close();
            makeSquaresClickable();
            theModel.getRuleMaster().isCheckedBlackProperty().setValue(false);
            theModel.getRuleMaster().isCheckedWhiteProperty().setValue(false);
            displayIfInCheck();
    }


    /**
     * Helper method to highlight a list of squares from the model on the view
     * @param legalMoves the squares to highlight
     * @param highlight whether or not the squares should be highlighted or un-highlighted
     */
    protected void highlightSquares(List<Square> legalMoves, boolean highlight){

        //highlight those positions on the board view
        for (Square pos : legalMoves) {
            if(highlight) {
                theView.getBoard().getSquareAt(pos.getRow(), pos.getCol()).highlight();
            }else{
                theView.getBoard().getSquareAt(pos.getRow(), pos.getCol()).unHighlight();
            }
        }
    }

    /**
     * change inCheckText if a player is in check
     */
    protected void displayIfInCheck(){
        RuleMaster rules = theModel.getRuleMaster();
        theView.getInCheckTextBlack().textProperty().bind(Bindings.when(rules.isCheckedBlackProperty())
                .then("Black Is In Check")
                .otherwise(""));
        theView.getInCheckTextWhite().textProperty().bind(Bindings.when(rules.isCheckedWhiteProperty())
                .then("White Is In Check")
                .otherwise(""));
    }

    //**********************************************************************************
    //A Player Object that will be initialized based off whether or not they want to host or join.
    private Player player;

    /**
     * When a Movement is read by the Player from the other Player, it will call this method and pass in the
     * Movement object that was received. Which holds the initial position of the square and the final position.
     * Because the opponent was able to make this move, and they have the same board, we do not have to bother checking,
     * all we have to do is set the currentSquareClicked to the initial square from the opponent, and
     * pass into squareWasClicked the final position of the piece on this square. After this it is now this players,
     * turn so we show them that in the GUI by setting the TurnText
     * @param opponentsMove, the moved the player received while listening to the opponent Player
     */
    public void simulateClick(Movement opponentsMove) {
        currentSquareSelected = theModel.getBoard().getSquareAt(opponentsMove.getInitialSquare().getRow(),
                opponentsMove.getInitialSquare().getCol());
        SquareView squareViewClicked = theView.getBoard().getSquareAt(opponentsMove.getFinalSquare().getRow(), opponentsMove.getFinalSquare().getCol());
        squareWasClicked(squareViewClicked);
        theView.getTurnText().setText("Your Turn...");
    }

    /**
     * Starts by restarting the game so both users start at the same point, then if they chose to be the
     * host it instantiates a HostPlayer and connects them to the client and starts the thread to listen for
     * moves. If it is a client it takes in the IP address from the MBC and instantiates a client and starts
     * that thread.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void makeConnection() throws IOException, ClassNotFoundException {
        restartGame();
        if (MBC.isHost){
            theView.getTurnText().setText("Waiting...");
            this.player = new HostPlayer(this);
            player.connect();
            Thread thread = new Thread(player);
            thread.start();
        } else {
            theView.getTurnText().setText("Your Turn...");
            this.player = new ClientPlayer(MBC.getIpAddressToJoin(), this);
            player.connect();
            Thread thread = new Thread(player);
            thread.start();
        }
    }

    /**
     * Checks to make sure that when a square was clicked it is the Users turn,
     * if not nothing happens, if it is then it uses the square was Clicked method.
     * @param squareSelected, the square that was clicked
     * @throws IOException
     * @throws ClassNotFoundException
     */
    protected void squareWasClickedNetwork(SquareView squareSelected) throws IOException, ClassNotFoundException {
        if (theModel.getCurrentTurn() == player.getTeam()){
            Movement moveMade = squareWasClicked(squareSelected);
            makeMove(moveMade);
        }
    }

    /**
     * Sends the move that is made to the other player using a movement object, than tells the
     * user that it is waiting for a move to be made using the Turn Text.
     * @param moveMade
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void makeMove(Movement moveMade) throws IOException, ClassNotFoundException {
        if(moveMade != null){
            player.sendMove(moveMade);
            theView.getTurnText().setText("Waiting...");
        }
    }
}
