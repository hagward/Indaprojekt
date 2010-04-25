import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class HighScoreState extends BasicGameState {
	private int id = -1;
	private Image background;
	private HighScoreHandler highScores;
	
	public HighScoreState(int stateID, HighScoreHandler highScoreHandler) {
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
		background = new Image("data/highscore_bg.png");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		background.draw();
		g.setColor(Color.black);
		g.drawString(highScores.toString(), 110, 210);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}
}