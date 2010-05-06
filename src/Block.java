import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

/**
 * @author Anders Hagward
 * @author Fredrik Hillnertz
 * @version 2010-05-06
 */
public class Block extends Rectangle {
	private static final long serialVersionUID = 7260235503507495737L;
	private int health;
	private Image image;
	private Image powerUpImage;
	private int powerUp;
	private ResourceLoader resources;

	public Block(float x, float y, int health, int powerUp) {
		super(x, y, 60, 14);
		this.resources = ResourceLoader.getInstance();
		this.health = health;
		this.powerUp = powerUp;
		if (powerUp >= 0) {
			powerUpImage = resources.getImage(
					PowerUp.powerUpImageName(powerUp));
		}
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
		if (health > 0)
			resetImage();
	}

	public int getHealth() {
		return health;
	}

	public void resetImage() {
		String imgName = "block" + health + ".png";
		image = resources.getImage(imgName);
	}

	public void draw() {
		image.draw(x, y);
		if (powerUpImage != null)
			powerUpImage.draw(x, y, powerUpImage.getWidth(), 14);
	}

	public int getPowerUp() {
		return powerUp;
	}

	public void setPowerUp(int val) {
		powerUp = val;
	}
}
