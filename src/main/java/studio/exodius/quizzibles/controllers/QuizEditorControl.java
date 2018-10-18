package studio.exodius.quizzibles.controllers;

import studio.exodius.quizzibles.ViewAdapter;
import studio.exodius.quizzibles.model.Quiz;
import studio.exodius.quizzibles.utility.Document;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class description
 *
 * @author Densorius
 * @version 1.0.0
 * @since 18-10-2018
 */
@Document("documents/quiz_editor_screen.fxml")
public class QuizEditorControl extends ViewAdapter {

    private Quiz quiz;

    QuizEditorControl(Quiz quiz) {
        this.quiz = quiz;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
