import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public abstract class PowerUp extends Rectangle implements Movable {
	public static int EXTEND = 0, RETRACT = 1, ADD_BALL = 2, PSPEED = 3,
			MSPEED = 4, LASER = 5, PLIFE = 6, MLIFE = 7, FIREBALL = 8;

	protected int duration;
	protected final float xSpeed;
	protected float ySpeed;
	protected Image image;

	protected PowerUp(float x, float y, Image image) {
		super(x, y, image.getWidth(), image.getHeight());
		xSpeed = 0;
		ySpeed = 0.2f;
		this.image = image;
	}

	public abstract void effect(LevelHandler level) throws SlickException;

	@Override
	public void move(int delta) {
		y += ySpeed * delta;
	}

	@Override
	public void setXSpeed(float xSpeed) {
		return;
	}

	@Override
	public void setYSpeed(float ySpeed) {
		this.ySpeed = ySpeed;
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
		return;
	}

	@Override
	public void incrementYSpeed(float increment) {
		ySpeed += increment;
	}

	public void draw() {
		image.draw(x, y);
	}
	
	public static String powerUpPath(int i) {
		switch(i) {
		case 0:
			return "data/ExtendRacket.png";
		case 1:
			return "data/RetractRacket.png";
		case 2:
			return "data/multiplyballs.png";
		case 3:
			return "data/speed+.png";
		case 4:
			return "data/speed-.png";
		case 5:
			return "data/lasers.png";
		case 6:
			return "data/life+.png";
		case 7:
			return "data/life-.png";
		case 8:
			return "data/fireballs.png";
		}
		return null;
	}

	public static PowerUp randomPowerUp(Block block)
			throws SlickException {
		float xPos = block.getX();
		float yPos = block.getY();
		int r = block.getPowerUp();
		if(r < 0) {
			Random rand = new Random();
			if(rand.nextInt(10) == 0)
				return null;
			r = rand.nextInt(9);
		}			
		switch (r) {
		case 0:
			return new ExtendRacket(xPos, yPos);
		case 1:
			return new RetractRacket(xPos, yPos);
		case 2:
			return new AddBall(xPos, yPos);
		case 3:
			return new Speed(xPos, yPos, 1);
		case 4:
			return new Speed(xPos, yPos, -1);
		case 5:
			return new PewPewLasers(xPos, yPos);
		case 6:
			return new Life(xPos, yPos, 1);
		case 7:
			return new Life(xPos, yPos, -1);
		case 8:
			return new Fireballs(xPos, yPos);
		}
		return null;
	}

	public static class RetractRacket extends PowerUp {

		public RetractRacket(float xPos, float yPos) throws SlickException {
			super(xPos, yPos, new Image("data/RetractRacket.png"));
		}

		@Override
		public void effect(LevelHandler level) throws SlickException {
			level.getRacket().decreaseSize();
		}
	}

	public static class ExtendRacket extends PowerUp {

		public ExtendRacket(float xPos, float yPos) throws SlickException {
			super(xPos, yPos, new Image("data/ExtendRacket.png"));
		}

		@Override
		public void effect(LevelHandler level) throws SlickException {
			level.getRacket().increaseSize();
		}
	}

	public static class AddBall extends PowerUp {

		public AddBall(float xPos, float yPos) throws SlickException {
			super(xPos, yPos, new Image("data/multiplyballs.png"));
		}

		@Override
		public void effect(LevelHandler level) throws SlickException {
			ArrayList<Ball> balls = level.getBalls();
			if (balls.size() > 0) {
				Ball origBall = balls.get(0);
				Ball newBall = new Ball(origBall.getX(), origBall.getY());
				newBall.setXSpeed(-origBall.getXSpeed());
				newBall.setYSpeed(origBall.getYSpeed());
				balls.add(newBall);
			}
		}
	}

	public static class Speed extends PowerUp {
		private float multiplier;

		public Speed(float xPos, float yPos, int dir) throws SlickException {
			super(xPos, yPos, new Image("data/speed+.png"));
			if (dir > 0)
				multiplier = 1.5f;
			else {
				multiplier = 0.5f;
				image = new Image("data/speed-.png");
			}
		}

		public void effect(LevelHandler level) {
			ArrayList<Ball> balls = level.getBalls();
			for (Ball ball : balls) {
				ball.setXSpeed(multiplier * ball.getXSpeed());
				ball.setYSpeed(multiplier * ball.getYSpeed());
			}
			ArrayList<PowerUp> pus = level.getPowerUps();
			for (PowerUp pu : pus) {
				pu.setYSpeed(multiplier * pu.getYSpeed());
			}
		}
	}

	public static class PewPewLasers extends PowerUp {
		private float shots = 20;
		private Racket racket;
		private ArrayList<Ball> balls;

		public PewPewLasers(float xPos, float yPos) throws SlickException {
			super(xPos, yPos, new Image("data/lasers.png"));
		}

		public void effect(LevelHandler level) throws SlickException {
			balls = level.getBalls();
			racket = level.getRacket();
			racket.addLasers(this);
		}

		public void shot() throws SlickException {
			shots--;
			if (shots == 0)
				racket.removeLasers();
			balls.add(new LaserShot());
		}

		class LaserShot extends Ball {
			public LaserShot() throws SlickException {
				super(racket.getCenterX(), racket.getY() - 20);
				this.image = new Image("data/lasershot.png");
				setXSpeed(0f);
				setYSpeed(-.2f);
				balls.add(this);
			}

			public void setYSpeed(float ySpeed) {
				if (ySpeed == -this.getYSpeed())
					this.ySpeed = 0;
				else
					this.ySpeed = ySpeed;
			}
		}
	}

	public static class Life extends PowerUp {
		private int plusMinus;

		public Life(float xPos, float yPos, int plusMinus)
				throws SlickException {
			super(xPos, yPos, new Image("data/life+.png"));
			this.plusMinus = plusMinus;
			if (plusMinus < 0)
				image = new Image("data/life-.png");
		}

		public void effect(LevelHandler level) throws SlickException {
			if (plusMinus < 0)
				level.getPlayer().decreaseLives();
			else
				level.getPlayer().increaseLives();
		}
	}

	public static class Fireballs extends PowerUp {
		private ArrayList<Ball> balls;
		private ArrayList<Ball> extraBalls;
		private ArrayList<Effect> animations;

		public Fireballs(float xPos, float yPos) throws SlickException {
			super(xPos, yPos, new Image("data/fireballs.png"));
		}

		public void effect(LevelHandler level) throws SlickException {
			this.balls = level.getBalls();
			this.extraBalls = level.getExtraBalls();
			this.animations = level.getAnimations();
			Ball ball = balls.get(0);

			float xSpeed = ball.getXSpeed();
			float ySpeed = ball.getYSpeed();
			float xPos = ball.getCenterX();
			float yPos = ball.getCenterY();

			ball.setXSpeed(0);
			ball.setYSpeed(0);

			Fireball fireball = new Fireball(xPos, yPos);
			fireball.setXSpeed(xSpeed);
			fireball.setYSpeed(ySpeed);

			balls.add(fireball);
		}

		class Fireball extends Ball {
			private int hits = 5;

			public Fireball(float centerPointX, float centerPointY)
					throws SlickException {
				super(centerPointX, centerPointY);
				this.image = new Image("data/fireball.png");
			}

			public void setXSpeed(float xSpeed) {
				if (xSpeed == -this.xSpeed) {
					hits--;
					ArrayList<Ball> newBalls = null;
					try {
						newBalls = creatBalls();
						createEffect();
					} catch (SlickException e) {
					}
					extraBalls.addAll(newBalls);
				}
				this.xSpeed = xSpeed;
				if (hits <= 0)
					removeFireball();
			}

			public void setYSpeed(float ySpeed) {
				if (ySpeed == -this.ySpeed) {
					hits--;
					ArrayList<Ball> newBalls = null;
					try {
						newBalls = creatBalls();
						createEffect();
					} catch (SlickException e) {
					}
					extraBalls.addAll(newBalls);
				}
				this.ySpeed = ySpeed;
				if (hits <= 0)
					removeFireball();
			}

			private void removeFireball() {
				Ball ball = new Ball(this.x, this.y);
				ball.setXSpeed(this.xSpeed);
				ball.setYSpeed(this.ySpeed);
				this.xSpeed = 0;
				this.ySpeed = 0;
				extraBalls.add(ball);
			}

			private ArrayList<Ball> creatBalls() throws SlickException {
				float x = this.getCenterX();
				float y = this.getCenterY();
				ArrayList<Ball> balls = new ArrayList<Ball>();

				class InvisibleBall extends Ball {
					public InvisibleBall(float x, float y)
							throws SlickException {
						super(x, y);
						this.image = new Image("data/ball.png");
					}

					public void setXSpeed(float x) {
						this.xSpeed = 0;
					}

					public void setYSpeed(float y) {
						this.ySpeed = 0;
					}

					public void setX(float x) {
					}

					public void setY(float y) {
					}

				}
				InvisibleBall ball1 = new InvisibleBall(x - 30, y - 14);
				InvisibleBall ball2 = new InvisibleBall(x - 30, y);
				InvisibleBall ball3 = new InvisibleBall(x - 30, y + 14);
				InvisibleBall ball4 = new InvisibleBall(x, y - 14);
				InvisibleBall ball5 = new InvisibleBall(x, y + 14);
				InvisibleBall ball6 = new InvisibleBall(x + 30, y - 14);
				InvisibleBall ball7 = new InvisibleBall(x + 30, y);
				InvisibleBall ball8 = new InvisibleBall(x + 30, y + 14);

				balls.add(ball1);
				balls.add(ball2);
				balls.add(ball3);
				balls.add(ball4);
				balls.add(ball5);
				balls.add(ball6);
				balls.add(ball7);
				balls.add(ball8);
				for (Ball ball : balls)
					ball.setYSpeed(0);
				return balls;
			}

			private void createEffect() throws SlickException {
				animations.add(new Effect.FireSplash(x - 30, y - 14));
			}
		}
	}
}