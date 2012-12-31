package de.windler.engine.mustacheengine.game.font;

import android.graphics.Bitmap;

/**
 * Character of a font
 * 
 * @author Nico Windler
 * 
 */
public class FontCharacter {

	private Bitmap bitmap;
	private int charHeight;
	private int charWidth;
	private int charX;
	private int y;
	private int x;
	private int bmpNr;

	public FontCharacter(Bitmap bmp, int width, int height, int x, int y,
			int charX, int bmpNr) {
		bitmap = bmp;
		charWidth = width;
		charHeight = height;
		this.x = x;
		this.y = y;
		this.charX = charX;
		this.bmpNr = bmpNr;
	}

	public int getCharWidth() {
		return charWidth;
	}

	public int getCharHeight() {
		return charHeight;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void changeBitmap(Bitmap bmp) {
		bitmap = bmp;
	}

	public int getCharX() {
		return charX;
	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}

	public int getBmpNr() {
		return bmpNr;
	}

}
