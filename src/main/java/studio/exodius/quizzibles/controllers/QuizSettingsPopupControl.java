package studio.exodius.quizzibles.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
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
 * @since 21-10-2018
 */
@Document("documents/quiz_settings_popup.fxml")
public class QuizSettingsPopupControl extends ViewAdapter {

    @FXML private TextField globalTimeTextField;
    @FXML private TextField maxPointsQuestionTextField;
    @FXML private CheckBox randomizedCheckBox;

    private Quiz quiz;

    QuizSettingsPopupControl(Quiz quiz) {
        this.quiz = quiz;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        globalTimeTextField.setText(Long.toString(quiz.answerDuration));

        globalTimeTextField.setOnKeyReleased(e -> {
            try {
                quiz.answerDuration = Long.parseLong(e.getText());
            } catch (NumberFormatException ex) {
                e.clone();
            }
        });
    }
}
