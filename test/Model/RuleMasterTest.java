package Model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RuleMasterTest {

    GameManager gm;

    @BeforeEach
    void setUp() {
        gm = new GameManager();
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * Test if checking for check works
     */
    @Test
    void checkAllPiecesIfCheckKing() {
        //move white knight to put black in check
        gm.movePiece(gm.getBoard().getSquareAt(0, 6), gm.getBoard().getSquareAt(5, 3));
        assertTrue(gm.getRuleMaster().isIsCheckedBlack());

        //move black knight so white is in check
        gm.movePiece(gm.getBoard().getSquareAt(7, 6), gm.getBoard().getSquareAt(2, 3));

        assertTrue(gm.getRuleMaster().isIsCheckedBlack());
        assertTrue(gm.getRuleMaster().isIsCheckedWhite());


    }
}