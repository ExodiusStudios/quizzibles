package studio.exodius.quizzibles.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import studio.exodius.quizzibles.ViewAdapter;
import studio.exodius.quizzibles.Window;
import studio.exodius.quizzibles.utility.Document;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Macjuul
 * @version 1.0.0
 */
@Document("documents/highscore_view.fxml")
public class HighscoreControl extends ViewAdapter  {

	@FXML private Button finish;
	@FXML private Label title;

	private Window mainWindow;
	private int score;

	public HighscoreControl(Window mainWindow, int score) {
		this.mainWindow = mainWindow;
		this.score = score;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.title.setText("You scored " + score + " points");

		this.finish.setOnMouseClicked(e -> {
			window.close();

			mainWindow.openView(new ChooseQuizScreenControl(false));
		});
	}

}
