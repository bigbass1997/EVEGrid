package com.bigbass1997.evegrid.commands;

import com.bigbass1997.evegrid.states.State;
import com.bigbass1997.evegrid.states.StateManager;

public class CommandChangeState implements Command {
	
	private StateManager sm;
	private State state;
	
	public CommandChangeState(StateManager sm, State state){
		this.sm = sm;
		this.state = state;
	}

	@Override
	public void execute() {
		sm.setState(state);
	}
}
