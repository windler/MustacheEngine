package de.windler.engine.mustacheengine.Layer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import android.graphics.Point;
import de.windler.engine.mustacheengine.FiniteStateMachine.Actor;
import de.windler.engine.mustacheengine.FiniteStateMachine.Button;
import de.windler.engine.mustacheengine.game.Color.Color;
import de.windler.engine.mustacheengine.game.engine.IGameEngine;
import de.windler.engine.mustacheengine.game.sprite.SpriteBatch;

/**
 * A Layer is an "area" on top of a gamescreen. Layers can be layed over other
 * layers
 * 
 * @author Nico Windler
 * 
 */
public class Layer implements Comparable<Layer> {
	private List<Actor> actors;
	private List<Button> btn;

	private Queue<LayerAnimation> animations;
	private Queue<LayerString> textToDraw;

	private int layerNr;
	private float rotation;
	private float scale;
	private Color color;

	public Layer(int nr) {
		actors = new ArrayList<Actor>();
		btn = new ArrayList<Button>();
		textToDraw = new LinkedList<LayerString>();
		animations = new LinkedList<LayerAnimation>();

		layerNr = nr;
		rotation = 0;
		scale = 1;
		color = Color.normal();
	}

	public void addActor(Actor a) {
		actors.add(a);
	}

	public void addAnimation(LayerAnimation a) {
		animations.add(a);
	}

	public void removeActor(Actor a) {
		actors.remove(a);
	}

	public void addButton(Button bt) {
		btn.add(bt);
	}

	public void removeButton(Button bt) {
		btn.remove(bt);
	}

	public void addText(String text, String font, Point pos, float size,
			int rotation, Color cl) {
		LayerString ls = new LayerString(text, font, pos, size, rotation, cl);
		textToDraw.add(ls);
	}

	public void draw(SpriteBatch batch) {
		batch.begin(rotation, scale, color);
		// engine.changeSurface(rotation, scale, color);
		for (Actor a : actors) {
			a.draw(batch);
		}
		for (Button b : btn) {
			b.draw(batch);
		}

		LayerString ls = null;
		while ((ls = textToDraw.poll()) != null) {
			batch.drawText(ls.getText(), ls.getFontText(), ls.getPosition().x,
					ls.getPosition().y, ls.getFontSize());
		}
		batch.end();
	}

	public void update(IGameEngine engine) {
		if (!animations.isEmpty()) {
			LayerAnimation animation = animations.element();

			animation.setElapsedTime(animation.getElapsedTime()
					+ engine.getGameTime().getElapsedTime());

			animation.update();

			rotation = animation.getRotation();
			scale = animation.getScale();
			color = animation.getColor();

			if (animation.finished()) {
				animations.remove();
			}
		}
	}

	public int getLayerNr() {
		return layerNr;
	}

	public int compareTo(Layer another) {
		int result = 0;
		if (getLayerNr() < another.getLayerNr()) {
			result = -1;
		} else if (getLayerNr() > another.getLayerNr()) {
			result = 1;
		}
		return result;
	}
}
