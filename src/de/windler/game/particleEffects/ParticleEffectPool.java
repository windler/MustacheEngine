package de.windler.game.particleEffects;

import de.windler.engine.mustacheengine.pool.ObjectPool;
import de.windler.engine.mustacheengine.util.Vector2D;

public class ParticleEffectPool extends
		ObjectPool<StarsAndDiamondsParticleEffect> {

	public ParticleEffectPool() {
		super(10, 5);
	}

	@Override
	protected StarsAndDiamondsParticleEffect onHandleAllocatePoolItems() {
		return new StarsAndDiamondsParticleEffect(new Vector2D(200, 200));
	}

	@Override
	protected void onHandleRecyclePoolObject(
			StarsAndDiamondsParticleEffect object) {

	}

}
