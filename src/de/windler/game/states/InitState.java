package de.windler.game.states;

import de.windler.engine.mustacheengine.FiniteStateMachine.Actor;
import de.windler.engine.mustacheengine.FiniteStateMachine.FiniteStateMachine;
import de.windler.engine.mustacheengine.FiniteStateMachine.State;

public class InitState extends State {

	private static InitState state = null;

	public static InitState instance() {
		if (state == null) {
			state = new InitState();
		}
		return state;
	}

	@Override
	public void onEnter() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Actor act, FiniteStateMachine fsm) {
		// TODO Auto-generated method stub

	}

}
