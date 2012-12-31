package de.windler.engine.mustacheengine.game.font;

import java.util.Hashtable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import de.windler.engine.mustacheengine.game.util.POTHelper;

/**
 * Creates a new font from a graphic. When constructed only the space exists!
 * 
 * @author Nico Windler
 * @see Font.createCapitalLetters
 * @see Font.createSmallLetters
 * @see Font.createNumbers
 * @see Font.recycle
 */
public class Font {

	private int[] rowCount;
	private int[] columnCount;
	private int[] glyphWidth;
	private int[] glyphHeight;
	private Bitmap[] glyph;
	private Hashtable<Character, FontCharacter> letters;
	private boolean recycled = false;
	private String name;
	private int id = 0;

	public Font(String name, Context context, int[] rows, int[] columns,
			int[] id) {
		rowCount = rows;
		columnCount = columns;
		this.name = name;

		BitmapFactory.Options bfo = new BitmapFactory.Options();
		bfo.inScaled = false;

		glyph = new Bitmap[id.length];
		glyphWidth = new int[id.length];
		glyphHeight = new int[id.length];

		for (int i = 0; i < id.length; i++) {
			glyph[i] = BitmapFactory.decodeResource(context.getResources(),
					id[i], bfo);
			glyphHeight[i] = glyph[i].getHeight() / rows[i];
			glyphWidth[i] = glyph[i].getWidth() / columns[i];
		}

		letters = new Hashtable<Character, FontCharacter>();
		Bitmap bitmap = Bitmap.createBitmap(glyph[0], 0, 0, 1, 1);
		bitmap = POTHelper.createPOT(bitmap);
		FontCharacter fc = new FontCharacter(bitmap, 1, 1, 1, 1, 1, 0);
		letters.put(' ', fc);
	}

	public String getName() {
		return name;
	}

	/**
	 * creates capitals
	 * 
	 * @param zeile
	 * @param spalte
	 * @param ascii
	 *            Position (row,column) where the first capital letter is. All
	 *            other capitals have to follow!
	 * @param bmpNr
	 *            Number of the bitmap here the capitals are in
	 */
	public void createCapitalLetters(int zeile, int spalte, int bmpNr) {
		createLetters(zeile, spalte, 65, 26, bmpNr);
	}

	/**
	 * creates small letters
	 * 
	 * @param zeile
	 * @param spalte
	 * @param ascii
	 *            Position (row,column) where the first small letter is. All
	 *            other small letters have to follow!
	 * @param bmpNr
	 *            Number of the bitmap here the capitals are in
	 */
	public void createSmallLetters(int zeile, int spalte, int bmpNr) {
		createLetters(zeile, spalte, 97, 26, bmpNr);
	}

	/**
	 * recycles all glyph-bmps. Should be called when all charecters are loaded
	 */

	public void recycle() {
		for (Bitmap g : glyph) {
			g.recycle();
		}
	}

	private void createLetters(int zeile, int spalte, int ascii,
			int numberOfChars, int bmpNr) {
		if (!recycled) {
			int rowPos = zeile;
			int columnPos = spalte;
			int letterASCII = ascii;
			Bitmap letter = null;

			for (int i = 0; i < numberOfChars; i++) {
				if (columnPos > columnCount[bmpNr]) {
					columnPos = 1;
					rowPos++;
				}
				if (rowPos > rowCount[bmpNr]) {
					rowPos = 1;
					Log.e("Font: Line not defined",
							"Mehr Zeilen werden benötigt!");
				}

				int x = (columnPos - 1) * glyphWidth[bmpNr];
				int y = (rowPos - 1) * glyphHeight[bmpNr];

				int[] charRange = calculateCharacterRange(x, y, bmpNr);

				int width = charRange[1] - charRange[0];
				int height = glyphHeight[bmpNr];

				letter = Bitmap.createBitmap(glyph[bmpNr], charRange[0], y,
						width, height);
				letter = POTHelper.createPOT(letter);

				FontCharacter fc = new FontCharacter(letter, width, height, x,
						y, charRange[0], bmpNr);

				letters.put((char) letterASCII, fc);
				letterASCII++;

				columnPos++;
			}
		}
	}

	private int[] calculateCharacterRange(int x, int y, int bmpNr) {
		int[] result = new int[2];

		result[0] = x;
		result[1] = x + glyphWidth[bmpNr];

		Bitmap bmp = glyph[bmpNr].extractAlpha();

		outerLoop: for (int i = x; i < x + glyphWidth[bmpNr]; i++) {
			for (int j = y; j < y + glyphHeight[bmpNr]; j++) {
				if (bmp.getPixel(i, j) != Color.TRANSPARENT) {
					result[0] = i - 2;
					break outerLoop;
				}
			}
		}

		outerLoop: for (int i = x + glyphWidth[bmpNr]; i > x; i--) {
			for (int j = y; j < y + glyphHeight[bmpNr]; j++) {
				if (bmp.getPixel(i, j) != Color.TRANSPARENT) {
					result[1] = i + 1;
					break outerLoop;
				}
			}
		}

		return result;
	}

	/**
	 * Position (row,column) where the first number-letter is. All other numbers
	 * have to follow!
	 * 
	 * @param zeile
	 * @param spalte
	 * @param bmpNr
	 * 
	 */
	public void createNumbers(int zeile, int spalte, int bmpNr) {
		createLetters(zeile, spalte, 48, 10, bmpNr);
	}

	/**
	 * Erstellt einen Sonderzeichen
	 * 
	 * @param nr
	 *            Number in the image
	 * @param c
	 *            character-identifier
	 * @param bmpNr
	 */
	public void createAdditionalGlyph(int zeile, int spalte, char c, int bmpNr) {
		createLetters(zeile, spalte, c, 1, bmpNr);
	}

	public FontCharacter getCharacter(char c) {
		FontCharacter result = letters.get(' ');
		result = letters.get(c);

		if (result == null) {
			Log.e("Font-Character not found", "Glyphe '" + c
					+ "' konnte in dem Font nicht gefunden werden.");
		}

		return result;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
