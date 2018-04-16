package inf101.v18.sem2;

public class Board {
    private int width;
    private int height;
    private Grid grid;

    public Board(int width, int height){
        this.width = width;
        this.height = height;
        grid = new Grid(width,height);
        initGrid();
    }
    
    private void initGrid(){
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid.set(i,j,new Slot(i,j));
            }
        }
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
