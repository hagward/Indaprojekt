import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

/**
 * @author Anders Hagward
 * @author Fredrik Hillnertz
 * @version 2010-04-20
 */
public class Ball extends Circle implements Movable {
	private static final String DEFAULT_IMG_PATH = "data/ball.png";
	private static final float DEFAULT_SPEED = -0.2f;

	private float xSpeed;
	private float ySpeed;
	private Image image;

	public Ball(float centerPointX, float centerPointY) {
		super(centerPointX, centerPointY, 8);
		xSpeed = DEFAULT_SPEED;
		ySpeed = DEFAULT_SPEED;
		
		try {
			image = new Image(DEFAULT_IMG_PATH);
		} catch (SlickException e) {
			System.err.println("Error: couldn't load image '"
					+ DEFAULT_IMG_PATH + "'");
		}
	}
	
	public float getRightX() {
		return x + radius;
	}
	
	public float getBottomY() {
		return y + radius;
	}

	@Override
	public void move(int delta) {
		x += xSpeed * delta;
		y += ySpeed * delta;
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
	public void setXSpeed(float xSpeed) {
		this.xSpeed = xSpeed;
	}

	@Override
	public void setYSpeed(float ySpeed) {
		this.ySpeed = ySpeed;
	}

	@Override
	public void incrementXSpeed(float increment) {
		xSpeed += increment;
	}

	@Override
	public void incrementYSpeed(float increment) {
		ySpeed += increment;
	}

	public void draw() {
		image.draw(x, y);
	}
}