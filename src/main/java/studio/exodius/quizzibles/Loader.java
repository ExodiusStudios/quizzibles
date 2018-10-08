package studio.exodius.quizzibles;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author Macjuul
 * @version 1.0.0
 */
public class Loader {

	public static URL url(String path) {
		URL res = Loader.class.getClassLoader().getResource(path);

		if(res == null) throw new RuntimeException("URI could not be located");

		return res;
	}

	public static InputStream stream(String path) {
		return Loader.class.getClassLoader().getResourceAsStream(path);
	}

	public static Image image(String path) {
		return new Image(stream(path));
	}

	public static Parent scene(URL index, Initializable controller) {
		try {
			FXMLLoader loader = new FXMLLoader(index);
			loader.setController(controller);
			return loader.load();
		} catch(IOException ex) {
			throw new RuntimeException("Scene failed to load", ex);
		}
	}

}
