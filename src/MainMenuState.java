import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author Anders Hagward & Fredrik Hillnertz
 * @version 2010-04-07
 */
public class MainMenuState extends BasicGameState {
	private int id = -1;
	private Image background = null;

	public MainMenuState(int stateID) {
		id = stateID;
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		background = new Image("data/mainmenu.png");
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		background.draw();
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame sb, int arg2)
			throws SlickException {
		Input input = arg0.getInput();

		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();

		boolean insideNewGame = false;
		if ((mouseX >= 61 && mouseX <= 300) && (mouseY >= 200 && mouseY <= 270))
			insideNewGame = true;

		if (insideNewGame) {
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				sb.enterState(BreakoutGame.GAMEPLAYSTATE);
			}
		}

	}
}