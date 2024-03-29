package game.entities;

import levels.Level;
import levels.tiles.Tile;

public abstract class Mob extends Entity {

	protected int speed;
	protected int numSteps = 0;
	protected boolean isMoving;
	protected int movingDir = 1; // 0 = north, 1 south, 2 = west, 3 = east
	protected int scale = 1;

	public Mob(Level level, String name, int x, int y, int speed) {
		super(level, name);
		this.speed = speed;
		this.x = x;
		this.y = y;
		this.solid = false;
	}

	public void move(int xa, int ya) {
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			numSteps--;
			return;
		}
		numSteps++;
		if (!hasCollided(xa, ya)) {
			if (ya < 0)
				movingDir = 0;
			if (ya > 0)
				movingDir = 1;
			if (xa < 0)
				movingDir = 2;
			if (xa > 0)
				movingDir = 3;
			x += xa * speed;
			y += ya * speed;
		}
	}

	public abstract boolean hasCollided(int xa, int ya);

	protected boolean isSolidTile(int xa, int ya, int x, int y) {
		if (level == null)
			return false;
		Tile lastTile = level.getTile((this.x + x) >> 3, (this.y + y) >> 3);
		Tile newTile = level.getTile((this.x + x + xa) >> 3, (this.y + y + ya) >> 3);
		if (!lastTile.equals(newTile) && newTile.isSolid())
			return true;
		return false;
	}

	public void setNumSteps(int numSteps) {
		this.numSteps = numSteps;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	public void setMovingDir(int movingDir) {
		this.movingDir = movingDir;
	}

}
