package inf101.v18.sem2.player;

import inf101.v18.sem2.Board;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Player implements IPlayer {
    private String name;
    private Color color;

    public Player(String name, Color color){
        this.name = name;
        this.color = color;
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
        context.setFill(color);
        context.fillOval(x + width*.1, y + height*.1, width*.8, height*.8);
        context.restore();
    }
}
