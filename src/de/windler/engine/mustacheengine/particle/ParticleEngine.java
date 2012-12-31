package de.windler.engine.mustacheengine.particle;

import java.util.ArrayList;
import java.util.List;

import de.windler.engine.mustacheengine.game.Bitmap.GameBitmap;
import de.windler.engine.mustacheengine.game.engine.GameEngine;
import de.windler.engine.mustacheengine.game.sprite.SpriteBatch;

/**
 * 
 * @author Nico Windler
 * 
 */
public class ParticleEngine {

	private List<ParticleEffect> particleEffects;
	private ParticlePool particlePool;
	private GameEngine gameEngine;

	public ParticleEngine(GameEngine engine) {
		particleEffects = new ArrayList<ParticleEffect>();
		particlePool = new ParticlePool();
		gameEngine = engine;
	}

	public void addEffect(ParticleEffect effect) {
		effect.setParticleEngine(this);
		effect.generateParticles();
		particleEffects.add(effect);
	}

	public void update(GameEngine engine) {
		for (int i = 0; i < particleEffects.size(); i++) {
			ParticleEffect e = particleEffects.get(i);
			e.update(engine);
			if (e.finished()) {
				e.recycleAllParticles();
				particleEffects.remove(i);
				i--;
			}
		}
	}

	public void draw(SpriteBatch batch) {
		batch.begin();
		for (ParticleEffect e : particleEffects) {
			e.draw(batch);
		}
		batch.end();
	}

	public Particle getParticleFromPool() {
		return particlePool.getPoolItem();
	}

	public GameBitmap getGamebitmapFromPool(int id) {
		return gameEngine.getGameBitmapFromPool(id);
	}

	public void recycleParticle(Particle particle) {
		particlePool.recyclePoolObject(particle);
	}

	public void recycleGameBitmap(GameBitmap bmp) {
		gameEngine.recycleGameBitmap(bmp);
	}

}
