package inf101.v18.sem2;

import inf101.v18.sem2.datastructures.Board;
import inf101.v18.sem2.datastructures.IBoard;
import inf101.v18.sem2.player.IAI;
import inf101.v18.sem2.player.IPlayer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private GameState gameState;
    private IBoard<Disc> board;
    private List<IPlayer> players; // Datainvariant: size 2
    private int turn;
    private final int columns = 7;
    private final int rows = 6;
    private List<Integer> history;
    private GraphicsContext context;
    private double width;
    private double height;
    private final int simulationDepth = 5;
    private FallingDisc fallingDisc;

    public Game(){
        board = new Board<>(columns,rows);
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

    public boolean addPlayer(IPlayer player){
        for(IPlayer p : players){
            if(p.getName().equals(player.getName()) || p.getDisc() == player.getDisc()){
                return false;
            }
        }
        players.add(player);
        return true;
    }

    public void mouseClicked(double x){
        if(gameState == GameState.PLAYING && !(getCurrentPlayer() instanceof IAI) && fallingDisc == null) {
           int column = (int) (x*columns / width);
           drop(column, false);
       }
    }

    public void keyPressed(KeyCode key){
        if(gameState == GameState.PLAYING && !(getCurrentPlayer() instanceof IAI) && fallingDisc == null) {
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
        if(Rules.isLegalMove(board, column)){
            if(!simulation){
                int row = Rules.getRow(board, column);
                fallingDisc = new FallingDisc(player.getDisc(), column,
                        (column+.1)*width/board.getWidth(), 0, (row+.1)*height/board.getHeight());
            } else {
                board.add(column, player.getDisc());
                history.add(column);
                nextTurn(true);
            }
        } else {
            System.out.println("Invalid drop.");
        }
    }

    private void nextTurn(boolean simulation){
        if(Rules.isWin(board, history.get(history.size()-1))){
            gameState = GameState.WIN;
        } else if(Rules.isDraw(board, history.get(history.size()-1))) {
            gameState = GameState.DRAW;
        } else{
            turn++;
            if(!simulation){
                Thread t = new Thread(() -> {
                    moveAI();
                });
                t.start();
            }
        }
    }

    private void moveAI(){
        IPlayer current = getCurrentPlayer();
        if(current instanceof IAI){
            int column = ((IAI)current).getMove(this, simulationDepth);
            drop(column, false);
        }
    }

    public void undo(){
        if(history.size() == 0) return;
        if(board.remove(history.get(history.size()-1))){
            if(gameState == GameState.PLAYING){
                turn--;
            }
            history.remove(history.size()-1);
            gameState = GameState.PLAYING;
        }
    }

    public void step(){
        if(fallingDisc != null){
            fallingDisc.step();
            if(fallingDisc.hasLanded()){
                board.add(fallingDisc.getColumn(), fallingDisc.getDisc());
                history.add(fallingDisc.getColumn());
                fallingDisc = null;
                nextTurn(false);
            }
        }

    }

    public void draw(){
        context.save();
        context.setFill(Color.WHITE);
        context.fillRect(0,0,width,height);
        double slotWidth = width / board.getWidth();
        double slotHeight = height / board.getHeight();

        if(fallingDisc != null){
            fallingDisc.draw(context,.8*slotWidth, .8*slotHeight);
        }

        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                context.save();
                Disc d = board.get(i,j);
                if(d != null){
                    d.draw(context, slotWidth*(i + .1), slotHeight*(j+.1), .8*slotWidth, .8*slotHeight);
                }
                context.restore();
            }
        }
        if(gameState == GameState.WIN) {
            int[] winLocation = Rules.getWinLocation(board, history.get(history.size()-1));
            // TODO: Mark winning discs (red stroke?)
        }
        context.restore();
    }

    public List<Circle> getClip(){
        List<Circle> circles = new ArrayList<>();
        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                double slotWidth = width / board.getWidth();
                Circle c = new Circle(.38*slotWidth);
                c.setCenterX(i*slotWidth + slotWidth/2);
                c.setCenterY(j*slotWidth + slotWidth/2);
                circles.add(c);
            }
        }
        return circles;
    }

    public boolean isReady(){
        return (players.size() == 2 && context != null && width > 0 && height > 0);
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public IBoard getBoard() {
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

    public void setContext(GraphicsContext context) {
        this.context = context;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
