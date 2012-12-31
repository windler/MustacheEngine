package de.windler.engine.mustacheengine.input;

/**
 * @author Nico Windler, Michael Janssen
 * 
 */
public class ButtonState extends InputState {

	private int btnId = 0;

	public ButtonState(int id) {
		btnId = id;
	}

	public void setBtnId(int btnId) {
		this.btnId = btnId;
	}

	public int getBtnId() {
		return btnId;
	}

}
