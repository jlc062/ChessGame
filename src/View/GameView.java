package View;

import Model.ChessPieces.ChessPiece;
import Model.Square;
import Model.Team;
import Model.GameManager;
import View.View2D.*;
import View.View3D.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;



public class GameView {

    private SimpleBooleanProperty is3D = new SimpleBooleanProperty(false);

    //components of scene graph
    private HBox gameHBox;
    private VBox root;
    VBox rightSideContainer;
    private int windowHeight = 750;
    private int windowWidth = 950;
    BoardView board;
    private FlowPane deadPieceHolderWhite;
    private FlowPane deadPieceHolderBlack;
    GameManager gm;
    Text inCheckTextBlack;
    Text inCheckTextWhite;
    Button quitButton;
    HBox boardContainer;
    Text turnText;

    private PerspectiveCamera camera;
    private String CAMERA = "Cam2";
    private final GameMenuBar gameMenuBar;
    private Button restartButton;
    private Stage endGameWindow;

    /**
     * Explicit constructor to create the main game view on which the chess game is displayed
     * @param model - the model game that this view should represent visually
     */
    public GameView(GameManager model) {

        this.gm = model;
        //make gameHBox which is a set of horizontal boxes
        gameHBox = new HBox(20);
        gameHBox.setPadding(new Insets(50));
        //root.setAlignment(Pos.CENTER);
        gameHBox.setMinSize(windowWidth,windowHeight);
        gameHBox.setBackground(makeBackground());
        boardContainer = new HBox();
        makeGameView();
        gameHBox.getChildren().add(boardContainer);
        makeRightSideContainer();

        //make the menu bar and add the menu bar and the gameHBox to the root
        gameMenuBar = new GameMenuBar();
        root = new VBox();
        root.getChildren().addAll(gameMenuBar,gameHBox);

    }

    private void makeRightSideContainer() {
        rightSideContainer = new VBox(5);
        createDeadPieceHolders();
        makeInCheckText();
        turnText = new Text();
        turnText.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        rightSideContainer.getChildren().add(turnText);
        gameHBox.getChildren().add(rightSideContainer);

    }

    private Background makeBackground() {
        //TODO - find a different background texture
        String imageLink = "https://images.freecreatives.com/wp-content/uploads/2016/01/Free-Photoshop-Purity-Wood-Texture.jpg";//"https://images.freecreatives.com/wp-content/uploads/2016/01/High-Quality-Oak-Seamless-Wood-Texture.jpg";//"https://tr.rbxcdn.com/7324f5e7134f93c9c9e41e30c4d5bb0a/420/420/Decal/Png";
        BackgroundImage bgImage = new BackgroundImage(new Image(imageLink), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        return new Background(bgImage);

    }

    /**
     * Clear the current visualization of the game and reload a new one dependent whether or not is3D is true
     */
    public void makeGameView(){
        boardContainer.getChildren().clear();


        //TODO - dont reset game model
        //this.gm.resetGame();

        if(is3D()) {
            board = new BoardView3D(gm.getBoard());
            //***********************************
            Group miniRoot = new Group();

            //initialize the camera
            camera = new PerspectiveCamera(true);
            //camera.setVerticalFieldOfView(false);
            camera.setNearClip(1.0);
            camera.setFarClip(10000.0);

            SubScene boardScene = new SubScene(miniRoot, 640, 640);
            //SubScene boardScene = new SubScene(board, 640,640);
            boardScene.setCamera(camera);
            boardScene.setFill(Color.GRAY);
            miniRoot.getChildren().add(board);
            changeCameraOnClick(boardScene);

            boardContainer.getChildren().add(boardScene);
            //***********************************
        } else {
            board = new BoardView2D(640, gm.getBoard());
            //add the actual board in
            boardContainer.getChildren().add(board);
        }

        //if there are no captured pieces, make sure the dead piece holders in view are empty
        if (deadPieceHolderBlack != null) {
            if (gm.getBoard().getCapturedWhitePieces().isEmpty()) {
                deadPieceHolderWhite.getChildren().clear();
            }
            if (gm.getBoard().getCapturedBlackPieces().isEmpty()) {
                deadPieceHolderBlack.getChildren().clear();
            }
        }


    }

    /**Creates 2 flowpanes
     * Each flowpane contains the dead/captured pieces for each team
     * Adds each flowpane to the rightside Container
     */
    private void createDeadPieceHolders() {

        //make the flowpanes for dead pieces
        deadPieceHolderWhite = new FlowPane();
        deadPieceHolderBlack = new FlowPane();
        Text deadPieceHolderWhiteName = new Text();
        Text deadPieceHolderBlackName = new Text();
        deadPieceHolderBlackName.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        deadPieceHolderWhiteName.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        deadPieceHolderBlackName.setText("Captured Player Two Pieces:");
        deadPieceHolderWhiteName.setText("Captured Player One Pieces:");

        rightSideContainer.getChildren().add(deadPieceHolderWhiteName);
        rightSideContainer.getChildren().add(deadPieceHolderWhite);
        rightSideContainer.getChildren().add(deadPieceHolderBlackName);
        rightSideContainer.getChildren().add(deadPieceHolderBlack);
    }

    /**
     * grabs the piece image at the specified spot and puts it in its respective deadpiece holder depending on team
     * @param row the row of the square
     * @param col the column of the square
     * @param deadPieceHolder the correct team's dead piece holder
     */
    public void killPiece(int row, int col, FlowPane deadPieceHolder){
        if(is3D()){
            SquareView3D oldLocationSquare = (SquareView3D)board.getSquareAt(row,col);
            PieceView3D pieceKilled = oldLocationSquare.removePiece();

            deadPieceHolder.getChildren().add(new PieceView2D(pieceKilled.getPieceType(), pieceKilled.getPieceColor()).getView());
        } else {
            SquareView2D oldLocationSquare = (SquareView2D)board.getSquareAt(row,col);
            deadPieceHolder.getChildren().add(oldLocationSquare.getPiece());
            oldLocationSquare.getChildren().clear();
        }
    }

    /**
     * Makes text object for showing if a player is in check
     */
    private void makeInCheckText() {
        inCheckTextBlack = new Text();
        inCheckTextWhite = new Text();
        inCheckTextBlack.setFont(Font.font(40));
        inCheckTextWhite.setFont(Font.font(40));
        inCheckTextBlack.setFill(Color.RED);
        inCheckTextWhite.setFill(Color.RED);
        inCheckTextBlack.setText("");
        inCheckTextWhite.setText("");
        rightSideContainer.getChildren().add(inCheckTextBlack);
        rightSideContainer.getChildren().add(inCheckTextWhite);
    }

    private void changeCameraOnClick(SubScene subScene) {

        Transform cam1A = new Translate(400,1600,-1400);
        Transform cam1B = new Rotate(40,Rotate.X_AXIS);

        Transform cam2A = new Translate(0,SquareView3D.SQUARE_SIZE*8,0);
        Transform cam2B = new Rotate(180,Rotate.X_AXIS);

        Transform cam2C = new Translate(SquareView3D.SQUARE_SIZE*8,0,0);
        Transform cam2D = new Rotate(180,Rotate.Y_AXIS);

        camera.getTransforms().addAll(cam1A,cam1B); //set camera angle for view 1

        subScene.setOnKeyPressed(event -> {
            System.out.println(CAMERA);
            if (event.getCode() == KeyCode.C) {

                camera.getTransforms().addAll(cam1A,cam1B);
                if (CAMERA == "Cam1") {
                    try {
                        camera.getTransforms().addAll(cam1B.createInverse(),cam1A.createInverse());
                        board.getTransforms().addAll(cam2D.createInverse(),cam2C.createInverse(),cam2B.createInverse(),cam2A.createInverse());
                        CAMERA = "Cam2";
                    } catch (NonInvertibleTransformException e) {
                        e.printStackTrace();
                    }
                }
                else if (CAMERA == "Cam2")  {
                    try {
                        camera.getTransforms().addAll(cam1B.createInverse(),cam1A.createInverse());
                        board.getTransforms().addAll(cam2A,cam2B,cam2C,cam2D);
                    } catch (NonInvertibleTransformException e) {
                        e.printStackTrace();
                    }
                    CAMERA = "Cam1";

                }

                System.out.println(CAMERA);
            }
        });
    }

    public Text getInCheckTextBlack() {
        return inCheckTextBlack;
    }

    public Text getInCheckTextWhite() {
        return inCheckTextWhite;
    }

    public Text getTurnText() {
        return turnText;
    }

    public BoardView getBoard() {
        return board;
    }

    public VBox getRoot() {
        return root;
    }

    public FlowPane getDeadPieceHolderWhite() {
        return deadPieceHolderWhite;
    }

    public FlowPane getDeadPieceHolderBlack() {
        return deadPieceHolderBlack;
    }

    public void createEndGameWindow(Team winner){
        String s = winner.toString() + " wins!";

        Text endGameText = new Text(s);
        endGameText.setFont(Font.font("Verdana", FontWeight.BOLD, 72));

        VBox secondaryLayout = new VBox();
        secondaryLayout.getChildren().add(endGameText);

        restartButton = new Button("Restart");
        quitButton = new Button("Quit Game");
        HBox buttonsBox = new HBox(20);
        buttonsBox.setPadding(new Insets(10,100,20,140));
        buttonsBox.getChildren().add(restartButton);
        buttonsBox.getChildren().add(quitButton);
        secondaryLayout.getChildren().add(buttonsBox);
        Scene secondScene = new Scene(secondaryLayout, 450, 120);

        // New window (Stage)
        endGameWindow = new Stage();
        endGameWindow.setTitle("Game Over!");
        endGameWindow.setScene(secondScene);
        endGameWindow.sizeToScene();
        endGameWindow.show();
    }


    public void refreshPieceColors(){
        for (int row = 0; row < BoardView.SIDE_LENGTH ; row++) {
            for (int col = 0; col < BoardView.SIDE_LENGTH; col++) {
                if(!gm.getBoard().getSquareAt(row, col).isEmpty()){ //if square not empty,
                    if(gm.getBoard().getSquareAt(row, col).getCurrentPiece().getTeam() == Team.WHITE){ //if it's a player one piece
                        board.getSquareAt(row, col).setPieceColor(BoardView.getPlayer1Color());
                    }else{
                        board.getSquareAt(row, col).setPieceColor(BoardView.getPlayer2Color());
                    }
                }
            }
        }

        deadPieceHolderWhite.getChildren().clear();
        deadPieceHolderBlack.getChildren().clear();
        for (ChessPiece piece : gm.getBoard().getCapturedWhitePieces()) {
            deadPieceHolderWhite.getChildren().add(new PieceView2D(piece.getPieceType(), BoardView.getPlayer1Color()).getView());
        }
        for (ChessPiece piece : gm.getBoard().getCapturedBlackPieces()) {
            deadPieceHolderBlack.getChildren().add(new PieceView2D(piece.getPieceType(), BoardView.getPlayer2Color()).getView());
        }


//        for (Node child: deadPieceHolderBlack.getChildren()) {
//            PieceView deadPiece = (PieceView) child;
//        }
    }


    public boolean is3D() {
        return is3D.get();
    }

    public SimpleBooleanProperty is3DProperty() {
        return is3D;
    }

    public GameMenuBar getGameMenuBar() {
        return gameMenuBar;
    }

    public Button getQuitButton(){
        return quitButton;
    }

    public Button getRestartButton() {
        return restartButton;
    }

    public Stage getEndGameWindow() {
        return endGameWindow;
    }
}
