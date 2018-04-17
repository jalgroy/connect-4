package inf101.v18.sem2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

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
        if(disc == null){
            drawColor(context,width,height,Color.WHITE);
        } else {
            switch (disc) {
                case HAL:
                    drawImage(context, width, height, "HAL9000");
                    break;
                case GREEN:
                    drawColor(context, width, height, Color.GREEN);
                    break;
                case YELLOW:
                    drawColor(context, width, height, Color.YELLOW);
                    break;
                case BLUE:
                    drawColor(context, width, height, Color.BLUE);
                    break;
                case BLACK:
                    drawColor(context, width, height, Color.BLACK);
                    break;
            }
        }
    }

    private void drawColor(GraphicsContext context, double width, double height, Color color){
        context.setFill(color);
        context.fillOval(width*(x+.1), height*(y+.1), .8*width, .8*height);
    }

    private void drawImage(GraphicsContext context, double width, double height, String filename){
        Image discImage = new Image("inf101/v18/sem2/images/" + filename + ".png");
        context.drawImage(discImage, width*(x+1),height*(y+1),.8*width,.8*height);
    }
}
