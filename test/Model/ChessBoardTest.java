package Model;

import Model.ChessBoard;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessBoardTest {

    private ChessBoard board;

    @BeforeEach
    void setUp() {
        board = new ChessBoard();
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * Just test to see if the board prints out correctly
     */
    @Test
    void testToString() {
        System.out.println(board.toString());
    }




}