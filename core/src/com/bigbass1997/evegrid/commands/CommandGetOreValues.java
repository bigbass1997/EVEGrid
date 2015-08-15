package com.bigbass1997.evegrid.commands;

import java.util.ArrayList;

public class CommandGetOreValues implements Command {
	
	private ArrayList<Float> values;
	private ArrayList<Integer> typeIDs;
	
	public CommandGetOreValues(ArrayList<Float> values, ArrayList<Integer> typeIDs){
		this.values = values;
		this.typeIDs = typeIDs;
	}
	
	@Override
	public void execute(){
		values.clear();
		for(int i = 0; i < typeIDs.size(); i++){
			values.add(new Float(System.nanoTime()));
		}
	}
}
