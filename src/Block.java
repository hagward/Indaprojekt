import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

/**
 * @author Anders Hagward
 * @author Fredrik Hillnertz
 * @version 2010-04-27
 */
public class Block extends Rectangle {
	private static final long serialVersionUID = 7260235503507495737L;
	private int health;
	private Image image;
	private Image powerUpImage;
	private int powerUp;

	public Block(float x, float y, int health, int powerUp) {
		super(x, y, 60, 14);
		this.health = health;
		this.powerUp = powerUp;
		if (powerUp >= 0) {
			try {
				powerUpImage = new Image(PowerUp.powerUpPath(powerUp));
			} catch (SlickException e) {
			}
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
		String imgPath = "data/images/block" + health + ".png";
		try {
			image = new Image(imgPath);
		} catch (SlickException e) {
			System.err.println("Error: couldn't load image '" + imgPath + "'");
		}
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