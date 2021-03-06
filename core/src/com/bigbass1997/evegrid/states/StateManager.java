package com.bigbass1997.evegrid.states;

import java.util.ArrayList;

import com.badlogic.gdx.assets.AssetManager;
import com.bigbass1997.evegrid.graphics.fonts.FontManager;

/**
 * Contains and controls all states within the program.
 * <p>
 * If you are familiar with Screens from libGDX, States are my own version of them,
 * and the StateManager is my version of the libGDX Game class.
 * 
 * @see State
 */
public class StateManager {
	
	//Current state being updated/rendered.
	private State runningState;
	
	//All previous states. Allows for switching between states without losing data.
	private ArrayList<State> previousStates;
	
	//AssetManager
	public static AssetManager assets;
	private boolean initAssets = false;
	
	public StateManager(){
		initAssets();
	}
	
	public StateManager(State firstState){
		initAssets();
		init(firstState);
	}
	
	public void init(State firstState){
		previousStates = new ArrayList<State>();
		
		setState(firstState);
	}
	
	public void initAssets(){
		if(!initAssets){
			//init AssetManager
			assets = new AssetManager();
			
			//load assets here
			FontManager.addFont("fonts/computer.ttf");
			
			//block until all the assets are done loading
			assets.finishLoading();
		}
	}
	
	/**
	 * Sets runningState to newState param.
	 * 
	 * @param newState The new runningState.
	 */
	public void setState(State newState){
		if(runningState != null) previousStates.add(runningState); //Stores current state
		
		runningState = newState;
	}
	
	public State getRunningState(){
		return runningState;
	}
	
	public State getPreviousState(){
		State previous = previousStates.get(previousStates.size() - 1); //Gets last running state
		previousStates.remove(previousStates.size() - 1); //Removes state from history to avoid duplicates
		
		return previous;
	}
	
	public ArrayList<State> getAllPreviousStates(){
		return previousStates;
	}
	
	public ArrayList<String> getAllPreviousStateNames(){
		ArrayList<String> names = new ArrayList<String>();
		
		for(int i = 0; i < previousStates.size(); i++){
			names.add(previousStates.get(i).getStateName());
		}
		
		return names;
	}
	
	public void render(){
		runningState.render();
	}
	
	public void update(float delta){
		runningState.update(delta);
	}
	
	public void dispose(){
		for(int i = 0; i < previousStates.size(); i++){
			State s = previousStates.get(i);
			if(s != null) s.dispose();
		}
		runningState.dispose();
		
		assets.dispose();
	}
}
