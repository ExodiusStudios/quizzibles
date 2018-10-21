package studio.exodius.quizzibles.model;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Macjuul
 * @version 1.0.0
 */
public class Question {

	/** The title of this question */
	public String title;

	/** The index of the correct option **/
	public int answer;

	/** The reward for answering a correct question **/
	public int maxReward = 50;

	/** An array of possible options **/
	public ArrayList<Option> options = new ArrayList<>();

	public Question() {}

	public Question(String title) {
		this.title = title;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		Question question = (Question) o;
		return answer == question.answer &&
			Objects.equals(title, question.title) &&
			Objects.equals(options, question.options);
	}

	@Override
	public int hashCode() {
		return Objects.hash(title, answer, options);
	}
}
