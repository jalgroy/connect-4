package inf101.v18.sem2.tests;

import inf101.v18.sem2.Board;
import inf101.v18.sem2.Disc;
import inf101.v18.sem2.Rules;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RuleTest {
    @Test
    void legalMoveTest(){
        Board board = new Board(7,6);
        // Fill board
        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < 6; j++) {
                board.getSlot(i,j).setDisc(Disc.EARTH);
            }
        }
        // Should have no legal moves
        for (int i = 0; i < board.getWidth(); i++) {
            assertFalse(Rules.isLegalMove(board, i));
        }
    }
}
