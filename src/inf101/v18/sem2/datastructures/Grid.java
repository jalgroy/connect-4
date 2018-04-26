package inf101.v18.sem2.datastructures;

import java.util.Arrays;
import java.util.Objects;

public class Grid<T> implements IGrid<T> {
    private int width;
    private int height;
    private T[][] arr;

    /**
     * @param width Columns
     * @param height Rows
     */
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

    @Override
    public void set(int x, int y, T t){
        arr[x][y] = t;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grid<?> grid = (Grid<?>) o;
        for (int i = 0; i < arr.length; i++) {
            if(!Arrays.equals(arr[i], grid.arr[i])){
                return false;
            }
        }
        return width == grid.width &&
                height == grid.height;
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(width, height);
        result = 31 * result + Arrays.hashCode(arr);
        return result;
    }
}
