import org.newdawn.slick.Image;


public abstract class GameObject {
	protected Image image;
	protected float xPos, yPos;
	
	public float getXPos() {
		return xPos;
	}
	public float getYPos() {
		return yPos;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public Image getImage() {
		return image;
	}
}
