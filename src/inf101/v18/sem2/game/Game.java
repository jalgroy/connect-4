package inf101.v18.sem2.game;

import inf101.v18.sem2.game.objects.Disc;
import inf101.v18.sem2.game.objects.FallingDisc;
import inf101.v18.sem2.datastructures.Board;
import inf101.v18.sem2.datastructures.IBoard;
import inf101.v18.sem2.player.IAI;
import inf101.v18.sem2.player.IPlayer;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

/**
 * Connect four game
 */
public class Game {
    private final int columns = 7;
    private final int rows = 6;
    private final String title = "Connect Four";

    private GameState gameState;
    private IBoard<Disc> board;
    private List<IPlayer> players;
    private int turn;
    private List<Integer> history;
    private GraphicsContext context;
    private double width;
    private double height;
    private FallingDisc fallingDisc;

    /**
     * New Game without players
     */
    public Game(){
        board = new Board<>(columns,rows);
        gameState = GameState.PLAYING;
        turn = 0;
        players = new ArrayList<>();
        history = new ArrayList<>();
    }

    /**
     * Deep copy of game in its current state. Does not copy players or graphics-related properties.
     * @return Copy of game
     */
    public Game copy(){
        Game g = new Game();
        g.board = board.copy();
        g.history.addAll(this.history);
        return g;
    }

    /**
     * Add player to game. Checks if name or disc is taken before adding.
     * @param player Player to add
     * @return True if successful
     * @throws IllegalStateException If game already has two players
     */
    public boolean addPlayer(IPlayer player){
        if(players.size() > 1){
            throw new IllegalStateException("Game already has two players");
        }
        for(IPlayer p : players){
            if(p.getName().equals(player.getName()) || p.getDisc() == player.getDisc()){
                return false;
            }
        }
        players.add(player);
        return true;
    }

    /**
     * Handle a mouse click.
     * @param x x-position of click
     */
    public void mouseClicked(double x){
        if(gameState == GameState.PLAYING && !(getCurrentPlayer() instanceof IAI) && fallingDisc == null) {
           int column = (int) (x*columns / width);
           drop(column, false);
       }
    }

    /**
     * Handle a key press.
     * @param key Key that was pressed
     */
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

    /**
     * Drop disc in column
     * @param column Column to drop disc in
     * @param simulation Is this a simulation
     */
    public void drop(int column, boolean simulation) throws IllegalArgumentException {
        if(column < 0 || column >= columns){
            throw new IndexOutOfBoundsException("Invalid column " + column);
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

    /**
     * Trigger next turn
     * @param simulation Is this part of a simulation
     */
    private void nextTurn(boolean simulation){
        if(Rules.isWin(board, history.get(history.size()-1))){
            gameState = GameState.WIN;
            //if(!simulation) System.out.println(history);
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

    /**
     * Trigger an AI move if it's an AI's turn
     */
    private void moveAI(){
        IPlayer current = getCurrentPlayer();
        if(current instanceof IAI){
            int column = ((IAI)current).getMove(this);
            drop(column, false);
        }
    }

    /**
     * Undo previous move
     */
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
        draw();
    }

    /**
     * Draw game in it's current state
     */
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
                Disc d = board.get(i, j);
                if (d != null) {
                    d.draw(context, slotWidth * (i + .1), slotHeight * (j + .1), .8 * slotWidth, .8 * slotHeight);
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

    /**
     * @return JavaFX nodes that can act as a mask for how the game is drawn.
     */
    public List<Node> getClip(){
        List<Node> clip = new ArrayList<>();
        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                double slotWidth = width / board.getWidth();
                Circle c = new Circle(.38*slotWidth);
                c.setCenterX(i*slotWidth + slotWidth/2);
                c.setCenterY(j*slotWidth + slotWidth/2);
                clip.add(c);
            }
        }
        return clip;
    }

    /**
     * @return Columns in game board
     */
    public int getColumns() {
        return columns;
    }

    /**
     * @return Rows in game board
     */
    public int getRows() {
        return rows;
    }

    /**
     * @return Board
     */
    public IBoard getBoard() {
        return board;
    }

    /**
     * @return Game state
     */
    public GameState getState(){
        return gameState;
    }

    /**
     * Get player number n
     * @param n
     * @return Player at index n
     * @throws IllegalStateException If requested player has not yet been added to game
     */
    public IPlayer getPlayer(int n){
        if(n < 0 || n > 1){
            throw new IndexOutOfBoundsException("" + n);
        }
        if(players.size() < n+1){
            throw new IllegalStateException("Player has not been initialized yet");
        }
        return players.get(n);
    }

    /**
     * @return Player whose turn it is
     */
    public IPlayer getCurrentPlayer(){
        return players.get(turn % 2);
    }

    /**
     * @return Players
     */
    public List<IPlayer> getPlayers(){
        return players;
    }

    /**
     * @return Previous move made in game
     */
    public int getLastMove(){
        return history.get(history.size()-1);
    }

    /**
     * Set Grapics Context used to draw game.
     * @param context GraphicsContext
     */
    public void setContext(GraphicsContext context) {
        this.context = context;
    }

    /**
     * @param width Game width in pixels
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * @param height Game height in pixels
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * @return Width
     */
    public double getWidth() {
        return width;
    }

    /**
     * @return Height
     */
    public double getHeight() {
        return height;
    }

    /**
     * @return Game title
     */
    public String getTitle(){
        return title;
    }
}
