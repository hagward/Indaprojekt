public class Player {
	private String name;
	private int lives;
	private Score score;
	
	public Player(int lives) {
		this.lives = lives;
		score = new Score("Anders", 0);
	}
	
	public void addScorePoints(int points) {
		score.addPoints(points);
	}
	
	public void setName(String name) {
		this.name = name;
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
		return name;
	}
	
	public int getLives() {
		return lives;
	}
	
	public int getScorePoints() {
		return score.getPoints();
	}
}