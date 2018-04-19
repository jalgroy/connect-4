package inf101.v18.sem2;

public class Rules {
    public static boolean isLegalMove(Board board, int column){
        return board.getSlot(column, 0).isEmpty();
    }

    /**
     * @param board
     * @return true if the board has 4 connected pieces
     */
    public static boolean isWin(Board board, int lastMove){
        int x = lastMove;
        int y = 0;
        for (int i = 0; i < board.getHeight(); i++) {
            if(!board.getSlot(x,i).isEmpty()){
                y = i;
                break;
            }
        }

        Disc disc = board.getSlot(x,y).getDisc();

        // Horizontal
        int inARow = 1;
        for (int dx = -3; dx <= 3; dx++) {
            if(dx == 0) continue;
            if(x+dx >= 0 && x+dx < board.getWidth()){
                if(board.getSlot(x+dx,y).getDisc() == disc){
                    inARow++;
                } else {
                    inARow = 1;
                }
                if(inARow == 4) return true;
            }
        }

        // Vertical
        inARow = 1;
        for (int dy = -3; dy <= 3; dy++) {
            if(dy == 0) continue;
            if(y+dy >= 0 && y+dy < board.getHeight()){
                if(board.getSlot(x,y+dy).getDisc() == disc){
                    inARow++;
                } else {
                    inARow = 1;
                }
                if(inARow == 4) return true;
            }
        }

        // Diagonal: up-right
        inARow = 1;
        for (int d = -3; d <= 3; d++) {
            if(d == 0) continue;
            if(x+d >= 0 && x+d < board.getWidth() && y+d >= 0 && y+d < board.getHeight()){
                if(board.getSlot(x+d,y+d).getDisc() == disc){
                    inARow++;
                } else {
                    inARow = 1;
                }
                if(inARow == 4) return true;
            }
        }

        // Diagonal: up-left
        inARow = 1;
        for (int d = -3; d <= 3; d++) {
            if(d == 0) continue;
            if(x-d >= 0 && x-d < board.getWidth() && y+d >= 0 && y+d < board.getHeight()){
                if(board.getSlot(x-d,y+d).getDisc() == disc){
                    inARow++;
                } else {
                    inARow = 1;
                }
                if(inARow == 4) return true;
            }
        }

        return false;
    }

    public static boolean isDraw(Board board, int lastMove){
        for (int i = 0; i < board.getWidth(); i++) {
            if(board.getSlot(i,0).isEmpty()) return false;
        }
        return !isWin(board, lastMove);
    }
}
