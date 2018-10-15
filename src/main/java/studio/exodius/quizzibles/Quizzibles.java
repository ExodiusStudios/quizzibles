package studio.exodius.quizzibles;

import javafx.application.Application;
import javafx.stage.Stage;
import studio.exodius.quizzibles.controllers.HomeScreenControl;

import java.io.File;

/**
 * @author Julian Mills
 * @version 1.0.0
 */
public class Quizzibles extends Application {

    public File storageDir;

	// JavaFX Bootstrap
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage window) {
		System.out.println("Loading Quizzibles...");

		try {
			storageDir = assertStorageDir();
			System.out.println(storageDir.getAbsolutePath());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		Window win = new Window(this, window, "Timed Java Quiz (HHS Project)");

		win.openView(new HomeScreenControl());

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
