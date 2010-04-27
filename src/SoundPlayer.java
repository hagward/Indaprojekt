import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class SoundPlayer {
	private Sound bounce, explosion, death, victory,
			blockDestroyed, laser, gameOver;
	private boolean enabled = true;

	public SoundPlayer() throws SlickException {
		bounce = new Sound("data/sounds/bounce.wav");
	//	explosion = new Sound("data/sounds/explosion");
		death = new Sound("data/sounds/death.wav");
		victory = new Sound("data/sounds/victory.wav");
	//	blockDestroyed = new Sound("data/sounds/blockDestroyed");
		laser = new Sound("data/sounds/laser.wav");
	}

	public void bounce() {
		if(enabled)
			bounce.play();
	}

	public void explosion() {
		if(enabled)
			explosion.play();
	}

	public void death() {
		if(enabled)
			death.play();
	}

	public void victory() {
		if(enabled)
			victory.play();
	}

	public void blockDestroyed() {
		if(enabled)
			blockDestroyed.play();
	}

	public void laser() {
		if(enabled)
			laser.play();
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
}