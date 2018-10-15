package studio.exodius.quizzibles.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.TilePane;
import studio.exodius.quizzibles.View;
import studio.exodius.quizzibles.Window;
import studio.exodius.quizzibles.utility.Document;
import studio.exodius.quizzibles.ViewAdapter;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Home screen. The player can play and create a quiz. The player can also exit the game from here.
 *
 * @author Densorius
 * @version 1.0.0
 * @since 15-10-2018
 */
@Document("documents/home_screen.fxml")
public class HomeScreenControl extends ViewAdapter {

	@FXML private Label question;
	@FXML private Label number;
	@FXML private Label score;
	@FXML private ProgressBar progress;
	@FXML private TilePane optionArea;

    @Override
    public void setup(View previous, Window window) {
        this.window = window;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
