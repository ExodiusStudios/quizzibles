package studio.exodius.quizzibles;

import javafx.application.Application;
import javafx.stage.Stage;
import studio.exodius.quizzibles.controllers.HomeController;

/**
 * @author Julian Mills
 * @version 1.0.0
 */
public class Quizzibles extends Application {

	// JavaFX Bootstrap
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage window) {
		System.out.println("Loading Quizzibles...");

		Window win = new Window(this, window, "Timed Java Quiz (HHS Project)");

		win.openView(new HomeController());

		System.out.println("Finished initializing Quizzibles...");
	}

	/**
	 * Create a new Window
	 *
	 * @return Window
	 */
	public Window createWindow(String title) {
		return new Window(this, new Stage(), title);
	}
}
