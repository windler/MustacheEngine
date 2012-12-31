package de.windler.engine.mustacheengine.game.Bitmap;

import de.windler.engine.mustacheengine.pool.ObjectPool;

/**
 * 
 * @author Nico Windler
 * 
 */
public class GameBitmapPool extends ObjectPool<GameBitmap> {

	public GameBitmapPool(int size, int growth) {
		super(size, growth);
	}

	@Override
	protected GameBitmap onHandleAllocatePoolItems() {
		return new GameBitmap();
	}

	@Override
	protected void onHandleRecyclePoolObject(GameBitmap object) {
		object.recycle();
	}

}
