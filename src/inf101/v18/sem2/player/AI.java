package inf101.v18.sem2.player;

import inf101.v18.sem2.Board;
import inf101.v18.sem2.Disc;
import inf101.v18.sem2.Game;
import inf101.v18.sem2.Rules;

import java.util.List;
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
        List<Integer> legalMoves = Rules.getLegalMoves(game.getBoard());
        int bestMove = legalMoves.get(0);
        int maxRating = -10000;
        for(Integer move : legalMoves){
            Game g = game.copy();
            g.getBoard().drop(move, disc);
            int rating = ratePosition(g.getBoard());
            if(rating > maxRating){
                bestMove = move;
                maxRating = rating;
            }
        }
        return bestMove;
    }

    private int ratePosition(Board board){
        int rating = 0;
        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = board.getHeight()-1; j >= 0; j--) {
                if(!board.getSlot(i,j).isEmpty()){
                    if(board.getSlot(i,j).getDisc() == disc){
                        rating += Rules.countNeighbours(board,i,j);
                    } else {
                        rating -= Rules.countNeighbours(board,i,j);
                    }
                } else {
                    break;
                }
            }
        }
        if(Rules.isWin(board, game.getLastMove())){
            if(game.getCurrentPlayer().getDisc() == disc){
                rating += 1000;
            } else {
                rating -= 1000;
            }
        }
        return rating;
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
