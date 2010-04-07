import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * @author Fredrik Hillnertz
 * @version 2010-04-07
 */
public class Block extends GameObject {
	private int hits;

	public Block(float xPos, float yPos, int hits) throws SlickException {
		this.xPos = xPos;
		this.yPos = yPos;
		this.hits = hits;
		setImage();
	}

	public void hit() throws SlickException {
		hits--;
		setImage();
	}

	private void setImage() throws SlickException {
		setImage(new Image("data/block" + hits + ".jpg"));
	}
}
