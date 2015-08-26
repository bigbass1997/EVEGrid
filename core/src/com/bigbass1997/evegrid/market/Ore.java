package com.bigbass1997.evegrid.market;

import java.util.Hashtable;

public class Ore {
	
	public String name;
	public Hashtable<String, Integer> minerals;
	
	public Ore(String name, Hashtable<String, Integer> minerals){
		this.name = name;
		this.minerals = minerals;
	}
}
