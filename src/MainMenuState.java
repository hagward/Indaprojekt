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
 * @version 2010-05-06
 */
public class MainMenuState extends BasicGameState {
	private int id = -1;
	private GameContainer container;
	private StateBasedGame game;
	private Image background;
	private MenuListener menuListener;
	private MouseOverArea newGameItem;
	private MouseOverArea highScoreItem;
	private MouseOverArea settingsItem;
	private MouseOverArea helpItem;
	private MouseOverArea quitItem;
	private MouseOverArea backItem;
	private MouseOverArea soundsOnOffItem;
	private ResourceLoader resources;
	private LevelHandler levels;
	private SoundPlayer sounds;
	private SubMenu currentMenu;

	protected static enum SubMenu {
		DEFAULT, SETTINGS, HELP
	};

	public MainMenuState(int stateID, LevelHandler levelHandler,
			SoundPlayer soundPlayer) {
		id = stateID;
		levels = levelHandler;
		sounds = soundPlayer;
	}

	protected void setSubMenu(SubMenu menu) {
		switch (menu) {
		case HELP:
			background = resources.getImage("mainmenu_help.png");
			break;
		default:
			background = resources.getImage("mainmenu.png");
			break;
		}
		currentMenu = menu;
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		game = sbg;
		container = gc;
		
		resources = ResourceLoader.getInstance();
		resources.loadImages("data/images");
		
		setSubMenu(SubMenu.DEFAULT);
		setupMenu();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		background.draw();
		switch (currentMenu) {
		case SETTINGS:
			soundsOnOffItem.render(gc, g);
			backItem.render(gc, g);
			break;
		default:
			newGameItem.render(gc, g);
			highScoreItem.render(gc, g);
			settingsItem.render(gc, g);
			helpItem.render(gc, g);
			quitItem.render(gc, g);
			break;
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
	}

	private void setupMenu() throws SlickException {
		menuListener = new MenuListener();

		int x = 600;
		int y = 400;

		newGameItem = new MouseOverArea(container,
				resources.getImage("menuitem-new_game.png"),
				x, y, menuListener);
		newGameItem.setMouseOverImage(
				resources.getImage("menuitem-hover-new_game.png"));
		highScoreItem = new MouseOverArea(container,
				resources.getImage("menuitem-highscore.png"),
				x, y + 35, menuListener);
		highScoreItem.setMouseOverImage(
				resources.getImage("menuitem-hover-highscore.png"));
		settingsItem = new MouseOverArea(container,
				resources.getImage("menuitem-settings.png"),
				x, y + 70, menuListener);
		settingsItem.setMouseOverImage(
				resources.getImage("menuitem-hover-settings.png"));
		helpItem = new MouseOverArea(container,
				resources.getImage("menuitem-help.png"),
				x, y + 105, menuListener);
		helpItem.setMouseOverImage(
				resources.getImage("menuitem-hover-help.png"));
		quitItem = new MouseOverArea(container,
				resources.getImage("menuitem-quit.png"),
				x, y + 140, menuListener);
		quitItem.setMouseOverImage(
				resources.getImage("menuitem-hover-quit.png"));
		soundsOnOffItem = new MouseOverArea(container,
				resources.getImage("menuitem-sounds_on.png"),
				x, y, menuListener);
		soundsOnOffItem.setMouseOverImage(
				resources.getImage("menuitem-hover-sounds_on.png"));
		backItem = new MouseOverArea(container,
				resources.getImage("menuitem-back.png"),
				x, y + 35, menuListener);
		backItem.setMouseOverImage(
				resources.getImage("menuitem-hover-back.png"));
	}

	protected void toggleSounds() throws SlickException {
		if (sounds.isEnabled()) {
			sounds.setEnabled(false);
			soundsOnOffItem.setNormalImage(
					resources.getImage("menuitem-sounds_off.png"));
			soundsOnOffItem.setMouseOverImage(
					resources.getImage("menuitem-hover-sounds_off.png"));
		} else {
			sounds.setEnabled(true);
			soundsOnOffItem.setNormalImage(
					resources.getImage("menuitem-sounds_on.png"));
			soundsOnOffItem.setMouseOverImage(
					resources.getImage("menuitem-hover-sounds_on.png"));
		}
	}

	private class MenuListener implements ComponentListener {
		@Override
		public void componentActivated(AbstractComponent ac) {
			MouseOverArea source = (MouseOverArea) ac;
			if (source == newGameItem) {
				setSubMenu(SubMenu.DEFAULT);
				container.getInput().clearMousePressedRecord();
				levels.reset();
				game.enterState(BreakoutGame.GAMEPLAYSTATE);
			} else if (source == settingsItem) {
				setSubMenu(SubMenu.SETTINGS);
			} else if (source == highScoreItem) {
				setSubMenu(SubMenu.DEFAULT);
				game.enterState(BreakoutGame.HIGHSCORESTATE);
			} else if (source == helpItem) {
				setSubMenu(SubMenu.HELP);
			} else if (source == quitItem) {
				container.exit();
			} else if (source == backItem) {
				setSubMenu(SubMenu.DEFAULT);
			} else if (source == soundsOnOffItem) {
				try {
					toggleSounds();
				} catch (SlickException e) {
					System.err.println("Unable to toggle sounds!");
				}
			}
		}
	}
}
