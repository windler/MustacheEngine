package de.windler.engine.mustacheengine.particle;

import de.windler.engine.mustacheengine.pool.ObjectPool;

/**
 * 
 * @author Nico Windler
 * 
 */
public class ParticlePool extends ObjectPool<Particle> {

	public ParticlePool() {
		super(1000, 10);
	}

	@Override
	protected Particle onHandleAllocatePoolItems() {
		return new Particle();
	}

	@Override
	protected void onHandleRecyclePoolObject(Particle object) {
		object.recycle();
	}

}
