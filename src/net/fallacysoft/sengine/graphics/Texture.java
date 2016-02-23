package net.fallacysoft.sengine.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class Texture
{
	// ---- Fields
	private int textureId;
	
	// ---- Constructor
	private Texture()
	{
		textureId = 0;
	}
	
	private Texture(int width, int height, int textureId)
	{
		this.textureId = textureId;
	}
	
	// ---- Methods
	public static Texture load(String path) throws IOException
	{
		File file = new File(path);
		if (!file.exists())
			return new Texture();
		
		BufferedImage image = ImageIO.read(file);
		int width = image.getWidth();
		int height = image.getHeight();
		
		int[] pixels = new int[width * height];
		image.getRGB(0, 0, width, height, pixels, 0, width);
		
		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				int i = pixels[x + y * width];
				buffer.put((byte) ((i >> 16) & 0xFF));
				buffer.put((byte) ((i >> 8) & 0xFF));
				buffer.put((byte) ((i) & 0xFF));
				buffer.put((byte) ((i >> 24) & 0xFF));
			}
		}
		buffer.flip();
		
		int id = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		
		buffer.clear();
		
		return new Texture(width, height, id);
	}
	
	public void bind()
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
	}
	
	public void unbind()
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
	public void dispose()
	{
		GL11.glDeleteTextures(textureId);
	}
}
