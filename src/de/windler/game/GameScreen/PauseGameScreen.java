package de.windler.game.GameScreen;

import de.nw90.jawi.R;
import de.windler.engine.mustacheengine.FiniteStateMachine.Actor;
import de.windler.engine.mustacheengine.FiniteStateMachine.Button;
import de.windler.engine.mustacheengine.FiniteStateMachine.FiniteStateMachine;
import de.windler.engine.mustacheengine.game.sprite.SpriteBatch;
import de.windler.engine.mustacheengine.game.texture.GameScreenTextures;
import de.windler.engine.mustacheengine.gamescreen.GameScreen;
import de.windler.engine.mustacheengine.gamescreen.GameScreenManager;
import de.windler.engine.mustacheengine.input.InputManager;
import de.windler.game.states.InitState;

public class PauseGameScreen extends GameScreen {

	private FiniteStateMachine oldFSM;
	private Button btn;

	public PauseGameScreen(GameScreenManager gsm, FiniteStateMachine fsm) {
		super(gsm);
		oldFSM = fsm;
	}

	@Override
	public void onPrepareGameScreen() {

		btn = new Button("Back", getEngine().getFont("standard"), 2, 1,
				getEngine().getGameBitmapFromPool(R.drawable.rahmen));

		getFSM().addActor(
				new Actor(InitState.instance(), 1, getEngine()
						.getGameBitmapFromPool(R.drawable.pause_bg)));

		addButton(btn);
	}

	@Override
	public void update() {
		getFSM().update(getEngine());
	}

	@Override
	public void draw(SpriteBatch batch) {
		oldFSM.draw(batch);
		getFSM().draw(batch);
	}

	@Override
	public void inputProcessed(InputManager inputManager) {
		if (inputManager.getState(btn).justPressed()) {
			// getEngine().playSound("btnClick");
			getGSM().pop();
		}
	}

	@Override
	public GameScreenTextures onLoadTextures() {
		GameScreenTextures t = new GameScreenTextures();
		t.addTexture(R.drawable.pause_bg);
		return t;
	}

}
