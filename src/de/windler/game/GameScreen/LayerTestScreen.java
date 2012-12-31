package de.windler.game.GameScreen;

import android.graphics.Point;
import de.nw90.jawi.R;
import de.windler.engine.mustacheengine.FiniteStateMachine.Actor;
import de.windler.engine.mustacheengine.FiniteStateMachine.Button;
import de.windler.engine.mustacheengine.game.Bitmap.GameBitmap;
import de.windler.engine.mustacheengine.game.Color.Color;
import de.windler.engine.mustacheengine.game.debug.Debug;
import de.windler.engine.mustacheengine.game.engine.IGameEngine;
import de.windler.engine.mustacheengine.game.sprite.SpriteBatch;
import de.windler.engine.mustacheengine.game.texture.GameScreenTextures;
import de.windler.engine.mustacheengine.gamescreen.GameScreen;
import de.windler.engine.mustacheengine.gamescreen.GameScreenManager;
import de.windler.engine.mustacheengine.input.InputManager;
import de.windler.game.LayerAnimations.ScaleAnimation;
import de.windler.game.particleEffects.ParticleEffectPool;
import de.windler.game.states.InitState;

@Debug
public class LayerTestScreen extends GameScreen {

	public LayerTestScreen(GameScreenManager gsm) {
		super(gsm);
	}

	private ParticleEffectPool pep;

	private Button btn1;
	private Button btn2;
	private Button btn3;
	private Point pt;
	private Color cl = Color.normal();

	@Override
	public void onPrepareGameScreen() {
		IGameEngine engine = getEngine();

		pt = new Point(10, 10);

		GameBitmap createGameBitmap = engine
				.getGameBitmapFromPool(R.drawable.strand_p2);
		getFSM().addActor(new Actor(InitState.instance(), 1, createGameBitmap));
		getFSM().addActor(
				new Actor(InitState.instance(), 2, engine
						.getGameBitmapFromPool(R.drawable.hud_neu)));

		btn1 = new Button("shake it", engine.getFont("standard"), 2, 3,
				engine.getGameBitmapFromPool(R.drawable.rahmen, 0, 100));
		btn2 = new Button("menu", engine.getFont("standard"), 2, 3,
				engine.getGameBitmapFromPool(R.drawable.rahmen, 0, 200));
		btn3 = new Button("particles", engine.getFont("standard"), 2, 3,
				engine.getGameBitmapFromPool(R.drawable.rahmen));

		addButton(btn1);
		addButton(btn2);
		addButton(btn3);

		// engine.changeMusic("intro");
		// engine.playMusic();s

		pep = new ParticleEffectPool();
	}

	@Override
	public void update() {
		getFSM().update(getEngine());
		getFSM().addTextToLayer("Test Text", "standard", pt, 1, 0, cl, 1);
	}

	@Override
	public void draw(SpriteBatch gameSpriteBatch) {
		getFSM().draw(gameSpriteBatch);
	}

	@Override
	public void inputProcessed(InputManager inputManager) {
		if (inputManager.getState(btn1).justPressed()) {
			// getEngine().playSound("btnClick");

			for (int i = 0; i < 10; i++) {
				getFSM().addLayerAnimation(new ScaleAnimation(100, 0, 20), 1);
				getFSM().addLayerAnimation(new ScaleAnimation(100, 20, 0), 1);

				getFSM().addLayerAnimation(new ScaleAnimation(100, 0, 20), 2);
				getFSM().addLayerAnimation(new ScaleAnimation(100, -20, 0), 2);
			}
		}

		if (inputManager.getState(btn2).justPressed()) {
			getGSM().push(new PauseGameScreen(getGSM(), getFSM()));
			// getEngine().playSound("btnClick");
		}

		if (inputManager.getState(btn3).justPressed()) {
			getEngine().addParticleEffect(pep.getPoolItem());
			// getEngine().playSound("btnClick");
		}
	}

	@Override
	public GameScreenTextures onLoadTextures() {
		GameScreenTextures t = new GameScreenTextures();
		t.addTexture(R.drawable.hud_neu);
		t.addTexture(R.drawable.strand_p2);
		t.addTexture(R.drawable.rahmen);
		return t;
	}

}
