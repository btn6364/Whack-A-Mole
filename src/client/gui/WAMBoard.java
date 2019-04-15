package client.gui;


import java.util.LinkedList;
import java.util.List;
/**
 * The model for the Whack-A-Mole game.
 *
 * @author Jared Sullivan (jms8376@rit.edu)
 * @author Bao Nguyen (btn6364@rit.edu)
 */
public class WAMBoard{
    /** the number of rows */
    public int rows = 10;

    /** the number of columns */
    public int cols = 10;

    /** the observers of this model */
    private List<Observer<WAMBoard>> observers;

    /** the board */
    private int[][] board;

    /**
     * The view calls this method to add themselves as an observer of the model.
     *
     * @param observer the observer
     */
    public void addObserver(Observer<WAMBoard> observer){
        this.observers.add(observer);
    }

    /** when the model changes, the observers are notified via their update() method */
    public void alertObservers(){
        for (Observer<WAMBoard> obs: this.observers){
            obs.update(this);
        }
    }

    public void setSize(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
    }

    /**
     * Create a model for the game.
     */
    public WAMBoard(){
        this.observers = new LinkedList<>();
        this.board = new int[cols][rows];
        for(int col = 0; col < cols; col++){
            for (int row = 0; row < rows; row++){
                board[col][row] = 0;
            }
        }

    }

    /**
     * What is at this square?
     * @param row row number of square
     * @param col column number of square
     * @return the value at the given location
     */
    public int getContents(int col, int row){
        return this.board[col][row];
    }

    /**
     * Set the value to a given location
     * @param col column number of square
     * @param row row number of square
     * @param value the value to set to the location
     */
    public void setContents(int col, int row, int value){
        this.board[col][row] = value;
    }

    /**
     * The user they may close at any time
     */
    public void close() {
        alertObservers();
    }

}
