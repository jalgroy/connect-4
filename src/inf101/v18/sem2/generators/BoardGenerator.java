package inf101.v18.sem2.generators;

import inf101.v18.sem2.datastructures.Board;

import java.util.List;
import java.util.Random;

public class BoardGenerator<T> implements IGenerator<Board> {
    int minWidth;
    int minHeight;
    int maxWidth;
    int maxHeight;
    IGenerator<T> tGenerator;
    Random random = new Random();

    public BoardGenerator(int minW, int minH, int maxW, int maxH, IGenerator<T> tGenerator){
        if(minW < 1 || minH < 1 || maxW < minW || maxH < minH){
            throw new IllegalArgumentException(
                    String.format("Illegal bounds minW: %d, minH: %d, maxW: %d, maxH: %d%n", minW, minH, maxW, maxH)
            );
        }
        this.minWidth = minW;
        this.minHeight = minH;
        this.maxWidth = maxW;
        this.maxHeight = maxH;
        this.tGenerator = tGenerator;
    }

    public BoardGenerator(int width, int height){
        if(width < 1 || height < 1){
            throw new IllegalArgumentException("Illegal dimensions - width: " + width + ", height: " + height);
        }
        this.minWidth = width;
        this.maxWidth = width;
        this.minHeight = height;
        this.maxHeight = height;
    }

    @Override
    public List<Board> generateEquals(int n) {
        return null;
    }

    @Override
    public Board generate() {
        int width = random.nextInt(maxWidth+1 - minWidth) + minWidth;
        int heigth = random.nextInt(maxWidth+1 - minWidth) + minWidth;
        Board<T> board = new Board<>(width, heigth);
        for (int i = 0; i < width; i++) {
            int columnHeight = random.nextInt(board.getHeight());
            for (int j = 0; j < columnHeight; j++) {
                board.add(i, tGenerator.generate());
            }
        }
        return board;
    }
}
