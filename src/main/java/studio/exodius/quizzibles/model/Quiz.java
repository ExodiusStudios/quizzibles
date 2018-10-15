package studio.exodius.quizzibles.model;

/**
 * Represents a quiz model
 *
 * @author Macjuul
 * @version 1.0.0
 */
public class Quiz {

	/** The full length name of this quiz */
	public String name = null;

	/** The current version of the quiz */
	public String version = null;

	/** The author of the quiz */
	public String author = "Unknown";

	/** The duration people have to answer **/
	public long answerDuration = 10000;

	/** The questions for this quiz */
	public Question[] questions = new Question[0];
}
