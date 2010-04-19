import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * @author Anders Hagward
 * @author Fredrik Hillnertz
 * @version 2010-04-08
 */
public class Ball extends GameObject implements Movable {
	private float xSpeed, ySpeed, radius;

	public Ball(float xPos, float yPos)
			throws SlickException {
		super(xPos, yPos, new Image("data/ball.png"));
		this.radius = 8; // wtf används radius till?
		this.xSpeed = -0.2f;
		this.ySpeed = -0.2f;
	}

	@Override
	public void move(int delta) {
		xPos += xSpeed * delta;
		yPos += ySpeed * delta;
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
	
	/**
	 * @return the coordinate for the ball's center on the horizontal axis
	 */
	@Override
	public float getX() {
		return xPos + radius;
	}
	
	/**
	 * @return the coordinate for the ball's center on the vertical axis
	 */
	@Override
	public float getY() {
		return yPos + radius;
	}
	
	/**
	 * Sets the coordinate for the ball's center on the horizontal axis.
	 * 
	 * @param xPos the x coordinate of the ball's center
	 */
	@Override
	public void setX(float xPos) {
		this.xPos = xPos - radius;
	}
	
	/**
	 * Sets the coordinate for the ball's center on the vertical axis.
	 * 
	 * @param xPos the y coordinate of the ball's center
	 */
	@Override
	public void setY(float yPos) {
		this.yPos = yPos - radius;
	}

	@Override
	public void incrementXSpeed(float increment) {
		xSpeed += increment;
	}

	@Override
	public void incrementYSpeed(float increment) {
		ySpeed += increment;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}
	
	public float getRadius() {
		return radius;
	}

	public void incrementRadius(float increment) {
		radius += increment;
	}
}