package studio.exodius.quizzibles;

/**
 * Base View implementation where the window is automatically saved
 * on the instance
 *
 * @author Macjuul
 * @version 1.0.0
 */
public abstract class ViewAdapter implements View {

	public Quizzible window;

	@Override
	public void setup(View previous, Quizzible window) {
		this.window = window;
	}

}
