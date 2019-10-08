package game.entities;

import gfx.Screen;
import levels.Level;

/*
 * abstract class for all moving entities in the game
 */
public abstract class Entity {

	public int x;
	public int y;
	protected Level level;
	protected boolean isPressurePlate;
	protected boolean isInteracted;
	protected boolean solid;
	private String name;
	
	public Entity(Level level, String name) {
		init(level);
		this.isPressurePlate = false;
		this.isInteracted = false;
		this.name = name;
	}
	
	public final void init(Level level) {
		this.level = level;
	}
	
	public boolean isSolid() {
		return solid;
	}
	
	public String getName() {
		return name;
	}
	
	public abstract void tick();
	
	public abstract void render(Screen screen);

	public boolean getIsInteracted() {
		return isInteracted;
	}
}
