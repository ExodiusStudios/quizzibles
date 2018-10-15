package studio.exodius.quizzibles.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import studio.exodius.quizzibles.ViewAdapter;
import studio.exodius.quizzibles.utility.Document;

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

    @FXML private Button btnPlayQuiz;
    @FXML private Button btnCreateQuiz;
    @FXML private Button btnExitGame;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Exit the game
        btnPlayQuiz.setOnMouseClicked(e -> window.openView(new ChooseQuizScreenControl()));
        btnExitGame.setOnMouseClicked(e -> window.close());
    }
}
