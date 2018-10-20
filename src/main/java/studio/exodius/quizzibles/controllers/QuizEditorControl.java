package studio.exodius.quizzibles.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import studio.exodius.quizzibles.ViewAdapter;
import studio.exodius.quizzibles.model.Option;
import studio.exodius.quizzibles.model.Question;
import studio.exodius.quizzibles.model.Quiz;
import studio.exodius.quizzibles.utility.Document;

import java.net.URL;
import java.util.ArrayList;
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

    private int currentQuestionIndex = 0;
    private Question currentQuestion;
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
            this.quiz.questions.get(currentQuestionIndex).answer = 0;

        } else {
            this.quiz = quiz;
            saved = true;
        }

        currentQuestion = this.quiz.questions.get(currentQuestionIndex);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // load the quiz
        for (Question question : quiz.questions) {
            questionsList.getItems().add(question.title);
        }

        populateView();

        questionTextField.setOnKeyReleased(e -> {
            questionsList.getItems().set(currentQuestionIndex, questionTextField.getText());
        });

        newAnswerButton.setOnMouseClicked(e -> {
            quiz.questions.get(currentQuestionIndex).options.add(new Option(""));
            addAnswer(questionsTilePane.getChildren().size());
        });

        rightAnswerComboBox.setOnAction(e -> {
            currentQuestion.answer = rightAnswerComboBox.getSelectionModel().getSelectedIndex();
        });

        questionsList.setOnMouseClicked(e -> {
            int selectedIndex = questionsList.getSelectionModel().getSelectedIndex();
            if (selectedIndex < quiz.questions.size()) {
                currentQuestionIndex = selectedIndex;
            }

            populateView();
        });
    }

    /**
     * Modify the elements in the view related to the current question
     */
    private void populateView() {

        // clear the tilePane
        ArrayList<Node> nodes = new ArrayList<>(questionsTilePane.getChildren());
        for (Node node : nodes) {
            questionsTilePane.getChildren().remove(node);
        }

        // clear the answer combobox
        ArrayList<String> strings = new ArrayList<>(rightAnswerComboBox.getItems());
        for (String string : strings) {
            rightAnswerComboBox.getItems().remove(string);
        }

        // put the current question in the input field
        Question currQuestion = quiz.questions.get(currentQuestionIndex);
        questionTextField.setText(currQuestion.title);
        questionTextField.positionCaret(currQuestion.title.length());

        // add the options
        for (int i = 0; i < currQuestion.options.size(); i++) {
           addAnswer(i);
        }

        questionsList.getSelectionModel().select(currentQuestionIndex);
        rightAnswerComboBox.getSelectionModel().select(currentQuestion.answer);

    }

    /**
     * Adds a answer to the views and options
     * @param index
     */
    private void addAnswer(int index) {

        ArrayList<Option> options = quiz.questions.get(currentQuestionIndex).options;

        HBox hBox = new HBox();
        Button delButton = new Button("X");
        TextField textField = new TextField(options.get(index).title);

        rightAnswerComboBox.getItems().add(options.get(index).title);
        rightAnswerComboBox.getSelectionModel().select(quiz.questions.get(currentQuestionIndex).answer);

        delButton.setOnMouseClicked(e -> {
            if (questionsTilePane.getChildren().size() > 2) {
                removeAnswer(index);
            }
        });

        textField.setOnKeyReleased(e -> {
            options.get(index).title = textField.getText();
            rightAnswerComboBox.getItems().set(index, textField.getText());
        });

        hBox.getChildren().addAll(textField, delButton);
        hBox.setAlignment(Pos.CENTER);

        questionsTilePane.getChildren().add(hBox);
        TilePane.setMargin(hBox, new Insets(10, 0, 0, 10));

    }

    /**
     * Remove an answer from the views and options
     * @param index answer to remove
     */
    private void removeAnswer(int index) {
        questionsTilePane.getChildren().remove(index);
        quiz.questions.get(currentQuestionIndex).options.remove(index);
        rightAnswerComboBox.getItems().remove(index);
        rightAnswerComboBox.getSelectionModel().select(0); // select the first answer.
    }
}
