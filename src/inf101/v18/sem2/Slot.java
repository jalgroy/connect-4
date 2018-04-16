package inf101.v18.sem2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

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
        context.setFill(Color.BLUE);
        context.beginPath();
        context.moveTo(x*width, y*height);
        context.lineTo((x+1)*width, y*height);
        context.lineTo((x+1)*width, y*height/2);
        context.lineTo((x+.9)*width, y*height/2);
        context.arcTo((x+.9)*width, y*height/2, (x+.1)*width, y*height/2, width*.4);
        context.lineTo(x*width, y*height/2);
        context.closePath();
        context.stroke();
        //context.fillArc(x*width + .1*width, y*height + .1*height, width*.8, height*.8, 0, 180, ArcType.OPEN);
        //context.fillOval(width*(x+.1), height*(y+.1), .8*width, .8*height);

    }
}
