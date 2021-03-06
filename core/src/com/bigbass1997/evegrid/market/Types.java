package com.bigbass1997.evegrid.market;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class Types {
	
	private static JsonParser jsonParser;
	private static JsonElement typeIDjson;
	
	private static Hashtable<Integer, String> itemCache;
	
	private static boolean loaded = false;
	
	private static void init(){
		if(!loaded){
			jsonParser = new JsonParser();
			
			try {
				typeIDjson = jsonParser.parse(new InputStreamReader(Types.class.getResourceAsStream("/data/types.json"), "UTF-8"));
			} catch (JsonIOException e) {
				e.printStackTrace();
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			itemCache = new Hashtable<Integer, String>();
			
			loaded = true;
		}
	}
	
	public static String getTypeName(int id){
		init();
		
		if(id < 0) return "unknown";
		
		if(itemCache.get(id) != null) return itemCache.get(id);
		
		for(JsonElement item : typeIDjson.getAsJsonObject().getAsJsonArray("items")){
			if(item.getAsJsonObject().get("id").getAsInt() == id){
				String name = item.getAsJsonObject().get("name").getAsString();
				itemCache.put(id, name);
				return name;
			}
		}
		
		return "unknown";
	}
	
	public static int getTypeID(String name){
		init();
		
		if(name.equalsIgnoreCase("N/A") || name.isEmpty()) return -1;
		
		if(itemCache.containsValue(name)){
			for(Entry<Integer, String> entry : itemCache.entrySet()){
				if(entry.getValue() == name) return entry.getKey();
			}
		}
		
		for(JsonElement item : typeIDjson.getAsJsonObject().getAsJsonArray("items")){
			if(item.getAsJsonObject().get("name").getAsString().equalsIgnoreCase(name)){
				int id = item.getAsJsonObject().get("id").getAsInt();
				itemCache.put(id, name);
				return id;
			}
		}
		
		return -1;
	}
}
