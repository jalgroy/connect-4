package inf101.v18.sem2.generators;

import inf101.v18.sem2.Disc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiscGenerator implements IGenerator<Disc> {
    private Random random = new Random();

    @Override
    public List<Disc> generateEquals(int n) {
        List<Disc> list = new ArrayList<>();
        int index = random.nextInt(Disc.values().length);
        Disc d = Disc.values()[index];
        for (int i = 0; i < n; i++) {
            list.add(d);
        }
        return list;
    }

    @Override
    public Disc generate() {
        int i = random.nextInt(Disc.values().length);
        return Disc.values()[i];
    }
}
