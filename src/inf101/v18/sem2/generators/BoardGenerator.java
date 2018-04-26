package inf101.v18.sem2.generators;

import inf101.v18.sem2.datastructures.Board;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BoardGenerator<T> implements IGenerator<Board> {
    private int maxWidth;
    private int maxHeight;
    private IGenerator<T> tGenerator;
    private Random random = new Random();

    /**
     * @param maxWidth Inclusive
     * @param maxHeight Inclusive
     * @param tGenerator Generator for objects to put in board
     */
    public BoardGenerator(int maxWidth, int maxHeight, IGenerator<T> tGenerator){
        if(maxWidth < 1 || maxHeight < 1){
            throw new IllegalArgumentException(
                    String.format("Illegal bounds maxWidth: %d, maxHeight: %d%n", maxWidth, maxHeight)
            );
        }
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.tGenerator = tGenerator;
    }

    @Override
    public List<Board> generateEquals(int n) {
        return generateEquals(random, n);
    }

    @Override
    public List<Board> generateEquals(Random r, int n) {
        long seed = r.nextLong();
        Random seededRandom = new Random(seed);
        List<Board> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(generate(seededRandom));
        }
        return list;
    }

    @Override
    public Board generate() {
        return generate(random);
    }

    @Override
    public Board generate(Random r) {
        int width = r.nextInt(maxWidth) + 1;
        int heigth = r.nextInt(maxHeight) + 1;
        Board<T> board = new Board<>(width, heigth);
        for (int i = 0; i < width; i++) {
            int columnHeight = r.nextInt(board.getHeight());
            for (int j = 0; j < columnHeight; j++) {
                board.add(i, tGenerator.generate());
            }
        }
        return board;
    }
}
