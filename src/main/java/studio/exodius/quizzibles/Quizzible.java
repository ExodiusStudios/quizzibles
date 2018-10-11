package studio.exodius.quizzibles;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import studio.exodius.quizzibles.controllers.HomeController;
import studio.exodius.quizzibles.utility.Document;
import studio.exodius.quizzibles.utility.Loader;

import java.net.URL;

/**
 * @author Julian Mills
 * @version 1.0.0
 */
public class Quizzible extends Application {

	private View currentView;
	private Stage window;

	// JavaFX Bootstrap
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage window) {
		this.window = window;

		openView(new HomeController());

		window.getIcons().add(Loader.image("images/icon.png"));
		window.setTitle("Quizzibles - Timed Java Quiz (HHS Project)");
		window.show();
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

		controller.setup(this.currentView, this);

		Parent view = Loader.scene(uri, controller);
		Scene scene = new Scene(view);

		window.setScene(scene);
		currentView = controller;
	}
}
