package inf101.v18.sem2.player;

import inf101.v18.sem2.game.objects.Disc;

/**
 * Player with a name and a disc
 */
public interface IPlayer {
    /**
     * @return Player name
     */
    String getName();

    /**
     * @return Player disc
     */
    Disc getDisc();
}