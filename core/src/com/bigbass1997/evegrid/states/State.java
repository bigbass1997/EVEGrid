package com.bigbass1997.evegrid.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * A "State" is an instance within the program that has its
 * own independent render and update functions. All program
 * assets as usual should be loaded in from the asset manager.
 * 
 * @see StateManager
 * @see Assets
 */
public class State {
	
	protected SpriteBatch batch;
	protected ShapeRenderer sr;
	protected StateManager sm;
	
	private String stateName;
	
	/**
	 * Constructor for a generic state.
	 * 
	 * @param sm StateManager so that the state can call back to
	 * other managers without having to recreate the manager or calling statically.
	 * @param stateName Name of the state so that it can be easily identified.
	 */
	public State(StateManager sm, String stateName) {
		this.stateName = stateName;
		this.sm = sm;
		
		/*
		 * To keep the program modular, every state has its own SpriteBatch and ShapeRenderer.
		 * 
		 * The batch is used for drawing strings, textures, etc.
		 * While the ShapeRenderer, is used in the Draw class for drawing shapes.
		 * 
		 * 
		 */
		batch = new SpriteBatch();
		sr = new ShapeRenderer();
	}
	
	/**
	 * Constructor for a generic state.
	 * 
	 * @param sm StateManager so that the state can call back to
	 * other managers without having to recreate the manager or calling statically.
	 */
	public State(StateManager sm){
		this(sm, "GENERIC");
	}
	
	public void render(){
		
	}
	
	public void update(float delta){
		
	}
	
	/**
	 * Usually called when the state no longer is needed and is removed.
	 */
	public void dispose(){
		batch.dispose();
		sr.dispose();
	}
	
	public String getStateName(){
		return stateName;
	}
}
