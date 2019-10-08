package game.entities;

import gfx.Colours;
import gfx.Screen;
import levels.Level;

public class Rock extends Entity {

	private int colour = Colours.get(-1, -1, -1, -1);;
	private int xTile = 2;
	private int yTile = 1;
	private int animCount = 0;
	private boolean isSpawning = true;
	private Button spawnButton;

	public Rock(Level level, String name, int x, int y, Button spawnButton) {
		super(level, name);
		this.x = x;
		this.y = y;
		this.spawnButton = spawnButton;
	}
	
	public void spawnAnimation() {
		xTile = 3;
		if(getAnimCount() < 100) {
			colour = Colours.get(-1, -1, -1, -1);
		} else if(100 <= getAnimCount() && getAnimCount() < 125) {
			colour = Colours.get(-1, -1, -1, 555);
		} else if(125 <= getAnimCount() &&  getAnimCount() < 150) {
			colour = Colours.get(-1, -1, 555, -1);
		} else if(150 <= getAnimCount() &&  getAnimCount() < 175) {
			colour = Colours.get(-1, 555, -1, -1);
		} else if(175 <= getAnimCount() &&  getAnimCount() < 200) {
			colour = Colours.get(-1, -1, -1, 555);
		} else if(200 <= getAnimCount() &&  getAnimCount() < 225) {
			colour = Colours.get(-1, -1, 555, -1);
		} else if(225 <= getAnimCount()) {
			colour = Colours.get(-1, 222, 333, 444);
			xTile = 2;
			isSpawning = false;
		}
		setAnimCount(getAnimCount() + 1);
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Screen screen) {
		if(isSpawning)
			spawnAnimation();
		screen.render(x, y, xTile + yTile * 32, colour, 0x00, 1);
	}

	public boolean isSpawning() {
		return isSpawning;
	}

	public void setSpawning(boolean isSpawning) {
		this.isSpawning = isSpawning;
	}

	public int getAnimCount() {
		return animCount;
	}

	public void setAnimCount(int animCount) {
		this.animCount = animCount;
	}

	public void interact(Player player) {
		if(player.x >> 3 == this.x >> 3 && player.y >> 3 == this.y >> 3) {
			player.setRock(this);
			level.getEntities().remove(this);
			spawnButton.decrementRockCount();
		}
	}

	public int getColour() {
		return colour;
	}

	public void setColour(int colour) {
		this.colour = colour;
	}
}
