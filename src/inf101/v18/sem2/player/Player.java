package inf101.v18.sem2.player;

import inf101.v18.sem2.Disc;

public class Player implements IPlayer {
    private String name;
    private Disc disc;

    public Player(String name, Disc disc){
        this.name = name;
        this.disc = disc;
    }

    @Override
    public Disc getDisc(){
        return disc;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

}
