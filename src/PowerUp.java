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
	protected float ySpeed = 0.2f;

	public abstract void effect(LevelHandler level) throws SlickException;

	protected void move(int delta) {
		setY(yPos + ySpeed * delta);
	}				
	
	protected void setYSpeed(float ySpeed) {
		this.ySpeed = ySpeed;
	}
	
	public float getYSpeed() {
		return ySpeed;
	}
	
	public static PowerUp randomPowerUp(float xPos, float yPos)
			throws SlickException {
		Random rand = new Random();	
		switch(rand.nextInt(6)){
		case 0:
			return new ExtendRacket(xPos, yPos);
		case 1:
			return new RetractRacket(xPos, yPos);		
		case 2:
			return new MultiplyBalls(xPos, yPos);
		case 3:
			return new Speed(xPos, yPos, 1);		
		case 4:
			return new Speed(xPos, yPos, -1);
		case 5:
			return new pewPewLasers(xPos, yPos);
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
//			ArrayList<Ball> balls = level.getBalls();
//			int k = balls.size();
//			float inc = 0.3f;
//			for(int i = 0; i < k; i++) {
//				Ball ball = balls.get(i);
//				float xSpeed = ball.getXSpeed();
//				float ySpeed = ball.getYSpeed();
//				ball.incrementXSpeed(inc);
//				ball.incrementYSpeed(-1 * inc);
//				
//				Ball ball2 = new Ball(ball.getX(), ball.getY());
//				ball2.setXSpeed(xSpeed - inc);
//				ball2.setYSpeed(ySpeed + inc);
//				
//				balls.add(ball2);
//			}
			
			ArrayList<Ball> balls = level.getBalls();
			int size = balls.size();
			for (int i = 0; i < size; i++) {
				Ball currBall = balls.get(i);
				Ball newBall = new Ball(currBall.getX(), currBall.getY());
				newBall.setXSpeed(-currBall.getXSpeed());
				newBall.setYSpeed(currBall.getYSpeed());
				balls.add(newBall);
			}
		}
	}
	
	public static class Speed extends PowerUp {
		private float multiplier;
		
		public Speed(float xPos, float yPos, int dir) throws SlickException {
			super(xPos, yPos, new Image ("data/speed+.png"));		
			if(dir > 0)
				multiplier = 1.5f;
			else {
				multiplier = 0.5f;
				setImage(new Image("data/speed-.png"));
			}
		}	
		
		public void effect(LevelHandler level) {
			ArrayList<Ball> balls = level.getBalls();
			for(Ball ball : balls) {
				ball.setXSpeed(multiplier * ball.getXSpeed());
				ball.setYSpeed(multiplier * ball.getYSpeed());
			}
			ArrayList<PowerUp> pus = level.getPowerUps();
			for(PowerUp pu : pus) {
				pu.setYSpeed(multiplier * pu.getYSpeed());
			}
		}
	}
	
	public static class pewPewLasers extends PowerUp {
		private float shots = 20;
		private Racket racket;
		private ArrayList<Ball> balls;
		
		public pewPewLasers(float xPos, float yPos) throws SlickException {
			super(xPos, yPos, new Image ("data/lasers.png"));
		}
		
		public void effect(LevelHandler level) throws SlickException {
			balls = level.getBalls();
			racket = level.getRacket();
			racket.addLasers(this);
		}
		
		public void shot() throws SlickException {
			shots--;
			if(shots == 0)
				racket.removeLasers();				
			balls.add(new laserShot());
		}
		
		class laserShot extends Ball {
			public laserShot() throws SlickException {
				super(racket.xPos-20, racket.yPos-20);
				setImage(new Image("data/lasershot.png"));
				setXSpeed(0f);
				setYSpeed(-.2f);
				balls.add(this);
			}
		}
	}
	
}