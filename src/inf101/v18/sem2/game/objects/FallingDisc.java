package inf101.v18.sem2.game.objects;

import javafx.scene.canvas.GraphicsContext;

/**
 * Object for keeping track of a Disc while it's falling
 */
public class FallingDisc {
    private Disc disc;
    private int column;
    private double x;
    private double y;
    private double targetY;
    private double dy = 0;
    private boolean landed = false;
    private final double G = 98.1;

    public FallingDisc(Disc disc, int column, double x, double y, double targetY){
        this.disc = disc;
        this.column = column;
        this.x = x;
        this.y = y;
        this.targetY = targetY;
    }

    /**
     * Update Disc location, detect landing
     */
    public void step(){
        if(!landed){
            dy += G / 60; // Because step() is called 60 times a sec
            y += dy;
            if(y >= targetY){
                y = targetY;
                landed = true;
            }
        }
    }

    /**
     * Draw disc
     * @param context Graphics context
     * @param width Disc width
     * @param height Disc height
     */
    public void draw(GraphicsContext context, double width, double height){
        disc.draw(context, x, y, width, height);
    }

    /**
     * @return True if disc has landed
     */
    public boolean hasLanded(){
        return landed;
    }

    /**
     * @return Disc
     */
    public Disc getDisc() {
        return disc;
    }

    /**
     * @return Column disc is falling in
     */
    public int getColumn() {
        return column;
    }
}
