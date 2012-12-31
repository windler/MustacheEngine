package de.windler.game.Animations;

import de.windler.engine.mustacheengine.game.Animation.Animation;
import de.windler.engine.mustacheengine.game.Bitmap.GameBitmap;
import de.windler.engine.mustacheengine.game.sprite.SpriteBatch;

public class RotationAnimation extends Animation {

	private float degree;

	private float v = 1;
	private float currentDegree = 0.0f;

	private boolean lastWasDrawn = false;
	private boolean lastWasLonger = false;

	public RotationAnimation(int duration, float degrees) {
		super(duration);
		degree = degrees;
	}

	@Override
	public void draw(SpriteBatch batch, GameBitmap bmp) {
		batch.draw(bmp);
		lastWasDrawn = true;
	}

	@Override
	public void update() {
		float elapsed = (float) getElapsedTime();
		if (elapsed > getDuration() && !lastWasLonger) {
			elapsed = getDuration();
			lastWasLonger = true;
		}
		v = elapsed / (float) getDuration();
		currentDegree = (float) (degree * v);

		lastWasDrawn = currentDegree <= degree ? false : true;
	}

	@Override
	public boolean finished() {
		return currentDegree >= degree && lastWasDrawn;
	}
}
