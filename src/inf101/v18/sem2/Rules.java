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
        // TODO: Use lastMove to check for win

        // Horizontal
        Disc last = null;
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 3; j < board.getWidth(); j++) {
                Disc current = board.getSlot(j,i).getDisc();
                if(current == null) continue;
                boolean fourEquals = true;
                for (int k = 1; k < 4; k++) {
                    if(board.getSlot(j-k, i).getDisc() != current){
                        fourEquals = false;
                        break;
                    }
                }
                if(fourEquals) return true;
            }
        }

        // Vertical
        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 3; j < board.getHeight(); j++) {
                Disc current = board.getSlot(i,j).getDisc();
                if(current == null) continue;
                boolean fourEquals = true;
                for (int k = 1; k < 4; k++) {
                    if(board.getSlot(i, j-k).getDisc() != current){
                        fourEquals = false;
                        break;
                    }
                }
                if(fourEquals) return true;
            }
        }

        // Diagonals from top
        for (int i = 0; i < board.getWidth(); i++) {
            // top -> down-right
            for (int j = 0; j+i < board.getWidth()-3 && j < board.getHeight()-3; j++) {
                Disc current = board.getSlot(j+i,j).getDisc();
                if(current == null) continue;
                boolean fourEquals = true;
                for (int k = 1; k < 4; k++) {
                    if(board.getSlot(j+i+k,j+k).getDisc() != current){
                        fourEquals = false;
                        break;
                    }

                }
                if(fourEquals) return true;

            }

            // top -> down-left
            for (int j = 0; i-j >= 3 && j < board.getHeight()-3; j++) {
                Disc current = board.getSlot(i-j,j).getDisc();
                if(current == null) continue;
                boolean fourEquals = true;
                for (int k = 1; k < 4; k++) {
                    if(board.getSlot(i-j-k,j+k).getDisc() != current){
                        fourEquals = false;
                        break;
                    }

                }
                if(fourEquals) return true;

            }
        }
        
        // Diagonals from sides
        for (int j = 0; j < board.getHeight(); j++) {
            // Left -> down-right
            for (int i = 0; j+i < board.getHeight()-3 && i < board.getWidth()-3; i++) {
                Disc current = board.getSlot(i,j).getDisc();
                if(current == null) continue;
                boolean fourEquals = true;
                for (int k = 1; k < 4; k++) {
                    if(board.getSlot(i+k,j+i+k).getDisc() != current){
                        fourEquals = false;
                        break;
                    }

                }
                if(fourEquals){
                    System.out.println(i+","+j);
                    return true;
                }
            }

            // Right -> down-left
            for (int i = 0; j+i < board.getHeight()-3 && board.getWidth()-i > 3; i++) {
                Disc current = board.getSlot(board.getWidth()-1-i, j+i).getDisc();
                if(current == null) continue;
                boolean fourEquals = true;
                for (int k = 1; k < 4; k++) {
                    if(board.getSlot(board.getWidth()-1-i-k, j+i+k).getDisc() != current){
                        fourEquals = false;
                        break;
                    }
                }
                if(fourEquals){
                    System.out.println((board.getWidth()-1-i)+","+(j+i));
                    return true;
                }

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
