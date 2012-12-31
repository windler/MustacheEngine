package de.windler.engine.mustacheengine.game.ui;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import de.windler.engine.mustacheengine.game.engine.EngineOptions;
import de.windler.engine.mustacheengine.game.engine.GameEngine;
import de.windler.engine.mustacheengine.game.engine.GameEngineImpl;
import de.windler.engine.mustacheengine.game.engine.IGameEngine;
import de.windler.engine.mustacheengine.gamescreen.GameScreenManager;

/**
 * This is the base for the android project
 * 
 * @author Nico Windler
 * 
 */
public abstract class BaseGameActivity extends Activity {

	private GLSurfaceView glSurfaceView;
	private GameEngine engine;

	/**
	 * called when GameEngine is needed for the firt time
	 * 
	 * @return GameEngine of BaseActivity
	 */
	public abstract EngineOptions onCreateGameOptions();

	/**
	 * called as soon as the GameScreenManager of Activity is created
	 */
	public abstract void onCreateGameScreenManager(GameScreenManager gsm);

	/**
	 * You can load fonts, sounds, music and so on. Take a look at:
	 * {@code registerFont() }, {@code registerMusic()}, {@code registerSound()}
	 * 
	 */
	public abstract void onLoadResources(IGameEngine engine);

	/**
	 * Ids of textures which should be loaded as soon as the game is created.
	 * Those CAN NOT BE UNLOADED WHILE RUNTIME. Load all textures for the
	 * paricleengine here.
	 * 
	 * 
	 * @return
	 */
	public abstract int[] onLoadTextures();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		engine = new GameEngineImpl(this, onCreateGameOptions());
		engine.prepareSurface(this);
		glSurfaceView = engine.createGLSurfaceView();
		setContentView(glSurfaceView);
		engine.createSoundManager(this);

		int[] textIds = onLoadTextures();
		if (textIds != null && textIds.length > 0) {
			engine.loadTextures(onLoadTextures());
		}

		onLoadResources(engine);
		onCreateGameScreenManager(engine.getGameScreenManager());

	}

	@Override
	protected void onPause() {
		super.onPause();
		glSurfaceView.onPause();
		engine.onPause();
	}

	@Override
	public void onBackPressed() {
		// nichts tun
	}

	@Override
	protected void onResume() {
		super.onResume();
		glSurfaceView.onResume();
		engine.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		engine.onDestroy();
	}
}
