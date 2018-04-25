package inf101.v18.sem2;

import inf101.v18.sem2.datastructures.Board;
import inf101.v18.sem2.datastructures.IBoard;

import java.util.ArrayList;
import java.util.List;

public class Rules {
    public static boolean isLegalMove(IBoard<Disc> board, int column){
        return board.get(column, 0) == null;
    }

    /**
     * @param board - Connect four board
     * @return true if the board has 4 connected pieces
     */
    public static boolean isWin(IBoard<Disc> board, int lastMove){
        int x = lastMove;
        int y = 0;
        for (int i = 0; i < board.getHeight(); i++) {
            if(board.get(x,i) != null){
                y = i;
                break;
            }
        }

        Disc disc = board.get(x,y);

        // Horizontal
        int inARow = 1;
        for (int dx = -3; dx <= 3; dx++) {
            if(dx == 0) continue;
            if(x+dx >= 0 && x+dx < board.getWidth()){
                if(board.get(x+dx,y) == disc){
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
                if(board.get(x,y+dy) == disc){
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
                if(board.get(x+d,y+d) == disc){
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
                if(board.get(x-d,y+d) == disc){
                    inARow++;
                } else {
                    inARow = 1;
                }
                if(inARow == 4) return true;
            }
        }

        return false;
    }

    public static List<Integer> getLegalMoves(IBoard<Disc> board){
        List<Integer> moves = new ArrayList<>();
        for (int i = 0; i < board.getWidth(); i++) {
            if(isLegalMove(board, i)) moves.add(i);
        }
        return moves;
    }

    public static int[] getWinLocation(IBoard<Disc> board, int lastMove){
        if(!isWin(board, lastMove)){
            throw new IllegalStateException("Game is not won");
        }
        // TODO
        return null;
    }

    public static int countNeighbours(IBoard<Disc> board, int x, int y){
        int count = 0;
        Disc disc = board.get(x,y);
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if(dx == 0 && dy == 0) continue;
                if(x+dx >= 0 && x+dx < board.getWidth() && y+dy >= 0 && y+dy < board.getHeight()){
                    if(board.get(x+dx,y+dy) == disc) count++;
                }
            }
        }
        return count;
    }

    public static boolean isDraw(IBoard<Disc> board, int lastMove){
        for (int i = 0; i < board.getWidth(); i++) {
            if(board.get(i,0) == null) return false;
        }
        return !isWin(board, lastMove);
    }

    public static int getRow(IBoard<Disc> board, int column){
        int row = -1;
        int i = 0;
        while (i < board.getHeight() && board.get(column, i) == null){
            row = i++;
        }
        if(row == -1){
            throw new IllegalArgumentException("Illegal move");
        }
        return row;
    }
}
