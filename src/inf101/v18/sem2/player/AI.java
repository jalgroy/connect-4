package inf101.v18.sem2.player;

import inf101.v18.sem2.Board;
import inf101.v18.sem2.Disc;
import inf101.v18.sem2.Game;

public class AI implements IPlayer {
    private String name = "HAL 9000";
    private Disc disc;
    private Game game;

    public AI(Game game){
        this.game = game;
        disc = Disc.HAL;
    }

    public int getMove(){
        return 0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void drop(Board board) {

    }

    @Override
    public Disc getDisc() {
        return disc;
    }
}
