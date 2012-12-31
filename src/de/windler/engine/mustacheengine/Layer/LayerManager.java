package de.windler.engine.mustacheengine.Layer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.graphics.Point;
import de.windler.engine.mustacheengine.FiniteStateMachine.Actor;
import de.windler.engine.mustacheengine.FiniteStateMachine.Button;
import de.windler.engine.mustacheengine.game.Color.Color;
import de.windler.engine.mustacheengine.game.engine.IGameEngine;
import de.windler.engine.mustacheengine.game.sprite.SpriteBatch;

/**
 * Manages the painting of all layers
 * 
 * @author Nico Windler
 * 
 */
public class LayerManager {

	private List<Layer> layer;

	public LayerManager() {
		layer = new ArrayList<Layer>();
	}

	private Layer getLayer(int nr, boolean createIfNull) {
		Layer result = null;

		for (Layer l : this.layer) {
			if (l.getLayerNr() == nr) {
				result = l;
				break;
			}
		}
		if (createIfNull && result == null) {
			result = new Layer(nr);
			this.layer.add(result);

			Collections.sort(this.layer);
		}

		return result;
	}

	public void registerActor(Actor a) {
		Layer theLayer = getLayer(a.getLayerNr(), true);
		theLayer.addActor(a);
	}

	public void registerButton(Button bt) {
		Layer theLayer = getLayer(bt.getLayerNr(), true);
		theLayer.addButton(bt);
	}

	public void removeButton(Button bt) {
		Layer theLayer = getLayer(bt.getLayerNr(), false);
		theLayer.removeButton(bt);
	}

	public void draw(SpriteBatch batch) {
		List<Layer> topLayer = new ArrayList<Layer>();

		for (Layer l : layer) {
			if (l.getLayerNr() != -1) {
				l.draw(batch);
			} else {
				topLayer.add(l);
			}
		}

		for (Layer l : topLayer) {
			l.draw(batch);
		}
	}

	public void update(IGameEngine engine) {
		for (Layer l : layer) {
			l.update(engine);
		}
	}

	/**
	 * Entdernt den Actor aus der Ebene
	 * 
	 * @param actor
	 */
	public void removeActor(Actor actor) {
		Layer theLayer = getLayer(actor.getLayerNr(), false);
		theLayer.removeActor(actor);
	}

	public void addAnimation(LayerAnimation ani, int layerNr) {
		Layer theLayer = getLayer(layerNr, true);
		theLayer.addAnimation(ani);
	}

	public void addText(String text, String font, Point pos, float size,
			int rotation, Color cl, int layerNr) {
		Layer theLayer = getLayer(layerNr, true);
		theLayer.addText(text, font, pos, size, rotation, cl);
	}
}
