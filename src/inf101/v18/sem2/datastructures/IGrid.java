package inf101.v18.sem2.datastructures;

/**
 * Generic grid, stores elements accessed with x-y coordinates.
 * @param <T> Type to store
 */
public interface IGrid<T> {
    /**
     * @return Width (number of columns)
     */
    int getWidth();

    /**
     * @return Height (number of rows)
     */
    int getHeight();

    /**
     * Get element from grid
     * @param x x-position
     * @param y y-position
     * @return Element at position (x,y)
     */
    T get(int x, int y);

    /**
     * Set element
     * @param x x-position
     * @param y y-position
     * @param t Element to set at (x,y)
     */
    void set(int x, int y, T t);
}
