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
 * @since 17-10-2018
 */
@Document("documents/no_quiz_installed_popup.fxml")
public class NoQuizInstalledPopupControl extends ViewAdapter {

    @FXML private Button openFolderButton;
    @FXML private Button backButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        openFolderButton.setOnMouseClicked(e -> {
            try {
                Desktop.getDesktop().open(window.getApp().getStorageDir());
            } catch(IOException ex) {
                throw new RuntimeException("Failed to open explorer", ex);
            }
        });
        backButton.setOnMouseClicked(e -> window.close());
    }
}
