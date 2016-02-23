package net.fallacysoft.sengine;

import net.fallacysoft.sengine.display.Window;

public abstract class SariasGame
{
	// ---- Fields
	private Window renderer;
	private GameState gameState;
	private boolean running;
	
	// ---- Constructor
	protected SariasGame()
	{
		running = false;
	}
	
	// ---- Get / Set
	protected void setRenderer(Window renderer)
	{
		this.renderer = renderer;
	}
	
	protected void setState(GameState gameState)
	{
		this.gameState = gameState;
	}
	
	// ---- Methods
	protected void create() throws Exception
	{
		if (renderer == null)
			throw new Exception("Sarias Engine Error : La valeur de renderer doit être différente de null");
		
		if (gameState == null)
			throw new Exception("Sarias Engine Error : La valeur de gameState doit être différente de null");
		
		renderer.create();
		renderer.initialize();
		running = true;
	}
	
	protected void run()
	{
		if (!running)
			return;
		
		gameState.initialize();
		while (renderer.isOpen())
		{
			checkState();
			gameState.update();
			gameState.render(renderer);
			renderer.display();
		}
		gameState.unload();
		renderer.dispose();
	}
	
	private void checkState()
	{
		if (gameState.nextState() == null)
			return;
		gameState.unload();
		gameState = gameState.nextState();
		gameState.initialize();
	}
}
