package studio.exodius.quizzibles.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
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
    @FXML private TextField quizNameTextField;
    @FXML private Label headerLabel;
    @FXML private Button cancelButton;
    @FXML private Button okButton;

    private Quiz quiz;
    private String name;
    private long duration;

    QuizSettingsPopupControl(Quiz quiz) {
        this.quiz = quiz;
        System.out.println(quiz.name);
        duration = quiz.answerDuration / 1000;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (quiz.name != null) {
            quizNameTextField.setText(quiz.name);
            quizNameTextField.positionCaret(quiz.name.length() - 1);
        }

        System.out.println("name: " + quiz.name);
        System.out.println("duration " + quiz.answerDuration);

        globalTimeTextField.setText(Long.toString(quiz.answerDuration / 1000));
        headerLabel.setText("Settings for '" + quiz.name + "'");

        window.getStage().setResizable(false);
        window.getStage().initModality(Modality.APPLICATION_MODAL);

        globalTimeTextField.addEventFilter(KeyEvent.KEY_TYPED, e -> {
            if (e.getCharacter().matches("[\\D]")) {
                e.consume();
            }
        });

        globalTimeTextField.setOnKeyReleased(e -> {
            if (!globalTimeTextField.getText().isEmpty()) {
                duration = Integer.parseInt(globalTimeTextField.getText());
                System.out.println("got text");
            } else {
                duration = 10; // TODO un hardcode this
            }
        });

        quizNameTextField.setOnKeyReleased(e -> {
            name = quizNameTextField.getText();
            headerLabel.setText("Settings for '" + name + "'");
            window.setTitle("Settings for '" + name + "'");
        });

        cancelButton.setOnAction(e -> window.close());
        okButton.setOnAction(e -> {
            quiz.answerDuration = duration * 1000;
            System.out.println("duration " + duration);
            quiz.name = this.name;
            window.close();
        });
    }

    public String getName() {
        return name;
    }

    public long getDuration() {
        return duration;
    }
}
