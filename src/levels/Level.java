package levels;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.imageio.ImageIO;

import game.entities.Door;
import game.entities.Entity;
import game.entities.PlayerMP;
import gfx.Screen;
import levels.tiles.Tile;

public class Level {

	private byte[] tiles;
	public int width;
	public int height;
	private CopyOnWriteArrayList<Entity> entities = new CopyOnWriteArrayList<>();
	private String imagePath;
	private BufferedImage image;

	public Level(String imagePath) {
		if (imagePath != null) {
			this.imagePath = imagePath;
			this.loadLevelFromFile();
		} else {
			this.width = 64;
			this.height = 64;
			tiles = new byte[width * height];
			this.generateLevel();
		}
	}

	private void loadLevelFromFile() {
		try {
			this.image = ImageIO.read(Level.class.getResource(this.imagePath));
			this.width = image.getWidth();
			this.height = image.getHeight();
			tiles = new byte[width * height];
			this.loadTiles();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadTiles() {
		int[] tileColours = this.image.getRGB(0, 0, width, height, null, 0, width);
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				tileCheck: for(Tile t : Tile.tiles) {
					if(t != null && t.getLevelColour() == tileColours[x + y * width]) {
						this.tiles[x + y * width] = t.getID();
						break tileCheck;
					}
				}
			}
		}
	}

	@SuppressWarnings("unused")
	private void saveLevelToFile() {
		try {
			ImageIO.write(image, "png", new File(Level.class.getResource(this.imagePath).getFile()));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void alterTile(int x, int y, Tile newTile) {
		this.tiles[x + y * width] = newTile.getID();
		image.setRGB(x, y, newTile.getLevelColour());
	}
	
	private void generateLevel() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (x * y % 10 < 7) {
					tiles[x + y * width] = Tile.GRASS.getID();
				} else {
					tiles[x + y * width] = Tile.STONE.getID();
				}
			}
		}
	}
	
	public synchronized CopyOnWriteArrayList<Entity> getEntities(){
		return this.entities;
	}
	
	public Entity getEntityByName(String entity) {
		for(Entity e : getEntities()) {
			if(e.getName().equalsIgnoreCase(entity)) {
				return e;
			}
		}
		return null;
	}

	public void tick() {
		Iterator<Entity> i = getEntities().iterator();
		while(i.hasNext()) {
			i.next().tick();
		}
		for(Tile t : Tile.tiles) {
			if(t == null) {
				break;
			} else {
				t.tick();
			}
		}
	}
	
	public void renderTiles(Screen screen, int xOffset, int yOffset) {
		if (xOffset < 0)
			xOffset = 0;
		if (xOffset > (width << 3) - screen.width)
			xOffset = (width << 3) - screen.width;
		if (yOffset < 0)
			yOffset = 0;
		if (yOffset > (height << 3) - screen.height)
			yOffset = (height << 3) - screen.height;

		screen.setOffset(xOffset, yOffset);

		//only rendering the tiles in the bounds of the screen
		for (int y = (yOffset >> 3); y < (yOffset + screen.height >> 3) + 1; y++) {
			for (int x = (xOffset >> 3); x < (xOffset + screen.width >> 3) + 1; x++) {
				getTile(x, y).render(screen, this, x << 3, y << 3);
			}
		}
	}
	
	//method for colouring the screen black with tiles
	public void renderBlackTiles(Screen screen, int xOffset, int yOffset) {
		if (xOffset < 0)
			xOffset = 0;
		if (xOffset > (width << 3) - screen.width)
			xOffset = (width << 3) - screen.width;
		if (yOffset < 0)
			yOffset = 0;
		if (yOffset > (height << 3) - screen.height)
			yOffset = (height << 3) - screen.height;
		
		screen.setOffset(xOffset, yOffset);
		
		//only rendering the tiles in the bounds of the screen
		for (int y = (yOffset >> 3); y < (yOffset + screen.height >> 3) + 1; y++) {
			for (int x = (xOffset >> 3); x < (xOffset + screen.width >> 3) + 1; x++) {
				Tile.VOID.render(screen, this, x << 3, y << 3);
			}
		}
	}
	
	public void renderEntities(Screen screen) {
		for (Entity e : getEntities()) {
			e.render(screen);
		}
		//I want to render the player on top of everything else so I render just the players again
		for(Entity e : getEntities()) {
			if(e instanceof PlayerMP) {
				e.render(screen);
			}
		}
	}

	public Tile getTile(int x, int y) {
		if (0 > x || x >= width || 0 > y || y >= height)
			return Tile.VOID;
		return Tile.tiles[tiles[x + y * width]];
	}
	
	public boolean addEntity(Entity entity) {
		if(!this.getEntities().contains(entity)) {
			this.getEntities().add(entity);
			return true;
		}
		return false;
	}

	public void removePlayerMP(String username) {
		int index = 0;
		for(Entity e : getEntities()) {
			if(e instanceof PlayerMP && ((PlayerMP) e).getUsername().equalsIgnoreCase(username)) {
				break;
			}
			index ++;
		}
		this.getEntities().remove(index);
	}
	
	private int getPlayerMPIndex(String username) {
		int index = 0;
		for(Entity e : getEntities()) {
			if(e instanceof PlayerMP && ((PlayerMP) e).getUsername().equalsIgnoreCase(username)) {
				break;
			}
			index ++;
		}
		return index;
	}
	
	public void movePlayer(String username, int x, int y, int numSteps, boolean isMoving, int movingDir) {
		int index = getPlayerMPIndex(username);
		PlayerMP player = (PlayerMP) this.getEntities().get(index);
		player.x = x;
		player.y = y;
		player.setNumSteps(numSteps);
		player.setMoving(isMoving);
		player.setMovingDir(movingDir);
	}
	
	public void usePlayer(String username, int x, int y, int movingDir, boolean isUsing) {
		int index = getPlayerMPIndex(username);
		PlayerMP player = (PlayerMP) this.getEntities().get(index);
		player.x = x;
		player.y = y;
		player.setMovingDir(movingDir);
		player.setUsing(isUsing);
	}

}
