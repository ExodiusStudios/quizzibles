package studio.exodius.quizzibles;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import studio.exodius.quizzibles.utility.Document;
import studio.exodius.quizzibles.utility.Loader;

import java.net.URL;

/**
 * @author Macjuul
 * @version 1.0.0
 */
public class Window {

	private Quizzibles quiz;

	private Stage window;

	public Window(Quizzibles quiz, Stage window, String title) {
		this.quiz = quiz;
		this.window = window;

		window.getIcons().add(Loader.image("images/icon.png"));
		window.setTitle("Quizzibles - " + title);
	}

	/**
	 * Open the given page in the Application
	 *
	 * @param controller Controller
	 */
	public void openView(View controller) {
		if(!controller.getClass().isAnnotationPresent(Document.class)) {
			throw new RuntimeException("View requires Document annotation");
		}

		String document = controller.getClass().getAnnotation(Document.class).value();
		URL uri = Loader.url(document);

		controller.setup(this);

		Parent view = Loader.scene(uri, controller);
		Scene scene = new Scene(view);

		window.setScene(scene);

		window.show();
	}

	public Quizzibles getApp() {
		return quiz;
	}

	/**
	 * Update the title of this window
	 *
	 * @param title Title
	 */
	public void setTitle(String title) {
		this.window.setTitle("Quizzibles - " + title);
	}

	/**
	 * Exit the current window
	 */
	public void close() {
		this.window.close();
	}

	public Stage getWindow() {
		return this.window;
	}
}
