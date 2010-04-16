import java.util.ArrayList;
import java.util.Iterator;

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
	private Racket racket;
	private int currentLevel = 1;

	public LevelHandler() throws SlickException {
		racket = new Racket(400, 550, 1);
		balls = new ArrayList<Ball>();
		balls.add(new Ball(400, 534));
		blocks = new ArrayList<Block>();
		nextLevel();
	}

	public void nextLevel() throws SlickException {
		String levelPath = "data/level" + currentLevel + ".tmx";
		tiledMap = new TiledMap(levelPath);
		generateBlockList();
		currentLevel++;
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
	}

	public void updateCurrentLevel() {
		for (Ball ball : balls) {
			ball.move();

			// Kollision med väggar och tak
			if (ball.getX() <= 0 || ball.getX() >= 800)
				ball.setXSpeed(-ball.getXSpeed());
			if (ball.getY() <= 0 || ball.getY() >= 600)
				ball.setYSpeed(-ball.getYSpeed());

			// Kollision med racket
			if (ball.collidesWithTop(racket))
				racketCollision(ball);

			// Kollision med block
			Iterator<Block> it = blocks.iterator();
			while (it.hasNext()) {
				Block block = it.next();
				if (block.getHealth() > 0) {
					if (ball.collidesWithLeft(block)
							|| ball.collidesWithRight(block)) {
						ball.setXSpeed(-ball.getXSpeed());
						block.hit();
					} else if (ball.collidesWithTop(block)
							|| ball.collidesWithBottom(block)) {
						ball.setYSpeed(-ball.getYSpeed());
						block.hit();
					}
				}
				if (block.getHealth() <= 0)
					it.remove();
			}
		}
	}
	
	public boolean checkLevelBeaten() {
		return (blocks.size() <= 0);
	}
	
	public void idle() {
		for(Ball ball : balls) {
			ball.setX(racket.getX() + racket.getWidth() / 2);
			ball.setY(racket.getY() - ball.getRadius());
		}
	}
	
	public void updateRacket(int xPos) {
		racket.setX(xPos - racket.getWidth() / 2);
	}
	
	// TODO: Fixa så att den bollen studsar olika beroende på var på racketet
	// den träffar.
	private void racketCollision(Ball ball) {
		ball.setYSpeed(-ball.getYSpeed());
	}
}