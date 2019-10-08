package game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import game.entities.Player;
import game.entities.PlayerMP;
import game.entities.PressurePlate;
import game.entities.VictoryPlate;
import gfx.Screen;
import gfx.SpriteSheet;
import levels.Level;
import levels.Level1;
import net.GameClient;
import net.GameServer;
import net.packets.Packet00Login;
import net.packets.Packet01Disconnect;

public class Game extends Canvas implements Runnable, KeyListener {

	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 160;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final int SCALE = 5;
	public static final String NAME = "Marten's Ploy";
	public static Game game;

	public JFrame frame;

	public boolean running = false;
	public boolean gameStarted = false;
	public int tickCount = 0;

	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	private int[] colours = new int[6 * 6 * 6]; // 6 shades for each of the 4 colours (6*6*6 == RGB)

	private Screen screen;
	public InputHandler input;
	public WindowHandler windowHandler;
	public Level level;
	public Player player;
	private Menu menu;
	private Help help;
	private VictoryScreen victory;

	private Level1 level1;

	public GameClient socketClient;
	public GameServer socketServer;

	private enum STATE {
		MENU, GAME, HELP, VICTORY;
	};

	private STATE state = STATE.MENU;

	public Game() {
		setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		frame = new JFrame(NAME);
		addKeyListener(this);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		frame.add(this, BorderLayout.CENTER);
		frame.pack();

		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void init() {
		game = this;
		int index = 0;
		for (int r = 0; r < 6; r++) {
			for (int g = 0; g < 6; g++) {
				for (int b = 0; b < 6; b++) {
					int rr = (r * 255 / 5);
					int gg = (g * 255 / 5);
					int bb = (b * 255 / 5);

					colours[index++] = rr << 16 | gg << 8 | bb;

				}
			}
		}
		screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("/spritesheet.png"));
		input = new InputHandler(this);
		windowHandler = new WindowHandler(this);
		level1 = new Level1();
		level = new Level(level1.LEVEL_PATH);
		level1.init(level);
		menu = new Menu();
		help = new Help();
		victory = new VictoryScreen();

	}

	public synchronized void start() {
		running = true;
		new Thread(this).start();
	}

	public synchronized void stop() {
		running = false;
	}

	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D / 60D; // This is how many nano seconds per tick. (D = double)

		int ticks = 0;
		int frames = 0;

		long lastTimer = System.currentTimeMillis();
		double delta = 0; // how many nano seconds have gone by so far. resets once it reaches the
							// nsPerTick value

		init();

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;

			while (delta >= 1) {
				ticks++;
				tick();
				delta -= 1;
				shouldRender = true;
			}

			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// Join/start the server only once the player selects play but before the next
			// render function
			if (state == STATE.GAME) {
				if (!gameStarted) {
					if (JOptionPane.showConfirmDialog(this, "Do you want to run the server?") == 0) {
						socketServer = new GameServer(this);
						socketServer.start();
						socketClient = new GameClient(this, "localhost");
						socketClient.start();
					} else {
						socketClient = new GameClient(this,
								JOptionPane.showInputDialog(this, "Enter the host's IP address"));
						socketClient.start();
					}

					player = new PlayerMP(level, 304, 40, input,
							JOptionPane.showInputDialog(this, "Please enter your username"), null, -1, screen);
					System.out.println(player.getName());
					level.getEntities().add(player);

					Packet00Login loginPacket = new Packet00Login(player.getUsername(), player.x, player.y);
					if (socketServer != null) {
						socketServer.addConnection((PlayerMP) player, loginPacket);
					}
					loginPacket.writeData(socketClient);

					gameStarted = true;

					playMusic("/music/martensploy.aif");
				}
			}

			if (shouldRender) {
				frames++;
				render();
			}

			if (System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				frames = 0;
				ticks = 0;
			}

		}
	}

	private void playMusic(String song) {
		try {
			AudioInputStream audio = AudioSystem.getAudioInputStream(Game.class.getResource(song));
			AudioFormat format = audio.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			Clip clip = (Clip) AudioSystem.getLine(info);
			clip.open(audio);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * game variables are updated here. updates happen 60 times/second
	 */
	public void tick() {
		if (state == STATE.GAME) {
			tickCount++;
			level.tick();
			level1.tick();
		}
		if (level.getEntityByName("vp1").getIsInteracted() && level.getEntityByName("vp2").getIsInteracted()) {
			state = STATE.VICTORY;
		}
			
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		if (state == STATE.GAME) {
			int xOffset = player.x - (screen.width / 2);
			int yOffset = player.y - (screen.height / 2);
			level.renderTiles(screen, xOffset, yOffset);
			level.renderEntities(screen);
		} else if (state == STATE.MENU) {
			level.renderBlackTiles(screen, 0, 0);
			menu.render(screen);
		} else if (state == STATE.HELP) {
			level.renderBlackTiles(screen, 0, 0);
			help.render(screen);
		} else if (state == STATE.VICTORY) {
			level.renderBlackTiles(screen, 0, 0);
			victory.render(screen);
		}

		for (int y = 0; y < screen.height; y++) {
			for (int x = 0; x < screen.width; x++) {
				int colourCode = screen.pixels[x + y * screen.width];
				if (colourCode < 255)
					pixels[x + y * WIDTH] = colours[colourCode];
			}
		}

		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		new Game().start();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (state == STATE.MENU) {
			// Main Menu navigation
			if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
				menu.setCurrentSelection(menu.getCurrentSelection() + 1);
			} else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
				menu.setCurrentSelection(menu.getCurrentSelection() == 0 ? 2 : menu.getCurrentSelection() - 1);
			}

			if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_E) {
				switch (menu.getCurrentSelection()) {
				case 0:
					state = STATE.GAME;
					break;

				case 1:
					state = STATE.HELP;
					break;

				case 2:
					int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit?", JOptionPane.YES_NO_OPTION);
					if(confirm == JOptionPane.YES_OPTION) {
						if(this.player != null) {
							Packet01Disconnect packet = new Packet01Disconnect(this.player.getUsername());
							packet.writeData(this.socketClient);
						}
						System.exit(1);
					}
				}
			}

			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				state = STATE.GAME;
				return;
			}
		}

		else if (state == STATE.HELP) {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_E) {
				state = STATE.MENU;
			}
		}

		else if (state == STATE.GAME) {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				state = STATE.MENU;
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
