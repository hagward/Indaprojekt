import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * @author Anders Hagward
 * @author Fredrik Hillnertz
 * @version 2010-04-08
 */
public class Block extends GameObject {
	private int hits;

	public Block(float xPos, float yPos, int hits, Image image)
			throws SlickException {
		super(xPos, yPos, image);
		this.xPos = xPos;
		this.yPos = yPos;
		this.hits = hits;
		setImage(image);
	}

	public void hit() throws SlickException {
		hits--;
		setImage();
	}

	private void setImage() throws SlickException {
		setImage(new Image("data/block" + hits + ".jpg"));
	}
}