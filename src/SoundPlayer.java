import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/**
 * @author Anders Hagward
 * @author Fredrik Hillnertz
 * @version 2010-05-06
 */
public class SoundPlayer {
	private Sound bounce, death, victory, laser;
	private boolean enabled = true;

	public SoundPlayer() throws SlickException {
		bounce = new Sound("data/sounds/bounce.wav");
		death = new Sound("data/sounds/death.wav");
		victory = new Sound("data/sounds/victory.wav");
		laser = new Sound("data/sounds/laser.wav");
	}

	public void bounce() {
		if (enabled)
			bounce.play();
	}

	public void death() {
		if (enabled)
			death.play();
	}

	public void victory() {
		if (enabled)
			victory.play();
	}

	public void laser() {
		if (enabled)
			laser.play();
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isEnabled() {
		return enabled;
	}
}
