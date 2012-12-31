package de.windler.game.LayerAnimations;

import de.windler.engine.mustacheengine.Layer.LayerAnimation;

public class ScaleAnimation extends LayerAnimation {

	private float startValue;
	private float endValue;

	private float currentValue;

	public ScaleAnimation(int duration, float start, float end) {
		super(duration);

		startValue = start;
		endValue = end;

		currentValue = startValue;
	}

	@Override
	public void update() {
		currentValue = (endValue - startValue) * (float) getElapsedTime()
				/ (float) getDuration() + startValue;
		setRotation(currentValue);
	}

	@Override
	public boolean finished() {
		return getElapsedTime() >= getDuration();
	}

}
