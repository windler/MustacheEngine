package de.windler.engine.mustacheengine.game.texture;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Nico Windler
 * 
 */
public class GameScreenTextures {

	private List<BufferedTexture> bufferedTextures;

	public GameScreenTextures() {
		bufferedTextures = new ArrayList<BufferedTexture>();
	}

	public void addTexture(int id, TexturePersistence persistence) {
		bufferedTextures.add(new BufferedTexture(id, persistence));
	}

	public void addTexture(int id) {
		addTexture(id, TexturePersistence.UNLOAD_ON_POP);
	}

	/**
	 * array of ids to be loaded
	 * 
	 * @return
	 */
	public int[] getIdsToLoad() {
		int[] ids = new int[bufferedTextures.size()];

		int i = 0;
		for (BufferedTexture b : bufferedTextures) {
			ids[i] = b.getId();
			i++;
		}

		return ids;
	}

	/**
	 * array of ids to be unloaded
	 * 
	 * @return
	 */
	public int[] getIdsToUnload() {
		List<Integer> ids = new ArrayList<Integer>();

		for (BufferedTexture b : bufferedTextures) {
			if (b.getPersistence().equals(TexturePersistence.UNLOAD_ON_POP)) {
				ids.add(b.getId());
			}
		}
		int[] idsToUnload = new int[ids.size()];

		int j = 0;
		for (int id : ids) {
			idsToUnload[j] = id;
			j++;
		}
		return idsToUnload;
	}
}
