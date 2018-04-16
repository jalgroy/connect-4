package inf101.v18.sem2;

public class Grid implements IGrid<Slot> {
    private int width;
    private int height;
    private Slot[][] slotArray;


    public Grid(int width, int height){
        this.width = width;
        this.height = height;
        slotArray = new Slot[width][height];
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
    public Slot get(int x, int y) {
        return slotArray[x][y];
    }

    public void set(int x, int y, Slot slot){
        slotArray[x][y] = slot;
    }
}
