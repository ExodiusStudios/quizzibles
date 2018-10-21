package studio.exodius.quizzibles.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import studio.exodius.quizzibles.ViewAdapter;
import studio.exodius.quizzibles.utility.Document;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class description
 *
 * @author Densorius
 * @version 1.0.0
 * @since 21-10-2018
 */
@Document("documents/confirm_popup.fxml")
public class ConfirmPopup extends ViewAdapter {

    @FXML private Label textLabel;
    @FXML private Button cancelButton;
    @FXML private Button okButton;

    private boolean confirmed;
    private String text;

    ConfirmPopup(String text) {
        this.text = text;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
