public class Score implements Comparable<Score> {
	private String author;
	private int points;
	
	public Score(String line) throws Exception {
		String[] splitted = line.split("[|]");
		if (splitted.length == 2) {
			author = splitted[0];
			points = Integer.parseInt(splitted[1]);
		}
	}
	
	public Score(String author, int points) {
		this.author = author;
		this.points = points;
	}
	
	public void addPoints(int points) {
		this.points += points;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public int getPoints() {
		return points;
	}
	
	@Override
	public int compareTo(Score anotherScore) {
		return points - anotherScore.getPoints();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Score))
			return false;
		
		Score anotherScore = (Score) obj;
		return (anotherScore.getAuthor() == author
				&& anotherScore.getPoints() == points);
	}
}