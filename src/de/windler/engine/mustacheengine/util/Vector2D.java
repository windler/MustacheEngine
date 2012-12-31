package de.windler.engine.mustacheengine.util;

import android.graphics.Point;

/**
 * Vector for GameEngine. Operations: + - * /
 * 
 * @author Nico Windler
 * 
 */
public class Vector2D {

	private float x;
	private float y;
	private Point point;

	/**
	 * creates null-vector
	 */
	public Vector2D() {
		this(0, 0);
	}

	public Vector2D(float x, float y) {
		this.x = x;
		this.y = y;
		point = new Point(0, 0);
	}

	public static Vector2D createNullVector() {
		return new Vector2D();
	}

	/**
	 * OPERATOREM
	 */

	public void add(float otherX, float otherY) {
		x += otherX;
		y += otherY;
	}

	public void sub(float otherX, float otherY) {
		x -= otherX;
		y -= otherY;
	}

	public void mul(float otherX, float otherY) {
		x *= otherX;
		y *= otherY;
	}

	public void div(float otherX, float otherY) {
		x /= otherX;
		y /= otherY;
	}

	/**
	 * PROPERTIES
	 */

	public float getVectorLength() {
		return (float) Math.sqrt((x * x) + (y * y));
	}

	public Point getPoint() {
		point.set((int) x, (int) y);
		return point;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

}
