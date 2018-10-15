package studio.exodius.quizzibles;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import studio.exodius.quizzibles.controllers.HomeController;

import java.io.File;
import java.net.URL;

/**
 * @author Julian Mills
 * @version 1.0.0
 */
public class QuizziblesMain extends Application {

	private View currentView;
	private Stage window;

	public File storageDir;

	// JavaFX Bootstrap
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage window) {
		this.window = window;

		openView(new HomeController());

		try {
		    storageDir = assertStorageDir();
			System.out.println(storageDir.getAbsolutePath());
        } catch (Exception ex) {
		    ex.printStackTrace();
        }

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

    /**
     * Create the storage directory for the quiz app
     * @return directory for quiz storage
     */
	private File assertStorageDir() {
        File quizziblesFolder = new File(System.getProperty("user.home") + "/.quizzibles");

        // if the directory doesn't exist create it
        if (!quizziblesFolder.isDirectory()) {
            if (!quizziblesFolder.mkdir()) {
                throw new RuntimeException("Quizzibles folder couldn't be created");
            }
        }

        return quizziblesFolder;
    }
}
