package inf101.v18.sem2;

import inf101.v18.sem2.player.AI;
import inf101.v18.sem2.player.IPlayer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private GameState gameState;
    private Board board;
    private List<IPlayer> players; // Datainvariant: size 2
    private int turn;
    private final int COLUMNS = 7;
    private final int ROWS = 6;
    private List<Integer> history;

    public Game(){
        board = new Board(COLUMNS, ROWS);
        gameState = GameState.PLAYING;
        turn = 0;
        players = new ArrayList<>();
        history = new ArrayList<>();
    }

    public void addPlayer(IPlayer player){
        players.add(player);
    }

    public void keyPressed(KeyCode key){
        if(gameState == GameState.PLAYING) {
            switch (key) {
                case DIGIT1:
                    drop(0);
                    break;
                case DIGIT2:
                    drop(1);
                    break;
                case DIGIT3:
                    drop(2);
                    break;
                case DIGIT4:
                    drop(3);
                    break;
                case DIGIT5:
                    drop(4);
                    break;
                case DIGIT6:
                    drop(5);
                    break;
                case DIGIT7:
                    drop(6);
                    break;
            }
        }
    }

    private void drop(int column){
        if(column < 0 || column >= COLUMNS){
            throw new IllegalArgumentException("Invalid column " + column);
        }
        IPlayer player = players.get(turn % 2);
        if(!(player instanceof AI)){
            if(board.drop(column, player.getDisc())){
                history.add(column);
                nextTurn();
            } else {
                System.out.println("Invalid drop.");
            }
        }
    }

    private void nextTurn(){
        if(Rules.isWin(board, history.get(history.size()-1))){
            if(turn % 2 == 0){
                gameState = GameState.ONE_WON;
            } else {
                gameState = GameState.TWO_WON;
            }
            System.out.println(gameState);
        } else if(Rules.isDraw(board, history.get(history.size()-1))) {
            gameState = GameState.DRAW;
        } else{
            turn++;
            IPlayer nextPlayer = players.get(turn % 2);
            if (nextPlayer instanceof AI) {
                int column = ((AI) nextPlayer).getMove();
                history.add(column);
                board.drop(column, nextPlayer.getDisc());
                nextTurn();
            }
        }
    }

    public void draw(GraphicsContext context, double width, double height){
        // TODO: Expand UI, falling animation somehow
        context.save();
        context.setFill(Color.DEEPSKYBLUE);
        context.fillRect(0, 0, width, height);
        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                context.save();
                board.getSlot(i, j).draw(context, width / board.getWidth(), height / board.getHeight());
                context.restore();
            }
        }
        context.restore();
    }

    public int getCOLUMNS() {
        return COLUMNS;
    }

    public Board getBoard() {
        return board;
    }

    public GameState getState(){
        return gameState;
    }
}
