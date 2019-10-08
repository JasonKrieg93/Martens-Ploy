package game.entities;

import java.net.InetAddress;

import game.InputHandler;
import gfx.Screen;
import levels.Level;

public class PlayerMP extends Player {

	public InetAddress ipAddress;
	public int port;
	
	public PlayerMP(Level level, int x, int y, InputHandler input, String username, InetAddress ipAddress, int port, Screen screen) {
		super(level, x, y, input, username, null);
		this.ipAddress = ipAddress;
		this.port = port;
		this.screen = screen;
	}
	
	//This constructor is for the connecting player, not the local one
	public PlayerMP(Level level, int x, int y, String username, InetAddress ipAddress, int port) {
		super(level, x, y, null, username, null);
		this.ipAddress = ipAddress;
		this.port = port;
	}
	
	@Override
	public void tick() {
		super.tick();
	}

}
