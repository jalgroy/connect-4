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
    private final int columns = 7;
    private final int rows = 6;
    private List<Integer> history;

    public Game(){
        board = new Board(columns,rows);
        gameState = GameState.PLAYING;
        turn = 0;
        players = new ArrayList<>();
        history = new ArrayList<>();
    }

    public Game copy(){
        Game g = new Game();
        g.board = board.copy();
        g.history.addAll(this.history);
        return g;
    }

    public void addPlayer(IPlayer player){
        players.add(player);
    }

    public void keyPressed(KeyCode key){
        if(gameState == GameState.PLAYING) {
            switch (key) {
                case DIGIT1:
                    drop(0, false);
                    break;
                case DIGIT2:
                    drop(1,false);
                    break;
                case DIGIT3:
                    drop(2,false);
                    break;
                case DIGIT4:
                    drop(3,false);
                    break;
                case DIGIT5:
                    drop(4,false);
                    break;
                case DIGIT6:
                    drop(5,false);
                    break;
                case DIGIT7:
                    drop(6,false);
                    break;
            }
        }
    }

    public void drop(int column, boolean simulation){
        if(column < 0 || column >= columns){
            throw new IllegalArgumentException("Invalid column " + column);
        }
        IPlayer player = players.get(turn % 2);
        if(board.drop(column, player.getDisc())){
            history.add(column);
            nextTurn(simulation);
        } else {
            System.out.println("Invalid drop.");
        }
    }

    private void nextTurn(boolean simulation){
        if(Rules.isWin(board, history.get(history.size()-1))){
            if(turn % 2 == 0){
                gameState = GameState.ONE_WON;
            } else {
                gameState = GameState.TWO_WON;
            }
        } else if(Rules.isDraw(board, history.get(history.size()-1))) {
            gameState = GameState.DRAW;
        } else{
            turn++;
            IPlayer nextPlayer = players.get(turn % 2);
            if (!simulation && nextPlayer instanceof AI) {
                int column = ((AI) nextPlayer).getMove(6);
                history.add(column);
                board.drop(column, nextPlayer.getDisc());
                nextTurn(false);
            }
        }
    }

    public void draw(GraphicsContext context, double width, double height){
        // TODO: Falling animation
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

    public int getColumns() {
        return columns;
    }

    public Board getBoard() {
        return board;
    }

    public GameState getState(){
        return gameState;
    }

    public IPlayer getPlayer(int i){
        return players.get(i);
    }

    public IPlayer getCurrentPlayer(){
        return players.get(turn % 2);
    }

    public int getLastMove(){
        return history.get(history.size()-1);
    }
}
