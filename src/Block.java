import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

/**
 * @author Anders Hagward
 * @author Fredrik Hillnertz
 * @version 2010-04-21
 */
public class Block extends Rectangle {
	private int health;
	private Image image;

	public Block(float x, float y, int health) {
		super(x, y, 60, 14);
		this.health = health;
		resetImage();
	}

	@Override
	public float getMaxX() {
		return x + width;
	}

	@Override
	public float getMaxY() {
		return y + height;
	}

	public void hit() throws SlickException {
		health--;
		if(health > 0)
			resetImage();
	}
	
	public int getHealth() {
		return health;
	}
	
	public void resetImage() {
		String imgPath = "data/block" + health + ".png";
		try {
			image = new Image(imgPath);
		} catch (SlickException e) {
			System.err.println("Error: couldn't load image '"
					+ imgPath + "'");
		}
	}
	
	public void draw() {
		image.draw(x, y);
	}
}