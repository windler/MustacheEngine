package de.windler.engine.mustacheengine.game.Animation;

import java.util.LinkedList;
import java.util.Queue;

import android.graphics.Point;
import de.windler.engine.mustacheengine.game.Bitmap.GameBitmap;
import de.windler.engine.mustacheengine.game.engine.IGameEngine;
import de.windler.engine.mustacheengine.game.sprite.SpriteBatch;

/**
 * Contains serveral Animation-objects und is itself a container of
 * (game-)bitmaps. When there are no Animation-objects the container will be
 * painted itself.
 * 
 * @author Nico Windler
 * 
 */
public class AnimationObject {

	private Queue<Animation> animations;
	private PermanentAnimation permAnimation;
	private GameBitmap[] bmp;
	private Point minStart = null;
	private int maxWidth = 0;
	private int maxHeight = 0;

	public AnimationObject(final GameBitmap... bmps) {
		animations = new LinkedList<Animation>();
		bmp = bmps;
		if (bmp.length > 0) {
			Point max = getMax();
			minStart = getMin();
			maxWidth = max.x - minStart.x;
			maxHeight = max.y - minStart.y;
		}
	}

	/**
	 * the startingpoint of the animation
	 * 
	 * @return
	 */
	public Point getStartPoint() {
		return this.minStart;
	}

	/**
	 * width of the animation-object
	 * 
	 * @return
	 */
	public int getWidth() {
		return this.maxWidth;
	}

	/**
	 * height of the animation object
	 * 
	 * @return
	 */
	public int getHeight() {
		return this.maxHeight;
	}

	/**
	 * updates all animations in queue and removes it if neccessary
	 * 
	 * @param gameTime
	 * @param context
	 */
	public void update(IGameEngine engine) {

		if (permAnimation != null) {
			permAnimation.setElapsedTime(permAnimation.getElapsedTime()
					+ engine.getGameTime().getElapsedTime());
			permAnimation.update();

		}

		if (!animations.isEmpty()) {
			Animation animation = animations.element();

			animation.setElapsedTime(animation.getElapsedTime()
					+ engine.getGameTime().getElapsedTime());

			animation.update();
			if (animation.finished()) {
				animations.remove();
			}
		}
	}

	private Point getMin() {
		Point min = new Point();
		int bufX = (int) bmp[0].getPosition().getX();
		int bufY = (int) bmp[0].getPosition().getY();
		min.x = bufX;
		min.y = bufY;
		if (bmp.length > 1) {
			int tmpX;
			int tmpY;
			for (GameBitmap g : bmp) {
				tmpX = (int) g.getPosition().getX();
				tmpY = (int) g.getPosition().getY();
				if (tmpX < bufX) {
					min.x = tmpX;
					bufX = tmpX;
				}
				if (tmpY < bufY) {
					min.y = tmpY;
					bufY = tmpY;
				}
			}
		}
		return min;
	}

	private Point getMax() {
		Point max = new Point();
		int bufX = (int) (bmp[0].getWidth() + bmp[0].getPosition().getX());
		int bufY = (int) (bmp[0].getHeight() + bmp[0].getPosition().getY());
		max.x = bufX;
		max.y = bufY;
		if (bmp.length > 1) {
			int tmpX;
			int tmpY;
			for (GameBitmap g : bmp) {
				tmpX = (int) (g.getWidth() + g.getPosition().getX());
				tmpY = (int) (g.getHeight() + g.getPosition().getY());
				if (tmpX > bufX) {
					max.x = tmpX;
					bufX = tmpX;
				}
				if (tmpY > bufY) {
					max.y = tmpY;
					bufY = tmpY;
				}
			}
		}
		return max;
	}

	/**
	 * moves the object and therefore every gamebitmap
	 * 
	 * @param add
	 */
	public void move(float x, float y) {
		for (GameBitmap g : bmp) {
			g.addToPosition(x, y);
		}
	}

	/**
	 * finish-state
	 * 
	 * @return
	 */
	public boolean finished() {
		return animations.isEmpty();
	}

	/**
	 * paints the current animation-state.
	 * 
	 * @param gl
	 * @param rotation
	 */
	public void draw(SpriteBatch batch) {
		if (!animations.isEmpty()) {
			for (GameBitmap g : bmp) {
				animations.element().draw(batch, g);
			}
		} else if (permAnimation != null) {
			for (GameBitmap g : bmp) {
				permAnimation.draw(batch, g);
			}
		} else {
			for (GameBitmap g : bmp) {
				batch.draw(g);
			}
		}

	}

	/**
	 * queues an animation
	 * 
	 * @param ani
	 */
	public void addAnimation(Animation ani) {
		animations.add(ani);
	}

	/**
	 * setzt the permanent animation which will be called every time there is no
	 * "normal" animation
	 * 
	 * @param name
	 * @param ani
	 */
	public void setPermanentAnimation(PermanentAnimation ani) {
		permAnimation = ani;
	}

}
