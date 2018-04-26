package inf101.v18.sem2.generators;

import inf101.v18.sem2.datastructures.Grid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GridGenerator<T> implements IGenerator<Grid> {
    private int maxWidth;
    private int maxHeight;
    private IGenerator<T> tGenerator;
    private Random random = new Random();

    public GridGenerator(int maxWidth, int maxHeight, IGenerator<T> tGenerator){
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.tGenerator = tGenerator;
    }

    @Override
    public List<Grid> generateEquals(int n) {
        return generateEquals(random, n);
    }

    @Override
    public List<Grid> generateEquals(Random r, int n) {
        long seed = r.nextLong();
        Random seededRandom = new Random(seed);
        List<Grid> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(generate(seededRandom));
        }
        return list;
    }

    @Override
    public Grid generate(){
        return generate(random);
    }

    @Override
    public Grid generate(Random r) {
        int width = r.nextInt(maxWidth) + 1;
        int height = r.nextInt(maxHeight) + 1;
        Grid<T> grid = new Grid<>(width, height);
        for (int i = 0; i < grid.getWidth(); i++) {
            for (int j = 0; j < grid.getHeight(); j++) {
                T element = r.nextInt(100) < 25 ? tGenerator.generate() : null;
                grid.set(i,j,element);
            }
        }
        return grid;
    }
}
