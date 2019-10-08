package game.entities;

import game.Game;
import game.InputHandler;
import gfx.Colours;
import gfx.FontGFX;
import gfx.Screen;
import levels.Level;
import net.packets.Packet02Move;
import net.packets.Packet03Use;

public class Player extends Mob {

	private InputHandler input;
	private int colour = Colours.get(-1, 111, 450, 360);
	private int scale = 1;
	protected boolean isSwimming = false;
	private int tickCount = 0;
	private String username;
	protected Screen screen;
	private boolean isUsing = false;
	private int animCount = 0; //Used to count the amount of frames for an animation
	private Rock rock;
	private int rockPickupCD = 0; // CD = cooldown
	private int rockDropCD = 0;
	
	public Player(Level level, int x, int y, InputHandler input, String username, Screen screen) {
		super(level, username, x, y, 1);
		this.input = input;
		this.username = username;
		this.screen = screen;
	}

	public void tick() {
		int xa = 0;
		int ya = 0;
		if(input != null) {
			if(input.up.isPressed()) {
				ya--;
				setUsing(false);
			}
			if(input.down.isPressed()) {
				ya++;
				setUsing(false);
			}
			if(input.left.isPressed()) {
				xa--;
				setUsing(false);
			}
			if(input.right.isPressed()) {
				xa++;
				setUsing(false);
			}
			if(input.e.isPressed()) {
				setUsing(true);
				animCount = 0;
				Packet03Use packet = new Packet03Use(this.username, this.x, this.y, this.movingDir, this.isUsing());
				packet.writeData(Game.game.socketClient);
				
			}
		}
		
		setIsSwimming();
		
		//set isMoving and send packet to the server
		if(xa != 0 || ya != 0) {
			move(xa, ya);
			isMoving = true;
			Packet02Move packet = new Packet02Move(this.username, this.x, this.y, this.numSteps, this.isMoving, this.movingDir);
			packet.writeData(Game.game.socketClient);
		} else {
			isMoving = false;
		}
		
		//interact with stuff
		if(this.isUsing) {
			for(Entity e : level.getEntities()) {
				if(e instanceof Button) {
					((Button)e).interact(this);
				}
				if(this.rock == null) {
					if(!(rockDropCD < 60)) {
						if(e instanceof Rock) {
							((Rock)e).interact(this);
							tickCount = 0;
							rockPickupCD = 0;
						}
					}
				}
				if(e instanceof BlinkingButton) {
					((BlinkingButton)e).interact(this);
				}
			}
			if(this.rock != null) {
				//Add a short cooldown of 150 ticks so that you cant immediately drop the rock after picking it up
				if(!(rockPickupCD < 60)) {
					rock.setColour(Colours.get(-1, 222, 333, 444));
					level.addEntity(rock);
					this.rock = null;
					System.out.println("rockDropped");
					rockDropCD = 0;
				}
			}
		}
		
		if(this.rock != null) {
			rock.x = this.x;
			rock.y = this.y;
		}
		
		tickCount ++;
		rockDropCD++;
		rockPickupCD++;
	}

	private void setIsSwimming() {
		if(level.getTile(this.x + 3 >> 3, this.y + 4 >> 3).getID() == 3){
			isSwimming = true;
		}
		if(isSwimming && level.getTile(this.x + 3 >> 3, this.y + 4 >> 3).getID() != 3){
			isSwimming = false;
		}
	}
	
	public void render(Screen screen) {
		//These are the tiles on the spritesheet that represent the player character
		int xTile = 0;
		int yTile = 26;
		int walkingSpeed = isSwimming ? 4 : 3; // Animation speed, not movement speed

		switch(movingDir) {
		case 0:
			yTile += ((numSteps >> walkingSpeed) & 1) * 2;
			break;
		case 1:
			xTile += 2;
			yTile += ((numSteps >> walkingSpeed) & 1) * 2;
			break;
		case 2:
			xTile += 4;
			yTile += ((numSteps >> walkingSpeed) & 1) * 2;
			break;
		case 3:
			xTile += 6;
			yTile += ((numSteps >> walkingSpeed) & 1) * 2;
			break;
		}
		
		int modifier = 8 * scale;
		int xOffset = x - modifier / 2;
		int yOffset = y - modifier / 2 - 4; // sets the middle of the character
		
		if(isUsing()) {
			xTile = 8;
			yTile = 28;
			if(animCount >= 25) {
				animCount = 0;
				setUsing(false);
			}
			if(movingDir != 2)
				movingDir = 3;
			if(movingDir == 2)
				xTile += 2;
		}

		if(isSwimming) {
			int waterColour = 0;
			yOffset += 4;
			if(tickCount % 60 < 15) {
				waterColour = Colours.get(-1, -1, 225, -1);
			} else if (15 <= tickCount % 60 && tickCount % 60 < 30) {
				yOffset -= 1;
				waterColour = Colours.get(-1, 225, 115, -1);
			} else if(30 <= tickCount % 60 && tickCount % 60 < 45) {
				waterColour = Colours.get(-1, 115, -1, 225);
			} else {
				yOffset -= 1;
				waterColour = Colours.get(-1, 225, 115, -1);
			}
			screen.render(xOffset, yOffset + 3, 0 + 25 * 32, waterColour, 0x01, 1);
			screen.render(xOffset + 8, yOffset + 3, 0 + 25 * 32, waterColour, 0x00, 1);
		}
		// the player takes up 4 tiles on the grid so we need to render all of them
		screen.render(xOffset, yOffset, xTile + yTile * 32, colour, 0x00, scale);
		screen.render(xOffset + modifier, yOffset, (xTile + 1) + yTile * 32, colour, 0x00, scale);

		//render bottom half of player if not swimming
		if(!isSwimming) {
			screen.render(xOffset, yOffset + modifier, xTile + (yTile + 1) * 32, colour, 0x00, scale);
			screen.render(xOffset + modifier, yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, colour, 0x00, scale);
		}
		
		if(username != null) {
			FontGFX.render(username, screen, xOffset - ((username.length() - 1) / 2 * 8), yOffset - 10, Colours.get(-1, -1, -1, 555), 1);
		}
		
		animCount++;
	}

	public boolean hasCollided(int xa, int ya) {
		int xMin = 0;
		int xMax = 7;
		int yMin = 3;
		int yMax = 7;
		for(int x = xMin; x < xMax; x++) {
			if(isSolidTile(xa, ya, x, yMin)) {
				return true;
			}
		}
		for(int x = xMin; x < xMax; x++) {
			if(isSolidTile(xa, ya, x, yMax)){
				return true;
			}
		}
		for(int y = yMin; y < yMax; y++) {
			if(isSolidTile(xa, ya, xMin, y)){
				return true;
			}
		}
		for(int y = yMin; y < yMax; y++) {
			if(isSolidTile(xa, ya, xMax, y)){
				return true;
			}
		}
		return false;
	}

	public String getUsername() {
		return this.username;
	}

	public boolean isUsing() {
		return isUsing;
	}

	public void setUsing(boolean isUsing) {
		this.isUsing = isUsing;
	}

	public Rock getRock() {
		return rock;
	}

	public void setRock(Rock rock) {
		this.rock = rock;
	}
}
