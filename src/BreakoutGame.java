import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * A re-implementation of the classical game 'Breakout'. Uses the <a
 * href="http://slick.cokeandcode.com/">Slick library</a>.
 * 
 * @author Anders Hagward
 * @author Fredrik Hillnertz
 * @version 2010-05-06
 */
public class BreakoutGame extends StateBasedGame {
	public static final int MAINMENUSTATE = 0;
	public static final int GAMEPLAYSTATE = 1;
	public static final int HIGHSCORESTATE = 2;

	public BreakoutGame() throws SlickException {
		super("Breakout");

		LevelHandler levels = new LevelHandler();
		SoundPlayer sounds = new SoundPlayer();
		HighScoreHandler highScores = new HighScoreHandler("data/data.bin", 10);
		highScores.parse();

		addState(new MainMenuState(MAINMENUSTATE, levels, sounds));
		addState(new GameplayState(GAMEPLAYSTATE, levels, highScores, sounds));
		addState(new HighScoreState(HIGHSCORESTATE, highScores));
		enterState(MAINMENUSTATE);
	}

	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new BreakoutGame());
			app.setDisplayMode(800, 600, false);
			app.setMinimumLogicUpdateInterval(10);
			app.setMaximumLogicUpdateInterval(10);
			app.setShowFPS(false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initStatesList(GameContainer c) throws SlickException {
		getState(MAINMENUSTATE).init(c, this);
		getState(GAMEPLAYSTATE).init(c, this);
		getState(HIGHSCORESTATE).init(c, this);
	}
}
