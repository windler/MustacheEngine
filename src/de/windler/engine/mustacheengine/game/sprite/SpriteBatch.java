package de.windler.engine.mustacheengine.game.sprite;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.opengl.GLUtils;
import de.windler.engine.mustacheengine.game.Bitmap.GameBitmap;
import de.windler.engine.mustacheengine.game.Color.Color;
import de.windler.engine.mustacheengine.game.engine.GameEngine;
import de.windler.engine.mustacheengine.game.font.Font;
import de.windler.engine.mustacheengine.game.font.FontCharacter;
import de.windler.engine.mustacheengine.game.texture.Texture;

/**
 * The SpriteBatch organizes all Sprites to draw in a batch. Sprites are pooled
 * by the use of begin() and end(). begin() and end() are inevitable.
 * 
 * @author Nico Windler
 * 
 */
public class SpriteBatch {

	private final int MAX_SIZE = 2 * 1024;

	private int screenWidth;
	private int screenHeight;

	private Texture lastTexture;
	private boolean begin = false;
	private GL11 gl;

	private int index;
	private float vertices[];
	private ByteBuffer vertexByteBuffer;

	private Point centerlocation;

	private int[] textures = new int[1];
	private FloatBuffer[] textureBuffer;
	private int[] textureName;

	private FloatBuffer[] glyphTextureBuffer;
	private int[] glyphTextureName;

	private float texture[] = {
			// Mapping coordinates for the vertices
			0.0f, 1.0f, // top left (V2)
			0.0f, 0.0f, // bottom left (V1)
			1.0f, 1.0f, // top right (V4)
			1.0f, 0.0f // bottom right (V3)
	};

	private FloatBuffer vertexBuffer;
	private FloatBuffer tmpTxtBuffer;

	private Color tmpColor;

	private int glPush = 0;

	private GameEngine engine;

	public SpriteBatch(GL10 gl, GameEngine engine) {
		this.gl = (GL11) gl;

		this.engine = engine;

		this.screenHeight = engine.getDisplayHeight();
		this.screenWidth = engine.getDisplayWidth();

		vertices = new float[12];

		vertexByteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
		vertexByteBuffer.order(ByteOrder.nativeOrder());
		vertexBuffer = vertexByteBuffer.asFloatBuffer();

		vertexByteBuffer = ByteBuffer.allocateDirect(texture.length * 4);
		vertexByteBuffer.order(ByteOrder.nativeOrder());
		tmpTxtBuffer = vertexByteBuffer.asFloatBuffer();

		textureBuffer = new FloatBuffer[MAX_SIZE];
		textureName = new int[MAX_SIZE];

		glyphTextureBuffer = new FloatBuffer[MAX_SIZE];
		glyphTextureName = new int[MAX_SIZE];

		centerlocation = new Point();
	}

	/**
	 * prepares the batch. Has to be calles pre draw() / drawText()
	 */
	public void begin() {
		begin(0, 1, null);
	}

	/**
	 * prepares the batch. Has to be calles pre draw() / drawText(). Surface
	 * will be affected!
	 * 
	 * @param rotation
	 * @param scale
	 * @param color
	 */
	public void begin(float rotation, float scale, Color color) {

		begin = !begin;
		if (begin == false) {
			throw new IllegalArgumentException(
					"begin() already called! Call end() before calling begin() again!");
		}

		gl.glPushMatrix();

		gl.glTranslatef((float) screenWidth / 2, (float) screenHeight / 2, 1);
		gl.glRotatef(rotation, 0, 0, 1);
		gl.glScalef(scale, scale, 0);
		if (color != null) {
			gl.glColor4f(color.getR(), color.getG(), color.getB(),
					color.getAlpha());
		}
		gl.glTranslatef(-((float) screenWidth / 2),
				-((float) screenHeight / 2), 1);

		gl.glAlphaFunc(GL10.GL_ALWAYS, 0.5f);

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

	}

	/**
	 * Has to be calles ante draw() / drawText()
	 */
	public void end() {
		begin = !begin;
		if (begin) {
			throw new IllegalArgumentException(
					"call begin() before calling end()!");
		}

		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

		gl.glPopMatrix();

		if (glPush > 0) {
			throw new IllegalArgumentException(
					"glPush() wurde im Batch aufgerufen, aber glPop() nicht. Vor end unbedingt glPop() aufrufen!");
		}
	}

	/**
	 * draws a gamebitmap
	 * 
	 * @param bmp
	 */
	public void draw(GameBitmap bmp) {
		if (begin == false) {
			throw new IllegalArgumentException(
					"call begin() first! draw() can only be called after begin(). Remember to call end() after all drawings are done!");
		}

		index = 0;
		if (lastTexture != bmp.getTexture()) {
			lastTexture = bmp.getTexture();

			tmpTxtBuffer = textureBuffer[bmp.getTexture().getId()];
			if (tmpTxtBuffer == null) {

				gl.glGenTextures(1, textures, 0);

				textureName[lastTexture.getId()] = textures[0];

				gl.glBindTexture(GL10.GL_TEXTURE_2D, textureName[bmp
						.getTexture().getId()]);

				FloatBuffer buffer = vertexByteBuffer.asFloatBuffer();
				buffer.put(texture);
				buffer.position(0);

				textureBuffer[lastTexture.getId()] = buffer;

				gl.glTexParameterf(GL10.GL_TEXTURE_2D,
						GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
				gl.glTexParameterf(GL10.GL_TEXTURE_2D,
						GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
				gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
						GL10.GL_CLAMP_TO_EDGE);

				GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp.getBmp(), 0);
			}
		}

		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureName[bmp.getTexture()
				.getId()]);

		tmpColor = bmp.getColor();

		gl.glPushMatrix();
		gl.glColor4f(tmpColor.getR(), tmpColor.getG(), tmpColor.getB(),
				tmpColor.getAlpha());

		centerlocation.x = ((int) bmp.getPosition().getX() + (bmp.getWidth() / 2));
		centerlocation.y = (int) bmp.getPosition().getY()
				+ (bmp.getHeight() / 2);

		gl.glTranslatef(centerlocation.x, centerlocation.y, 0);

		if (bmp.getRotation() != 0) {
			gl.glRotatef(bmp.getRotation(), 0, 0, 1);
		}

		if (bmp.getScale() != 1) {
			gl.glScalef(bmp.getScale(), bmp.getScale(), 1);
		}

		gl.glAlphaFunc(GL10.GL_ALWAYS, 0.5f);

		gl.glTranslatef(bmp.getPosition().getX() - centerlocation.x, bmp
				.getPosition().getY() - centerlocation.y, 0);

		vertices[index++] = 0;
		vertices[index++] = 0;
		vertices[index++] = 0;
		vertices[index++] = 0;
		vertices[index++] = bmp.getHeight();
		vertices[index++] = 0;
		vertices[index++] = bmp.getWidth();
		vertices[index++] = 0;
		vertices[index++] = 0;
		vertices[index++] = bmp.getWidth();
		vertices[index++] = bmp.getHeight();
		vertices[index++] = 0;

		vertexBuffer.put(vertices);
		vertexBuffer.position(0);

		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer[bmp
				.getTexture().getId()]);

		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);

		gl.glPopMatrix();

	}

	/**
	 * draws a text
	 */
	public void drawText(String text, String fontName, int x, int y, float scale) {
		if (begin == false) {
			throw new IllegalArgumentException(
					"call begin() first! drawText() can only be called after begin(). Remember to call end() after all drawings are done!");
		}

		Font font = engine.getFont(fontName);

		char[] chars = text.toCharArray();
		Bitmap bmp = null;

		int length = 0;

		for (char c : chars) {

			index = 0;

			FontCharacter fc = font.getCharacter(c);
			bmp = fc.getBitmap();

			gl.glPushMatrix();

			tmpTxtBuffer = glyphTextureBuffer[font.getId() + c];
			if (tmpTxtBuffer == null) {

				gl.glGenTextures(1, textures, 0);

				glyphTextureName[font.getId() + c] = textures[0];

				gl.glBindTexture(GL10.GL_TEXTURE_2D,
						glyphTextureName[font.getId() + c]);

				FloatBuffer buffer = vertexByteBuffer.asFloatBuffer();
				buffer.put(texture);
				buffer.position(0);

				glyphTextureBuffer[font.getId() + c] = buffer;

				gl.glTexParameterf(GL10.GL_TEXTURE_2D,
						GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
				gl.glTexParameterf(GL10.GL_TEXTURE_2D,
						GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
				gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
						GL10.GL_CLAMP_TO_EDGE);

				GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);
			}

			gl.glBindTexture(GL10.GL_TEXTURE_2D, glyphTextureName[font.getId()
					+ c]);

			centerlocation.x = ((int) length + x + (fc.getCharWidth() / 2));
			centerlocation.y = (int) y + (fc.getCharHeight() / 2);

			gl.glTranslatef(centerlocation.x, centerlocation.y, 0);

			if (scale != 1) {
				gl.glScalef(scale, scale, 1);
			}

			gl.glAlphaFunc(GL10.GL_ALWAYS, 0.5f);

			gl.glTranslatef(length + x - centerlocation.x,
					y - centerlocation.y, 0);

			vertices[index++] = 0;
			vertices[index++] = 0;
			vertices[index++] = 0;
			vertices[index++] = 0;
			vertices[index++] = bmp.getHeight();
			vertices[index++] = 0;
			vertices[index++] = bmp.getWidth();
			vertices[index++] = 0;
			vertices[index++] = 0;
			vertices[index++] = bmp.getWidth();
			vertices[index++] = bmp.getHeight();
			vertices[index++] = 0;

			vertexBuffer.put(vertices);
			vertexBuffer.position(0);

			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0,
					glyphTextureBuffer[font.getId() + c]);

			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);

			length += fc.getCharWidth() + 3;

			gl.glPopMatrix();
		}

	}

	/**
	 * pushs the current surfaces onto the stack
	 */
	public void glPush() {
		glPush++;
		gl.glPushMatrix();
	}

	/**
	 * pops a surface from the stack
	 */
	public void glPop() {
		glPush--;
		gl.glPopMatrix();
	}

}
