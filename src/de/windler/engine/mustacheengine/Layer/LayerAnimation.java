package de.windler.engine.mustacheengine.Layer;

import de.windler.engine.mustacheengine.game.Color.Color;

/**
 * Animation for Layers
 * 
 * @author Nico Windler
 * 
 */
public abstract class LayerAnimation {

	private long elapsedTime = 0;
	private long duration = 0;
	private float rotation = 0;
	private float scale = 1;
	private Color color = Color.normal();

	public LayerAnimation(int duration) {
		this.duration = duration;
	}

	protected long getDuration() {
		return duration;
	}

	protected void setDuration(int duration) {
		this.duration = duration;
	}

	public abstract void update();

	public abstract boolean finished();

	protected long getElapsedTime() {
		return elapsedTime;
	}

	protected void setElapsedTime(long ms) {
		elapsedTime = ms;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
}
