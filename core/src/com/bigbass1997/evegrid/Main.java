package com.bigbass1997.evegrid;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.bigbass1997.evegrid.states.StateMainMenu;
import com.bigbass1997.evegrid.states.StateManager;

public class Main extends ApplicationAdapter {
	
	private StateManager sm;
	
	@Override
	public void create(){
		sm = new StateManager();
		sm.init(new StateMainMenu(sm));
	}

	@Override
	public void render(){
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//Rendering
		sm.render();
		
		//Updating
		sm.update(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void dispose(){
		sm.dispose();
	}
}
