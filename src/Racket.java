import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * @author Anders Hagward
 * @author Fredrik Hillnertz
 * @version 2010-04-08
 */
public class Racket extends GameObject implements Movable {
	private float xSpeed;
	private final float ySpeed = 0;
	private int size;
	private static final int MAX_SIZE = 2;
	private static final int MIN_SIZE = 0;

	public Racket(float xPos, float yPos, float xSpeed)
			throws SlickException {
		super(xPos, yPos, new Image("data/racket1.png"));
		size = 1;		
		this.xSpeed = xSpeed;
	}

	@Override
	public void setXSpeed(float XSpeed) {
		this.xSpeed = XSpeed;
	}

	@Override
	public void setYSpeed(float YSpeed) {
		return;
	}

	@Override
	public float getXSpeed() {
		return xSpeed;
	}

	@Override
	public float getYSpeed() {
		return ySpeed;
	}

	@Override
	public void incrementXSpeed(float increment) {
		xSpeed += increment;
	}

	@Override
	public void incrementYSpeed(float increment) {
		return;
	}

	@Override
	public void move() {
		xPos += xSpeed;
	}
	
	public void increaseSize() throws SlickException {
		size++;
		if(size <= MAX_SIZE)
			setImage(new Image("data/racket" + size + ".png"));
		else
			size--;
	}
	
	public void decreaseSize() throws SlickException {
		size--;
		if(size >= MIN_SIZE)
			setImage(new Image("data/racket" + size + ".png"));
		else
			size++;
	}
	
}