package de.windler.engine.mustacheengine.FiniteStateMachine;

import android.graphics.Point;
import de.windler.engine.mustacheengine.game.Animation.AnimationObject;
import de.windler.engine.mustacheengine.game.Bitmap.GameBitmap;
import de.windler.engine.mustacheengine.game.font.Font;
import de.windler.engine.mustacheengine.game.sprite.SpriteBatch;

/**
 * 
 * @author Nico Windler
 * 
 */
public class Button {

	private String text;
	private AnimationObject aniObj;
	private String key;
	private Font font;
	private int fontSize;
	private Point fontPos;
	private int layerNr;

	/**
	 * Button from InputManager
	 * 
	 */
	public Button(String label, Font font, int size, int layerNr, GameBitmap bmp) {
		text = label;
		this.font = font;
		fontSize = size;
		aniObj = new AnimationObject(bmp);
		this.layerNr = layerNr;

		key = "btn:" + aniObj.hashCode();

		calculateFontPosition();
	}

	private void calculateFontPosition() {
		int width = 0;

		if (font != null) {
			for (char c : text.toCharArray()) {
				width += font.getCharacter(c).getCharWidth();
			}

			width *= fontSize;

			int midX = aniObj.getStartPoint().x + (aniObj.getWidth() / 2);
			int midY = aniObj.getStartPoint().y + (aniObj.getHeight() / 2);

			fontPos = new Point(midX - (width / 2), midY - (fontSize / 2));
		} else {
			fontPos = new Point(0, 0);
		}
	}

	public Point getStartPoint() {
		return aniObj.getStartPoint();
	}

	public Point getEndPoint() {
		return new Point(getStartPoint().x + aniObj.getWidth(),
				getStartPoint().y + aniObj.getHeight());
	}

	public String getKey() {
		return key;
	}

	public int getLayerNr() {
		return layerNr;
	}

	public void draw(SpriteBatch batch) {
		aniObj.draw(batch);
		batch.drawText(text, font.getName(), fontPos.x, fontPos.y, fontSize);
	}

	public void update() {
		if (fontPos.equals(0, 0)) {
			calculateFontPosition();
		}
	}

}
