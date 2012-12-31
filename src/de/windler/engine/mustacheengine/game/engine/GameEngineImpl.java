package de.windler.engine.mustacheengine.game.engine;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import de.windler.engine.mustacheengine.audio.SoundManager;
import de.windler.engine.mustacheengine.game.Bitmap.GameBitmap;
import de.windler.engine.mustacheengine.game.font.Font;
import de.windler.engine.mustacheengine.game.texture.TextureManager;
import de.windler.engine.mustacheengine.game.ui.BaseGameActivity;
import de.windler.engine.mustacheengine.gamescreen.GameScreenManager;
import de.windler.engine.mustacheengine.input.InputManager;
import de.windler.engine.mustacheengine.particle.ParticleEffect;
import de.windler.engine.mustacheengine.particle.ParticleEngine;

/**
 * the actual GameEngine Implementation. Here is the mustache :)
 * 
 * @author Nico Windler
 * 
 */
public class GameEngineImpl implements GameEngine {

	private Activity context;
	private GameTime gt;
	private InputManager inman;
	private GameScreenManager gsm;
	private SoundManager soundManager;
	private ParticleEngine particleEngine;
	private EngineOptions gameOptions;
	private GameRenderer gameRenderer;
	private TextureManager textureManager;
	private GLSurfaceView glSurfaceView;
	private int displayWidth;
	private int displayHeight;

	public GameEngineImpl(BaseGameActivity activity, EngineOptions options) {
		context = activity;
		gt = new GameTime();
		gameOptions = options;

		textureManager = new TextureManager(this);
		particleEngine = new ParticleEngine(this);

		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		displayWidth = metrics.widthPixels;
		displayHeight = metrics.heightPixels;

		gameRenderer = new GameRenderer(this, options);
	}

	public void prepareSurface(Activity activity) {
		if (gameOptions.isFullscreen()) {
			activity.getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}

		if (gameOptions.isNoTitle()) {
			activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		}

		activity.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	public GLSurfaceView createGLSurfaceView() {
		glSurfaceView = new GLSurfaceView(context);
		glSurfaceView.setRenderer(gameRenderer);

		float ratioWidth = (float) getDisplayWidth()
				/ (float) gameOptions.getGameWidth();
		float ratioHeight = (float) getDisplayHeight()
				/ (float) gameOptions.getGameHeight();
		inman = new InputManager(glSurfaceView, ratioWidth, ratioHeight,
				getDisplayHeight());
		gsm = new GameScreenManager(inman, this);
		return glSurfaceView;
	}

	public Context getContext() {
		return context;
	}

	public GameTime getGameTime() {
		return gt;
	}

	public GameScreenManager getGameScreenManager() {
		return gsm;
	}

	public InputManager getInputManager() {
		return inman;
	}

	public ParticleEngine getParticleEngine() {
		return particleEngine;
	}

	public void addParticleEffect(ParticleEffect effect) {
		particleEngine.addEffect(effect);
	}

	public void onPause() {
		soundManager.pauseMusic();
	}

	public void onResume() {
		textureManager.reloadTextures();
		soundManager.resumeMusic();

	}

	public void onDestroy() {
		soundManager.dispose();
	}

	/*******************
	 * Textures
	 ***************/

	public GameBitmap getGameBitmapFromPool(int id) {
		return textureManager.getGameBitmapFromPool(id);
	}

	public GameBitmap getGameBitmapFromPool(int id, int x, int y) {
		GameBitmap gameBitmap = textureManager.getGameBitmapFromPool(id);
		gameBitmap.setPosition(x, y);
		return gameBitmap;
	}

	public void recycleGameBitmap(GameBitmap bmp) {
		textureManager.recycleGameBitmap(bmp);
	}

	public void loadTextures(int[] ids) {
		textureManager.loadTextures(ids);
	}

	public void unloadTextures(int[] ids) {
		textureManager.unloadTextures(ids);
	}

	/**************
	 * Display
	 ********/

	public int getDisplayWidth() {
		return displayWidth;
	}

	public int getDisplayHeight() {
		return displayHeight;
	}

	/*****************
	 * Sound
	 *************/

	public void createSoundManager(Activity activity) {
		soundManager = new SoundManager(activity);
	}

	public void playSound(String name) {
		soundManager.playSound(name);
	}

	public void playMusic() {
		soundManager.playMusic();
	}

	public void registerSound(String name, int id) {
		soundManager.registerSound(name, id, context);
	}

	public void registerMusic(String name, int id) {
		soundManager.registerMusic(name, id, context);
	}

	public void changeMusic(String name) {
		soundManager.changeMusic(name, context);
	}

	/***************
	 * FONT
	 *******/

	public void registerFont(Font f) {
		textureManager.addFont(f);
	}

	public Font getFont(String key) {
		return textureManager.getFont(key);
	}

}