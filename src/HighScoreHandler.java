import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.TreeSet;

public class HighScoreHandler {
	private TreeSet<Score> scores;
	
	public HighScoreHandler(File file) throws FileNotFoundException {
		scores = new TreeSet<Score>();
		try {
			BufferedReader in = new BufferedReader(
					new FileReader(file));
			String line = null;
			while ((line = in.readLine()) != null) {
				try {
					scores.add(new Score(line));
				} catch (Exception e) {
					System.err.println("Error parsing score data: " + line);
				}
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (Score score : scores)
			System.out.println(score.getAuthor() + ": " + score.getPoints());
	}
	
	public Iterator<Score> getScoreIterator() {
		return scores.descendingIterator();
	}
}