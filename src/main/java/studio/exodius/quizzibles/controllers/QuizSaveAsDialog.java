package studio.exodius.quizzibles.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
@Document("documents/quiz_save_as_dialog.fxml")
public class QuizSaveAsDialog extends ViewAdapter {

    @FXML private TextField saveTextField;
    @FXML private Button cancelButton;
    @FXML private Button saveButton;

    private String fileName;
    private boolean canceled = true;
    private Quiz quiz;

    public QuizSaveAsDialog(Quiz quiz) {
        this.fileName = quiz.name;
        this.quiz = quiz;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (fileName != null) {
            saveTextField.setText(fileName);
            saveTextField.positionCaret(fileName.length());
        }

        saveTextField.setOnKeyReleased(e -> {
            if (!saveTextField.getText().isEmpty()) {
                fileName = saveTextField.getText();
            } else {
                fileName = quiz.name;
            }
        });

        window.getStage().setResizable(false);
        window.getStage().initModality(Modality.APPLICATION_MODAL);

        saveButton.setOnAction(e -> {
            canceled = false;
            window.close();
        });

        cancelButton.setOnAction(e -> window.close());
    }

    public boolean isCanceled() {
        return canceled;
    }

    public String getFileName() {
        return fileName;
    }
}
