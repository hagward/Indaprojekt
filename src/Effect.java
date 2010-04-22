import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public abstract class Effect extends Animation {
	protected float xPos;
	protected float yPos;
	protected int nFrames;
	
	protected Effect(float xPos, float yPos) {
		this.yPos = yPos;
		this.xPos = xPos;
	}
	
	public void draw() {
		this.draw(xPos, yPos);
		if(getFrame() == 5)
			stop();
	}
	
	public int getNFrames() {
		return nFrames;
	}
	
	public static class FireSplash extends Effect {

		protected FireSplash(float xPos, float yPos) throws SlickException {
			super(xPos, yPos);
			for(int i = 1; i < 7; i++)
				this.addFrame(new Image("data/effects/firesplash" + i + ".png"), 50);
			nFrames = getFrameCount() - 1;
		}
	}
}
