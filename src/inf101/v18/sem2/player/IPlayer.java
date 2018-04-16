package inf101.v18.sem2.player;

import inf101.v18.sem2.Board;
import javafx.scene.canvas.GraphicsContext;

public interface IPlayer {
    String getName();

    void drop(Board board);

    void drawDisc(GraphicsContext context, double x, double y, double width, double height);
}
