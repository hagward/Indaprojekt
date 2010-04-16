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
	
	protected float getX() {
		return xPos;
	}
	
	protected float getY() {
		return yPos;
	}
	
	protected void setX(float xPos) {
		this.xPos = xPos;
	}
	
	protected void setY(float yPos) {
		this.yPos = yPos;
	}
	
	protected void setImage(Image image) {
		this.image = image;
	}
	
	protected Image getImage() {
		return image;
	}
	
	protected boolean collidesWithTop(GameObject go) {
		return (insideXArea(go) && (int)(yPos+getHeight()) == (int)go.yPos);
	}
	
	protected boolean collidesWithRight(GameObject go) {
		return (insideYArea(go) && (int)xPos == (int)go.xPos+go.getWidth());
	}
	
	protected boolean collidesWithBottom(GameObject go) {
		return (insideXArea(go) && (int)yPos == (int)go.yPos+go.getHeight());
	}
	
	protected boolean collidesWithLeft(GameObject go) {
		return (insideYArea(go) && (int)(xPos+getWidth()) == (int)go.xPos);
	}
	
	private boolean insideXArea(GameObject go) {
		return (xPos >= go.getX() && xPos <= go.getX() + go.getWidth());
	}
	
	private boolean insideYArea(GameObject go) {
		return (yPos >= go.getY() && yPos <= go.getY() + go.getHeight());
	}
}