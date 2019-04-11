package client.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

/**
 * @author Jared Sullivan (jms8376@rit.edu)
 */
public class WAMGUI extends Application //implements Observer<type>
{
    private Button[][] buttons;

    public void init() throws Exception
    {
        List<String> args = getParameters().getRaw();
        String host = args.get(0);
        int port = Integer.parseInt(args.get(1));


        //TODO getBoardSize(server)


    }

    public int[] getBoardSize()
    {
        return null;
    }


    public void start(Stage stage)
    {
        // get the command line args
        List<String> args = getParameters().getRaw();

        //TODO get rows and cols from the server's WELCOME message
        int COLS = 3;
        int ROWS = 3;

        GridPane gridPane = new GridPane();
        buttons = new Button[COLS][ROWS];

        Image mole = new Image(getClass().getResourceAsStream("moleimage.png"));

        for (int row = 0; row < ROWS; row++)
        {
            for (int col = 0; col < COLS; col++)
            {
                Button button = new Button();
                ImageView moleImageView = new ImageView(mole);
                button.setGraphic(moleImageView);
                buttons[col][row] = button;
                gridPane.add(button, col, row);
            }
        }

        Text time = new Text("TIME: ");
        Text score = new Text("SCORE: ");
        gridPane.add(time, COLS-1, ROWS);
        gridPane.add(score, 0, ROWS);


        // TODO
//        for (int i = 0; i < COLS; i++)
//        {
//            int x = i;
//            Button button = buttons[i];
//            button.setOnAction(event ->
//            {
//                client1.sendMove(x);
//            });
//            button.setDisable(true);
//        }

        Scene scene = new Scene(gridPane);
        stage.setTitle("WAMGUI");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();


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
