import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 * @author Anders Hagward
 * @author Fredrik Hillnertz
 * @version 2010-04-16
 */
public class LevelHandler {
	private TiledMap tiledMap;
	private ArrayList<Ball> balls;
	private ArrayList<Block> blocks;
	private ArrayList<PowerUp> powerUps;
	private Racket racket;
	private int currentLevel;

	public LevelHandler() throws SlickException {
		currentLevel = 0;
		nextLevel();
	}

	public void nextLevel() throws SlickException {
		currentLevel++;
		
		String levelPath = "data/level" + currentLevel + ".tmx";
		tiledMap = new TiledMap(levelPath);
		blocks = new ArrayList<Block>();
		powerUps = new ArrayList<PowerUp>();
		racket = new Racket(400, 550);
		balls = new ArrayList<Ball>();
		balls.add(new Ball(300, 400));
		
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
	}

	public void updateCurrentLevel(Score score, int delta, GameContainer gc)
			throws SlickException {
		Iterator<Ball> ballIterator = balls.iterator();
		while (ballIterator.hasNext()) {
			Ball ball = ballIterator.next();
			ball.move(delta);

			// wall collision
			if (ball.getX() < 0) {
				ball.setX(0);
				ball.setXSpeed(-ball.getXSpeed());
			} else if (ball.getRightX() > 800) {
				ball.setX(800 - ball.getRadius());
				ball.setXSpeed(-ball.getXSpeed());
			}
			if (ball.getY() < 0) {
				ball.setY(0);
				ball.setYSpeed(-ball.getYSpeed());
			} else if (ball.getBottomY() > 600) {
				ball.setY(600 - ball.getRadius());
				ball.setYSpeed(-ball.getYSpeed());
			}

			// racket collision
			if (ball.intersects(racket)) {
				if (ball.getMaxY() > racket.getY()) {
					//ball.setY(racket.getY() - ball.getRadius());
					ball.setYSpeed(-ball.getYSpeed());
				} else if (ball.getY() < racket.getMaxY()) {
					//ball.setY(racket.getBottomY());
					ball.setYSpeed(-ball.getYSpeed());
				} else if (ball.getX() < racket.getMaxX()) {
					//ball.setX(racket.getRightX());
					ball.setXSpeed(-ball.getXSpeed());
				} else if (ball.getMaxX() > racket.getX()) {
					ball.setX(racket.getX() - ball.getRadius());
					//ball.setXSpeed(-ball.getXSpeed());
				}
			}

			// block collision
			Iterator<Block> blockIterator = blocks.iterator();
			boolean collided = false;
			while (blockIterator.hasNext() && !collided) {
				Block currBlock = blockIterator.next();
				if ((currBlock.getHealth() > 0)
						&& ball.intersects(currBlock)) {
					if (ball.getX() < currBlock.getMaxX()) {
						//ball.setX(currBlock.getRightX());
						ball.setXSpeed(-ball.getXSpeed());
					} else if (ball.getMaxX() > currBlock.getX()) {
						//ball.setX(currBlock.getX() - ball.getRadius());
						ball.setXSpeed(-ball.getXSpeed());
					}
					if (ball.getY() < currBlock.getMaxY()) {
						//ball.setY(currBlock.getMaxY());
						ball.setYSpeed(-ball.getYSpeed());
					} else if (ball.getMaxY() > currBlock.getY()) {
						//ball.setY(currBlock.getY() - ball.getRadius());
						ball.setYSpeed(-ball.getYSpeed());
					}
					
					currBlock.hit();

					if (currBlock.getHealth() <= 0) {
						spawnPowerUp(currBlock);
						score.addPoints(1);
						blockIterator.remove();
					}
					
					collided = true;
				}
			}
			//tar bort stillaståend bollar, och laserskott som träffat ngt
			if(ball.getXSpeed() == 0 && ball.getYSpeed() == 0)
				ballIterator.remove();

			// Kollision med block
//			Iterator<Block> it = blocks.iterator();
//			while (it.hasNext()) {
//				Block block = it.next();
//				if (block.getHealth() > 0) {
//					if (ball.collidesWithLeft(block)
//							|| ball.collidesWithRight(block)) {
//						ball.setXSpeed(-ball.getXSpeed());
//						block.hit();
//					} else if (ball.collidesWithTop(block)
//							|| ball.collidesWithBottom(block)) {
//						ball.setYSpeed(-ball.getYSpeed());
//						block.hit();
//					}
//				}
//				if (block.getHealth() <= 0) {
//					score.addPoints(1);
//					spawnPowerUp(block);
//					it.remove();
//				}
//			}
		}

		Iterator<PowerUp> it = powerUps.iterator();
		while(it.hasNext()) {
			PowerUp pu = it.next();
			pu.move(delta);
			if(pu.intersects(racket)) {
				pu.effect(this);
				it.remove();
			}
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
			ball.setX(100);
			ball.setY(400);
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
		if(rand.nextInt(2) == 0) {
			PowerUp pu = PowerUp.randomPowerUp(block.getX(), block.getY());
			if(pu != null)
				powerUps.add(pu);
		}
	}

	public ArrayList<PowerUp> getPowerUps() {
		// TODO Auto-generated method stub
		return powerUps;
	}
}