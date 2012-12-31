package de.windler.engine.mustacheengine.FiniteStateMachine;

import android.graphics.Point;
import de.windler.engine.mustacheengine.game.Animation.AnimationObject;
import de.windler.engine.mustacheengine.game.Bitmap.GameBitmap;
import de.windler.engine.mustacheengine.game.engine.IGameEngine;
import de.windler.engine.mustacheengine.game.sprite.SpriteBatch;

/**
 * an actor is someone who does something in the game
 * 
 * @author Nico Windler
 * 
 */
public class Actor {

	private State curState = null;
	private AnimationObject ani = null;
	private int layer;

	/**
	 * Actor with Initialstate
	 * 
	 */
	public Actor(State initState, int layer, GameBitmap... bmps) {
		this.layer = layer;
		ani = new AnimationObject(bmps);
		curState = initState;
		curState.onEnter();
	}

	/**
	 * Actor with Initialstate and layerNR = 1!
	 * 
	 * @param initState
	 * @param bmps
	 */
	public Actor(State initState, GameBitmap... bmps) {
		this(initState, 1, bmps);
	}

	public int getLayerNr() {
		return layer;
	}

	public void changeState(State nextState) {
		curState.onExit();
		curState = nextState;
		curState.onEnter();
	}

	public Point getStartPoint() {
		Point start = null;
		if (ani != null) {
			start = new Point();
			start.x = ani.getStartPoint().x;
			start.y = ani.getStartPoint().y;
		}
		return start;
	}

	public int getWidth() {
		return ani.getWidth();
	}

	public int getHeight() {
		return ani.getHeight();
	}

	public void draw(SpriteBatch batch) {
		ani.draw(batch);
	}

	public void update(FiniteStateMachine fsm, IGameEngine engine) {
		curState.update(this, fsm);
	}

}
