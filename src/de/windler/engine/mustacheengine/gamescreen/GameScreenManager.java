package de.windler.engine.mustacheengine.gamescreen;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Stack;

import android.graphics.Point;
import android.util.Log;
import de.windler.engine.mustacheengine.game.debug.Debug;
import de.windler.engine.mustacheengine.game.engine.GameEngine;
import de.windler.engine.mustacheengine.game.engine.IGameEngine;
import de.windler.engine.mustacheengine.game.sprite.SpriteBatch;
import de.windler.engine.mustacheengine.game.texture.GameScreenTextures;
import de.windler.engine.mustacheengine.input.IInputListener;
import de.windler.engine.mustacheengine.input.InputManager;

/**
 * Manages all GameScreens. This is a stack of GameScreens. The top-Screen will
 * be painted, updated,...
 * 
 * @author Nico Windler
 * 
 */
public class GameScreenManager implements IInputListener {

	private Stack<GameScreen> screens;

	private Dictionary<GameScreen, GameScreenTextures> gameScreenTextures;

	private boolean debug = false;
	private int frames = 0;
	private long elapsedTime = 0; // since last frame
	private InputManager inputManager;
	private GameEngine engine;

	public GameScreenManager(InputManager im, GameEngine eng) {
		screens = new Stack<GameScreen>();
		gameScreenTextures = new Hashtable<GameScreen, GameScreenTextures>();
		inputManager = im;
		inputManager.setInputListener(this);
		engine = eng;
	}

	public void push(GameScreen screen) {
		screens.push(screen);
		GameScreenTextures textures = screen.onLoadTextures();
		gameScreenTextures.put(screen, textures);
		engine.loadTextures(textures.getIdsToLoad());
		screen.onPrepareGameScreen();
		checkDebug();
	}

	public IGameEngine getEngine() {
		return engine;
	}

	/**
	 * registers a touchEvent in the inputmanager
	 * 
	 */
	public void registerTouchEvent(String key, Point start, Point end) {
		inputManager.registerNewTouchState(key, start, end);
	}

	public void registerButtonEvent(String key, int id) {
		inputManager.registerNewBtnState(key, id);
	}

	private void checkDebug() {
		if (screens.lastElement().getClass().isAnnotationPresent(Debug.class)) {
			debug = true;
			elapsedTime = 0;
			frames = 0;
		} else {
			debug = false;
		}
	}

	public GameScreen pop() {
		GameScreen gs = screens.pop();
		GameScreenTextures textures = gameScreenTextures.get(gs);
		engine.unloadTextures(textures.getIdsToUnload());
		gameScreenTextures.remove(gs);
		checkDebug();
		return gs;
	}

	public void drawTopGameScreen(SpriteBatch gameSpriteBatch) {
		screens.lastElement().draw(gameSpriteBatch);
	}

	public void updateTopGameScreen() {
		screens.lastElement().update();

		if (debug) {
			frames++;
			elapsedTime += engine.getGameTime().getElapsedTime();
			if (elapsedTime > 1000) {
				Log.d("FPS", "-->" + frames);
				frames = 0;
				elapsedTime = 0;
			}
		}
	}

	/**
	 * delegate for input
	 */
	public void onInput(InputManager inputManager) {
		screens.lastElement().inputProcessed(inputManager);
	}

}
