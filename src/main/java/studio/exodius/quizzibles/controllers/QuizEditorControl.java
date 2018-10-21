package studio.exodius.quizzibles.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import studio.exodius.quizzibles.ViewAdapter;
import studio.exodius.quizzibles.Window;
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
    @FXML private Button deleteQuestionButton;
    @FXML private Menu FileMenu;

    private Quiz quiz;

    private int currentQuestionIndex = 0;
    private Question currentQuestion;
    private boolean saved = false;

    QuizEditorControl(Quiz quiz) {
        if (quiz == null) {

            // create new quiz with a empty question and two empty answers
            this.quiz = new Quiz();

            newQuestion();

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
            quiz.questions.get(currentQuestionIndex).title = questionTextField.getText();
        });

        newAnswerButton.setOnMouseClicked(e -> {
            quiz.questions.get(currentQuestionIndex).options.add(new Option(""));
            addAnswer(questionsTilePane.getChildren().size());
        });

        rightAnswerComboBox.setOnAction(e -> {
            if (rightAnswerComboBox.getItems().size() > 0) {
                currentQuestion.answer = rightAnswerComboBox.getSelectionModel().getSelectedIndex();
            }
        });

        questionsList.setOnMouseClicked(e -> {
            int selectedIndex = questionsList.getSelectionModel().getSelectedIndex();
            if (selectedIndex < quiz.questions.size() && selectedIndex >= 0) {
                currentQuestionIndex = selectedIndex;
                currentQuestion = quiz.questions.get(selectedIndex);
            }

            populateView();
        });

        newQuestionButton.setOnAction(e -> {
            newQuestion();
            questionsList.getItems().add("");
            currentQuestion = this.quiz.questions.get(currentQuestionIndex);
            populateView();
        });

        deleteQuestionButton.setOnAction(e -> removeQuestion());
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
        rightAnswerComboBox.getItems().clear();
        // put the current question in the input field
        System.out.println(quiz.questions.size());
        Question currQuestion = quiz.questions.get(currentQuestionIndex);
        questionTextField.setText(currQuestion.title);
        questionTextField.positionCaret(currQuestion.title.length());

        // add the options
        for (int i = 0; i < currQuestion.options.size(); i++) {
           addAnswer(i);
        }
        rightAnswerComboBox.getSelectionModel().select(currentQuestion.answer);

    }

    private void newQuestion() {
        Question question = new Question("");
        Option answerA = new Option("");
        Option answerB = new Option("");

        question.options.add(answerA);
        question.options.add(answerB);
        question.answer = 0;

        this.quiz.questions.add(question);

        currentQuestionIndex = this.quiz.questions.size() - 1;
        System.out.println(this.quiz.questions.size());
        System.out.println("answer new question: " + quiz.questions.get(currentQuestionIndex).answer);
        System.out.println("currentQuestionIndex: " + currentQuestionIndex);
    }

    /**
     * Adds a answer to the views and options
     * @param index
     */
    private void addAnswer(int index) {

        ArrayList<Option> options = currentQuestion.options;

        HBox hBox = new HBox();
        Button delButton = new Button("X");
        TextField textField = new TextField(options.get(index).title);

        rightAnswerComboBox.getItems().add(options.get(index).title);
        //rightAnswerComboBox.getSelectionModel().select(currentQuestion.answer);

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

    private void removeQuestion() {
        // open confirmBox
        Stage stage = new Stage();

        ConfirmPopup popup = new ConfirmPopup("Are you sure you want to delete '" + currentQuestion.title + "'?");

        Window win = new Window(window.getApp(), stage, "Delete " + currentQuestion.title);
        win.openView(popup);

        win.getStage().setOnHiding(e -> {
            if (popup.isConfirmed()) {
                quiz.questions.remove(currentQuestion);
                questionsList.getItems().remove(currentQuestion.title);

                if (questionsList.getItems().size() > 0) {
                    currentQuestionIndex = 0;
                    currentQuestion = quiz.questions.get(0);
                } else {
                    newQuestion();
                    System.out.println(quiz.questions.size());
                }

                questionsList.getItems().add("");
                populateView();
            }
        });
    }
}
