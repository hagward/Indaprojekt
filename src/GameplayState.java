import java.util.ArrayList;

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
 * @version 2010-04-08
 */
public class GameplayState extends BasicGameState {
	private int id = -1;
	private Racket racket;
	private Image hud;
	private State currentState;
	private ArrayList<Ball> balls;

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
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		hud.draw(0, 0);
		racket.draw();
		for (Ball ball : balls)
			ball.draw();
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {

		Input input = gc.getInput();
		int mouseX = input.getMouseX();

		updateRacket(mouseX);

		for (Ball ball : balls) {
			if (currentState == State.START) {
				if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
					ball.setXSpeed(0.1f);
					ball.setYSpeed(0.1f);

					currentState = State.PLAYING;
				}
				ball.setXPos(racket.getXPos() + racket.getWidth() / 2);
			} else if (currentState == State.PLAYING) {
				ball.move();

				// Collision detection
				if (ball.getXPos() <= 0 || ball.getXPos() >= 800)
					ball.setXSpeed(-ball.getXSpeed());
				if (ball.getYPos() <= 0 || ball.getYPos() >= 600)
					ball.setYSpeed(-ball.getYSpeed());
				if (insideRacket(ball))
					racketCollision(ball);				
			}
		}

	}

	private void updateRacket(int xPos) {
		racket.setXPos(xPos - racket.getWidth() / 2);
	}
	//Inte perfekt
	private boolean insideRacket(Ball ball) {
		float r = ball.getRadius();
		float x = ball.getXPos() + r;
		float y = ball.getYPos() + r;
		
		if(x + r >= racket.getXPos() && x - r <= racket.getXPos() + racket.getWidth())
			if(y + r >= racket.getYPos() && y -r <= racket.getYPos() + racket.getHeight())
				return true;
		return false;
	}
	//Ideer?
	private void racketCollision(Ball ball) {
		float r = ball.getRadius();
		float x = ball.getXPos() + r;
		float y = ball.getYPos() + r;
		float xDif = x - (racket.getXPos() + racket.getWidth() / 2);
		float yDif = y - (racket.getYPos() + racket.getHeight() / 2);
		
		float xSpeed = ball.getXSpeed();
		float ySpeed = ball.getYSpeed();
		
		//xSpeed = ??
		//ySpeed = ??
			
		ball.setYSpeed(ySpeed);
		ball.setXSpeed(xSpeed);
	}
}