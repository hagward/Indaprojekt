/**
 * @author Anders Hagward
 * @author Fredrik Hillnertz
 * @version 2010-04-27
 */
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
	
	public Score(int points) {
		this(null, points);
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
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public void reset() {
		author = null;
		points = 0;
	}
	
	@Override
	public int compareTo(Score anotherScore) {
		int result = points - anotherScore.getPoints();
		return (result != 0) ? result : -1;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Score))
			return false;
		
		Score anotherScore = (Score) obj;
		return (anotherScore.getAuthor() == author
				&& anotherScore.getPoints() == points);
	}
	
	@Override
	public String toString() {
		return author + "|" + points;
	}
}