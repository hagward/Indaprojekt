import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;

/**
 * @author Anders Hagward
 * @author Fredrik Hillnertz
 * @version 2010-05-06
 */
public class Ball extends Circle implements Movable {
	private static final long serialVersionUID = 7889780389881617843L;
	private static final float DEFAULT_SPEED = -0.2f;
	private static final float MAX_SPEED = 1f;

	protected float xSpeed;
	protected float ySpeed;
	protected Image image;

	public Ball(float centerPointX, float centerPointY) {
		super(centerPointX, centerPointY, 8);
		xSpeed = 0;
		ySpeed = DEFAULT_SPEED;
		image = ResourceLoader.getInstance().getImage("ball.png");
	}

	@Override
	public float getCenterX() {
		return x + radius;
	}

	@Override
	public void setCenterX(float x) {
		this.x = x - radius;
	}

	@Override
	public float getMaxX() {
		return x + (2 * radius);
	}

	@Override
	public float getMaxY() {
		return y + (2 * radius);
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
		if (Math.abs(xSpeed) > MAX_SPEED) {
			if (xSpeed >= 0)
				this.xSpeed = MAX_SPEED;
			else
				this.xSpeed = -MAX_SPEED;
		}
	}

	@Override
	public void setYSpeed(float ySpeed) {
		this.ySpeed = ySpeed;
		if (Math.abs(ySpeed) > MAX_SPEED) {
			if (ySpeed >= 0)
				this.ySpeed = MAX_SPEED;
			else
				this.ySpeed = -MAX_SPEED;
		}
	}

	@Override
	public void incrementXSpeed(float increment) {
		xSpeed += increment;
	}

	@Override
	public void incrementYSpeed(float increment) {
		ySpeed += increment;
	}

	public boolean insideXArea(float x1, float x2) {
		return (x > x1 && x < x2);
	}

	public boolean insideYArea(float y1, float y2) {
		return (y > y1 && y < y2);
	}

	public void draw() {
		image.draw(x, y);
	}
}
