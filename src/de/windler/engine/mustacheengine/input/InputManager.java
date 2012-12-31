package de.windler.engine.mustacheengine.input;

import java.util.Hashtable;

import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import de.windler.engine.mustacheengine.FiniteStateMachine.Button;
import de.windler.engine.mustacheengine.game.engine.GameTime;

/**
 * Delegates inputs to a gamescreen
 * 
 * @author Nico Windler, Michael Janssen
 * 
 */
public class InputManager implements OnTouchListener, OnKeyListener {

	/**
	 * ID of Menu-Button
	 */
	public static final int BTN_MENU = KeyEvent.KEYCODE_MENU;

	/**
	 * ID of Back-Button
	 */
	public static final int BTN_BACK = KeyEvent.KEYCODE_BACK;

	/**
	 * ID of Home-Button
	 */
	public static final int BTN_HOME = KeyEvent.KEYCODE_HOME;

	/**
	 * ID of Vol(+)-Button
	 */
	public static final int BTN_VOL_UP = KeyEvent.KEYCODE_VOLUME_UP;

	/**
	 * ID of Vol(-)-Button
	 */
	public static final int BTN_VOL_DOWN = KeyEvent.KEYCODE_VOLUME_DOWN;

	private Hashtable<String, ButtonState> btnStates = null;
	private Hashtable<String, TouchState> touchStates = null;

	private int curKeyCode = -1;
	private boolean down = false;

	private MotionEvent motionEvent = null;

	private IInputListener inputListener = null;

	private float displayWidthRatio;

	private int displayHeight;

	private float displayHeightRatio;

	public InputManager(GLSurfaceView view, float dspWidthRatio,
			float dspHeightRatio, int dspHeight) {
		btnStates = new Hashtable<String, ButtonState>();
		touchStates = new Hashtable<String, TouchState>();

		displayWidthRatio = dspWidthRatio;
		displayHeightRatio = dspHeightRatio;
		displayHeight = dspHeight;

		view.setFocusable(true);
		view.setFocusableInTouchMode(true);
		view.setOnTouchListener(this);
		view.setOnKeyListener(this);

	}

	/**
	 * Registriert einen neuen Button (InputState)
	 * 
	 * @param name
	 *            Name des States
	 * @param id
	 *            ID des Buttons
	 */
	public void registerNewBtnState(String name, int id) {
		btnStates.put(name, new ButtonState(id));
	}

	public void registerNewTouchState(String name, Point start, Point end) {
		touchStates.put(name, new TouchState(start, end, displayWidthRatio,
				displayHeightRatio, displayHeight));
	}

	public boolean onTouch(View v, MotionEvent event) {
		motionEvent = event;
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			down = true;
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			down = false;
		}
		return true;
	}

	public boolean onKey(View v, int keyCode, KeyEvent event) {
		curKeyCode = keyCode;
		return false;
	}

	public void update(GameTime gameTime) {
		boolean previousValueExists = false;
		for (ButtonState b : btnStates.values()) {
			if (curKeyCode != -1 && curKeyCode == b.getBtnId()) {
				b.update(gameTime, 1);
			} else {
				b.update(gameTime, 0);
			}
			if (!previousValueExists && b.getPreviousValue() == 1) {
				previousValueExists = true;
			}
		}
		for (TouchState t : touchStates.values()) {

			if (down && inRange(t.getPointStart(), t.getPointEnd())) {
				t.update(gameTime, 1);
			} else {
				t.update(gameTime, 0);
			}
			if (!previousValueExists && t.getPreviousValue() == 1) {
				previousValueExists = true;
			}
		}
		if (previousValueExists
				|| (inputListener != null && (down || curKeyCode != -1))) {
			inputListener.onInput(this);
		}
		// motionEvent = null;
		curKeyCode = -1;
	}

	public InputState getState(String name) {
		InputState state = null;
		if (btnStates.containsKey(name)) {
			state = btnStates.get(name);
		} else if (touchStates.containsKey(name)) {
			state = touchStates.get(name);
		}
		return state;
	}

	public InputState getState(Button btn) {
		InputState state = null;
		if (touchStates.containsKey(btn.getKey())) {
			state = touchStates.get(btn.getKey());
		}
		return state;
	}

	private boolean inRange(Point start, Point end) {
		boolean result = false;

		// if (motionEvent != null) {
		if (down) {
			result = (start.x < Math.round(motionEvent.getRawX()) && end.x > Math
					.round(motionEvent.getRawX()));
			result = result
					&& (start.y < Math.round(motionEvent.getRawY()) && end.y > Math
							.round(motionEvent.getRawY()));
		}
		return result;
	}

	public void setInputListener(IInputListener listener) {
		this.inputListener = listener;
	}
}
