/**
 * @author Anders Hagward
 * @author Fredrik Hillnertz
 * @version 2010-05-03
 */
public class Player {
	private int startLives;
	private int lives;
	private Score score;

	public Player(int lives) {
		this.startLives = lives;
		this.lives = lives;
		this.score = new Score(0);
	}

	public void setName(String name) {
		score.setAuthor(name);
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public void increaseLives() {
		lives++;
	}

	public void decreaseLives() {
		lives--;
	}

	public String getName() {
		return score.getAuthor();
	}

	public int getLives() {
		return lives;
	}

	public Score getScore() {
		return score;
	}

	public void reset() {
		lives = startLives;
		score.reset();
	}
}
