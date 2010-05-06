import org.newdawn.slick.Color;
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
public class HighScoreState extends BasicGameState {
	private int id = -1;
	private Image background;
	private GameContainer container;
	private StateBasedGame game;
	private ResourceLoader resources;
	private HighScoreHandler highScores;
	private MouseOverArea backItem;

	public HighScoreState(int stateID, HighScoreHandler highScoreHandler) {
		id = stateID;
		highScores = highScoreHandler;
		resources = ResourceLoader.getInstance();
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		container = gc;
		game = sbg;
		background = resources.getImage("highscore_bg.png");

		MenuListener menuListener = new MenuListener();
		backItem = new MouseOverArea(gc,
				resources.getImage("menuitem-back.png"),
				600, 400, menuListener);
		backItem.setMouseOverImage(
				resources.getImage("menuitem-hover-back.png"));
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		background.draw();
		backItem.render(gc, g);
		g.setColor(Color.black);
		g.drawString(highScores.toString(), 110, 210);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	private class MenuListener implements ComponentListener {
		@Override
		public void componentActivated(AbstractComponent ac) {
			MouseOverArea source = (MouseOverArea) ac;
			if (source == backItem) {
				container.getInput().clearMousePressedRecord();
				game.enterState(BreakoutGame.MAINMENUSTATE);
			}
		}
	}
}
