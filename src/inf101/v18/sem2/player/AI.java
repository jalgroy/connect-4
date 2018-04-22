package inf101.v18.sem2.player;

import inf101.v18.sem2.Board;
import inf101.v18.sem2.Disc;
import inf101.v18.sem2.Game;
import inf101.v18.sem2.Rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AI implements IAI {
    private String name;
    private Disc disc;
    private Random random;

    public AI(){
        disc = Disc.HAL;
        name = "HAL 9000";
        random = new Random();
    }

    public AI(String name, Disc disc){
        this.name = name;
        this.disc = disc;
        random = new Random();
    }

    @Override
    public int getMove(Game game, int depth){
        List<Integer> legalMoves = Rules.getLegalMoves(game.getBoard());
        List<Integer> moveRatings = new ArrayList<>();
        for(Integer move : legalMoves){
            moveRatings.add(rateMove(game, move, depth));
        }
        int maxRating = Collections.max(moveRatings);
        int moveIndex = getRandomMax(moveRatings, maxRating);
        return legalMoves.get(moveIndex);
    }


    private int rateMove(Game game, int move, int depth){
        Game g = game.copy();
        g.addPlayer(new AI("sim1", game.getPlayer(1).getDisc()));
        g.addPlayer(new AI("sim2", game.getPlayer(0).getDisc()));
        g.drop(move, true);
        int rating = 0;
        rating += ratePosition(g);
        if(depth > 1 && rating < 500){
            int nextMove = ((AI) g.getCurrentPlayer()).getMove(g,depth-1);
            g.drop(nextMove, true);
            rating += ratePosition(g)*depth;
        }
        return rating;
    }

    private int ratePosition(Game g){
        Board board = g.getBoard();
        int rating = 0;
        if(Rules.isWin(board, g.getLastMove())){
            if(g.getCurrentPlayer().getDisc() == disc){
                rating += 1000;
            } else {
                rating -= 1000;
            }
        }
        return rating;
    }

    private int getRandomMax(List<Integer> moveRatings, int max){
        List<Integer> maxIndexes = new ArrayList<>();
        for (int i = 0; i < moveRatings.size(); i++) {
            if(moveRatings.get(i) == max) maxIndexes.add(i);
        }
        return maxIndexes.get(random.nextInt(maxIndexes.size()));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Disc getDisc() {
        return disc;
    }
}
