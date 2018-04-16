package inf101.v18.sem2.player;

import inf101.v18.sem2.Board;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class AI implements IPlayer {
    private String name = "HAL 9000";
    private Image discImage;

    public AI(){
        discImage = new Image("inf101/v18/sem2/images/HAL9000.png");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void drop(Board board) {

    }

    @Override
    public void drawDisc(GraphicsContext context, double x, double y, double width, double height) {
        context.save();
        context.drawImage(discImage, x,y,width,height);
        context.restore();
    }
}
