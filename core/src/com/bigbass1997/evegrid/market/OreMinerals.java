package com.bigbass1997.evegrid.market;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class OreMinerals {
	
	private static JsonParser jsonParser;
	private static JsonElement oreMineralsjson;
	
	private static Hashtable<String, Ore> itemCache;
	
	private static boolean loaded = false;
	
	private static void init(){
		if(!loaded){
			jsonParser = new JsonParser();
			
			try {
				oreMineralsjson = jsonParser.parse(new InputStreamReader(Types.class.getResourceAsStream("/data/oreMinerals.json"), "UTF-8"));
			} catch (JsonIOException e) {
				e.printStackTrace();
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			itemCache = new Hashtable<String, Ore>();
			
			loaded = true;
		}
	}
	
	public static Ore getOre(String name){
		if(!loaded) init();
		
		if(name.isEmpty()) return null;
		
		if(itemCache.containsKey(name)){
			for(Entry<String, Ore> entry : itemCache.entrySet()){
				if(entry.getKey().equalsIgnoreCase(name)){
					return entry.getValue();
				}
			}
		}
		
		for(JsonElement item : oreMineralsjson.getAsJsonObject().getAsJsonArray("items")){
			if(item.getAsJsonObject().get("name").getAsString().equalsIgnoreCase(name)){
				Hashtable<String, Integer> minerals = new Hashtable<String, Integer>();
				for(JsonElement element : item.getAsJsonObject().get("minerals").getAsJsonArray()){
					minerals.put(element.getAsJsonObject().get("name").getAsString(), Integer.valueOf(element.getAsJsonObject().get("quantity").getAsInt()));
				}
				Ore ore = new Ore(name, minerals);
				itemCache.put(name, ore);
				return ore;
			}
		}
		
		return null;
	}
}
