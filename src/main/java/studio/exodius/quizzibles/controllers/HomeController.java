package studio.exodius.quizzibles.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import studio.exodius.quizzibles.utility.Document;
import studio.exodius.quizzibles.QuizziblesMain;
import studio.exodius.quizzibles.View;
import studio.exodius.quizzibles.ViewAdapter;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Macjuul
 * @version 1.0.0
 */
@Document("documents/index.fxml")
public class HomeController extends ViewAdapter {

	private int i = 0;
	private @FXML Label lbl_clicks;
	private @FXML Label lbl_title;
	private @FXML Button btn_increment;

	@Override
	public void setup(View previous, QuizziblesMain window) {
		super.setup(previous, window);
		this.i = previous == null ? 0 : ((HomeController) previous).i;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lbl_title.setText(System.identityHashCode(this) + ""); // Display unique object id
		lbl_clicks.setText("Clicks: " + i);

		btn_increment.setOnMouseClicked(event -> {
			this.i++;

			this.window.openView(new HomeController());
		});
	}

}
