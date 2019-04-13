package server;

public class WAM {
    public final static int ROWS = 6;
    public final static int COLS = 7;

    private int rows;
    private int cols;
    private Case[][] board;

    public enum Case{
        UP('O'),
        DOWN('X');

        private char symbol;
        private Case (char symbol) {
            this.symbol = symbol;
        }

        public char getSymbol(){
            return symbol;
        }
    }

    public WAM(){
        this(ROWS, COLS);
    }

    public WAM(int rows, int cols){
        this.rows = rows;
        this.cols = cols;

        board = new Case[cols][rows];
        for (int col = 0; col < cols; col++){
            for (int row = 0; row < rows; row++){
                board[col][row] = Case.DOWN;
            }
        }
    }

    public void Up(){
        for (int col = 0; col < cols; col++){
            for (int row = 0; row < rows; row++) {

            }
        }
    }

    public void main(String[] args){



    }

}
