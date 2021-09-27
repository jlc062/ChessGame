//A portion of this code was taken from http://www.interactivemesh.org/models/jfx3dimporter.html
//The camera motion aspects of this code were taken from https://stackoverflow.com/questions/28731460/javafx-moving-3d-objects-with-mouse-on-a-virtual-plane

package View.View3D;

//https://stackoverflow.com/questions/31148690/get-real-position-of-a-node-in-javafx
//https://stackoverflow.com/questions/20825935/javafx-get-node-by-row-and-column

import Model.ChessBoard;
import Model.ChessPieces.ChessPiece;
import Model.Team;
import View.BoardView;
import View.PieceEnum;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;

public class BoardView3D extends BoardView {

//    private final Color PLAYER1_COLOR = Color.WHITE;
//    private final Color PLAYER2_COLOR = Color.RED;
//
//    private final Color SQUARE1_COLOR = Color.BLACK;
//    private final Color SQUARE2_COLOR = Color.WHITE;


     public BoardView3D(ChessBoard modelBoard) {
         super();

         //initialize the board
         this.setPrefColumns(SIDE_LENGTH);
         //this.setPrefSize(SIDE_LENGTH*SquareView3D.SQUARE_SIZE,SIDE_LENGTH*SquareView3D.SQUARE_SIZE);
         this.setAlignment(Pos.CENTER);

         //center the board in the view
//         this.setTranslateX(SquareView3D.SQUARE_SIZE*-SIDE_LENGTH/2);
//         this.setTranslateY(SquareView3D.SQUARE_SIZE*-SIDE_LENGTH/2);

         //add all of the squares to the board
         initializeBoardSquares();

         //setup the board based on the state of the board passed in
         initPiecesFromBoard(modelBoard);
     }

    private void initializeBoardSquares() {
        for (int row = 0; row < SIDE_LENGTH; row++) {
            for (int col = 0; col < SIDE_LENGTH ; col++) {
                SquareView3D square;
                if ((col+row) % 2 == 0) {
                    square = new SquareView3D(row, col, getSquare2Color());
                }
                else {
                    square = new SquareView3D(row, col, getSquare1Color());
                }
                this.getChildren().add(square);
            }
        }
    }

    @Override
    public PieceView3D removePieceFromBoard(int row, int col) {
        return this.getSquareAt(row, col).removePiece();
    }

    @Override
    public SquareView3D getSquareAt(int row, int col){
        return (SquareView3D)this.getChildren().get((7-row) * SIDE_LENGTH + col);
    }
}
