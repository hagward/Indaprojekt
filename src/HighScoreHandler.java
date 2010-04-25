import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.TreeSet;

public class HighScoreHandler {
	private File file;
	private TreeSet<Score> scores;
	
	public HighScoreHandler(String fileName) {
		file = new File(fileName);
		scores = new TreeSet<Score>();
	}
	
	public void addScore(Score score) {
		scores.add(score);
	}
	
	public void removeScore(Score score) {
		scores.remove(score);
	}
	
	public void parse() {
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
	}
	
	public void save() {
		try {
			PrintWriter out = new PrintWriter(file);
			for (Score score : scores) {
				out.println(score);
			}
			out.close();
		} catch (IOException e) {
			System.err.println("Error writing to file: " + file.getName());
		}
	}
	
	public Iterator<Score> getScoreIterator() {
		return scores.descendingIterator();
	}
	
	@Override
	public String toString() {
		Iterator<Score> it = getScoreIterator();
		StringBuilder sb = new StringBuilder();
		while (it.hasNext()) {
			Score score = it.next();
			sb.append(score.getAuthor());
			sb.append(": ");
			sb.append(score.getPoints());
			sb.append("\n");
		}
		return sb.toString();
	}
}