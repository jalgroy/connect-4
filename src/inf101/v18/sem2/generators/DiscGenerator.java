package inf101.v18.sem2.generators;

import inf101.v18.sem2.Disc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiscGenerator implements IGenerator<Disc> {
    private Random random = new Random();

    @Override
    public List<Disc> generateEquals(int n) {
        return generateEquals(random, n);
    }

    @Override
    public List<Disc> generateEquals(Random r, int n) {
        List<Disc> list = new ArrayList<>();
        int index = r.nextInt(Disc.values().length);
        Disc d = Disc.values()[index];
        for (int i = 0; i < n; i++) {
            list.add(d);
        }
        return list;
    }

    @Override
    public Disc generate() {
        return generate(random);
    }

    @Override
    public Disc generate(Random r) {
        int i = r.nextInt(Disc.values().length);
        return Disc.values()[i];
    }
}
