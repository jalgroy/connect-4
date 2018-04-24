package inf101.v18.sem2.tests;

import inf101.v18.sem2.datastructures.Board;
import inf101.v18.sem2.Disc;
import inf101.v18.sem2.Rules;
import inf101.v18.sem2.datastructures.IBoard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RuleTest {
    @Test
    void legalMoveTest(){
        IBoard<Disc> board = new Board<>(7,6);
        // Fill board
        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < 6; j++) {
                board.add(i,Disc.EARTH);
            }
        }
        // Should have no legal moves
        for (int i = 0; i < board.getWidth(); i++) {
            assertFalse(Rules.isLegalMove(board, i));
        }
        assertEquals(0, Rules.getLegalMoves(board).size());
    }
}
