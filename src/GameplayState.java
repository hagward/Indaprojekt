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
 * @version 2010-04-05
 */
public class GameplayState extends BasicGameState {
	private int id = -1;
	private Racket racket;
	private Image hud;
	private STATES currentState;
	private ArrayList<Ball> balls;
	private final float defaultBallSpeed = 10;
	

	private enum STATES {
		START_GAME_STATE, WON_LEVEL_STATE, NEXT_LEVEL_STATE, PAUSE_GAME_STATE, HIGHSCORE_STATE, GAME_OVER_STATE
	}

	public GameplayState(int stateID) {
		id = stateID;
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		hud = new Image("data/hud.png");
		racket = new Racket(400, 550, (float) 1, new Image(
				"data/racket.png"));
	
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics arg2)
			throws SlickException {
		hud.draw(0, 0);
		racket.draw();
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		
		Input input = gc.getInput();
		int mouseX = input.getMouseX();
		
		updateRacket(mouseX);
	}
	
	private void updateRacket(int xPos) {
		racket.setXPos(xPos - racket.getLength()/2);
	}
}