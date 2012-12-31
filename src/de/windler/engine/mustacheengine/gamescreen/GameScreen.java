package de.windler.engine.mustacheengine.gamescreen;

import de.windler.engine.mustacheengine.FiniteStateMachine.Button;
import de.windler.engine.mustacheengine.FiniteStateMachine.FiniteStateMachine;
import de.windler.engine.mustacheengine.game.engine.IGameEngine;
import de.windler.engine.mustacheengine.game.sprite.SpriteBatch;
import de.windler.engine.mustacheengine.game.texture.GameScreenTextures;
import de.windler.engine.mustacheengine.input.InputManager;

/**
 * The current screen to be painted
 * 
 * @author Nico Windler
 * 
 */
public abstract class GameScreen {

	private FiniteStateMachine fsm;
	private GameScreenManager gsm;

	public GameScreen(GameScreenManager gsm) {
		fsm = new FiniteStateMachine();
		this.gsm = gsm;
	}

	/**
	 * calles as soon as the gamescreen is pushed and all textures are loaded.
	 * DO NOT USE TEXTURE IN CONSTRUCTOR!
	 */
	public abstract void onPrepareGameScreen();

	public abstract void update();

	public abstract void draw(SpriteBatch gameSpriteBatch);

	/**
	 * called as soon as an input is detected
	 * 
	 * @param inputManager
	 */
	public abstract void inputProcessed(InputManager inputManager);

	/**
	 * called after push. Load all textures here which are needed in this
	 * screen, They will be unloaded on pop of screen
	 * 
	 */
	public abstract GameScreenTextures onLoadTextures();

	/**
	 * FiniteStateMachine of GameScreen
	 * 
	 * @return
	 */
	public FiniteStateMachine getFSM() {
		return fsm;
	}

	/**
	 * GameScreenManager of GS
	 * 
	 * @return
	 */
	public GameScreenManager getGSM() {
		return gsm;
	}

	public IGameEngine getEngine() {
		return gsm.getEngine();
	}

	public void addButton(Button btn) {
		fsm.addButton(btn);
		gsm.registerTouchEvent(btn.getKey(), btn.getStartPoint(),
				btn.getEndPoint());
	}

	/**
	 * NOT YET IMPLEMENTED
	 * 
	 * @deprecated
	 */
	public void removeButton() {
		// TODO IMPLEMENT
	}

}
