package de.windler.engine.mustacheengine.game.Animation;

import de.windler.engine.mustacheengine.game.Bitmap.GameBitmap;
import de.windler.engine.mustacheengine.game.sprite.SpriteBatch;

/**
 * An Animation with an infinite duration
 * 
 * @author Nico Windler
 * 
 */
public abstract class PermanentAnimation {

	private long elapsedTime;

	/**
	 * draws the current state
	 * 
	 * @param gl
	 * @param bmp
	 * @param position
	 */
	public abstract void draw(SpriteBatch batch, GameBitmap bmp);

	/**
	 * updates the animation
	 */
	public abstract void update();

	/**
	 * returns the elapsed time
	 * 
	 * @return
	 */
	protected long getElapsedTime() {
		return elapsedTime;
	}

	protected void setElapsedTime(long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

}
