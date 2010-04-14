import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author Anders Hagward
 * @author Fredrik Hillnertz
 * @version 2010-04-14
 */
public class GameplayState extends BasicGameState {
	private int id = -1;
	private Racket racket;
	private Image hud;
	private State currentState;
	private ArrayList<Ball> balls;
	private ArrayList<Block> blocks;
	private int currentLevel;

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
		currentLevel = 1;
		nextLevel();
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

				// Kollision med väggar och tak
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
				// Nästa level?
				if (blocks.size() == 0) {
					nextLevel();
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
		ball.setYSpeed(-ball.getYSpeed());
	}

	private void nextLevel() throws SlickException {
		currentState = State.START;
		String levelPath = "data/level" + currentLevel + ".tmx";
		Level level = new Level(levelPath);
		blocks = level.generateBlockList();
		currentLevel++;
	}
}