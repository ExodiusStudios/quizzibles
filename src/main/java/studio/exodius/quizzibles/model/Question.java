package studio.exodius.quizzibles.model;

import java.util.ArrayList;

/**
 * @author Macjuul
 * @version 1.0.0
 */
public class Question {

	/** The title of this question */
	public String title;

	/** The index of the correct option **/
	public int answer;

	/** An array of possible options **/
	public ArrayList<Option> options = new ArrayList<>();

	public Question() {}

	public Question(String title) {
		this.title = title;
	}

}
