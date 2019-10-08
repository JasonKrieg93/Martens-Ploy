package net.packets;

import net.GameClient;
import net.GameServer;

public class Packet03Use extends Packet {

	private String username;
	private int x;
	private int y;
	
	private int movingDir = 1; // 0 = north, 1 south, 2 = west, 3 = east
	private boolean isUsing;
	
	public Packet03Use(byte[] data) {
		super(03);
		String[] dataArray = readData(data).split(",");
		this.username = dataArray[0];
		this.x = Integer.parseInt(dataArray[1]);
		this.y = Integer.parseInt(dataArray[2]);
		this.movingDir = Integer.parseInt(dataArray[3]);
		this.isUsing = Integer.parseInt(dataArray[4]) == 1;
	}
	
	public Packet03Use(String username, int x, int y, int movingDir, boolean isUsing) {
		super(03);
		this.username = username;
		this.x = x;
		this.y = y;
		this.movingDir = movingDir;
		this.isUsing = isUsing;
	}

	@Override
	public void writeData(GameClient client) {
		client.sendData(getData());
	}

	@Override
	public void writeData(GameServer server) {
		server.sendDataToAllClients(getData());
	}

	@Override
	public byte[] getData() {
		return ("03" + this.username + "," + this.x + "," + this.y + "," + this.movingDir + "," + (this.isUsing ? 1 : 0)).getBytes();
	}
	
	public String getUsername() {
		return username;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}

	public int getMovingDir() {
		return movingDir;
	}

	public void setMovingDir(int movingDir) {
		this.movingDir = movingDir;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isUsing() {
		return isUsing;
	}

	public void setUsing(boolean isUsing) {
		this.isUsing = isUsing;
	}

}
