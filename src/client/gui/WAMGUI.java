package client.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

/**
 * A JavaFX GUI for the networked Whack-A-Mole game.
 *
 * @author Jared Sullivan (jms8376@rit.edu)
 * @author Bao Nguyen (btn6364@rit.edu)
 */
public class WAMGUI extends Application implements Observer<WAMBoard> {
    /**
     * a hole image.
     */
    private Image holeImage = new Image(getClass().getResourceAsStream("holeimage.png"));

    /**
     * a MoleThread image.
     */
    private Image moleImage = new Image(getClass().getResourceAsStream("moleimage.png"));

    /**
     * An array of Button
     */
    private Button[][] buttons;

    /**
     * the model
     */
    private WAMBoard board;

    /**
     * connection to network interface to server
     */
    private WAMClient serverConn;

    /**
     * the stage of game
     */
    private Stage stage;


    /**
     * Do all the set up for the GUI.
     * @throws Exception the Exception if the server goes wrong.
     */
    @Override
    public void init() throws Exception {
        try {
            List<String> args = getParameters().getRaw();
            String host = args.get(0);
            int port = Integer.parseInt(args.get(1));

            this.serverConn = new WAMClient(host, port);

            this.board = this.serverConn.getBoard();

            this.board.addObserver(this);


        } catch (ArrayIndexOutOfBoundsException |
                NumberFormatException e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Construct the layout for the game.
     *
     * @param stage container (window) in which to render the GUI
     * @throws Exception if there is a problem
     */
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        // get the command line args
        List<String> args = getParameters().getRaw();

        // get rows and cols from the server's WELCOME message
        int colmsg = serverConn.getColumn();
        int rowmsg = serverConn.getRow();
        GridPane gridPane = new GridPane();
        buttons = new Button[colmsg][rowmsg];


        for (int row = 0; row < rowmsg; row++) {
            for (int col = 0; col < colmsg; col++) {
                Button button = new Button();
                ImageView moleImageView = new ImageView(holeImage);
                button.setGraphic(moleImageView);
                buttons[col][row] = button;
                gridPane.add(button, col, row);
                int moleNumber = row * colmsg + col;
                button.setOnAction(event -> serverConn.whack(moleNumber));
            }
        }

        Text score = new Text("SCORE: " + String.valueOf(this.serverConn.getMyScore()));
        gridPane.add(score, 0, rowmsg);


        Scene scene = new Scene(gridPane);
        stage.setTitle("WAMGUI");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        this.serverConn.startListener();
    }

    /**
     * Do all the GUI update
     *
     * @param board the model of the game
     */
    private void refresh(WAMBoard board) {
        this.board = board;
        GridPane gridPane = new GridPane();
        for (int r = 0; r < serverConn.getRow(); r++) {
            for (int c = 0; c < serverConn.getColumn(); c++) {
                Image image = holeImage;
                int content = board.getContents(c, r);
                if (content == 1) {
                    image = moleImage;
                } else {
                    image = holeImage;
                }
                ImageView view = new ImageView(image);
                buttons[c][r].setGraphic(view);
                gridPane.add(buttons[c][r], c, r);
            }
        }

        gridPane.add(new Text("SCORE: " + this.serverConn.getMyScore()), 0, serverConn.getRow()); //add score
        Scene scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.show();
        stage.setAlwaysOnTop(true);
        stage.setAlwaysOnTop(false);

        int result = serverConn.getGameResult();
        switch (result){
            case WAMClient.RESULT_WON:
                showDialog("You won! Yay!");
                break;
            case WAMClient.RESULT_LOST:
                showDialog("You lost! Boo!");
                break;
            case WAMClient.RESULT_TIED:
                showDialog("It's a tie! Good try!");
                break;
            case WAMClient.RESULT_ERROR:
                showError("ERROR!!!!!");
                break;
        }
    }

    /**
     * Show the pop-up information about the game result
     * @param message the result message
     */
    private void showDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Show the pop-up information about the error
     * @param message the error message
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Game Over!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Notify the observer when the game ends.
     */
    private synchronized void endGame() {
        this.notify();
    }

    /**
     * Called by the model, client.ConnectFourBoard, whenever there is a state change
     * that needs to be updated by the GUI.
     *
     * @param board the board of the game
     */
    @Override
    public void update(WAMBoard board) {
        if (Platform.isFxApplicationThread()) {
            this.refresh(board);
        } else {
            Platform.runLater(() -> this.refresh(board));
        }
    }

    /**
     * The main method expects the host and port.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java host port");
            System.exit(-1);
        } else {
            Application.launch(args);
        }
    }
}
