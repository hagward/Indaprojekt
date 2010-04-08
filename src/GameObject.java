import org.newdawn.slick.Image;

/**
 * @author Anders Hagward
 * @author Fredrik Hillnertz
 * @version 2010-04-08
 */
public abstract class GameObject {
	protected Image image;
	protected float xPos, yPos;
	
	protected GameObject(float xPos, float yPos, Image image) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.image = image;
	}
	
	protected void draw() {
		image.draw(xPos, yPos);
	}
	
	protected int getWidth() {
		return image.getWidth();
	}
	
	protected int getHeight() {
		return image.getHeight();
	}
	
	protected float getXPos() {
		return xPos;
	}
	
	protected float getYPos() {
		return yPos;
	}
	
	protected void setXPos(float xPos) {
		this.xPos = xPos;
	}
	
	protected void setYPos(float yPos) {
		this.yPos = yPos;
	}
	
	protected void setImage(Image image) {
		this.image = image;
	}
	
	protected Image getImage() {
		return image;
	}
}