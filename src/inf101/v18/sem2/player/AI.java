package inf101.v18.sem2.player;

import inf101.v18.sem2.Board;
import inf101.v18.sem2.Disc;
import inf101.v18.sem2.Game;
import inf101.v18.sem2.Rules;

import java.util.Random;

public class AI implements IPlayer {
    private String name = "HAL 9000";
    private Disc disc;
    private Game game;
    private Random random;

    public AI(Game game){
        this.game = game;
        disc = Disc.HAL;
        random = new Random();
    }

    public int getMove(){
        // TODO: Prevent infinite loop, make smarter
        while (true) {
            int column = random.nextInt(game.getCOLUMNS());
            if(Rules.isLegalMove(game.getBoard(), column)){
                return column;
            }
        }
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
