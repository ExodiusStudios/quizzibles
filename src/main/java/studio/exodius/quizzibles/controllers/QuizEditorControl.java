package studio.exodius.quizzibles.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.TilePane;
import studio.exodius.quizzibles.ViewAdapter;
import studio.exodius.quizzibles.controllers.quiz.WipQuestion;
import studio.exodius.quizzibles.controllers.quiz.WipQuiz;
import studio.exodius.quizzibles.model.Option;
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
    @FXML private TextField questionTextField;
    @FXML private TextField durationTextField;
    @FXML private TilePane questionsTilePane;
    @FXML private ComboBox<String> rightAnswerComboBox;
    @FXML private Button newQuestionButton;
    @FXML private Button newAnswerButton;
    @FXML private Button quizOptionsButton;
    @FXML private Menu FileMenu;

    private WipQuiz quiz;

    QuizEditorControl(Quiz quiz) {
        if (quiz == null) {
            this.quiz = new WipQuiz();

            WipQuestion question = new WipQuestion("");
            Option optionA = new Option();
            Option optionB = new Option();

            optionA.title = "True";
            optionB.title = "False";

            question.optionsList.add(optionA);
            question.optionsList.add(optionB);

        } else {

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
