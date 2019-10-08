package game.entities;

import game.Game;
import gfx.Colours;
import gfx.Screen;
import levels.Level;

public class PressurePlate extends Entity {
	
	private String plateName;
	
	public PressurePlate(Level level, int x, int y, String plateName) {
		super(level, plateName);
		this.x = x;
		this.y = y;
		this.isPressurePlate = true;
	}

	@Override
	public void tick() {
		activatePressurePlate();
	}
	
	private void activatePressurePlate() {
		boolean isInteracted = false;
		for(Entity e : Game.game.level.getEntities()) {
			if(!e.isPressurePlate) {
				if(this.x >> 3 == e.x + 4 >> 3 && this.y >> 3 == e.y + 7 >> 3){
					isInteracted = true;
					break;
				}
			}
		}
		if(isInteracted) {
			this.isInteracted = true;
		} else {
			this.isInteracted = false;
		}
		
	}

	@Override
	public void render(Screen screen) {
		int xTile = 0;
		int colour = Colours.get(-1, 333, 444, -1);
		if(this.isInteracted) {
			xTile = 1;
			colour = Colours.get(-1, 222, 333, -1);
		}
		int yTile = 2;
		screen.render(x, y, xTile + yTile * 32, colour, 0x00, 1);
	}
	
	public boolean getIsInteracted() {
		return this.isInteracted;
	}
	
	public String getPlateName() {
		return plateName;
	}

	public void setPlateName(String plateName) {
		this.plateName = plateName;
	}

	public void setIsInteracted(boolean isInteracted) {
		this.isInteracted = isInteracted;
		
	}
	
}
