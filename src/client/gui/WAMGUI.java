package client.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import server.WAM;

import java.util.List;
import java.util.Random;

/**
 * @author Jared Sullivan (jms8376@rit.edu)
 * @author Bao Nguyen (btn6364@rit.edu)
 */
public class WAMGUI extends Application implements Observer<WAMBoard>{
    private Image holeImage = new Image(getClass().getResourceAsStream("holeimage.png"));
    private Image moleImage = new Image(getClass().getResourceAsStream("moleimage.png"));

    private Button[][] buttons;

    private WAMBoard board;

    private WAMClient serverConn;

    private Stage stage;

    public void init() throws Exception
    {
        try {
            List<String> args = getParameters().getRaw();
            String host = args.get(0);
            int port = Integer.parseInt(args.get(1));
            //TODO getBoardSize(server)
            this.board = new WAMBoard();

            this.board.addObserver(this);

            this.serverConn = new WAMClient(host, port, this.board);
        } catch (ArrayIndexOutOfBoundsException |
                NumberFormatException e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }

    public int[] getBoardSize()
    {

        return null;
    }


    public void start(Stage stage) throws Exception {
            this.stage = stage;

            // get the command line args
            List<String> args = getParameters().getRaw();

            //TODO get rows and cols from the server's WELCOME message
            int COLS = WAMBoard.COLS;
            int ROWS = WAMBoard.ROWS;

            GridPane gridPane = new GridPane();
            buttons = new Button[COLS][ROWS];


            for (int row = 0; row < ROWS; row++)
            {
                for (int col = 0; col < COLS; col++)
                {
                    Button button = new Button();
                    ImageView moleImageView = new ImageView(holeImage);
                    button.setGraphic(moleImageView);
                    buttons[col][row] = button;
                    gridPane.add(button, col, row);
                }
            }

            Text time = new Text("TIME: ");
            Text score = new Text("SCORE: ");
            gridPane.add(time, COLS-1, ROWS);
            gridPane.add(score, 0, ROWS);



            Scene scene = new Scene(gridPane);
            stage.setTitle("WAMGUI");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            this.serverConn.startListener();


    }


    private void refresh(WAMBoard board){
        this.board = board;
        GridPane gridPane = new GridPane();
        for (int r = 0; r < WAMBoard.ROWS; r++){
            for (int c = 0; c < WAMBoard.COLS; c++){
                Image image = holeImage;
                int content = board.getContents(c, r);
                if (content == 1){
                    image = moleImage;
                } else {
                    image = holeImage;
                }
                ImageView view = new ImageView(image);
                gridPane.add(view, c, r);

            }
        }
        Scene scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.show();
        stage.setAlwaysOnTop(true);
        stage.setAlwaysOnTop(false);
    }

    private synchronized void endGame(){
        this.notify();
    }

    @Override
    public void update(WAMBoard board){
        if (Platform.isFxApplicationThread()){
            this.refresh(board);
        } else {
            Platform.runLater(() -> this.refresh(board));
        }
    }


    public static void main(String[] args)
    {
        if (args.length != 2)
        {
            System.out.println("Usage: java host port");
            System.exit(-1);
        } else
        {
            Application.launch(args);
        }
    }
}
