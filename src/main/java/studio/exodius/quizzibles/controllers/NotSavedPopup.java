package studio.exodius.quizzibles.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
@Document("documents/not_saved_popup.fxml")
public class NotSavedPopup extends ViewAdapter {

    @FXML private Label messageLabel;
    @FXML private Button cancelButton;
    @FXML private Button dontSaveButton;
    @FXML private Button saveButton;

    private Quiz quiz;
    private boolean saveConfirmed = false;
    private boolean canceled = true;

    public NotSavedPopup(Quiz quiz) {
        this.quiz = quiz;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        window.getStage().setResizable(false);
        window.getStage().initModality(Modality.APPLICATION_MODAL);

        messageLabel.setText(String.format("Quiz '%s' is not saved.", quiz.name));

        dontSaveButton.setOnAction(e -> {
            saveConfirmed = false;
            canceled = false;
            window.close();
        });
        saveButton.setOnAction(e -> {
            saveConfirmed = true;
            canceled = false;
            window.close();
        });

        cancelButton.setOnAction(e -> {
            canceled = true;
            window.close();
        });
    }

    public boolean isSaveConfirmed() {
        return saveConfirmed;
    }

    public boolean isCanceled() {
        return canceled;
    }
}
