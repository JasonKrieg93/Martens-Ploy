package levels.tiles;

import gfx.Colours;
import gfx.Screen;
import levels.Level;

public abstract class Tile {

	public static final Tile[] tiles = new Tile[256];
	public static final Tile VOID = new BasicSolidTile(0, 0, 0, Colours.get(000, -1, -1, -1), 0xff000000);
	public static final Tile STONE = new BasicSolidTile(1, 1, 0, Colours.get(-1, 222, 333, -1), 0xff555555);
	public static final Tile GRASS = new BasicTile(2, 2, 0, Colours.get(-1, 131, 141, -1), 0xff00ff00);
	public static final Tile WATER = new AnimatedTile(3, new int[][] {{0, 5}, {1, 5}, {2, 5}, {3, 5}, {4, 5}}, Colours.get(-1, 004, 115, -1), 0xff0000ff, 500);
	public static final Tile PRESSURE_PLATE = new BasicTile(4, 0, 2, Colours.get(-1, 333, 444, -1), 0xffaaaaaa);
	public static final Tile STONE_DOOR = new BasicSolidTile(5, 0, 1, Colours.get(444, 444, 444, 555), 0xff999999);
	public static final Tile GRASS_2 = new BasicTile(6, 3, 0, Colours.get(-1, 131, 141, -1), 0xff00aa00);
	public static final Tile DIRT_1 = new BasicTile(7, 4, 0, Colours.get(-1, 431, 541, 222), 0xffddaa00);
	public static final Tile DIRT_2 = new BasicTile(8, 5, 0, Colours.get(-1, 431, 541, 222), 0xffaa8800);
	public static final Tile BRICK = new BasicSolidTile(9, 6, 0, Colours.get(-1, 422, 222, 533), 0xffdd6666);
	public static final Tile BUSH_1 = new BasicSolidTile(10, 7, 0, Colours.get(-1, 020, 141, -1), 0xff005500);
	public static final Tile BUSH_2 = new BasicSolidTile(11, 8, 0, Colours.get(-1, 020, 131, -1), 0xff558800);
	public static final Tile STONE_PATTERN_1 = new BasicTile(12, 9, 0, Colours.get(-1, 333, 444, 210), 0xff111111);
	public static final Tile STONE_PATTERN_2 = new BasicTile(13, 10, 0, Colours.get(-1, 333, 444, 210), 0xff222222);
	public static final Tile STONE_PATTERN_3 = new BasicTile(14, 10, 1, Colours.get(-1, 333, 444, 210), 0xff333333);
	public static final Tile STONE_PATTERN_4 = new BasicTile(15, 9, 1, Colours.get(-1, 333, 444, 210), 0xff444444);
	public static final Tile BUTTON = new BasicSolidTile(16, 1, 1, Colours.get(-1, 111, 543, 555), 0xffff0000);
	public static final Tile DIRT_3 = new BasicTile(17, 4, 1, Colours.get(-1, 431, 541, 222), 0xffddaa22);
	public static final Tile DIRT_4 = new BasicTile(18, 5, 1, Colours.get(-1, 431, 541, 222), 0xffaa8855);
	public static final Tile BRIDGE_1 = new BasicTile(19, 11, 0, Colours.get(-1, 111, 444, 320), 0xffff3333);
	public static final Tile BRIDGE_2 = new BasicTile(20, 12, 0, Colours.get(-1, 111, 444, 320), 0xffff4444);
	public static final Tile SPAWN_TILE = new AnimatedTile(21, new int[][] {{0, 4}, {1, 4}, {2, 4}, {3, 4}, {4, 4}, {5, 4}}, Colours.get(-1, 111, 444, 144), 0xffff5555, 500);
	public static final Tile SPAWN_TILE_FAST = new AnimatedTile(22, new int[][] {{0, 6}, {1, 6}, {2, 6}, {3, 6}, {4, 6}, {5, 6}}, Colours.get(-1, 111, 444, 144), 0xffff6666, 50);
	public static final Tile WOOD_FLOOR_1 = new BasicTile(23, 13, 0, Colours.get(-1, 321, 431, -1), 0xffaaaaff);
	public static final Tile WOOD_FLOOR_2 = new BasicTile(24, 14, 0, Colours.get(-1, 321, 431, -1), 0xffbbbbff);
	public static final Tile WOOD_FLOOR_3 = new BasicTile(25, 13, 1, Colours.get(-1, 321, 431, -1), 0xffccccff);
	public static final Tile WOOD_FLOOR_4 = new BasicTile(26, 14, 1, Colours.get(-1, 321, 431, -1), 0xffddddff);
	public static final Tile SMOOTH_FLOOR = new BasicTile(27, 0, 0, Colours.get(133, -1, -1, -1), 0xff33cccc);
	public static final Tile BUTTON_2 = new BasicSolidTile(28, 1, 1, Colours.get(-1, 111, 345, 555), 0xff123456);

	protected byte id;
	protected boolean solid;
	protected boolean emitter;
	protected boolean pressurePlate;
	private int levelColour;
	
	public Tile(int id, boolean isSolid, boolean isEmitter, boolean isPressurePlate, int levelColour) {
		this.id = (byte) id;
		if(tiles[id] != null)
			throw new RuntimeException("Duplicate tile id on " + id);
		tiles[id] = this;
		this.solid = isSolid;
		this.emitter = isEmitter;
		this.pressurePlate = isPressurePlate;
		this.levelColour = levelColour;
		
	}
	
	public byte getID() {
		return id;
	}
	
	public boolean isSolid() {
		return solid;
	}
	
	public boolean isEmitter() {
		return emitter;
	}
	
	public abstract void tick();
	
	public abstract void render(Screen screen, Level level, int x, int y);

	public int getLevelColour() {
		return levelColour;
	}
}
