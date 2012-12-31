package de.windler.engine.mustacheengine.input;

import de.windler.engine.mustacheengine.game.engine.GameTime;

/**
 * 
 * @author Nico Windler, Michael Janssen
 * 
 */
public class InputState {

	private int currentValue = 0;
	private int previousValue = 0;
	private long pressedTime = 0;

	public boolean justPressed() {
		return (previousValue == 0 && currentValue == 1);
	}

	public boolean justReleased() {
		return (previousValue == 1 && currentValue == 0);
	}

	public boolean pressed() {
		return (previousValue == 1 && currentValue == 1);
	}

	public long pressedTime() {
		return pressedTime;
	}

	public void update(GameTime gameTime, int value) {
		previousValue = currentValue;
		currentValue = value;
		pressedTime += gameTime.getElapsedTime();

		if (value > 0 && previousValue == 0) {
			pressedTime = 0;
		}
	}

	protected int getPreviousValue() {
		return previousValue;
	}

}
