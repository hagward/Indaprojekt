import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author Anders Hagward
 * @version 2010-04-05
 */
public class BreakoutGame extends StateBasedGame {
	private static final int MAINMENUSTATE = 0;
	private static final int GAMEPLAYSTATE = 0;
	
	public BreakoutGame() {
		super("Breakout");
		addState(new MainMenuState(MAINMENUSTATE));
		addState(new MainMenuState(GAMEPLAYSTATE));
		enterState(MAINMENUSTATE);
	}
	
	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new BreakoutGame());
			app.setDisplayMode(800, 600, false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initStatesList(GameContainer c) throws SlickException {
		getState(MAINMENUSTATE).init(c, this);
		getState(GAMEPLAYSTATE).init(c, this);
	}
}