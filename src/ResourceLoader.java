import java.io.File;
import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;

/**
 * @author Anders Hagward
 * @author Fredrik Hillnertz
 * @version 2010-05-06
 */
public class ResourceLoader {
	private static ResourceLoader instance;
	private HashMap<String, Image> images;
	private HashMap<String, Sound> sounds;
	
	private ResourceLoader() {
		images = new HashMap<String, Image>();
	}
	
	public static ResourceLoader getInstance() {
		if (instance == null) {
			instance = new ResourceLoader();
		}
		return instance;
	}
	
	public void loadImages(String folderPath) {
		File path = new File(folderPath);
		File[] files = path.listFiles();
		for (File file : files) {
			try {
				images.put(file.getName(), new Image(file.getPath()));
			} catch (Exception e) {
				System.err.println("Failed to create image object from file: "
						+ file.getPath());
			}
		}
	}
	
	public void loadSounds(String folderPath) {
		File path = new File(folderPath);
		File[] files = path.listFiles();
		for (File file : files) {
			try {
				sounds.put(file.getName(), new Sound(file.getPath()));
			} catch (Exception e) {
				System.err.println("Failed to create sound object from file: "
						+ file.getPath());
			}
		}
	}
	
	public Image getImage(String name) {
		try {
			return images.get(name);
		} catch (NullPointerException e) {
			System.err.println("Failed to find image: " + name);
			return null;
		}
	}
	
	public Sound getSound(String name) {
		try {
			return sounds.get(name);
		} catch (NullPointerException e) {
			System.err.println("Failed to find sound: " + name);
			return null;
		}
	}
}
