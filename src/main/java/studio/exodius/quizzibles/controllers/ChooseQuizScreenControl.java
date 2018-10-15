package studio.exodius.quizzibles.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import studio.exodius.quizzibles.ViewAdapter;
import studio.exodius.quizzibles.model.Quiz;
import studio.exodius.quizzibles.utility.Document;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class description
 *
 * @author Densorius
 * @version 1.0.0
 * @since 15-10-2018
 */
@Document("documents/choose_quiz_screen.fxml")
public class ChooseQuizScreenControl extends ViewAdapter {

    @FXML private Button backButton;
    @FXML private Button openFolderButton;
    @FXML private Button startButton;
    @FXML private AnchorPane quizList;
    @FXML private VBox quizDetails;
    @FXML private Label quizName;
    @FXML private Label quizAuthor;
    @FXML private Label quizVersion;
    @FXML private Label quizHighscore;

    /** The currently selected quizz */
    private Quiz selected;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backButton.setOnMouseClicked(e -> window.openView(new HomeScreenControl()));
        startButton.setOnMouseClicked(e -> window.openView(new QuizControl(selected)));
        openFolderButton.setOnMouseClicked(e -> {
	        try {
		        Desktop.getDesktop().open(window.getApp().getStorageDir());
	        } catch(IOException ex) {
		        throw new RuntimeException("Failed to open explorer", ex);
	        }
        });

        // Add all quizzes to the list
        for(Quiz quiz : window.getApp().quizList) {
        	Button button = new Button(quiz.name);
        	quizList.getChildren().add(button);

        	button.setOnMouseClicked(e -> selectQuiz(quiz));
        }
    }

	/**
	 * Select a quiz and update the details pane
	 *
	 * @param quiz Quiz to select
	 */
	private void selectQuiz(Quiz quiz) {
	    this.selected = quiz;

	    // Update details pane
		quizName.setText(quiz.name);
		quizVersion.setText("Versie: " + quiz.version);
		quizAuthor.setText("Autheur: " + quiz.author);
		quizHighscore.setText("Highscore: " + 0);

	    // Reveal details pane
	    if(!quizDetails.isVisible()) {
	    	quizDetails.setVisible(true);
	    }
    }
}
