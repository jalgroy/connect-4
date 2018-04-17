package inf101.v18.sem2.datastructures;

public class Grid<T> implements IGrid<T> {
    private int width;
    private int height;
    private T[][] arr;

    @SuppressWarnings("unchecked")
    public Grid(int width, int height){
        this.width = width;
        this.height = height;
        arr = (T[][]) new Object[width][height];
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public T get(int x, int y) {
        return arr[x][y];
    }

    public void set(int x, int y, T t){
        arr[x][y] = t;
    }
}
