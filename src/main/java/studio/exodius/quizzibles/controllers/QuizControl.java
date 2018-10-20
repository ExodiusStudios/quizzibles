package studio.exodius.quizzibles.controllers;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.TilePane;
import studio.exodius.quizzibles.ViewAdapter;
import studio.exodius.quizzibles.model.Option;
import studio.exodius.quizzibles.model.Quiz;
import studio.exodius.quizzibles.utility.Document;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Macjuul
 * @version 1.0.0
 */
@Document("documents/quiz_view.fxml")
public class QuizControl extends ViewAdapter {

	@FXML private Label question;
	@FXML private Label number;
	@FXML private Label score;
	@FXML private ProgressBar progress;
	@FXML private TilePane optionArea;

	private Quiz quiz;
	private int currQuestion;
	private long startTime;
	private AnimationTimer timer;

	/**
	 * Create a new Quiz View displaying the first question
	 *
	 * @param quiz Quiz
	 */
	public QuizControl(Quiz quiz) {
		this(quiz, 0);
	}

	/**
	 * Create a new Quiz View displaying the given question
	 *
	 * @param quiz Quiz
	 */
	public QuizControl(Quiz quiz, int currQuestion) {
		this.quiz = quiz;
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
		this.question.setText(quiz.questions.get(currQuestion).title);
		this.number.setText("Vraag " + (currQuestion + 1) + "/" + (quiz.questions.size()));
		this.score.setText("0 Punten");

		for(Option option : quiz.questions.get(currQuestion).options) {
			Button button = new Button(option.title);
			this.optionArea.getChildren().add(button);
		}
	}
}
