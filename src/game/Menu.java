package game;

import gfx.Colours;
import gfx.FontGFX;
import gfx.Screen;

public class Menu {

	private String[] options = { "Play", "Help", "Quit" };
	private int currentSelection;
	
	public Menu() {
		this.currentSelection = 0;
	}

	public void render(Screen screen) {

		String msg = (" - Marten's Ploy - ");
		FontGFX.render(msg, screen, screen.xOffset + screen.width / 2 - ((msg.length() * 8) / 2), screen.yOffset + 10,
				Colours.get(-1, -1, -1, 234), 1);

		int deselectedColour = Colours.get(0, -1, -1, 555);
		int selectedColour = Colours.get(0, -1, -1, 511);
		int colour = 0;
		String prefix = "";

		for (int i = 0; i < options.length; i++) {
			if (i == currentSelection) {
				colour = selectedColour;
				prefix = ">";
			} else {
				colour = deselectedColour;
				prefix = "";
			}
			FontGFX.render(prefix + options[i], screen, screen.xOffset + screen.width / 2 - ((options[i].length() * 8) / 2),
					screen.yOffset + i * 10 + 40, colour, 1);
		}
	}
	
	public int getCurrentSelection() {
		return this.currentSelection;
	}
	public void setCurrentSelection(int currentSelection) {
		this.currentSelection = currentSelection % options.length;
	}
}
