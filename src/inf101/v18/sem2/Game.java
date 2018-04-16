package inf101.v18.sem2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Game {
    private GameState gameState = GameState.PLAYING;
    private Board board;

    /*public void draw(GraphicsContext context){
        context.save();
        context.setFill(Color.WHITE);
        context.fillRect(0,0,1050*SF,900*SF);
        context.restore();
        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                context.save();
                board.getSlot(i,j).draw(context, width/board.getWidth(), height/board.getHeight());
                context.restore();
            }
        }
    }*/
}
