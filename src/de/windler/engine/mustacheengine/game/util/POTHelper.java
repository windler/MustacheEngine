package de.windler.engine.mustacheengine.game.util;

import android.graphics.Bitmap;

/**
 * Creates a power of two bitmap
 * 
 * @author Nico Windler
 * 
 */
public class POTHelper {

	public static Bitmap createPOT(Bitmap bmp) {
		Bitmap potBmp = null;
		int potWidth = (int) Math.ceil(Math.log(bmp.getWidth()) / Math.log(2));
		int potHeight = (int) Math
				.ceil(Math.log(bmp.getHeight()) / Math.log(2));
		potHeight = (int) Math.pow(2, potHeight);
		potWidth = (int) Math.pow(2, potWidth);

		if (potWidth == 1) {
			potWidth = 2;
		}
		if (potHeight == 1) {
			potHeight = 2;
		}

		if (potHeight != bmp.getHeight() || potWidth != bmp.getWidth()) {
			int[] colors = new int[potWidth * potHeight];
			int index = 0;

			int offset = potHeight - bmp.getHeight();
			for (int i = 0; i < potHeight; i++) {
				for (int j = 0; j < potWidth; j++) {
					if (i > offset - 1) {
						if (j < bmp.getWidth() && i < bmp.getHeight()) {
							colors[index] = bmp.getPixel(j, i);
						} else {
							colors[index] = 0;
						}
					}
					index++;
				}
			}

			potBmp = Bitmap.createBitmap(colors, potWidth, potHeight,
					bmp.getConfig());
		} else {
			potBmp = bmp;
		}

		System.gc();

		return potBmp;
	}
}
