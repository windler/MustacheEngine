package de.windler.engine.mustacheengine.game.engine;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import de.windler.engine.mustacheengine.input.InputManager;
import de.windler.engine.mustacheengine.particle.ParticleEngine;

/**
 * this is the contract for the gameengine (public)
 * 
 * @author Nico Windler
 * 
 */
public interface GameEngine extends IGameEngine {

	/**
	 * loads textures / for ids
	 * 
	 * @param ids
	 */
	public void loadTextures(int[] ids);

	/**
	 * unloads textures / for ids
	 * 
	 * @param ids
	 */
	public void unloadTextures(int[] ids);

	public InputManager getInputManager();

	public ParticleEngine getParticleEngine();

	/**
	 * prepares the surface
	 * 
	 * @param act
	 */
	public void prepareSurface(Activity act);

	/**
	 * creates and returns the surface view
	 */
	public GLSurfaceView createGLSurfaceView();

	public void createSoundManager(Activity act);

	public void onPause();

	public void onResume();

	public void onDestroy();
}
