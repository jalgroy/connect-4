package inf101.v18.sem2.datastructures;

public interface IBoard<T> {
    IBoard copy();

    boolean add(int column, T t);

    boolean remove(int column);

    int getWidth();

    int getHeight();

    T get(int x, int y);
}
