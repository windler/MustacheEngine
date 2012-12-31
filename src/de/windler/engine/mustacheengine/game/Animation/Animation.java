package de.windler.engine.mustacheengine.game.Animation;

import de.windler.engine.mustacheengine.game.Bitmap.GameBitmap;
import de.windler.engine.mustacheengine.game.sprite.SpriteBatch;

/**
 * Abstract class for an animation
 * 
 * @author Nico Windler
 * 
 */
public abstract class Animation {

	private long elapsedTime = 0;
	private long duration = 0;

	public Animation(int duration) {
		this.duration = duration;
	}

	/**
	 * returns the duration of the animation
	 * 
	 * @return
	 */
	protected long getDuration() {
		return duration;
	}

	protected void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * draws the current state of the animation
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
	 * determs whether the animation is over
	 * 
	 * @return
	 */
	public abstract boolean finished();

	/**
	 * returns the elapsed time the animation was running
	 * 
	 * @return
	 */
	protected long getElapsedTime() {
		return elapsedTime;
	}

	/**
	 * resets the elapsedtime (ms)
	 * 
	 * @param ms
	 */
	protected void setElapsedTime(long ms) {
		elapsedTime = ms;
	}

}
