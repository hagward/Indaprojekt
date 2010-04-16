import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author Anders Hagward
 * @author Fredrik Hillnertz
 * @version 2010-04-16
 */
public class MainMenuState extends BasicGameState {
	private int id = -1;
	private StateBasedGame game;
	private Image background = null;
	private MenuListener menuListener;
	private MouseOverArea newGameItem;
	private MouseOverArea settingsItem;
	private MouseOverArea helpItem;
	private MouseOverArea quitItem;

	public MainMenuState(int stateID) {
		id = stateID;
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		game = sbg;
		background = new Image("data/mainmenu.png");
		setupMenu(gc);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		background.draw();
		newGameItem.render(gc, g);
		settingsItem.render(gc, g);
		helpItem.render(gc, g);
		quitItem.render(gc, g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
	}
	
	private void setupMenu(GameContainer gc)
			throws SlickException{
		menuListener = new MenuListener();
		
		int x = 600;
		int y = 400;
		
		newGameItem = new MouseOverArea(
				gc, new Image("data/menuitem-new_game.png"),
				x, y, menuListener);
		newGameItem.setMouseOverImage(
				new Image("data/menuitem-hover-new_game.png"));
		settingsItem = new MouseOverArea(
				gc, new Image("data/menuitem-settings.png"),
				x, y + 35, menuListener);
		settingsItem.setMouseOverImage(
				new Image("data/menuitem-hover-settings.png"));
		helpItem = new MouseOverArea(
				gc, new Image("data/menuitem-help.png"),
				x, y + 70, menuListener);
		helpItem.setMouseOverImage(
				new Image("data/menuitem-hover-help.png"));
		quitItem = new MouseOverArea(
				gc, new Image("data/menuitem-quit.png"),
				x, y + 105, menuListener);
		quitItem.setMouseOverImage(
				new Image("data/menuitem-hover-quit.png"));
	}
	
	private class MenuListener implements ComponentListener {
		@Override
		public void componentActivated(AbstractComponent ac) {
			MouseOverArea source = (MouseOverArea) ac;
			if (source == newGameItem) {
				game.enterState(BreakoutGame.GAMEPLAYSTATE);
			}
		}
	}
}