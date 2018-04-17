package inf101.v18.sem2;

import inf101.v18.sem2.datastructures.Grid;
import inf101.v18.sem2.datastructures.Slot;

public class Board {
    private int width;
    private int height;
    private Grid<Slot> grid;

    public Board(int width, int height){
        this.width = width;
        this.height = height;
        grid = new Grid<>(width,height);
        initGrid();
    }
    
    private void initGrid(){
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid.set(i,j,new Slot(i,j));
            }
        }
    }
    
    public boolean drop(int column, Disc disc){
        int row = -1;
        int i = 0;
        while (i < height && getSlot(column, i).isEmpty()){
            row = i++;
        }
        if(row == -1) return false;
        getSlot(column, row).setDisc(disc);
        return true;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public Slot getSlot(int x, int y){
        return grid.get(x,y);
    }

}
