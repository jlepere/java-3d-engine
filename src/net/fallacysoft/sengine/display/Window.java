package net.fallacysoft.sengine.display;

import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import net.fallacysoft.sengine.graphics.Color;
import net.fallacysoft.sengine.graphics.Renderer;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Window implements Renderer
{
	// ---- Fields
	private String title;
	private int width, height, fps;
	
	// ---- Constructor
	public Window(String title, int width, int height, int fps)
	{
		this.title = title;
		this.width = width;
		this.height = height;
		this.fps = fps;
	}
	
	// ---- Get / Set
	public boolean isOpen()
	{
		if (!Display.isCreated() || Display.isCloseRequested())
			return false;
		return true;
	}
	
	// ---- Methods
	public void create() throws LWJGLException
	{
		if (isOpen())
			return;
		
		Display.setTitle(title);
		Display.setDisplayMode(new DisplayMode(width, height));
		Display.create();
	}
	
	public void initialize()
	{
		glEnable(GL11.GL_BLEND);
		glEnable(GL11.GL_DEPTH_TEST);
		glEnable(GL11.GL_TEXTURE_2D);
		
		glDepthFunc(GL11.GL_LESS);
		glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public void clear(Color color)
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(color.r, color.g, color.b, 1);
	}
	
	public void display()
	{
		Display.update();
		if (fps != -1)
			Display.sync(fps);
	}
	
	public void dispose()
	{
		Display.destroy();
	}
}
