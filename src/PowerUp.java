import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

/**
 * @author Anders Hagward
 * @author Fredrik Hillnertz
 * @version 2010-05-06
 */
public abstract class PowerUp extends Rectangle implements Movable {
	private static final long serialVersionUID = 3337170896734989953L;
	public static int EXTEND = 0, RETRACT = 1, ADD_BALL = 2, PSPEED = 3,
			MSPEED = 4, LASER = 5, PLIFE = 6, MLIFE = 7, FIREBALL = 8;

	protected int duration;
	protected float xSpeed;
	protected float ySpeed;
	protected Image image;

	protected PowerUp(float x, float y, Image image) {
		super(x, y, image.getWidth(), image.getHeight());
		xSpeed = 0;
		ySpeed = 0.15f;
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

	public static String powerUpImageName(int i) {
		switch (i) {
		case 0:
			return "ExtendRacket.png";
		case 1:
			return "RetractRacket.png";
		case 2:
			return "multiplyballs.png";
		case 3:
			return "speed+.png";
		case 4:
			return "speed-.png";
		case 5:
			return "lasers.png";
		case 6:
			return "life+.png";
		case 7:
			return "life-.png";
		case 8:
			return "fireballs.png";
		}
		return null;
	}

	public static PowerUp randomPowerUp(Block block) throws SlickException {
		float xPos = block.getX();
		float yPos = block.getY();
		int r = block.getPowerUp();
		if (r < 0) {
			Random rand = new Random();
			if (rand.nextInt(4) != 0)
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
		private static final long serialVersionUID = -2425503375922462872L;

		public RetractRacket(float xPos, float yPos) throws SlickException {
			super(xPos, yPos,
					ResourceLoader.getInstance().getImage("RetractRacket.png"));
		}

		@Override
		public void effect(LevelHandler level) throws SlickException {
			level.getRacket().decreaseSize();
		}
	}

	public static class ExtendRacket extends PowerUp {
		private static final long serialVersionUID = -4958823379809789717L;

		public ExtendRacket(float xPos, float yPos) throws SlickException {
			super(xPos, yPos,
					ResourceLoader.getInstance().getImage("ExtendRacket.png"));
		}

		@Override
		public void effect(LevelHandler level) throws SlickException {
			level.getRacket().increaseSize();
		}
	}

	public static class AddBall extends PowerUp {
		private static final long serialVersionUID = -6550943363444882422L;

		public AddBall(float xPos, float yPos) throws SlickException {
			super(xPos, yPos,
					ResourceLoader.getInstance().getImage("multiplyballs.png"));
		}

		@Override
		public void effect(LevelHandler level) throws SlickException {
			ArrayList<Ball> balls = level.getBalls();
			if (balls.size() > 0) {
				Ball origBall = balls.get(0);
				Ball newBall = new Ball(origBall.getX(), origBall.getY());
				newBall.setXSpeed(-origBall.getXSpeed());
				newBall.setYSpeed(origBall.getYSpeed());
				Ball newBall2 = new Ball(origBall.getX(), origBall.getY());
				newBall2.setXSpeed(origBall.getXSpeed());
				newBall2.setYSpeed(-origBall.getYSpeed());
				balls.add(newBall);
				balls.add(newBall2);
			}
		}
	}

	public static class Speed extends PowerUp {
		private static final long serialVersionUID = -8861061013150381349L;
		private float multiplier;

		public Speed(float xPos, float yPos, int dir) throws SlickException {
			super(xPos, yPos,
					ResourceLoader.getInstance().getImage("speed+.png"));
			if (dir > 0) {
				multiplier = 1.5f;
			} else {
				multiplier = 0.5f;
				image = ResourceLoader.getInstance().getImage("speed-.png");
			}
		}

		@Override
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
		private static final long serialVersionUID = -1339389505069960388L;
		private float shots = 20;
		private Racket racket;
		private ArrayList<Ball> balls;

		public PewPewLasers(float xPos, float yPos) throws SlickException {
			super(xPos, yPos,
					ResourceLoader.getInstance().getImage("lasers.png"));
		}

		@Override
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
			private static final long serialVersionUID = 8834007952965136151L;

			public LaserShot() throws SlickException {
				super(racket.getCenterX(), racket.getY() - 20);
				this.image = ResourceLoader.getInstance().getImage(
						"lasershot.png");
				setXSpeed(0f);
				setYSpeed(-.2f);
				balls.add(this);
			}

			@Override
			public void setYSpeed(float ySpeed) {
				if (ySpeed == -this.getYSpeed())
					this.ySpeed = 0;
				else
					this.ySpeed = ySpeed;
			}
		}
	}

	public static class Life extends PowerUp {
		private static final long serialVersionUID = -804012507251184235L;
		private int plusMinus;

		public Life(float xPos, float yPos, int plusMinus)
				throws SlickException {
			super(xPos, yPos,
					ResourceLoader.getInstance().getImage("life+.png"));
			this.plusMinus = plusMinus;
			if (plusMinus < 0)
				image = ResourceLoader.getInstance().getImage("life-.png");
		}

		@Override
		public void effect(LevelHandler level) throws SlickException {
			if (plusMinus < 0)
				level.getPlayer().decreaseLives();
			else
				level.getPlayer().increaseLives();
		}
	}

	public static class Fireballs extends PowerUp {
		private static final long serialVersionUID = -6934067609858958243L;
		private ArrayList<Ball> balls;
		private ArrayList<Ball> extraBalls;
		private ArrayList<Effect> animations;

		public Fireballs(float xPos, float yPos) throws SlickException {
			super(xPos, yPos,
					ResourceLoader.getInstance().getImage("fireballs.png"));
		}

		@Override
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
			private static final long serialVersionUID = -5400086564573984130L;
			private int hits = 6;

			public Fireball(float centerPointX, float centerPointY)
					throws SlickException {
				super(centerPointX, centerPointY);
				this.image = ResourceLoader.getInstance().getImage(
						"fireball.png");
			}

			@Override
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

			@Override
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
					private static final long serialVersionUID = -1950391023146557602L;

					public InvisibleBall(float x, float y)
							throws SlickException {
						super(x, y);
						this.image = ResourceLoader.getInstance().getImage(
								"invisibleball.png");
					}

					@Override
					public void setXSpeed(float x) {
						this.xSpeed = 0;
					}

					@Override
					public void setYSpeed(float y) {
						this.ySpeed = 0;
					}

					@Override
					public void setX(float x) {
					}

					@Override
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
