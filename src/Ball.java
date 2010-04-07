import org.newdawn.slick.Image;

/**
 * @author Fredrik Hillnertz
 * @version 2010-04-07
 */
public class Ball extends GameObject implements Movable {
	private float xSpeed, ySpeed, radius;

	public Ball(float xPos, float yPos, float xSpeed, float ySpeed,
			float radius, Image image) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.radius = radius;
		this.image = image;
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

	public void incrementRadius(float increment) {
		radius += increment;
	}
}
