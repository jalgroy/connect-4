package inf101.v18.sem2.tests;

import inf101.v18.sem2.Disc;
import inf101.v18.sem2.Rules;
import inf101.v18.sem2.datastructures.Board;
import inf101.v18.sem2.generators.BoardGenerator;
import inf101.v18.sem2.generators.DiscGenerator;
import inf101.v18.sem2.generators.IGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    private IGenerator<Disc> discGen = new DiscGenerator();
    private IGenerator<Board> boardGen = new BoardGenerator<>(1,1,100,100, discGen);
    private final int N = 10000;

    @Test
    void addGetTest(){
        for (int i = 0; i < N; i++) {
            Board<Disc> board = boardGen.generate();
            for (int j = 0; j < board.getWidth(); j++) {
                if(Rules.isLegalMove(board, j)){
                    Disc d1 = discGen.generate();
                    board.add(j,d1);
                    Disc d2 = board.get(j, Rules.getRow(board, j) + 1);
                    assertEquals(d1,d2);
                }

            }
        }
    }

    @Test
    void addRemoveTest(){
        Board<Disc> board;
        for (int i = 0; i < N; i++) {
            board = boardGen.generate();
            Board<Disc> copy = board.copy();
            for (int j = 0; j < board.getWidth(); j++) {
                if(Rules.isLegalMove(board, j)){
                    board.add(j, discGen.generate());
                    board.remove(j);
                }
            }
            assertTrue(copy.equals(board));
        }
    }
}
