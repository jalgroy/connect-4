package inf101.v18.sem2.player;

import inf101.v18.sem2.Disc;
import inf101.v18.sem2.Game;
import inf101.v18.sem2.Rules;
import inf101.v18.sem2.datastructures.IBoard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * HAL9000-inspired AI. Not as smart as the original.
 */
public class HAL implements IAI {
    private String name;
    private Disc disc;
    private Random random;
    private final int simulationDepth = 5;

    public HAL(){
        disc = Disc.HAL;
        name = "HAL 9000";
        random = new Random();
    }

    private HAL(String name, Disc disc){
        this.name = name;
        this.disc = disc;
        random = new Random();
    }

    @Override
    public int getMove(Game game){
        return getMove(game, simulationDepth);
    }

    /**
     * Get move using given simulation depth
     * @param game Current depth
     * @param depth Simulation depth
     * @return Column to drop in
     */
    private int getMove(Game game, int depth){
        List<Integer> legalMoves = Rules.getLegalMoves(game.getBoard());
        List<Integer> moveRatings = new ArrayList<>();
        for(Integer move : legalMoves){
            moveRatings.add(rateMove(game, move, depth));
        }
        int maxRating = Collections.max(moveRatings);
        int moveIndex = getRandomMax(moveRatings, maxRating);
        return legalMoves.get(moveIndex);
    }


    /**
     * Give an integer rating to a given move.
     * @param game Current game
     * @param move Column to drop in
     * @param depth Simulation depth - How many moves in the future to check
     * @return Rating of move
     */
    private int rateMove(Game game, int move, int depth){
        Game g = game.copy();
        g.addPlayer(new HAL("sim1", game.getPlayer(1).getDisc()));
        g.addPlayer(new HAL("sim2", game.getPlayer(0).getDisc()));
        g.drop(move, true);
        int rating = 0;
        rating += ratePosition(g);

        // Skip recursion if rating > 500 which means the game is won
        if(depth > 1 && rating < 500){
            int nextMove = ((HAL) g.getCurrentPlayer()).getMove(g,depth-1);
            g.drop(nextMove, true);
            rating += ratePosition(g)*depth;
        }
        return rating;
    }

    /**
     * Give an integer rating of the current board position
     * @param game Current game
     * @return Rating of position
     */
    private int ratePosition(Game game){
        IBoard<Disc> board = game.getBoard();
        int rating = 0;
        if(Rules.isWin(board, game.getLastMove())){
            if(game.getCurrentPlayer().getDisc() == disc){
                rating += 1000;
            } else {
                rating -= 1000;
            }
        }
        // TODO: More complex rating of position (connected discs, open ends, crossing lines etc.)
        return rating;
    }

    /**
     * Get the index of one of the max values in a list
     * @param moveRatings List of values
     * @param max Max value of list
     * @return Index of a randomly chosen max value
     */
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
