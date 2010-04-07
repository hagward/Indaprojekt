import org.newdawn.slick.Image;

/**
 * @author Fredrik Hillnertz
 * @version 2010-04-07
 */
public class Racket extends GameObject implements Movable {
	private float xSpeed, length;
	private final float ySpeed = 0;

	public Racket(float xPos, float yPos, float xSpeed, float length,
			Image image) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.xSpeed = xSpeed;
		this.image = image;
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

	public void setLength(float length) {
		this.length = length;
	}

	public float getLength() {
		return length;
	}

}
