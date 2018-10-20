package studio.exodius.quizzibles.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import studio.exodius.quizzibles.ViewAdapter;
import studio.exodius.quizzibles.model.Option;
import studio.exodius.quizzibles.model.Question;
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

    private Quiz quiz;

    private int currentQuestion = 0;
    private boolean saved = false;

    QuizEditorControl(Quiz quiz) {
        if (quiz == null) {

            // create new quiz with a empty question and two empty answers
            this.quiz = new Quiz();

            Question question = new Question("");
            Option answerA = new Option("");
            Option answerB = new Option("");

            question.options.add(answerA);
            question.options.add(answerB);

            this.quiz.questions.add(question);

        } else {
            this.quiz = quiz;
            saved = true;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // load the quiz
        for (Question question : quiz.questions) {
            questionsList.getItems().add(question.title);
        }

        populateView();
    }

    /**
     * Modify the elements in the view related to the current question
     */
    private void populateView() {

        // put the current question in the input field
        Question currQuestion = quiz.questions.get(currentQuestion);
        questionTextField.setText(currQuestion.title);
        questionTextField.positionCaret(currQuestion.title.length());

        // add the options
        for (int i = 0; i < currQuestion.options.size(); i++) {
            HBox hBox = new HBox();
            Button delButton = new Button("X");
            TextField textField = new TextField(currQuestion.options.get(i).title);
            //textField.setPrefWidth(200);

            //delButton.setPrefWidth(textField.getPrefWidth());

            int finalI = i;
            delButton.setOnMouseClicked(e -> {
                if (questionsTilePane.getChildren().size() > 2) {
                    questionsTilePane.getChildren().remove(finalI);
                }
            });

            hBox.getChildren().addAll(textField, delButton);
            hBox.setAlignment(Pos.CENTER);

            questionsTilePane.getChildren().add(hBox);
            TilePane.setMargin(hBox, new Insets(10, 0, 0, 10));
        }
//        for (Option option : currQuestion.options) {
//            HBox hBox = new HBox();
//            Button delButton = new Button("-");
//            TextField textField = new TextField(option.title);
//
//            delButton.setOnMouseClicked(e -> {
//                System.out.println("TBD");
//                hBox.getChildren().remove();
//            });
//
//            hBox.getChildren().addAll(delButton, textField);
//
//            questionsTilePane.getChildren().add(hBox);
//            TilePane.setMargin(hBox, new Insets(10, 0, 0, 10));
//        }

    }
}
