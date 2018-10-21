package studio.exodius.quizzibles.model;

import java.util.Objects;

/**
 * @author Macjuul
 * @version 1.0.0
 */
public class Option {

	/** The name of this option */
	public String title;

	public Option() {}

	public Option(String title) {
		this.title = title;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		Option option = (Option) o;
		return Objects.equals(title, option.title);
	}

	@Override
	public int hashCode() {

		return Objects.hash(title);
	}
}
