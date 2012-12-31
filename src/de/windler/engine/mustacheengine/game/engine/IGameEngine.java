package de.windler.engine.mustacheengine.game.engine;

import android.content.Context;
import de.windler.engine.mustacheengine.game.Bitmap.GameBitmap;
import de.windler.engine.mustacheengine.game.font.Font;
import de.windler.engine.mustacheengine.gamescreen.GameScreenManager;
import de.windler.engine.mustacheengine.particle.ParticleEffect;

/**
 * contract of the gameeninge (internal)
 * 
 * @author Nico Windler
 * 
 */
public interface IGameEngine {

	public Context getContext();

	public GameTime getGameTime();

	public GameScreenManager getGameScreenManager();

	/*******************
	 * Textures
	 ***************/

	/**
	 * Creates a gamebitmap at (0,0). Also usable in particleeninge
	 * 
	 * @param id
	 * @return
	 */
	public GameBitmap getGameBitmapFromPool(int id);

	/**
	 * Creates a gamebitmap at (0,0). Also usable in particleeninge
	 * 
	 * @param id
	 * @return
	 */
	public GameBitmap getGameBitmapFromPool(int id, int x, int y);

	/**
	 * recylces the gamebitmap and adds it to the pool
	 * 
	 * @param bmp
	 */
	public void recycleGameBitmap(GameBitmap bmp);

	/**************
	 * Display
	 ********/

	public int getDisplayWidth();

	public int getDisplayHeight();

	/*****************
	 * Sound
	 *************/

	public void playSound(String name);

	public void playMusic();

	public void registerSound(String name, int id);

	public void registerMusic(String name, int id);

	public void changeMusic(String name);

	/***************
	 * FONT
	 *******/

	public void registerFont(Font f);

	public Font getFont(String key);

	public void addParticleEffect(ParticleEffect effect);

}
