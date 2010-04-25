public class Player {
	private int lives;
	private Score score;
	
	public Player(int lives) {
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
}