package server;

public class WAM {
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

    public void setUp(int moleNumber){
        board[moleNumber/cols][moleNumber%cols] = Case.UP;
    }


    public void setDown(int moleNumber){
        board[moleNumber/cols][moleNumber%cols] = Case.DOWN;
    }

    public boolean isUp(int moleNumber)
    {
        return (board[moleNumber / cols][moleNumber % cols].equals(Case.UP));
    }


    public void main(String[] args){



    }

}
