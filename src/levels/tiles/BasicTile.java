package levels.tiles;

import gfx.Screen;
import levels.Level;

public class BasicTile extends Tile{

	protected int tileID;
	protected int tileColour;
	
	//tileColour is the colour of the tile in the game, levelColour the reference to the tile when generating the world
	public BasicTile(int id, int x, int y, int tileColour, int levelColour) {
		super(id, false, false, false, levelColour);
		this.tileID = x + y * 32;
		this.tileColour = tileColour;
	}

	public void render(Screen screen, Level level, int x, int y) {
		screen.render(x, y, tileID, tileColour, 0x00, 1);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

}
