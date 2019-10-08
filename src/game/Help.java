package game;

import gfx.Colours;
import gfx.FontGFX;
import gfx.Screen;

public class Help {

	private String[] options = {"w - move up", "s - move down", "a - move left", "d - move right", "e - interact", "esc - pause"};
	
	public Help() {
		
	}

	public void render(Screen screen) {

		String msg = (" - Help - ");
		FontGFX.render(msg, screen, screen.xOffset + screen.width / 2 - ((msg.length() * 8) / 2), screen.yOffset + 10,
				Colours.get(-1, -1, -1, 234), 1);

		int colour = Colours.get(-1, -1, -1, 555);

		for (int i = 0; i < options.length; i++) {
			FontGFX.render(options[i], screen, screen.xOffset + 8, screen.yOffset + i * 10 + 32, colour, 1);
		}
		
		colour = Colours.get(0, -1, -1, 511);
		FontGFX.render(">back", screen, screen.xOffset + screen.width / 2 - (4 * 8) / 2,
				screen.yOffset + screen.height - 2 * 8, colour, 1);
	}

}
