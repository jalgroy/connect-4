package inf101.v18.sem2.player;

import inf101.v18.sem2.Game;

/**
 * AI Player
 */
public interface IAI extends IPlayer {
    /**
     * Get an automatic move from the AI
     * @param game Current game
     * @return column to drop disc in
     */
    int getMove(Game game);
}
