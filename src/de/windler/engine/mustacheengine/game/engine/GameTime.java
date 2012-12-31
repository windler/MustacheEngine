package de.windler.engine.mustacheengine.game.engine;

/**
 * Saves the creation-time and returns information about the eplased time
 * 
 * @author Nico Windler
 * 
 */
public class GameTime {

	private long lastTime;

	public GameTime() {
		lastTime = System.currentTimeMillis();
	}

	/**
	 * elapsed time since last call of update()
	 * 
	 * @return elapsed time
	 */
	public long getElapsedTime() {
		long time = System.currentTimeMillis();
		return time - lastTime;
	}

	/**
	 * saves new time
	 */
	public void update() {
		lastTime = System.currentTimeMillis();
	}

}
