package de.windler.engine.mustacheengine.game.texture;

/**
 * 
 * @author Nico Windler
 * 
 */
public class BufferedTexture {

	private int id;
	private TexturePersistence persistence;

	public BufferedTexture(int id, TexturePersistence persistence) {
		this.id = id;
		this.persistence = persistence;
	}

	public int getId() {
		return id;
	}

	public TexturePersistence getPersistence() {
		return persistence;
	}

}
