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
		this.radius = 8;
		this.xSpeed = -0.3f;
		this.ySpeed = -0.3f;
	}

	@Override
	public void move() {
		xPos += xSpeed;
		yPos += ySpeed;
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
	public void setXSpeed(float XSpeed) {
		this.xSpeed = XSpeed;
	}

	@Override
	public void setYSpeed(float YSpeed) {
		this.ySpeed = YSpeed;
	}
	
	/**
	 * @return the coordinate for the ball's center on the horizontal axis
	 */
	@Override
	public float getXPos() {
		return xPos + radius;
	}
	
	/**
	 * @return the coordinate for the ball's center on the vertical axis
	 */
	@Override
	public float getYPos() {
		return yPos + radius;
	}
	
	/**
	 * Sets the coordinate for the ball's center on the horizontal axis.
	 * 
	 * @param xPos the x coordinate of the ball's center
	 */
	@Override
	public void setXPos(float xPos) {
		this.xPos = xPos - radius;
	}
	
	/**
	 * Sets the coordinate for the ball's center on the vertical axis.
	 * 
	 * @param xPos the y coordinate of the ball's center
	 */
	@Override
	public void setYPos(float yPos) {
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