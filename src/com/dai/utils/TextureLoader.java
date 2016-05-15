package com.dai.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import com.dai.render.Texture;
import static org.lwjgl.opengl.GL11.*;

public class TextureLoader {

	public static Texture getTexture(String ext, File file) {
		try {
			BufferedImage image = ImageIO.read(file);
			int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
			ByteBuffer buffer = Util.createByteBuffer(image.getHeight() * image.getWidth() * 4);
			boolean hasAlpha = image.getColorModel().hasAlpha();
			for (int y = 0; y < image.getHeight(); y++) {
				for (int x = 0; x < image.getWidth(); x++) {
					int pixel = pixels[y * image.getWidth() + x];
					buffer.put((byte) ((pixel >> 16) & 0xFF));
					buffer.put((byte) ((pixel >> 8) & 0xFF));
					buffer.put((byte) ((pixel) & 0xFF));
					if (hasAlpha) {
						buffer.put((byte) ((pixel >> 24) & 0xFF));
					} else {
						buffer.put((byte) (0xFF));
					}
				}
			}
			buffer.flip();
			
			int id = glGenTextures();
			glBindTexture(GL_TEXTURE_2D, id);
			
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

			glPixelStorei(GL_UNPACK_ALIGNMENT, 1);  
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
			glPixelStorei(GL_UNPACK_ALIGNMENT, 4);
			return new Texture(id);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}
