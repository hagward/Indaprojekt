import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 * @author Anders Hagward
 * @author Fredrik Hillnertz
 * @version 2010-05-06
 */
public class LevelHandler {
	private TiledMap tiledMap;
	private ArrayList<Ball> balls;
	private ArrayList<Block> blocks;
	private ArrayList<PowerUp> powerUps;
	private ArrayList<Ball> extraBalls;
	private ArrayList<Effect> animations;
	private Racket racket;
	private Player player;
	private int currentLevel;

	public LevelHandler() throws SlickException {
		currentLevel = 0;
	}

	public void nextLevel() throws SlickException {
		currentLevel++;
		nextLevel(currentLevel);
	}

	public void restartLevel() throws SlickException {
		nextLevel(currentLevel);
	}

	public void nextLevel(int level) throws SlickException {
		String levelPath = "data/levels/level" + level + ".tmx";
		currentLevel = level;
		tiledMap = new TiledMap(levelPath);
		blocks = new ArrayList<Block>();
		resetLevel();
		generateBlockList();
	}

	private void resetLevel() {
		powerUps = new ArrayList<PowerUp>();
		extraBalls = new ArrayList<Ball>();
		animations = new ArrayList<Effect>();
		racket = new Racket(400, 550);
		balls = new ArrayList<Ball>();
		balls.add(new Ball(350, 400));
	}

	public void reset() {
		try {
			player = new Player(3);
			nextLevel(1);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void generateBlockList() throws SlickException {
		ArrayList<Block> blocks = new ArrayList<Block>();
		int tileWidth = tiledMap.getTileWidth();
		int tileHeight = tiledMap.getTileHeight();
		for (int xAxis = 0; xAxis < tiledMap.getWidth(); xAxis++) {
			for (int yAxis = 0; yAxis < tiledMap.getHeight(); yAxis++) {
				int tileID = tiledMap.getTileId(xAxis, yAxis, 0);
				String healthVal = tiledMap.getTileProperty(tileID, "health",
						"0");
				int health = Integer.parseInt(healthVal);
				String puVal = tiledMap
						.getTileProperty(tileID, "powerUp", "-1");
				int powerUp = Integer.parseInt(puVal);
				if (health > 0) {
					blocks.add(new Block(tileWidth * xAxis, tileHeight * yAxis,
							health, powerUp));

				}
			}
		}
		this.blocks = blocks;
	}

	public void renderCurrentLevel() {
		racket.draw();
		for (Ball ball : balls) {
			ball.draw();
		}
		for (Block block : blocks) {
			if (block.getHealth() > 0) {
				block.draw();
			}
		}
		for (PowerUp pu : powerUps) {
			pu.draw();
		}
		for (Animation animation : animations) {
			animation.draw();
		}

	}

	public void updateCurrentLevel(GameplayState gs, int delta, GameContainer gc)
			throws SlickException {
		player.getScore().addPoints(-1);
		Iterator<Ball> ballIterator = balls.iterator();
		while (ballIterator.hasNext()) {
			Ball ball = ballIterator.next();
			ball.move(delta);

			// wall collision
			if ((ball.getX() < 0 || ball.getMaxX() > 800)
					|| (ball.getY() < 0 || (ball.getMaxY() > 600))) {
				if (ball.getX() < 0) {
					ball.setXSpeed(-ball.getXSpeed());
					ball.setX(0);
				} else if (ball.getMaxX() > 800) {
					ball.setXSpeed(-ball.getXSpeed());
					ball.setX(800 - (2 * ball.getRadius()));
				}
				if (ball.getY() < 0) {
					ball.setY(0);
					ball.setYSpeed(-ball.getYSpeed());
				} else if (ball.getY() > 600) {
					if (balls.size() <= 1) {
						gs.getSounds().death();
						player.decreaseLives();
						resetLevel();
						gs.setState(GameplayState.State.START);
					} else {
						ball.setXSpeed(0);
						ball.setYSpeed(0);
					}
				}
			} else if (ball.intersects(racket)) { // racket collision

				gs.getSounds().bounce();
				if (ball.getY() < racket.getY()) {
					float newXSpeed = ball.getCenterX() - racket.getCenterX();
					newXSpeed /= 2.0f * racket.getWidth();
					ball.setXSpeed(newXSpeed);
					ball.setYSpeed(-ball.getYSpeed());
					ball.setY(racket.getY() - (2 * ball.getRadius()));
				} else if (ball.getMaxY() > racket.getMaxY()) {
					ball.setYSpeed(-ball.getYSpeed());
					ball.setY(racket.getMaxY());
				} else if (ball.getX() < racket.getX()) {
					ball.setX(racket.getX() - (2 * ball.getRadius()));
					ball.setXSpeed(-ball.getXSpeed());
				} else if (ball.getMaxX() > racket.getMaxX()) {
					ball.setXSpeed(-ball.getXSpeed());
					ball.setX(racket.getMaxX());
				}
			} else { // block collision
				Iterator<Block> blockIterator = blocks.iterator();
				boolean collided = false;
				while (blockIterator.hasNext() && !collided) {
					Block currBlock = blockIterator.next();
					if ((currBlock.getHealth() > 0)
							&& ball.intersects(currBlock)) {
						float cX = ball.getCenterX();
						float cY = ball.getCenterY();
						if (cX < currBlock.getX()
								&& ball.insideYArea(currBlock.getY() - 20,
										currBlock.getMaxY() + 20)) {
							ball.setXSpeed(-ball.getXSpeed());
							ball
									.setX(currBlock.getX()
											- (2 * ball.getRadius()));
						} else if (cX > currBlock.getMaxX()
								&& ball.insideYArea(currBlock.getY() - 20,
										currBlock.getMaxY() + 20)) {
							ball.setXSpeed(-ball.getXSpeed());
							ball.setX(currBlock.getMaxX());
						} else if (cY < currBlock.getY()
								&& ball.insideXArea(currBlock.getX() - 20,
										currBlock.getMaxX() + 20)) {
							ball.setYSpeed(-ball.getYSpeed());
							ball
									.setY(currBlock.getY()
											- (2 * ball.getRadius()));
						} else if (cY > currBlock.getMaxY()
								&& ball.insideXArea(currBlock.getX() - 20,
										currBlock.getMaxX() + 20)) {
							ball.setYSpeed(-ball.getYSpeed());
							ball.setY(currBlock.getMaxY());
						}

						currBlock.hit();
						gs.getSounds().bounce();

						if (currBlock.getHealth() <= 0) {
							spawnPowerUp(currBlock);
							player.getScore().addPoints(1000);
							blockIterator.remove();
						}

						collided = true;
					}
				}
			}

			// removes stationary balls (and laser shots after hits)
			if (ball.getXSpeed() == 0 && ball.getYSpeed() == 0)
				ballIterator.remove();
		}

		balls.addAll(extraBalls);
		extraBalls.clear();

		if (balls.size() <= 0) {
			gs.getSounds().death();
			player.decreaseLives();
			resetLevel();
			gs.setState(GameplayState.State.START);
		}

		Iterator<PowerUp> it = powerUps.iterator();
		while (it.hasNext()) {
			PowerUp pu = it.next();
			pu.move(delta);
			if (pu.intersects(racket)) {
				pu.effect(this);
				it.remove();
			} else if (pu.getY() > 600) {
				it.remove();
			}
		}

		Iterator<Effect> it2 = animations.iterator();
		while (it2.hasNext()) {
			Effect ef = it2.next();
			if (ef.getFrame() == ef.getNFrames())
				it2.remove();
		}

		// Skjut?
		if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)
				&& racket.getLaser() != null) {
			racket.getLaser().shot();
			gs.getSounds().laser();
		}
	}

	public boolean checkLevelBeaten() {
		return (blocks.size() <= 0);
	}

	public void idle() {
		for (Ball ball : balls) {
			ball.setCenterX(racket.getCenterX());
			ball.setY(racket.getY() - (2 * ball.getRadius()));
		}
	}

	public void updateRacket(int xPos) {
		racket.setX(xPos - racket.getWidth() / 2);
	}

	public Racket getRacket() {
		return racket;
	}

	public ArrayList<Ball> getBalls() {
		return balls;
	}

	private void spawnPowerUp(Block block) throws SlickException {
		PowerUp pu = PowerUp.randomPowerUp(block);
		if (pu != null)
			powerUps.add(pu);

	}

	public ArrayList<PowerUp> getPowerUps() {
		return powerUps;
	}

	public ArrayList<Ball> getExtraBalls() {
		return extraBalls;
	}

	public ArrayList<Effect> getAnimations() {
		return animations;
	}

	public Player getPlayer() {
		return player;
	}
}
