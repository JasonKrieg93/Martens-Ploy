package game;

import gfx.Colours;
import gfx.FontGFX;
import gfx.Screen;

public class VictoryScreen {

	private String[] options = {"Congratulations", "You have averted", "Marten's Ploy and", "returned to your", "tree with your", "new friend!"};
	
	public VictoryScreen() {
		
	}

	public void render(Screen screen) {

		String msg = (" - VICTORY! - ");
		FontGFX.render(msg, screen, screen.xOffset + screen.width / 2 - ((msg.length() * 8) / 2), screen.yOffset + 10,
				Colours.get(-1, -1, -1, 234), 1);

		int colour = Colours.get(-1, -1, -1, 555);

		for (int i = 0; i < options.length; i++) {
			FontGFX.render(options[i], screen, screen.xOffset + screen.width / 2 - ((options[i].length() * 8) / 2), screen.yOffset + i * 10 + 32, colour, 1);
		}
	}

}
