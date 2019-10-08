package gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	public String path;
	public int width;
	public int height;
	
	public int[] pixels;
	
	public SpriteSheet(String path) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(SpriteSheet.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Just in case image is not loaded correctly, should be handled by catch block anyway
		if(image == null) {
			return;
		}
		
		this.path = path;
		this.width = image.getWidth();
		this.height = image.getHeight();
		
		pixels = image.getRGB(0, 0, width, height, null, 0, width);

		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = (pixels[i] & 0xff)/64;	//getting rid of the alphachannel of the colour (the first 2 digits are for the alphachannel - setting it to be not opaque (0xffRRGGBB))
		}
	}
}
