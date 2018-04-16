package inf101.v18.sem2;

public interface IGrid<T> {
    int getWidth();
    int getHeight();
    T get(int x, int y);
}
