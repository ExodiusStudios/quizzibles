package studio.exodius.quizzibles;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import studio.exodius.quizzibles.controllers.HomeScreenControl;
import studio.exodius.quizzibles.model.Quiz;
import studio.exodius.quizzibles.utility.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * @author Julian Mills
 * @version 1.0.0
 */
public class Quizzibles extends Application {

	/** The list holding all currently known quizzes */
    public ObservableList<Quiz> quizList = FXCollections.observableArrayList();

    /** The Quizzibles storage directory */
    private File storageDir;

    /** Holds whether the app is currently running */
    public boolean running = true;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage window) {
		System.out.println("Loading Quizzibles...");

		this.storageDir = assertStorageDir();

		// Parse files and register a watcher
		parseDocuments();
		registerWatcher();

		// Create the main window
		Window win = new Window(this, window, "Timed Java Quiz (HHS Project)");

		// Open the HomeScreen
		win.openView(new HomeScreenControl());

		System.out.println("Finished initializing Quizzibles...");
	}

	@Override
	public void stop() {
		this.running = false;

		// Make sure the application exists gracefully
		System.exit(0);
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

	/**
	 * Parse all documents in the storage directory
	 */
	private void parseDocuments() {
	    try {
		    Gson gson = new Gson();

		    // Find all quiz files in the storage directory
		    File[] documents = storageDir.listFiles(child ->
			    child.getName().endsWith(".quiz")
		    );

		    if(documents == null) {
			    throw new RuntimeException("Failed to list read storage");
		    }

		    // Store all created quiz elements in an array temporarily
		    Quiz[] quizzes = new Quiz[documents.length];

		    for(int i = 0; i < documents.length; i++) {
			    File doc = documents[i];
			    String contents = FileUtils.readFile(doc);
			    Quiz quiz = gson.fromJson(contents, Quiz.class);

			    quizzes[i] = quiz;
		    }

		    // Set all new items for the quizList
		    this.quizList.setAll(quizzes);
	    } catch (Exception ex) {
		    throw new RuntimeException("Failed to locate storage directory", ex);
	    }
    }

    public void registerWatcher() {
		Thread watchThread = new Thread(() -> {
			try {
				WatchService watcher = storageDir.toPath().getFileSystem().newWatchService();
				storageDir.toPath().register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

				WatchKey watchKey;

				System.out.println("Watching for file changes...");

				while(running) {
					watchKey = watcher.poll();

					if(watchKey != null) {
						watchKey.pollEvents();
						watchKey.reset();
					}
				}

				System.out.println("Terminating watcher...");

				watcher.close();
			} catch(IOException ex) {
				throw new RuntimeException("Failed to register watcher", ex);
			}
		});

		watchThread.setDaemon(true);
		watchThread.start();
    }

	public File getStorageDir() {
		return storageDir;
	}
}
