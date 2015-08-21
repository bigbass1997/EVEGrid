package com.bigbass1997.evegrid.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.bigbass1997.evegrid.commands.CommandChangeState;
import com.bigbass1997.evegrid.graphics.Draw;
import com.bigbass1997.evegrid.graphics.fonts.FontID;
import com.bigbass1997.evegrid.objects.ButtonFactory;

public class StateMainMenu extends State {
	
	private ButtonFactory bFactory;
	
	public StateMainMenu(StateManager sm){
		super(sm, "MAIN_MENU");
		
		bFactory = new ButtonFactory();
		bFactory.createButton(
				new CommandChangeState(sm, new StateOreBuy(sm)),
				new Vector2(10, 10),
				new Vector2(50, 50),
				new FontID("bin/fonts/computer.ttf", 42),
				0x000000FF, 0xFFFFFFFF,
				"ORE"
		);
	}
	
	public void render(){
		Draw.string(batch, "EVEGrid - The EVE Online MultiTool", new Vector2(200, Gdx.graphics.getHeight() - 20), new FontID("bin/fonts/computer.ttf", 82), 0x00FFFFFF);
		
		bFactory.render(sr, batch);
	}
	
	public void update(float delta){
		bFactory.update(delta);
	}
}
