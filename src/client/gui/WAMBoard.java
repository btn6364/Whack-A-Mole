package client.gui;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Jared Sullivan (jms8376@rit.edu)
 * @author Bao Nguyen (btn6364@rit.edu)
 */
public class WAMBoard{
    public final static int ROWS = 6;

    public final static int COLS = 7;

    private List<Observer<WAMBoard>> observers;

    private int[][] board;

    public void addObserver(Observer<WAMBoard> observer){
        this.observers.add(observer);
    }

    public void alertObservers(){
        for (Observer<WAMBoard> obs: this.observers){
            obs.update(this);
        }
    }



    public WAMBoard(){
        this.observers = new LinkedList<>();

        this.board = new int[COLS][ROWS];
        for(int col = 0; col < COLS; col++){
            for (int row = 0; row < ROWS; row++){
                board[col][row] = 0;
            }
        }

    }

    public int getContents(int row, int col){
        return this.board[row][col];
    }








}
