package inf101.v18.sem2.tests;

import inf101.v18.sem2.datastructures.Board;
import inf101.v18.sem2.Disc;
import inf101.v18.sem2.Rules;
import inf101.v18.sem2.datastructures.IBoard;
import inf101.v18.sem2.generators.BoardGenerator;
import inf101.v18.sem2.generators.DiscGenerator;
import inf101.v18.sem2.generators.IGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RulesTest {
    private IGenerator<Disc> discGen = new DiscGenerator();
    private IGenerator<Board> boardGen = new BoardGenerator<>(10,10, discGen);
    private final int N = 1000;

    // Known sets of moves that leads to a win in a 2-player connect four game
    private int[][] winningGames = {
            {1, 2, 1, 2, 1, 2, 1},
            {0, 1, 1, 2, 2, 3, 2, 3, 3, 4, 3},
            {1, 2, 0, 6, 2, 3, 3, 4, 3, 5},
            {2, 3, 2, 1, 1, 0, 0, 0, 0, 0, 0, 3, 1, 1, 1, 2},
            {0, 1, 2, 0, 1, 2, 0, 1, 2, 0, 1, 2, 0, 1, 2, 0, 1, 2, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 6, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 5},
            {0, 1, 1, 0, 2, 3, 2, 3, 2, 2, 1, 1, 4, 0, 0, 0, 1, 4, 4, 1, 3},
            {0, 1, 1, 2, 3, 4, 5, 6, 1, 1, 1, 2, 2, 2, 3, 3, 3},
            {0, 4, 1, 4, 2, 3, 2, 3, 2, 3, 3, 4, 2},
            {3, 3, 4, 4, 3, 2, 5, 4, 6},
            {1, 0, 1, 0, 1, 0, 2, 3, 2, 2, 3, 3, 4, 4, 4, 4, 4, 4, 2, 2, 3, 3, 1},
    };

    @Test
    void legalMoveTest1(){
        IBoard<Disc> board = new Board<>(7,6);
        // Fill board
        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < 6; j++) {
                board.add(i,Disc.values()[0]);
            }
        }
        // Should have no legal moves
        for (int i = 0; i < board.getWidth(); i++) {
            assertFalse(Rules.isLegalMove(board, i));
        }
        assertEquals(0, Rules.getLegalMoves(board).size());
    }

    @Test
    void legalMoveTest2(){
        for (int i = 0; i < N; i++) {
            IBoard<Disc> board = boardGen.generate();
            for (int j = 0; j < board.getWidth(); j++) {
                // If the top slot is available, that column should have a legal move
                assertEquals(board.get(j,0) == null, Rules.isLegalMove(board, j));
            }
        }
    }

    @Test
    void isWinTest1(){
        // Get two random, but different discs
        Disc d1 = discGen.generate();
        Disc d2 = discGen.generate();
        while (d2 == d1){
            d2 = discGen.generate();
        }

        for(int[] game : winningGames){
            // Execute the known winning games
            IBoard<Disc> board = new Board<>(7,6);
            for (int i = 0; i < game.length; i++) {
                board.add(game[i], i % 2 == 0 ? d1 : d2);
            }
            assertTrue(Rules.isWin(board, game[game.length-1]));
        }
    }
}
