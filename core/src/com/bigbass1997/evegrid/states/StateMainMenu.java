package com.bigbass1997.evegrid.states;

import com.badlogic.gdx.math.Vector2;
import com.bigbass1997.evegrid.commands.CommandChangeState;
import com.bigbass1997.evegrid.objects.ButtonFactory;

public class StateMainMenu extends State {
	
	private ButtonFactory bFactory;
	
	public StateMainMenu(StateManager sm){
		super(sm, "MAIN_MENU");
		
		bFactory = new ButtonFactory();
		bFactory.createButton(new CommandChangeState(sm, new StateOreBuy(sm)), new Vector2(10, 10), new Vector2(50, 50), 0xFFFFFFFF, "");
	}
	
	public void render(){
		bFactory.render(sr, batch);
	}
	
	public void update(float delta){
		bFactory.update(delta);
	}
}
