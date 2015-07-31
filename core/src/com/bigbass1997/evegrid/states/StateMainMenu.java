package com.bigbass1997.evegrid.states;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.bigbass1997.evegrid.graphics.Draw;

public class StateMainMenu extends State {
	
	public StateMainMenu(StateManager sm){
		super(sm, "MAIN_MENU");
	}
	
	public void render(){
		Draw.rect(sr, 50, 50, 100, 200, ShapeType.Filled, 0x00FF00FF);
	}
	
	public void update(float delta){
		
	}
}
