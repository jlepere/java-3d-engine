package net.fallacysoft.sengine;

import net.fallacysoft.sengine.graphics.Renderer;

public interface GameState
{
	public GameState nextState();
	public void initialize();
	public void unload();
	public void update();
	public void render(Renderer render);
}
