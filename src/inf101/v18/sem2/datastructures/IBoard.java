package inf101.v18.sem2.datastructures;

/**
 * "Connect four"-like board. Acts like a set of stacks, can only add elements to columns, each column behaves like a stack.
 * @param <T> Type to store on the board.
 */
public interface IBoard<T> {
    /**
     * Deep copy of <code>IBoard</code>
     * @return New <code>IBoard</code> with the same state as this.
     */
    IBoard copy();

    /**
     * Add element to board
     * @param column Column to add the element to
     * @param t Element
     * @return true if successful
     */
    boolean add(int column, T t);

    /**
     * Remove element from board
     * @param column Column to remove from
     * @return true if successful
     */
    boolean remove(int column);

    /**
     * @return Width of the board
     */
    int getWidth();

    /**
     * @return Height of the board
     */
    int getHeight();

    /**
     * Get element from board
     * @param x Column
     * @param y Row
     * @return Element at (x,y)
     */
    T get(int x, int y);
}
