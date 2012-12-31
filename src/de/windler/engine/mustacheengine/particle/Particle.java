package de.windler.engine.mustacheengine.particle;

import android.graphics.Bitmap;
import de.windler.engine.mustacheengine.game.Bitmap.GameBitmap;
import de.windler.engine.mustacheengine.game.sprite.SpriteBatch;
import de.windler.engine.mustacheengine.util.Vector2D;

/**
 * a paricle for the engine
 * 
 * @author windler
 * 
 */
public class Particle {

	private GameBitmap bmp;
	private float scale;
	private float maxRotation;
	private float currentRotationSpeed;
	private float rotation;
	private Vector2D velocity;
	private int timeToLive;

	public Particle() {
		this.rotation = 0;
		this.currentRotationSpeed = 0;
		this.velocity = Vector2D.createNullVector();
	}

	protected void recycle() {
		this.rotation = 0;
		this.currentRotationSpeed = 0;
	}

	public void update(long elapsedTime, Vector2D gravitation,
			float additionalRotation) {
		velocity.add(gravitation.getX(), gravitation.getY());
		bmp.getPosition().add(velocity.getX(), velocity.getY());
		bmp.setPosition(bmp.getPosition().getX(), bmp.getPosition().getY());
		timeToLive -= elapsedTime;

		if (currentRotationSpeed < maxRotation) {
			currentRotationSpeed += additionalRotation;
			if (currentRotationSpeed > maxRotation) {
				currentRotationSpeed = maxRotation;
			}
		}
		rotation += currentRotationSpeed;
		if (rotation > 360) {
			rotation -= 360;
		}

		bmp.setRotation(rotation);
	}

	public void draw(SpriteBatch batch) {
		batch.draw(bmp);
	}

	public int getTimeToLive() {
		return timeToLive;
	}

	public Bitmap getBitmap() {
		return bmp.getBmp();
	}

	public float getScale() {
		return scale;
	}

	public void setBitmap(GameBitmap bmp) {
		this.bmp = bmp;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public void setMaxRotation(float maxRotation) {
		this.rotation = 0;
		this.currentRotationSpeed = 0;
		this.maxRotation = maxRotation;
	}

	public void setVelocity(float velocityX, float velocityY) {
		this.velocity.setX(velocityX);
		this.velocity.setY(velocityY);
	}

	public void setTimeToLive(int timeToLive) {
		this.timeToLive = timeToLive;
	}

	public GameBitmap getGameBitmap() {
		return bmp;
	}

}
