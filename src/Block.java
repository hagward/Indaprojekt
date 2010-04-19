import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * @author Anders Hagward
 * @author Fredrik Hillnertz
 * @version 2010-04-14
 */
public class Block extends GameObject {
	private int health;

	public Block(float xPos, float yPos, int health)
			throws SlickException {
		super(xPos, yPos, new Image("data/block" + health + ".png"));
		this.xPos = xPos;
		this.yPos = yPos;
		this.health = health;
	}

	public void hit() throws SlickException {
		health--;
		if(health > 0)
			setImage();
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setImage() throws SlickException {
		setImage(new Image("data/block" + health + ".png"));
	}	
}