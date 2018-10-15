package studio.exodius.quizzibles.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a quiz model
 *
 * @author Macjuul
 * @version 1.0.0
 */
public class Quiz {

	/** The slug (Short name) for this quiz */
	public String slug = null;

	/** The full length name of this quiz */
	public String name = null;

	/** The current version of the quiz */
	public String version = null;

	/** The author of the quiz */
	public String author = "Unknown";

	/** The duration people have to answer **/
	public long answerDuration = 10000;

	/** The questions for this quiz */
	public List<Question> questions = new ArrayList<>();
}
