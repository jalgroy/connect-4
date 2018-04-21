package inf101.v18.sem2.player;

import inf101.v18.sem2.Game;

public interface IAI extends IPlayer {
    int getMove(Game game, int depth);
}
