package studio.exodius.quizzibles.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import studio.exodius.quizzibles.ViewAdapter;
import studio.exodius.quizzibles.Window;
import studio.exodius.quizzibles.model.Quiz;
import studio.exodius.quizzibles.utility.Document;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
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
    @FXML private Button newQuizButton;
    @FXML private ListView<String> quizList;
    @FXML private VBox quizDetails;
    @FXML private Label quizName;
    @FXML private Label quizAuthor;
    @FXML private Label quizVersion;
    @FXML private Label header;

    /** The currently selected quizz */
    private Quiz selected;

    private boolean create;

    ChooseQuizScreenControl(boolean create) {
		this.create = create;
	}

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // restore the width and height
        window.getWindow().setHeight(window.getWindow().getHeight());
        window.getWindow().setWidth(window.getWindow().getWidth());

    	// make the newQuizButton visible and attach a click event listener on it only
		// if the user wants to create/edit a quiz
    	if (create) {

    		newQuizButton.setVisible(true);
    		newQuizButton.setDisable(false);
    		newQuizButton.setOnMouseClicked(e -> window.openView(new QuizEditorControl(null))); // create new quiz

			header.setText("Quiz selection");

			startButton.setText("Edit");

		} else if (window.getApp().quizList.isEmpty()) {
    		openNoQuizPopup();
		}

        backButton.setOnMouseClicked(e -> window.openView(new HomeScreenControl()));
        startButton.setOnMouseClicked(e -> {
        	if (create) {
        		window.openView(new QuizEditorControl(selected));
			} else {
		        Collections.shuffle(selected.questions);
				window.openView(new QuizControl(selected));
			}
        });
        openFolderButton.setOnMouseClicked(e -> {
	        try {
		        Desktop.getDesktop().open(window.getApp().getStorageDir());
	        } catch(IOException ex) {
		        throw new RuntimeException("Failed to open explorer", ex);
	        }
        });

        // Add all quizzes to the list
        for(Quiz quiz : window.getApp().quizList) {
        	quizList.getItems().add(quiz.name);
        }

        quizList.setOnMouseClicked(e -> {
            // Get the selected quiz
        	for (Quiz quiz : window.getApp().quizList) {
        		if (quizList.getSelectionModel().getSelectedItem().equalsIgnoreCase(quiz.name)) {
        			selectQuiz(quiz);
				}
			}
		});
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
		quizAuthor.setText("Auteur: " + quiz.author);

	    // Reveal details pane
	    if(!quizDetails.isVisible()) {
	    	quizDetails.setVisible(true);
	    }
    }

	/**
	 * Open the popup stating the user does not have a quiz installed
	 */
	private void openNoQuizPopup() {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setResizable(false);

		Window win = new Window(window.getApp(), stage, "No quiz installed");
		win.openView(new NoQuizInstalledPopupControl());

		// go back when the quizlist is empty when the popup closes
		stage.onHiddenProperty().setValue(e -> {
			if (window.getApp().quizList.isEmpty()) {
				window.openView(new HomeScreenControl());
			}
		});
	}

}
