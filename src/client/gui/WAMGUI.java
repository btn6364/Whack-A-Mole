package client.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.Observer;

/**
 * @author Jared Sullivan (jms8376@rit.edu)
 */
public class WAMGUI extends Application //implements Observer<type>
{
    private Button[][] grid;

    public void init()
    {

    }

    public void start(Stage stage)
    {
        // get the command line args
        List<String> args = getParameters().getRaw();

        int COLS = Integer.parseInt(args.get(1));
        int ROWS = Integer.parseInt(args.get(2));


        GridPane gridPane = new GridPane();
        grid = new Button[COLS][ROWS];

        Image mole = new Image(getClass().getResourceAsStream("moleimage.png"));

        for (int row = 0; row < ROWS; row++)
        {
            for (int col = 0; col < COLS; col++)
            {
                Button button = new Button();
                ImageView moleImageView = new ImageView(mole);
                button.setGraphic(moleImageView);
                grid[col][row] = button;
                gridPane.add(button, col, row);
            }
        }

        Scene scene = new Scene(gridPane);
        stage.setTitle("WAMGUI");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();


    }







    public static void main(String[] args)
    {
        if (args.length != 5)
        {
            System.out.println("Usage: java WAMGUI port columns rows players game-duration-seconds");
            System.exit(-1);
        } else
        {
            Application.launch(args);
        }
    }
}
