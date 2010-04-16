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
	private Image hud;
	private State currentState;	
	private Level currentLevel;

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
		
		currentLevel = new Level();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		hud.draw(0, 0);
		currentLevel.render();
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {

		Input input = gc.getInput();
		int mouseX = input.getMouseX();

		currentLevel.updateRacket(mouseX);
		
		switch(currentState) {
		case START:
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				currentState = State.PLAYING;
			}
			currentLevel.idle();
		case PLAYING:
			currentLevel.update();
			if(currentLevel.checkWin()) {
				currentLevel.nextLevel();
				currentState = State.START;
			}
				
		}		
	}	
}