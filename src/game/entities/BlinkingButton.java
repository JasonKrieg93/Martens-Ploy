package game.entities;

import java.util.ArrayList;
import java.util.List;

import gfx.Colours;
import gfx.Screen;
import levels.Level;

public class BlinkingButton extends Entity {

	private int colour;
	private int currentColourIndex;
	private long lastIterationTime;
	private int animationSwitchDelay;
	private List<Integer> animationColours = new ArrayList<>();
	private int activateRangeX;
	private int activateRangeY;
	private int activateMovingDir;
	private Entity pair;
	private Integer timer;
	private long lastTimer;

	public BlinkingButton(Level level, int x, int y, String name, int animationSwitchDelay, int activateRangeX, int activateRangeY, int activateMovingDir, Integer timer) {
		super(level, name);
		this.x = x;
		this.y = y;
		this.activateRangeX = activateRangeX;
		this.activateRangeY = activateRangeY;
		this.activateMovingDir = activateMovingDir;
		this.lastIterationTime = System.currentTimeMillis();
		this.animationSwitchDelay = animationSwitchDelay;
		this.animationColours.add(Colours.get(-1, 111, 345, 511));
		this.animationColours.add(Colours.get(-1, 111, 345, 555));
		this.animationColours.add(Colours.get(-1, 111, 345, 511));
		this.animationColours.add(Colours.get(-1, 111, 345, 555));
		this.animationColours.add(Colours.get(-1, 111, 345, 511));
		this.animationColours.add(Colours.get(-1, 111, 345, 555));
		this.animationColours.add(Colours.get(-1, 111, 345, 151));
		this.animationColours.add(Colours.get(-1, 111, 345, 555));
		this.timer = timer;
	}
	
	public void addpair(Entity pair) {
		this.pair = pair;
	}

	@Override
	public void tick() {
		if(!(colour == Colours.get(-1, 111, 345, 151))) {
			if(pair != null) {
				if(!pair.getIsInteracted()) {
					isInteracted = false;
				}
			}
		}
		
		if(timer != null) {
			if((System.currentTimeMillis() - lastTimer) >= timer) {
				lastTimer = System.currentTimeMillis();
				isInteracted = false;
			}
		}
	}
	
	public void interact(Player player) {
		if (player.x >> 3 == this.activateRangeX && player.y >> 3 == this.activateRangeY
				&& player.movingDir == this.activateMovingDir) {
			if(colour == Colours.get(-1, 111, 345, 151)) {
				isInteracted = true;
			}
		}
	}

	@Override
	public void render(Screen screen) {
		int xTile = 1;
		int yTile = 1;
		if (isInteracted) {
			colour = Colours.get(-1, 111, 345, 550);
		} else {
			if ((System.currentTimeMillis() - lastIterationTime) >= (animationSwitchDelay)) {
				lastIterationTime = System.currentTimeMillis();
				currentColourIndex = (currentColourIndex + 1) % animationColours.size();
				this.colour = animationColours.get(currentColourIndex);
			}
		}

		screen.render(x, y, xTile + yTile * 32, colour, 0x00, 1);

	}

}
