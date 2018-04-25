package inf101.v18.sem2.datastructures;

public class Board<T> implements IBoard<T> {
    private int width;
    private int height;
    private Grid<T> grid;

    public Board(int width, int height){
        this.width = width;
        this.height = height;
        grid = new Grid<>(width,height);
    }

    public IBoard copy(){
        Board<T> b = new Board<>(width,height);
        b.grid = new Grid<>(width, height);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                b.grid.set(i,j, grid.get(i,j));
            }
        }
        return b;
    }
    
    public boolean add(int column, T t){
        int row = -1;
        int i = 0;
        while (i < height && grid.get(column, i) == null){
            row = i++;
        }
        if(row == -1) return false;
        grid.set(column, row, t);
        return true;
    }

    public boolean remove(int column){
        for (int i = 0; i < height; i++) {
            if(get(column, i) != null){
                grid.set(column, i, null);
                return true;
            }
        }
        return false;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public T get(int x, int y){
        return grid.get(x,y);
    }

}
