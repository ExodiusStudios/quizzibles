package studio.exodius.quizzibles.controllers;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.TilePane;
import studio.exodius.quizzibles.ViewAdapter;
import studio.exodius.quizzibles.Window;
import studio.exodius.quizzibles.model.Option;
import studio.exodius.quizzibles.model.Question;
import studio.exodius.quizzibles.model.Quiz;
import studio.exodius.quizzibles.utility.Document;

import java.net.URL;
import java.util.*;

/**
 * @author Macjuul
 * @version 1.0.0
 */
@Document("documents/quiz_view.fxml")
public class QuizControl extends ViewAdapter {

	@FXML private Label questionText;
	@FXML private Label number;
	@FXML private Label score;
	@FXML private ProgressBar progress;
	@FXML private TilePane optionArea;
	@FXML private Button next;

	private Quiz quiz;
	private Question question;
	private int currQuestion;
	private int currScore;
	private long startTime;
	private AnimationTimer timer;
	private boolean chosen = false;

	/**
	 * Create a new Quiz View displaying the first question
	 *
	 * @param quiz Quiz
	 */
	public QuizControl(Quiz quiz) {
		this(quiz, 0, 0);
	}

	/**
	 * Create a new Quiz View displaying the given question
	 *
	 * @param quiz Quiz
	 */
	public QuizControl(Quiz quiz, int currQuestion, int currScore) {
		this.quiz = quiz;
		this.currScore = currScore;
		this.question = quiz.questions.get(currQuestion);
		this.currQuestion = currQuestion;
		this.startTime = System.currentTimeMillis();

		// Create the animation timer that runs at 60 FPS
		this.timer = new AnimationTimer() {

			@Override
			public void handle(long d) {
				long now = System.currentTimeMillis();
				float prog = (float) (now - startTime) / quiz.answerDuration;

				// Out of time!
				if(prog >= 1) {
					progress.setProgress(1);
					stop();
					return;
				}

				progress.setProgress(prog);
			}

		};

		timer.start();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.questionText.setText(question.title);
		this.number.setText("Vraag " + (currQuestion + 1) + "/" + (quiz.questions.size()));

		// Display the current score
		updateScore();

		// Create a mapping between buttons and options
		Map<Button, Option> buttonMapping = new HashMap<>();
		List<Button> buttons = new ArrayList<>();

		for(Option option : question.options) {
			Button button = new Button(option.title);
			buttonMapping.put(button, option);
			buttons.add(button);

			button.setOnMouseClicked(e -> {
				if(chosen) return;

				chosen = true;
				timer.stop();

				optionPicked(buttonMapping, button);
			});
		}

		// Shuffle the buttons order and add them to the UI
		Collections.shuffle(buttons);
		buttons.forEach(button -> optionArea.getChildren().add(button));

		// Set next button onClick
		this.next.setOnMouseClicked(e -> nextQuestion());
	}

	private void optionPicked(Map<Button, Option> mapping, Button button) {

		// Disable other buttons
		optionArea.getChildren().forEach(node -> {
			Button btn = (Button) node;
			Option opt = mapping.get(btn);

			// Color the button
			if(question.answer == question.options.indexOf(opt)) {
				btn.setStyle("-fx-background-color: #60ef40");
			} else {
				btn.setStyle("-fx-background-color: #ef5047");
			}
		});

		// Validate and reward points
		final Option answer = mapping.get(button);

		if(question.answer == question.options.indexOf(answer)) {

			// Calculate a score based on time remaining
			long now = System.currentTimeMillis();
			double modifier = 1f - (float) (now - startTime) / quiz.answerDuration;

			currScore += question.maxReward * modifier;

			// Update the score in the UI
			updateScore();
		}

		// Rewrite next button text for last question
		if(quiz.questions.size() == currQuestion + 1) {
			next.setText("Finish quiz");
		}

		// Make the next button visible
		next.setVisible(true);
	}

	private void updateScore() {
		this.score.setText(currScore + " Punten");
	}

	private void nextQuestion() {
		if(quiz.questions.size() == currQuestion + 1) {
			Window win = window.getApp().createWindow("Quiz finished!");

			// Open the highscore view
			win.openView(new HighscoreControl(window, currScore));
		} else {
			window.openView(new QuizControl(quiz, currQuestion + 1, currScore));
		}
	}
}
