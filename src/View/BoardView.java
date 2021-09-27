package View;


import Model.ChessBoard;
import Model.ChessPieces.ChessPiece;
import Model.Team;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

public abstract class BoardView extends TilePane {

    protected static final int SIDE_LENGTH = 8;

    private static Color player1Color = Color.WHITE;
    private static Color player2Color = Color.BLACK;

    private static Color square1Color = Color.BEIGE;//new Color(237, 232, 218, 1);
    private static Color square2Color = Color.BROWN.darker();//new Color(148, 144, 133, 1);
    private static Color square1Highlight = Color.LIGHTGREEN.brighter();//new Color(178, 237, 179, 1);
    private static Color square2Highlight = Color.LIGHTGREEN.darker();//new Color(123, 189, 123, 1);


    public abstract SquareView getSquareAt(int row, int col);

    public abstract PieceView removePieceFromBoard(int row, int col);

    public void putPieceOnBoard(int row, int col, PieceEnum pieceType, Color pieceColor){
        this.getSquareAt(row, col).putPiece(pieceType, pieceColor);
    }

    public void movePiece(int startRow, int startCol, int endRow, int endCol) {
        PieceView removedPiece = removePieceFromBoard(startRow,startCol);
        putPieceOnBoard(endRow,endCol,removedPiece.getPieceType(),removedPiece.getPieceColor());
    }

    public void initPiecesFromBoard(ChessBoard modelBoard) {
        // loop through the entire board and create 2D pieces where necessary
        for (int row = 0; row < SIDE_LENGTH; row++) {
            for (int col = 0; col < SIDE_LENGTH; col++) {
                //if the current square has a piece, make a 2D representation of it
                if(!modelBoard.getSquareAt(row, col).isEmpty()){
                    ChessPiece currentPiece = modelBoard.getSquareAt(row, col).getCurrentPiece();
                    //get the correct color from the model
                    Color pieceColor = getPlayer1Color();
                    if(currentPiece.getTeam() == Team.BLACK){ // if piece belongs to player2 (assuming black is player 2)
                        pieceColor = getPlayer2Color(); //use player2 color
                    }
                    //create the 3D piece with using the tye enum and color
                    getSquareAt(row, col).putPiece(currentPiece.getPieceType(), pieceColor);

                }
            }

        }
    }




    //Getters and Setters *************************************

    public static Color getPlayer1Color() {
        return player1Color;
    }
    public static Color getPlayer2Color() {
        return player2Color;
    }
    public static void setPlayer1Color(Color newColor) {
        player1Color = newColor;
    }
    public static void setPlayer2Color(Color newColor) {
        player2Color = newColor;
    }



    public static Color getSquare1Color() {
        return square1Color;
    }

    public static Color getSquare2Color() {
        return square2Color;
    }

    public static Color getSquare1Highlight() {
        return square1Highlight;
    }

    public static Color getSquare2Highlight() {
        return square2Highlight;
    }
}
