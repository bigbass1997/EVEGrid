package com.bigbass1997.evegrid.commands;

import com.bigbass1997.evegrid.states.State;
import com.bigbass1997.evegrid.states.StateMainMenu;
import com.bigbass1997.evegrid.states.StateManager;
import com.bigbass1997.evegrid.states.StateOreHighBuy;

public class CommandChangeState implements Command {
	
	private StateManager sm;
	private Class<?> state;
	
	public CommandChangeState(StateManager sm, Class<?> state){
		this.sm = sm;
		this.state = state;
	}

	@Override
	public void execute() {
		if(state.equals(State.class)){
			sm.setState(new State(sm));
		}else if(state.equals(StateMainMenu.class)){
			sm.setState(new StateMainMenu(sm));
		}else if(state.equals(StateOreHighBuy.class)){
			sm.setState(new StateOreHighBuy(sm));
		}
	}
}
