package de.windler.engine.mustacheengine.game.texture;

import java.util.Hashtable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import de.windler.engine.mustacheengine.game.Bitmap.GameBitmap;
import de.windler.engine.mustacheengine.game.Bitmap.GameBitmapPool;
import de.windler.engine.mustacheengine.game.engine.GameEngine;
import de.windler.engine.mustacheengine.game.font.Font;
import de.windler.engine.mustacheengine.game.util.POTHelper;

/**
 * TextureManager has the respnsebility for all textures. Textures can be loaded
 * and freed. It checks whether the texture is in power of two. If not the next
 * POT will be created.
 * 
 * @author Nico Windler
 * 
 */
public class TextureManager {

	private GameBitmapPool gameBitmapPool;
	private Hashtable<Integer, Texture> loadedTextures;
	private Hashtable<String, Font> fonts;
	private GameEngine gameEngine;
	private int idCount = 0;

	/**
	 * Erstellt einen neuen TextureManager
	 * 
	 * @param engine
	 */
	public TextureManager(GameEngine engine) {
		loadedTextures = new Hashtable<Integer, Texture>();
		fonts = new Hashtable<String, Font>();
		gameEngine = engine;
		gameBitmapPool = new GameBitmapPool(10000, 10);
	}

	/**
	 * L�dt die Texturen anhand der IDs. Die IDs m�ssen von R.drawable kommen
	 * 
	 * @param ids
	 */
	public void loadTextures(int[] ids) {
		for (int id : ids) {
			if (loadedTextures.get(id) == null) {
				createTexture(id);
			}
		}
	}

	/**
	 * enfernt die Texturen und setzt den Speicher frei. ACHTUNG: GC wird
	 * aufgerufen!
	 * 
	 * @param ids
	 */
	public void unloadTextures(int[] ids) {
		for (int id : ids) {
			if (loadedTextures.get(id) != null) {
				loadedTextures.remove(id);
			}
		}

		System.gc();
	}

	/**
	 * Erstellt ein Gamebitmap anhand eines geladenen Bitmap (POT)
	 * 
	 * @param id
	 * @param position
	 * @return
	 */
	public GameBitmap getGameBitmapFromPool(int id) {
		Texture txt = loadedTextures.get(id);
		GameBitmap gbmp = gameBitmapPool.getPoolItem();
		if (txt == null) {
			throw new IllegalArgumentException(
					"Couldn't find Texture with id: " + id + ". Was it loaded?");
		}
		gbmp.init(txt);
		return gbmp;
	}

	/**
	 * Recyclet das GB und f�gt es in den Pool
	 * 
	 * @param bmp
	 */
	public void recycleGameBitmap(GameBitmap bmp) {
		gameBitmapPool.recyclePoolObject(bmp);
	}

	private void createTexture(int id) {
		Bitmap bmp = decodeBmp(id);

		Texture newTxt = new Texture(++idCount);
		newTxt.setTextureWidth(bmp.getWidth());
		newTxt.setTextureHeight(bmp.getHeight());

		bmp = POTHelper.createPOT(bmp);
		newTxt.setPotBmp(bmp);

		loadedTextures.put(id, newTxt);
	}

	private Bitmap decodeBmp(int id) {
		BitmapFactory.Options bfo = new BitmapFactory.Options();
		bfo.inScaled = false;
		Bitmap bmp = BitmapFactory.decodeResource(gameEngine.getContext()
				.getResources(), id, bfo);
		return bmp;
	}

	/**
	 * liefert den Font zur�ck
	 * 
	 * @param key
	 * @return
	 */
	public Font getFont(String key) {
		return fonts.get(key);
	}

	/**
	 * f�gt ein Font hinzu und macht aus dem Bitmaps POT's
	 */
	public void addFont(Font f) {
		if (f != null) {
			fonts.put(f.getName(), f);
		} else {
			Log.e("Register Font", "Font nicht initialisiert.");
		}
	}

	/**
	 * L�dt alle geladen Textures erneut
	 */
	public void reloadTextures() {
		for (int id : loadedTextures.keySet()) {
			Texture t = loadedTextures.get(id);
			t.setPotBmp(POTHelper.createPOT(decodeBmp(id)));
		}
	}

}
