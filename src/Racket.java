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
	private static final int MAX_SIZE = 600;
	private static final int MIN_SIZE = 20;
	private PowerUp.pewPewLasers lasers;

	public Racket(float xPos, float yPos, float xSpeed)
			throws SlickException {
		super(xPos, yPos, new Image("data/racket.png"));
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
	public void move(int delta) {
		xPos += xSpeed * delta;
	}
	
	public void increaseSize() throws SlickException {
		int width = image.getWidth() + 40;
		if(width > MAX_SIZE)
			width = MAX_SIZE;
		setImage(image.getScaledCopy(width, image.getHeight()));
	}
	
	public void decreaseSize() throws SlickException {
		int width = image.getWidth() - 40;
		if(width < MIN_SIZE)
			width = MIN_SIZE;
		setImage(image.getScaledCopy(width, image.getHeight()));
	}

	public void addLasers(PowerUp.pewPewLasers lasers) throws SlickException {		
		this.lasers = lasers;
		setImage(new Image("data/laserracket.png"));
	}

	public void removeLasers() throws SlickException {
		lasers = null;		
		setImage(new Image("data/racket.png"));
	}

	public PowerUp.pewPewLasers getLaser() {
		return lasers;
	}
	
}