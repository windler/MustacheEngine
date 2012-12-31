package de.windler.engine.mustacheengine.FiniteStateMachine;

/**
 * 
 * @author Nico Windler, Michael Janssen
 * 
 */

public abstract class State {

	public abstract void onEnter();

	public abstract void onExit();

	public abstract void update(Actor act, FiniteStateMachine fsm);

}
