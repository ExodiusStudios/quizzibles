package studio.exodius.quizzibles.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import studio.exodius.quizzibles.ViewAdapter;
import studio.exodius.quizzibles.utility.Document;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class description
 *
 * @author Densorius
 * @version 1.0.0
 * @since 15-10-2018
 */
@Document("documents/choose_quiz_screen.fxml")
public class ChooseQuizScreenControl extends ViewAdapter {

    @FXML private Button backButton;
    @FXML private Button openFolderButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backButton.setOnMouseClicked(e -> window.openView(new HomeScreenControl()));
        openFolderButton.setOnMouseClicked(e -> {
	        try {
		        Desktop.getDesktop().open(window.getApp().getStorageDir());
	        } catch(IOException ex) {
		        throw new RuntimeException("Failed to open explorer", ex);
	        }
        });
    }
}
