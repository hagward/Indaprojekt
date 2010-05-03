import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * @author Anders Hagward
 * @author Fredrik Hillnertz
 * @version 2010-05-03
 */
public class HighScoreHandler {
	private File file;
	private TreeSet<Score> scores;
	private int maxSize;

	public HighScoreHandler(String fileName, int maxNumberOfScores) {
		file = new File(fileName);
		scores = new TreeSet<Score>();
		maxSize = maxNumberOfScores;
	}

	public void addScore(Score score) {
		scores.add(score);
		if (scores.size() > maxSize) {
			scores.pollFirst();
		}
	}

	public void removeScore(Score score) {
		scores.remove(score);
	}

	public void parse() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = in.readLine()) != null) {
				try {
					scores.add(new Score(line));
				} catch (Exception e) {
					System.err.println("Error parsing score data: " + line);
				}
			}
			in.close();

			while (scores.size() > maxSize) {
				scores.pollFirst();
			}
		} catch (FileNotFoundException e) {
			try {
				file.createNewFile();
			} catch (IOException e1) {
				System.err.println("Unable to create file " + file.getName());
			}
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
		int n = 1;
		while (it.hasNext()) {
			Score score = it.next();
			sb.append(n);
			sb.append(": ");
			sb.append(score.getAuthor());
			sb.append(": ");
			sb.append(score.getPoints());
			sb.append("\n");
			n++;
		}
		return sb.toString();
	}
}
