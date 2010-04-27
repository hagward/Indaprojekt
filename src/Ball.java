import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

/**
 * @author Anders Hagward
 * @author Fredrik Hillnertz
 * @version 2010-04-21
 */
public class Ball extends Circle implements Movable {
	private static final String DEFAULT_IMG_PATH = "data/ball.png";
	private static final float DEFAULT_SPEED = -0.2f;
	private static final float MAX_SPEED = 1f;

	protected float xSpeed;
	protected float ySpeed;
	protected Image image;

	public Ball(float centerPointX, float centerPointY) {
		super(centerPointX, centerPointY, 8);
		xSpeed = 0;
		ySpeed = DEFAULT_SPEED;
		
		try {
			image = new Image(DEFAULT_IMG_PATH);
		} catch (SlickException e) {
			System.err.println("Error: couldn't load image '"
					+ DEFAULT_IMG_PATH + "'");
		}
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
		if(Math.abs(xSpeed) > MAX_SPEED) {
			if(xSpeed >= 0)
				this.xSpeed = MAX_SPEED;
			else
				this.xSpeed = -MAX_SPEED;
		}
	}

	@Override
	public void setYSpeed(float ySpeed) {
		this.ySpeed = ySpeed;
		if(Math.abs(ySpeed) > MAX_SPEED) {
			if(ySpeed >= 0)
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