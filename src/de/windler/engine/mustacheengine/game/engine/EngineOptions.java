package de.windler.engine.mustacheengine.game.engine;

import de.windler.engine.mustacheengine.game.engine.policy.ResolutionPolicy;

/**
 * the options of the engine
 * 
 * @author Nico Windler
 * 
 */
public class EngineOptions {

	private ResolutionPolicy gameResolutionPolicy;
	private boolean isFullscreen;
	private boolean isNoTitle;
	private int gameWidth;
	private int gameHeight;

	public EngineOptions(ResolutionPolicy resolutionPolicy, int width,
			int height, boolean fullscreen, boolean noTitle) {
		gameResolutionPolicy = resolutionPolicy;
		gameWidth = width;
		gameHeight = height;
		isFullscreen = fullscreen;
		isNoTitle = noTitle;
	}

	public ResolutionPolicy getResolutionPolicy() {
		return gameResolutionPolicy;
	}

	public int getGameWidth() {
		return gameWidth;
	}

	public int getGameHeight() {
		return gameHeight;
	}

	public boolean isFullscreen() {
		return isFullscreen;
	}

	public boolean isNoTitle() {
		return isNoTitle;
	}
}
