package studio.exodius.quizzibles.utility;

import java.io.*;

/**
 * @author Macjuul
 * @version 1.0.0
 */
public class FileUtils {

	/**
	 * Read a files contents
	 *
	 * @param file File
	 * @return String contents
	 */
	public static String readFile(File file) {

		// Door deze try-with-resources statement zorgen
		// we dat de InputStream automatish geclosed word!

		try(
			BufferedReader br = new BufferedReader(new FileReader(file))
		) {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}

			return sb.toString();
		} catch(IOException ex) {
			throw new RuntimeException("Failed to read file", ex);
		}
	}

}
