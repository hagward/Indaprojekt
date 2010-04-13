import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

/**
 * @author Anders Hagward
 * @author Fredrik Hillnertz
 * @version 2010-04-08
 */
public class GameplayState extends BasicGameState {
	private int id = -1;
	private Racket racket;
	private Image hud;
	private State currentState;
	private ArrayList<Ball> balls;
	private ArrayList<Block> blocks;
	private TiledMap tiledMap;
	private int level;

	private enum State {
		START, PLAYING, PAUSED, LEVEL_WON, NEXT_LEVEL, HIGHSCORE, GAME_OVER
	}

	public GameplayState(int stateID) {
		id = stateID;
		currentState = State.START;
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		hud = new Image("data/hud.png");
		racket = new Racket(400, 550, 1);
		balls = new ArrayList<Ball>();
		balls.add(new Ball(400, 534));
		blocks = new ArrayList<Block>();
		level = 1;
		nextLevel(level);

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		hud.draw(0, 0);
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

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {

		Input input = gc.getInput();
		int mouseX = input.getMouseX();

		updateRacket(mouseX);

		for (Ball ball : balls) {
			if (currentState == State.START) {
				if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
					currentState = State.PLAYING;
				}
				ball.setXPos(racket.getXPos() + racket.getWidth() / 2);
				ball.setYPos(racket.getYPos() - ball.getRadius());
			} else if (currentState == State.PLAYING) {
				ball.move();

				// Kollision med v√§ggar och tak
				if (ball.getXPos() <= 0 || ball.getXPos() >= 800)
					ball.setXSpeed(-ball.getXSpeed());
				if (ball.getYPos() <= 0 || ball.getYPos() >= 600)
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
				// N‰sta level?
				if (blocks.size() == 0) {
					currentState = State.START;
					nextLevel(0);
				}
			}
		}
	}

	private void updateRacket(int xPos) {
		racket.setXPos(xPos - racket.getWidth() / 2);
	}

	// TODO: Fixa s√• att den bollen studsar olika beroende p√• var p√• racketet
	// den tr√§ffar.
	private void racketCollision(Ball ball) {
		// float r = ball.getRadius();
		// float x = ball.getXPos();
		// float y = ball.getYPos();
		// float xDif = x - (racket.getXPos() + racket.getWidth() / 2);
		// float yDif = y - (racket.getYPos() + racket.getHeight() / 2);
		//		
		// float xSpeed = ball.getXSpeed();
		// float ySpeed = ball.getYSpeed();
		//		
		// xSpeed = ??
		// ySpeed = ??

		// ball.setXSpeed(xSpeed);
		ball.setYSpeed(-ball.getYSpeed());
	}

	private void nextLevel(int i) throws SlickException {
		
		//NÂgor lurt med init(), kˆrs tvÂ grÂnger. D‰rav int i...
		if (i == 0) {
			level++;
			tiledMap = new TiledMap("data/level" + level + ".tmx");
		} else if(tiledMap == null) {
			tiledMap = new TiledMap("data/level" + i + ".tmx");
		}

		int tileHeight = tiledMap.getTileHeight();
		int tileWidth = tiledMap.getTileWidth();
		for (int xAxis = 0; xAxis < tiledMap.getWidth(); xAxis++) {
			for (int yAxis = 0; yAxis < tiledMap.getHeight(); yAxis++) {
				int tileID = tiledMap.getTileId(xAxis, yAxis, 0);
				String value = tiledMap.getTileProperty(tileID, "health", "0");
				int health = new Integer(value);
				if (health > 0) {
					blocks.add(new Block(tileWidth * xAxis, tileHeight * yAxis,
							health));				
				}
			}
		}
	}
}