package studio.exodius.quizzibles;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.stage.Stage;
import studio.exodius.quizzibles.controllers.QuizControl;
import studio.exodius.quizzibles.model.Quiz;
import studio.exodius.quizzibles.utility.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Julian Mills
 * @version 1.0.0
 */
public class Quizzibles extends Application {

	/** The list holding all currently known quizzes */
    public List<Quiz> quizList = new ArrayList<>();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage window) {
		System.out.println("Loading Quizzibles...");

		try {
			File storage = assertStorageDir();
			Gson gson = new Gson();

			File[] documents = storage.listFiles(child ->
				child.getName().endsWith(".quiz")
			);

			if(documents == null) {
				throw new RuntimeException("Failed to list read storage");
			}

			for(File doc : documents) {
				String contents = FileUtils.readFile(doc);
				Quiz quiz = gson.fromJson(contents, Quiz.class);

				this.quizList.add(quiz);

			}
		} catch (Exception ex) {
			throw new RuntimeException("Failed to locate storage directory", ex);
		}

		// Create the main window
		Window win = new Window(this, window, "Timed Java Quiz (HHS Project)");

		// Open the HomeScreen
		win.openView(new QuizControl(this.quizList.get(0)));

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
