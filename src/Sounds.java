import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Sounds {
	private Sound bounce, explosion, death, victory, blockDestroyed, laser, gameOver;
	private boolean sounds = true;

	public Sounds() throws SlickException {
		bounce = new Sound("data/sounds/bounce.wav");
	//	explosion = new Sound("data/sounds/explosion");
		death = new Sound("data/sounds/death.wav");
		victory = new Sound("data/sounds/victory.wav");
	//	blockDestroyed = new Sound("data/sounds/blockDestroyed");
		laser = new Sound("data/sounds/laser.wav");
	}

	public void bounce() {
		if(sounds)
			bounce.play();
	}

	public void explosion() {
		if(sounds)
			explosion.play();
	}

	public void death() {
		if(sounds)
			death.play();
	}

	public void victory() {
		if(sounds)
			victory.play();
	}

	public void blockDestroyed() {
		if(sounds)
			blockDestroyed.play();
	}

	public void laser() {
		if(sounds)
			laser.play();
	}

	public void setSound(boolean offon) {
		sounds = offon;
	}
	
}
