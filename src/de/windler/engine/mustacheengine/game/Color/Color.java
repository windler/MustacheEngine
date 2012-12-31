package de.windler.engine.mustacheengine.game.Color;

/**
 * represents a color
 * 
 * @author Nico Windler
 * 
 */
public class Color {

	private float r;
	private float g;
	private float b;
	private float alpha;

	public Color(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
		alpha = 1f;
	}

	public float getR() {
		return r;
	}

	public float getG() {
		return g;
	}

	public float getB() {
		return b;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float a) {
		alpha = a;
	}

	public void setR(float r) {
		this.r = r;
	}

	public void setG(float g) {
		this.g = g;
	}

	public void setB(float b) {
		this.b = b;
	}

	public static Color red() {
		Color red = new Color(1, 0, 0);
		return red;
	}

	public static Color green() {
		Color green = new Color(0, 1, 0);
		return green;
	}

	public static Color blue() {
		Color blue = new Color(0, 0, 1);
		return blue;
	}

	public static Color normal() {
		Color normal = new Color(1, 1, 1);
		return normal;
	}

	public Color copy() {
		Color c = new Color(getR(), getG(), getB());
		c.setAlpha(getAlpha());
		return c;
	}

}
