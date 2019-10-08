package game.entities;

import java.util.ArrayList;
import java.util.List;

import gfx.Colours;
import gfx.Screen;
import levels.Level;
import levels.tiles.Tile;

public class Door extends Entity {
	
	protected int tickCount;
	public boolean startOpen;
	private boolean isOpen;
	protected List<Entity> activaters = new ArrayList<>();
	protected int colour;
	private byte mirror; 
	protected int animCount = 0;

	public Door(Level level, int x, int y, String doorName, byte mirror) {
		super(level, doorName);
		this.solid = true;
		this.tickCount = 0;
		this.startOpen = false;
		this.setOpen(false);
		this.colour = Colours.get(444, 444, 444, 555);
		this.x = x;
		this.y = y;
		this.mirror = mirror;
	}
	
	public void openDoor() {
		
		if(animCount % 100 < 25) {
			this.colour = Colours.get(444, 444, 555, 144);
		} else if(25 <= animCount % 100 && animCount % 100 < 50) {
			this.colour = Colours.get(444, 555, 144, 144);
		} else if(50 <= animCount % 100 && animCount % 100 < 75) {
			this.colour = Colours.get(555, 144, 144, 144);
		} else if(75 <= animCount % 100 && animCount % 100 < 100){
			this.solid = false;
			this.setOpen(true);
			this.startOpen = false;
			level.alterTile(this.x >> 3, this.y >> 3, Tile.tiles[2]);
		}
		
	}

	@Override
	public void tick() {
		int activaterCount = 0;
		for(Entity p : activaters) {
			if(p.getIsInteracted())
				activaterCount++;
		}
		if(activaterCount == activaters.size() && !isOpen())
			startOpen = true;
		if(startOpen) {
			animCount = (animCount + 1) % 100;
			openDoor();
		}
		tickCount = (tickCount + 1) % 100;
	}

	@Override
	public void render(Screen screen) {
		screen.render(x, y, 0 + 1 * 32, colour, mirror, 1);
	}
	
	public void addActivater(Entity entity) {
		this.activaters.add(entity);
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	
}
