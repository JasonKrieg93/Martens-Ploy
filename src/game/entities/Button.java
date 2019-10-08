package game.entities;

import gfx.Colours;
import gfx.Screen;
import levels.Level;
import levels.tiles.Tile;

public class Button extends Entity {

	private int animCount = 0;
	private boolean isPressed;
	private int colour = Colours.get(-1, 111, 543, 555);
	private int activateRangeX;
	private int activateRangeY;
	private int activateMovingDir;
	private int spawnTileX;
	private int spawnTileY;
	private int spawnAnimCount = 0;
	private boolean isSpawning;
	private int rockCount = 0;
	private int rockMax;
	private String rockName;

	public Button(Level level, int x, int y, String name, int activateRangeX, int activateRangeY, int activateMovingDir,
			int spawnTileX, int spawnTileY, int rockMax) {
		super(level, name);
		this.activateRangeX = activateRangeX;
		this.activateRangeY = activateRangeY;
		this.activateMovingDir = activateMovingDir;
		this.x = x;
		this.y = y;
		this.spawnTileX = spawnTileX;
		this.spawnTileY = spawnTileY;
		this.rockMax = rockMax;
		this.rockName = name + "rock" + rockCount;
	}

	@Override
	public void tick() {
		if (isSpawning)
			spawnAnim();

	}

	public void interact(Player player) {

		if (player.x >> 3 == this.activateRangeX && player.y >> 3 == this.activateRangeY
				&& player.movingDir == this.activateMovingDir) {
			this.isPressed = true;

			// if rock isn't created yet, create it
			if (rockCount < rockMax) {
				rockCount++;
				for (Entity e : level.getEntities()) {
					if (e instanceof Player) {
						if (((Player) e).getRock() != null) {
							if (((Player) e).getRock().getName().equals(rockName)) {
								((Player) e).setRock(null);
							}
						}
					}
					if(e instanceof Rock) {
						if(((Rock)e).getName().equals(rockName)) {
							level.getEntities().remove(e);
						}
					}
				}
				if (level.getEntities().add(new Rock(level, rockName, spawnTileX << 3, spawnTileY << 3, this))) {
					level.alterTile(spawnTileX, spawnTileY, Tile.tiles[22]);
					isSpawning = true;
				}

				// else move it back to the spawn tile
			} else if (!isSpawning) {
				for (Entity p : level.getEntities()) {
					if (p instanceof Player) {
						if (((Player) p).getRock() != null) {
							if (((Player) p).getRock().getName().equals(rockName)) {
								((Player) p).setRock(null);
							}
						}
					}
				}
				level.getEntityByName(this.rockName).x = spawnTileX << 3;
				level.getEntityByName(this.rockName).y = spawnTileY << 3;
				((Rock) level.getEntityByName(rockName)).setSpawning(true);
				((Rock) level.getEntityByName(rockName)).setAnimCount(0);
				level.alterTile(spawnTileX, spawnTileY, Tile.tiles[22]);
				isSpawning = true;
			}
		}
	}

	private void spawnAnim() {
		if (spawnAnimCount >= 120) {
			level.alterTile(spawnTileX, spawnTileY, Tile.tiles[21]);
			spawnAnimCount = 0;
			isSpawning = false;
		}
		spawnAnimCount++;
	}

	private void animate() {
		if (animCount < 50) {
			colour = Colours.get(-1, 111, 543, 131);
			animCount++;
		} else {
			isPressed = false;
			animCount = 0;
		}

	}

	@Override
	public void render(Screen screen) {
		int xTile = 1;
		int yTile = 1;
		colour = Colours.get(-1, 111, 543, 555);
		if (isPressed) {
			animate();
		}
		screen.render(x, y, xTile + yTile * 32, colour, 0x00, 1);
	}

	public int getRockCount() {
		return rockCount;
	}

	public void setRockCount(int rockCount) {
		this.rockCount = rockCount;
	}

	public void decrementRockCount() {
		rockCount--;
	}

}
