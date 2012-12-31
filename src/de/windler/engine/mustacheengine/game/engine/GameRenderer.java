package de.windler.engine.mustacheengine.game.engine;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;
import de.windler.engine.mustacheengine.game.engine.policy.ResolutionPolicy;
import de.windler.engine.mustacheengine.game.sprite.SpriteBatch;

/**
 * GameRenderer is a fixed-step renderer (25 ticks, max frameskip 5)
 * 
 * @author Nico Windler
 * 
 */
public class GameRenderer implements Renderer {

	private final int TICKS_PER_SECOND = 25;
	private final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
	private final int MAX_FRAMESKIP = 5;

	private int loops = 0;
	private long nextGameTick = 0;

	private GameEngine engine;
	private EngineOptions engineOptions;
	private SpriteBatch gameSpriteBatch;

	public GameRenderer(GameEngine engine, EngineOptions options) {
		this.engine = engine;
		engineOptions = options;
	}

	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		gl.glTranslatef(0.0f, 0.0f, -100);

		loops = 0;
		nextGameTick = engine.getGameTime().getElapsedTime();
		GameTime gt = engine.getGameTime();
		while (gt.getElapsedTime() >= nextGameTick && loops < MAX_FRAMESKIP) {
			engine.getInputManager().update(gt);
			engine.getGameScreenManager().updateTopGameScreen();
			engine.getParticleEngine().update(engine);
			loops++;
			nextGameTick += SKIP_TICKS;
			gt.update();
		}
		engine.getGameScreenManager().drawTopGameScreen(gameSpriteBatch);
		engine.getParticleEngine().draw(gameSpriteBatch);
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		if (height == 0) {
			height = 1;
		}

		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();

		if (engineOptions.getResolutionPolicy() == ResolutionPolicy.FILL) {
			gl.glViewport(0, 0, width, height);
			gl.glOrthof(0, engineOptions.getGameWidth(), 0,
					engineOptions.getGameHeight(), 1, 100);
		}

		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gameSpriteBatch = new SpriteBatch(gl, engine);

		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);

		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
	}

}
