package de.windler.engine.mustacheengine.Layer;

import android.graphics.Point;
import de.windler.engine.mustacheengine.game.Color.Color;

/**
 * Container for all string to be painted in a layer
 * 
 * @author Nico Windler
 * 
 */
public class LayerString {

	private String text;
	private String fontText;
	private Point position;
	private float fontSize;
	private int textRotation;
	private Color color;

	public LayerString(String txt, String font, Point pos, float size,
			int rotation, Color cl) {
		setText(txt);
		setFontText(font);
		setPosition(pos);
		setFontSize(size);
		setTextRotation(rotation);
		setColor(cl);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getFontText() {
		return fontText;
	}

	public void setFontText(String fontText) {
		this.fontText = fontText;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public float getFontSize() {
		return fontSize;
	}

	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
	}

	public int getTextRotation() {
		return textRotation;
	}

	public void setTextRotation(int textRotation) {
		this.textRotation = textRotation;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
