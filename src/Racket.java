import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

/**
 * @author Anders Hagward
 * @author Fredrik Hillnertz
 * @version 2010-04-27
 */
public class Racket extends Rectangle implements Movable {
	private static final long serialVersionUID = 1467497140421312672L;
	private static final String DEFAULT_IMG_PATH = "data/images/racket.png";
	private static final int MAX_SIZE = 600;
	private static final int MIN_SIZE = 20;

	private float xSpeed;
	private final float ySpeed;
	private Image image;
	private PowerUp.PewPewLasers lasers;

	public Racket(float x, float y) {
		super(x, y, 80, 16);
		xSpeed = 0;
		ySpeed = 0;
		
		try {
			image = new Image(DEFAULT_IMG_PATH);
		} catch (SlickException e) {
			System.err.println("Error: couldn't load image '"
					+ DEFAULT_IMG_PATH + "'");
		}
	}
	
	@Override
	public float getCenterX() {
		return x + (width / 2);
	}

	@Override
	public float getMaxX() {
		return x + width;
	}

	@Override
	public float getMaxY() {
		return y + height;
	}

	@Override
	public void setXSpeed(float xSpeed) {
		this.xSpeed = xSpeed;
	}

	@Override
	public void setYSpeed(float ySpeed) {
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
		x += xSpeed * delta;
	}

	public void increaseSize() throws SlickException {
		width += 40;
		if(width > MAX_SIZE)
			width = MAX_SIZE;
		image = image.getScaledCopy((int) width, (int) height);
	}

	public void decreaseSize() throws SlickException {
		width -= 40;
		if(width < MIN_SIZE)
			width = MIN_SIZE;
		image = image.getScaledCopy((int) width, (int) height);
	}

	public void addLasers(PowerUp.PewPewLasers lasers) throws SlickException {
		this.lasers = lasers;
		image = new Image("data/images/laserracket.png").getScaledCopy(
				(int) width, (int) height);
	}

	public void removeLasers() throws SlickException {
		lasers = null;
		image = new Image("data/images/racket.png").getScaledCopy(
				(int) width, (int) height);
	}

	public PowerUp.PewPewLasers getLaser() {
		return lasers;
	}
	
	public void draw() {
		image.draw(x, y);
	}	
}