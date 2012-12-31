package de.windler.engine.mustacheengine.FiniteStateMachine;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Point;
import android.util.Log;
import de.windler.engine.mustacheengine.Layer.LayerAnimation;
import de.windler.engine.mustacheengine.Layer.LayerManager;
import de.windler.engine.mustacheengine.game.Color.Color;
import de.windler.engine.mustacheengine.game.engine.IGameEngine;
import de.windler.engine.mustacheengine.game.sprite.SpriteBatch;

/**
 * 
 * @author windler
 * 
 */
public class FiniteStateMachine {

	private List<Actor> actList;
	private List<Button> btList;
	private LayerManager lm;

	public FiniteStateMachine() {
		actList = new ArrayList<Actor>();
		btList = new ArrayList<Button>();
		lm = new LayerManager();
	}

	public void addActor(Actor act) {
		actList.add(act);
		lm.registerActor(act);
	}

	public void addButton(Button btn) {
		btList.add(btn);
		lm.registerButton(btn);
	}

	public void changeState(Actor act, State nextState) {
		act.changeState(nextState);
	}

	public void recycleActor(Actor act) {
		actList.remove(act);
		Log.d("FSM", "Actor " + act + " gelöscht!");
		act = null;
	}

	public void update(IGameEngine engine) {
		lm.update(engine);
		for (Actor a : actList) {
			a.update(this, engine);
		}

		for (Button b : btList) {
			b.update();
		}
	}

	public void addLayerAnimation(LayerAnimation ani, int layerNr) {
		lm.addAnimation(ani, layerNr);
	}

	public void addTextToLayer(String text, String font, Point pos, float size,
			int rotation, Color cl, int layerNr) {
		lm.addText(text, font, pos, size, rotation, cl, layerNr);
	}

	public void draw(SpriteBatch batch) {
		lm.draw(batch);
	}

}
