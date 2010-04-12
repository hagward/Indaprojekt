import java.util.ArrayList;

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
	private TiledMap level;

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
		level = new TiledMap("data/level1.tmx");

		blocks = new ArrayList<Block>();
		int tileHeight = level.getTileHeight();
		int tileWidth = level.getTileWidth();
		for (int xAxis = 0; xAxis < level.getWidth(); xAxis++) {
			for (int yAxis = 0; yAxis < level.getHeight(); yAxis++) {
				int tileID = level.getTileId(xAxis, yAxis, 0);
				String value = level.getTileProperty(tileID, "health", "0");
				int health = new Integer(value);
				if (health > 0) {
					blocks.add(new Block(tileWidth * xAxis, tileHeight * yAxis,
							health));
				}
			}
		}
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
			} else if (currentState == State.PLAYING) {
				ball.move();

				// Kollision med väggar och tak
				if (ball.getXPos() <= 0 || ball.getXPos() >= 800)
					ball.setXSpeed(-ball.getXSpeed());
				if (ball.getYPos() <= 0 || ball.getYPos() >= 600)
					ball.setYSpeed(-ball.getYSpeed());

				// Kollision med racket
				if (ball.collidesWithTop(racket))
					racketCollision(ball);

				// Kollision med block
				for (Block block : blocks) {
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
				}
			}
		}
	}

	private void updateRacket(int xPos) {
		racket.setXPos(xPos - racket.getWidth() / 2);
	}

	// TODO: Fixa så att den bollen studsar olika beroende på var på racketet
	// den träffar.
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
}