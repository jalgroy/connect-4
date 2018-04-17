package inf101.v18.sem2;

public class Rules {
    public static boolean isLegalMove(Board board, int column){
        return board.getSlot(column, 0).isEmpty();
    }

    // TODO: Checks for win and draw
}
