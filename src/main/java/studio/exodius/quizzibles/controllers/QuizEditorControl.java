package studio.exodius.quizzibles.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
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

    @FXML private ListView<String> questionsList;
    @FXML private ScrollPane questionScrollPane;
    @FXML private AnchorPane questionAnchorPane;

    private Quiz quiz;

    QuizEditorControl(Quiz quiz) {
        this.quiz = quiz;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        questionScrollPane.setFitToHeight(true);
        questionScrollPane.setFitToWidth(true);
    }
}
