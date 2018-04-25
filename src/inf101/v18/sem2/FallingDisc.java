package inf101.v18.sem2;

import javafx.scene.canvas.GraphicsContext;

public class FallingDisc {
    private Disc disc;
    private int column;
    private double x;
    private double y;
    private double targetY;
    private double dy = 0;
    private boolean landed = false;
    private final double G = 0.981;

    FallingDisc(Disc disc, int column, double x, double y, double targetY){
        this.disc = disc;
        this.column = column;
        this.x = x;
        this.y = y;
        this.targetY = targetY;
    }

    public void step(){
        if(!landed){
            dy += 2*G;
            y += dy;
            if(y >= targetY){
                y = targetY;
                landed = true;
            }
        }
    }

    public void draw(GraphicsContext context, double width, double height){
        disc.draw(context, x, y, width, height);
    }

    public boolean hasLanded(){
        return landed;
    }

    public Disc getDisc() {
        return disc;
    }

    public int getColumn() {
        return column;
    }
}
