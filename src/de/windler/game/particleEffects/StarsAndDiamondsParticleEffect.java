package de.windler.game.particleEffects;

import de.nw90.jawi.R;
import de.windler.engine.mustacheengine.game.Color.Color;
import de.windler.engine.mustacheengine.particle.ParticleEffect;
import de.windler.engine.mustacheengine.util.Vector2D;

public class StarsAndDiamondsParticleEffect extends ParticleEffect {

	public StarsAndDiamondsParticleEffect(Vector2D position) {
		super(position);
	}

	@Override
	public int[] onLoadGameBitmapIds() {
		return new int[] { R.drawable.star, R.drawable.diamond };
	}

	@Override
	public Color[] onLoadColors() {
		return new Color[] { Color.normal(), Color.blue(), Color.green(),
				Color.red() };
	}

	@Override
	public float getMinScale() {
		return 0.6f;
	}

	@Override
	public float getMaxScale() {
		return 1f;
	}

	@Override
	public int getMaxTimeToLive() {
		return 1000;
	}

	@Override
	public int getMinTimeToLive() {
		return 600;
	}

	@Override
	public float getMinVelocity() {
		return 1f;
	}

	@Override
	public float getMaxVelocity() {
		return 5f;
	}

	@Override
	public int getPositionRadius() {
		return 10;
	}

	@Override
	public int getParticleCount() {
		return 300;
	}

	@Override
	public int getTickDuration() {
		return 100;
	}

	@Override
	public int getParticlesPerDurationTick() {
		return 50;
	}

	@Override
	public int getMinDegree() {
		return 0;
	}

	@Override
	public int getMaxDegree() {
		return 360;
	}

	@Override
	public Vector2D getGravitation() {
		return new Vector2D(0, -0.2f);
	}

	@Override
	public float getMinRotationSpeed() {
		return 0;
	}

	@Override
	public float getMaxRotationSpeed() {
		return 10f;
	}

	@Override
	public float getAdditionalRotation() {
		return 5f;
	}

}
