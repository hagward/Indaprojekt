import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class PowerUp extends GameObject {
	
	protected PowerUp(float xPos, float yPos, Image image) {
		super(xPos, yPos, image);
	}

	protected static final int UNLIMITED = 0;
	protected int duration;

	public abstract void effect(LevelHandler level) throws SlickException;

	protected void move() {
		float yPos = getY();
		setY(yPos + 0.3f);
	}				
	
	public static PowerUp randomPowerUp(float xPos, float yPos) throws SlickException {
		Random rand = new Random();	
		switch(rand.nextInt(2)){
		case 0:
			return new ExtendRacket(xPos, yPos);
		case 1:
			return new RetractRacket(xPos, yPos);		
		}
		return null;
	}
	
	public static class RetractRacket extends PowerUp {
		
		public RetractRacket(float xPos, float yPos) throws SlickException {
			super(xPos, yPos, new Image ("data/RetractRacket.png"));		
		}

		@Override
		public void effect(LevelHandler level) throws SlickException {
			level.getRacket().decreaseSize();		
		}
	}
	
	public static class ExtendRacket extends PowerUp {
		
		public ExtendRacket(float xPos, float yPos) throws SlickException {
			super(xPos, yPos, new Image ("data/ExtendRacket.png"));			
		}
		
		@Override
		public void effect(LevelHandler level) throws SlickException {
			level.getRacket().increaseSize();		
		}
	}	
}
