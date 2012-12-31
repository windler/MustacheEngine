package de.windler.engine.mustacheengine.particle;

import de.windler.engine.mustacheengine.game.Bitmap.GameBitmap;
import de.windler.engine.mustacheengine.game.Color.Color;
import de.windler.engine.mustacheengine.game.engine.GameEngine;
import de.windler.engine.mustacheengine.game.sprite.SpriteBatch;
import de.windler.engine.mustacheengine.util.Vector2D;

/**
 * Abstract Class of a Particle-Effect. ParticleEffects are updated and drawn by
 * the ParticleEngine. Concider that all Particles are generatet just when the
 * constructor is called. So it is advisable to use a {@code ObjectPool} for all
 * Effects.
 * 
 * @author Nico Windler
 * 
 */
public abstract class ParticleEffect {

	private Particle[] particles;
	private int[] bitmaps;
	private Color[] colors;
	private int particleCount;
	private int particlesPerTick;
	private int elapsedTime = 0;
	private Vector2D location;
	private boolean isFinished = false;
	private boolean isFirstTime = true;
	private int createdParticles;
	private ParticleEngine particleEngine;
	private Vector2D gravitation;

	private int liveOffset;
	private int deadOffset;

	public ParticleEffect(Vector2D position) {

		if (getParticleCount() < 0) {
			throw new IllegalArgumentException(
					"getParticleCount has to be grater than 0.");
		}
		if (getParticleCount() % getParticlesPerDurationTick() != 0) {
			throw new IllegalArgumentException(
					"getParticleCount has to be a multiple of getParticlesPerDurationTick.");
		}

		particles = new Particle[getParticleCount()];

		liveOffset = 0;
		deadOffset = 0;

		bitmaps = onLoadGameBitmapIds();
		colors = onLoadColors();
		particleCount = getParticleCount();
		particlesPerTick = getParticlesPerDurationTick();
		location = position;
		createdParticles = 0;
		gravitation = getGravitation();
	}

	/**
	 * Creates all Particles of the Effect
	 */
	protected void generateParticles() {
		for (int i = 0; i < particles.length; i++) {
			particles[i] = generateParticle();
		}
	}

	/**
	 * The ParticleEngine og the Effect
	 * 
	 * @param pEngine
	 */
	protected void setParticleEngine(ParticleEngine pEngine) {
		particleEngine = pEngine;
	}

	/**
	 * GameBitmaps which will randomly picked
	 * 
	 * @param engine
	 * @return
	 */
	public abstract int[] onLoadGameBitmapIds();

	/**
	 * all colors for the particles
	 * 
	 * @return
	 */
	public abstract Color[] onLoadColors();

	/**
	 * 
	 * @return
	 */
	public abstract float getMinScale();

	/**
	 * maximum scale size
	 * 
	 * @return
	 */
	public abstract float getMaxScale();

	/**
	 * the max time a particle is updated an painted
	 * 
	 * @return
	 */
	public abstract int getMaxTimeToLive();

	/**
	 * the min time a particle is updated an painted
	 * 
	 * @return
	 */
	public abstract int getMinTimeToLive();

	/**
	 * min velocity
	 * 
	 * @return
	 */
	public abstract float getMinVelocity();

	/**
	 * max velocity
	 * 
	 * @return
	 */
	public abstract float getMaxVelocity();

	/**
	 */
	public abstract int getMinDegree();

	/**
	 */
	public abstract int getMaxDegree();

	/**
	 * Die minimale Rotationsgeschwindigkeit
	 * 
	 * @return
	 */
	public abstract float getMinRotationSpeed();

	/**
	 */
	public abstract float getMaxRotationSpeed();

	/**
	 * value to be added to the rotation in each update-step
	 */
	public abstract float getAdditionalRotation();

	/**
	 * spawn radius
	 * 
	 * @return
	 */
	public abstract int getPositionRadius();

	/**
	 * additional force
	 * 
	 * @return
	 */
	public abstract Vector2D getGravitation();

	/**
	 * count of all particles
	 * 
	 * @return
	 */
	public abstract int getParticleCount();

	/**
	 * length of a tick-unit. in each tick there will be several particles
	 * spawned
	 * 
	 * @return
	 */
	public abstract int getTickDuration();

	/**
	 * number of particles to b spawned per tick
	 * 
	 * @return
	 */
	public abstract int getParticlesPerDurationTick();

	private Particle generateParticle() {
		GameBitmap bmp = particleEngine
				.getGamebitmapFromPool(bitmaps[(int) (Math.random() * bitmaps.length)]);

		float scale = (float) (Math.random() * getMaxScale() + getMinScale());

		Color color = colors[(int) (Math.random() * colors.length)];

		int degree = (int) (Math.random() * getMaxDegree() + getMinDegree());
		float angleX = (float) Math.cos(Math.toRadians(degree));
		float angleY = (float) Math.sin(Math.toRadians(degree));

		float speed = (float) (Math.random() * getMaxVelocity() + getMinVelocity());
		float velocityX = (float) (speed * angleX);
		float velocityY = (float) (speed * angleY);

		int timeToLive = (int) (Math.random() * getMaxTimeToLive() + getMinTimeToLive());

		int radius = (int) (Math.random() * getPositionRadius());
		float positionX = location.getX() + (radius * angleX);
		float positionY = location.getY() + (radius * angleY);

		float rotation = (float) (Math.random() * getMaxRotationSpeed() + getMinRotationSpeed());

		Particle particle = particleEngine.getParticleFromPool();
		particle.setBitmap(bmp);
		bmp.setColor(color);
		bmp.setPosition(positionX, positionY);
		particle.setScale(scale);
		particle.setMaxRotation(rotation);
		particle.setVelocity(velocityX, velocityY);
		particle.setTimeToLive(timeToLive);

		return particle;
	}

	public void update(GameEngine engine) {
		elapsedTime += engine.getGameTime().getElapsedTime();
		Particle particle = null;
		if (elapsedTime >= getTickDuration()
				&& createdParticles < particleCount || isFirstTime) {
			liveOffset += particlesPerTick;
			elapsedTime = 0;
			isFirstTime = false;
			createdParticles += particlesPerTick;
		}
		for (int i = 0; i < liveOffset; i++) {
			particle = particles[i];
			if (particle.getTimeToLive() > 0) {
				particle.update(engine.getGameTime().getElapsedTime(),
						gravitation, getAdditionalRotation());
			} else {
				liveOffset--;
				swap(i);
				deadOffset++;
			}
		}

		if (createdParticles >= particleCount && liveOffset <= 0) {
			isFinished = true;
		}
	}

	private void swap(int i) {
		Particle temp = particles[particleCount - deadOffset - 1];
		particles[particleCount - deadOffset - 1] = particles[i];
		particles[i] = temp;
	}

	public void draw(SpriteBatch batch) {
		for (int i = 0; i < liveOffset; i++) {
			particles[i].draw(batch);
		}
	}

	public void recycleAllParticles() {
		for (Particle p : particles) {
			particleEngine.recycleGameBitmap(p.getGameBitmap());
			particleEngine.recycleParticle(p);
		}
	}

	public boolean finished() {
		return isFinished;
	}
}
