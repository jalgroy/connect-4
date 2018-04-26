package inf101.v18.sem2.tests;

import inf101.v18.sem2.Disc;
import inf101.v18.sem2.datastructures.Grid;
import inf101.v18.sem2.generators.DiscGenerator;
import inf101.v18.sem2.generators.GridGenerator;
import inf101.v18.sem2.generators.IGenerator;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GridTest {
    private IGenerator<Disc> discGen = new DiscGenerator();
    private IGenerator<Grid> gridGen = new GridGenerator<>(100, 100, discGen);
    private int N = 1000;
    private Random random = new Random();

    @Test
    void setGetTest(){
        for (int i = 0; i < N; i++) {
            Disc d = discGen.generate();
            Grid grid = gridGen.generate();
            int x = random.nextInt(grid.getWidth());
            int y = random.nextInt(grid.getHeight());
            grid.set(x,y,d);
            assertEquals(d, grid.get(x,y));
        }
    }

}
