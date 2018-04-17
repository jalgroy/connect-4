package inf101.v18.sem2;

import inf101.v18.sem2.player.AI;
import inf101.v18.sem2.player.IPlayer;
import inf101.v18.sem2.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class Game {
    private GameState gameState;
    private Board board;
    private IPlayer p1;
    private IPlayer p2;
    private final int COLUMNS = 7;
    private final int ROWS = 6;

    public Game(){
        board = new Board(COLUMNS, ROWS);
        gameState = GameState.PLAYING;
    }

    public void setPlayer(int nPlayer, IPlayer player){
        if(nPlayer == 1){
            p1 = player;
        } else if(nPlayer == 2){
            p2 = player;
        } else {
            throw new IllegalArgumentException("Player has to be number 1 or 2.");
        }
    }

    public void keyPressed(KeyCode key){
        switch (key) {
            case DIGIT1:
                drop(1);
                break;
            case DIGIT2:
                drop(2);
                break;
            case DIGIT3:
                drop(3);
                break;
            case DIGIT4:
                drop(4);
                break;
            case DIGIT5:
                drop(5);
                break;
            case DIGIT6:
                drop(6);
                break;
            case DIGIT7:
                drop(7);
                break;
        }
    }

    private void drop(int column){
        if(column < 1 || column > COLUMNS){
            throw new IllegalArgumentException("Invalid column " + column);
        }
    }

    public void draw(GraphicsContext context, double width, double height){
        context.save();
        context.setFill(Color.BLUE);
        context.fillRect(0,0,width,height);
        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                context.save();
                board.getSlot(i,j).draw(context, width/board.getWidth(), height/board.getHeight());
                context.restore();
            }
        }
        context.restore();
    }
}
