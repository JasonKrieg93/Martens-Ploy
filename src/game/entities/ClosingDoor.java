package game.entities;

import gfx.Colours;
import levels.Level;
import levels.tiles.Tile;

//Like a toggle door, once you step off the pressure plate then it closes
public class ClosingDoor extends Door{

	private int modifier;
	
	public ClosingDoor(Level level, int x, int y, String doorName, byte mirror, int modifier) {
		super(level, x, y, doorName, mirror);
		this.modifier = modifier;
	}
	
	@Override
	public void tick() {
		int activaterCount = 0;
		for(Entity p : activaters) {
			if(p.getIsInteracted())
				activaterCount++;
		}
		//modifier is for a few doors in the game which can be opened by any combination of 2 of a possible 4 pressure plates - in that case modifier = 2, all other cases modifier = 1
		if(activaterCount == (activaters.size() / modifier)) {
			startOpen = true;
		} else {
			setOpen(false);
			startOpen = false;
			this.animCount = 0;
			colour = Colours.get(444, 444, 444, 555);
			level.alterTile(this.x >> 3, this.y >> 3, Tile.tiles[5]);
		}
		if(startOpen && !isOpen()) {
			animCount = (animCount + 1) % 100;
			openDoor();
		}
		
		tickCount = (tickCount + 1) % 100;
	}

}
