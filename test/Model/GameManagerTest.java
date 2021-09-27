package Model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {

    GameManager gm;

    @BeforeEach
    void setUp() {
        gm = new GameManager();
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * Insure the reset game method does everything necessary to reset the game
     */
    @Test
    void testResetGame() {
        //make a move to change up the state of the game
        gm.movePiece(gm.getBoard().getSquareAt(1, 3), gm.getBoard().getSquareAt(2, 3));
        //reset the game
        gm.resetGame();
        //check if gm has all the starting values of a new game
        GameManager newGame = new GameManager();
        assertEquals(newGame.getCurrentTurn(), gm.getCurrentTurn());
        System.out.println(gm.getBoard().toString());
    }

    /**
     * Test if the turn switches correctly when a move is made
     */
    @Test
    void testSwitchTurn() {
        assertEquals(Team.WHITE, gm.getCurrentTurn());
        gm.movePiece(gm.getBoard().getSquareAt(1, 3), gm.getBoard().getSquareAt(2, 3));
        assertEquals(Team.BLACK, gm.getCurrentTurn());
    }
}