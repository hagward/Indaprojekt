import java.util.ArrayList;
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
		switch(rand.nextInt(3)){
		case 0:
			return new ExtendRacket(xPos, yPos);
		case 1:
			return new RetractRacket(xPos, yPos);		
		case 2:
			return new MultiplyBalls(xPos, yPos);
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
	
	public static class MultiplyBalls extends PowerUp {
		
		public MultiplyBalls(float xPos, float yPos) throws SlickException {
			super(xPos, yPos, new Image ("data/multiplyballs.png"));			
		}
		
		@Override
		public void effect(LevelHandler level) throws SlickException {
			ArrayList<Ball> balls = level.getBalls();
			int k = balls.size();
			float inc = 0.3f;
			for(int i = 0; i < k; i++) {
				Ball ball = balls.get(i);
				float xSpeed = ball.getXSpeed();
				float ySpeed = ball.getYSpeed();
				ball.incrementXSpeed(inc);
				ball.incrementYSpeed(-1 * inc);
				
				Ball ball2 = new Ball(ball.getX(), ball.getY());
				ball2.setXSpeed(xSpeed - inc);
				ball2.setYSpeed(ySpeed + inc);
				
				balls.add(ball2);
			}
		}
	}
}
