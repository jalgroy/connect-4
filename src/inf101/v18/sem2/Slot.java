package inf101.v18.sem2;

public class Slot {
    private Disc disc;
    private int x;
    private int y;

    public Slot(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Disc getDisc() {
        return disc;
    }

    public void setDisc(Disc disc) {
        this.disc = disc;
    }

    public boolean isEmpty() {
        return disc == null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
