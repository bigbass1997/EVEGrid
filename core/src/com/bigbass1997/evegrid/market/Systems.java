package com.bigbass1997.evegrid.market;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class Systems {
	
	private static JsonParser jsonParser;
	private static JsonElement systemIDjson;
	
	private static Hashtable<Integer, String> systemCache;
	
	private static boolean loaded = false;
	
	private static void init(){
		if(!loaded){
			jsonParser = new JsonParser();
			
			try {
				systemIDjson = jsonParser.parse(new InputStreamReader(Types.class.getResourceAsStream("/data/systems.json"), "UTF-8"));
			} catch (JsonIOException e) {
				e.printStackTrace();
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			systemCache = new Hashtable<Integer, String>();
			
			loaded = true;
		}
	}
	
	public static String getSystemName(int id){
		init();
		
		if(systemCache.get(id) != null) return systemCache.get(id);
		
		for(JsonElement system : systemIDjson.getAsJsonObject().getAsJsonArray("systems")){
			if(system.getAsJsonObject().get("id").getAsInt() == id){
				String name = system.getAsJsonObject().get("name").getAsString();
				systemCache.put(id, name);
				return name;
			}
		}
		
		return "unknown";
	}
	
	public static int getSystemID(String name){
		init();
		
		if(name.equalsIgnoreCase("N/A") || name.isEmpty()) return -1;
		
		if(systemCache.containsValue(name)){
			for(Entry<Integer, String> entry : systemCache.entrySet()){
				if(entry.getValue() == name) return entry.getKey();
			}
		}
		
		for(JsonElement system : systemIDjson.getAsJsonObject().getAsJsonArray("systems")){
			if(system != null && system.isJsonObject()){
				if(system.getAsJsonObject().get("name").getAsString().equalsIgnoreCase(name)){
					int id = system.getAsJsonObject().get("id").getAsInt();
					systemCache.put(id, name);
					return id;
				}
			}
		}
		
		return -1;
	}
}
