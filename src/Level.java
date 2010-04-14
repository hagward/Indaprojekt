import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 * @author Anders Hagward
 * @version 2010-04-14
 */
public class Level {
	private TiledMap tiledMap;
	
	public Level(String filePath) throws SlickException {
		this.tiledMap = new TiledMap(filePath);
	}
	
	public ArrayList<Block> generateBlockList() throws SlickException {
		ArrayList<Block> blocks = new ArrayList<Block>();
		int tileWidth = tiledMap.getTileWidth();
		int tileHeight = tiledMap.getTileHeight();
		for (int xAxis = 0; xAxis < tiledMap.getWidth(); xAxis++) {
			for (int yAxis = 0; yAxis < tiledMap.getHeight(); yAxis++) {
				int tileID = tiledMap.getTileId(xAxis, yAxis, 0);
				String healthVal = tiledMap.getTileProperty(
						tileID, "health", "0");
				int health = Integer.parseInt(healthVal);
				if (health > 0) {
					blocks.add(new Block(
							tileWidth * xAxis, tileHeight * yAxis, health));
				}
			}
		}
		return blocks;
	}
}