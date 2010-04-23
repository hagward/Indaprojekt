import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

/**
 * @author Anders Hagward
 * @author Fredrik Hillnertz
 * @version 2010-04-21
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

	public LevelHandler(Player player) throws SlickException {
		currentLevel = 0;
		this.player = player;
		nextLevel();
	}
	
	public void nextLevel() throws SlickException {
		currentLevel++;
		nextLevel(currentLevel);
	}
	
	public void restartLevel() throws SlickException {
		nextLevel(currentLevel);
	}
	
	private void nextLevel(int level) throws SlickException {
		String levelPath = "data/level" + level + ".tmx";
		tiledMap = new TiledMap(levelPath);
		blocks = new ArrayList<Block>();
		powerUps = new ArrayList<PowerUp>();
		racket = new Racket(400, 550);
		balls = new ArrayList<Ball>();
		extraBalls = new ArrayList<Ball>();
		animations = new ArrayList<Effect>();
		balls.add(new Ball(350, 400));
		
		generateBlockList();
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
				if (health > 0) {
					blocks.add(new Block(tileWidth * xAxis, tileHeight * yAxis,
							health));
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
						player.decreaseLives();
						restartLevel();
						gs.setState(GameplayState.State.START);
					} else {
						ball.setXSpeed(0);
						ball.setYSpeed(0);
					}
				}
			} else if (ball.intersects(racket)) { // racket collision
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
//						if (ball.getY() < currBlock.getY()) {
//							ball.setYSpeed(-ball.getYSpeed());
//							ball.setY(currBlock.getY() - (2 * ball.getRadius()));
//						} else if (ball.getMaxY() > currBlock.getMaxY()) {
//							ball.setYSpeed(-ball.getYSpeed());
//							ball.setY(currBlock.getMaxY());
//						}
//						if (ball.getX() < currBlock.getX()) {
//							ball.setXSpeed(-ball.getXSpeed());
//							ball.setX(currBlock.getX() - (2 * ball.getRadius()));
//						} else if (ball.getMaxX() > currBlock.getMaxX()) {
//							ball.setXSpeed(-ball.getXSpeed());
//							ball.setX(currBlock.getMaxX());
//						}
						
						float cX = ball.getCenterX();
						float cY = ball.getCenterY();
						if (cX < currBlock.getX()
								&& ball.insideYArea(currBlock.getY(), currBlock.getMaxY())) {
							ball.setXSpeed(-ball.getXSpeed());
							ball.setX(currBlock.getX() - (2 * ball.getRadius()));
						} else if (cX > currBlock.getMaxX()
								&& ball.insideYArea(currBlock.getY(), currBlock.getMaxY())) {
							ball.setXSpeed(-ball.getXSpeed());
							ball.setX(currBlock.getMaxX());
						} else if (cY < currBlock.getY()
								&& ball.insideXArea(currBlock.getX(), currBlock.getMaxX())) {
							ball.setYSpeed(-ball.getYSpeed());
							ball.setY(currBlock.getY() - (2 * ball.getRadius()));
						} else if (cY > currBlock.getMaxY()
								&& ball.insideXArea(currBlock.getX(), currBlock.getMaxX())) {
							ball.setYSpeed(-ball.getYSpeed());
							ball.setY(currBlock.getMaxY());
						}
							
						currBlock.hit();

						if (currBlock.getHealth() <= 0) {
							spawnPowerUp(currBlock);
							player.addScorePoints(1);
							blockIterator.remove();
						}
						
						collided = true;
					}
				}
			}
			
			// removes stationary balls (and laser shots after hits)
			if(ball.getXSpeed() == 0 && ball.getYSpeed() == 0)
				ballIterator.remove();
		}
		balls.addAll(extraBalls);
		extraBalls.clear();
		
		Iterator<PowerUp> it = powerUps.iterator();
		while(it.hasNext()) {
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
		while(it2.hasNext()) {
			Effect ef = it2.next();
			if(ef.getFrame() == 5)
				it2.remove();
		}

		//Skjut?
		if(gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)
				&& racket.getLaser() != null) 
			racket.getLaser().shot();			
	}
	
	public boolean checkLevelBeaten() {
		return (blocks.size() <= 0);
	}
	
	public void idle() {
		for(Ball ball : balls) {
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
		Random rand = new Random();
		if(rand.nextInt(1) == 0) {
			PowerUp pu = PowerUp.randomPowerUp(block.getX(), block.getY());
			if(pu != null)
				powerUps.add(pu);
		}
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