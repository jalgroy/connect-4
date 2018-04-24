package inf101.v18.sem2.datastructures;

import inf101.v18.sem2.Disc;
import javafx.scene.canvas.GraphicsContext;


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

    public void draw(GraphicsContext context, double width, double height){
        if(disc != null){
            disc.draw(context, width*(x+.1), height*(y+.1), .8*width, .8*height);
        }
    }
}
