import javax.swing.JOptionPane;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * This class handles all the calculations and rendering (that is, when a new
 * game is started, and not in the menus.)
 * 
 * @author Anders Hagward
 * @author Fredrik Hillnertz
 * @version 2010-04-16
 */
public class GameplayState extends BasicGameState {
	private int id = -1;	
	private Image hud;
	private State currentState;
	private LevelHandler currentLevel;
	private HighScoreHandler highScores;
	private Player player;
	
	/**
	 * Contains all the 'inner' states.
	 */
	public static enum State {
		START, PLAYING, PAUSED, LEVEL_WON, NEXT_LEVEL, HIGHSCORE, GAME_OVER
	}
	
	/**
	 * Creates a new GameplayState with a specified id and sets the inner
	 * state to <code>State.START</code>.
	 * @param stateID an arbitrary integer id
	 */
	public GameplayState(int stateID, HighScoreHandler highScoreHandler) {
		id = stateID;
		highScores = highScoreHandler;
	}
	
	@Override
	public int getID() {
		return id;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		hud = new Image("data/hud.png");
		player = new Player(3);
		currentLevel = new LevelHandler(player);
		currentState = State.START;
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		hud.draw(0, 0);
		currentLevel.renderCurrentLevel();
		g.setColor(Color.black);
		g.drawString("Lives: " + currentLevel.getPlayer().getLives(),
				20, 300);
		g.drawString("Score: " + currentLevel.getPlayer().getScore().getPoints(),
				20, 320);
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
			break;
		case PLAYING:
			currentLevel.updateCurrentLevel(this, delta, gc);
			if(currentLevel.checkLevelBeaten()
					|| input.isKeyPressed(Input.KEY_N)) {
				try {
					currentLevel.nextLevel();
					currentState = State.START;
				} catch (Exception e) {
					String name = JOptionPane.showInputDialog(
							"Please enter your name:");
					if (name != null) {
						player.setName(name);
						highScores.addScore(player.getScore());
						highScores.save();
						sbg.enterState(BreakoutGame.HIGHSCORESTATE);
					} else {
						sbg.enterState(BreakoutGame.MAINMENUSTATE);
					}
				}
			}
			else if(input.isKeyPressed(Input.KEY_R)) {
				currentLevel.restartLevel();
				currentState = State.START;
			}
			break;
		}
	}
	
	public void setState(State newState) {
		currentState = newState;
	}
}