package de.windler.engine.mustacheengine.game.Bitmap;

import android.graphics.Bitmap;
import de.windler.engine.mustacheengine.game.Color.Color;
import de.windler.engine.mustacheengine.game.texture.Texture;
import de.windler.engine.mustacheengine.util.Vector2D;

/**
 * Container for gamebitmaps
 * 
 * @author Nico Windler
 * 
 */
public class GameBitmap {

	private Texture texture;

	private Vector2D position = Vector2D.createNullVector();
	private float rotation = 0f;
	private float scale = 1f;
	private Color color = Color.normal();

	public void init(Texture txt) {
		texture = txt;
		position = Vector2D.createNullVector();
	}

	public void recycle() {
		rotation = 0f;
		scale = 1f;
		color.setAlpha(1);
		color.setB(0);
		color.setG(0);
		color.setR(0);
		position.setX(0);
		position.setY(0);
	}

	/**
	 * Width of the visible part of the bitmap
	 * 
	 * @return
	 */
	public int getWidth() {
		return texture.getTextureWidth();
	}

	/**
	 * height of the visible part of the bitmap
	 * 
	 * @return
	 */
	public int getHeight() {
		return texture.getTextureHeight();
	}

	/**
	 * returns the bitmap in power of two (POT)
	 * 
	 * @return
	 */
	public Bitmap getBmp() {
		return texture.getPotBmp();
	}

	public Vector2D getPosition() {
		return position;
	}

	public void setPosition(float x, float y) {
		this.position.setX(x);
		this.position.setY(y);
	}

	public void addToPosition(float x, float y) {
		position.add(x, y);
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public float getRotation() {
		return rotation;
	}

	public float getScale() {
		return scale;
	}

	public Color getColor() {
		return color;
	}

	public Texture getTexture() {
		return texture;
	}
}
