import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * @author Anders Hagward
 * @author Fredrik Hillnertz
 * @version 2010-04-08
 */
public class Block extends GameObject {
	private int health;

	public Block(float xPos, float yPos, int health)
			throws SlickException {
		super(xPos, yPos, new Image("data/block.png"));
		this.xPos = xPos;
		this.yPos = yPos;
		this.health = health;
	}

	public void hit() {
		health--;
//		setImage();
	}
	
	public int getHealth() {
		return health;
	}

	private void setImage() throws SlickException {
		setImage(new Image("data/block" + health + ".jpg"));
	}
}