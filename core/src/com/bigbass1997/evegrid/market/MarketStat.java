package com.bigbass1997.evegrid.market;

import java.util.ArrayList;

import javax.json.JsonObject;

public class MarketStat {
	
	public static enum OrderType {
		BUY, SELL, ALL
	}

	public OrderType orderType;
	public int typeid, systemid, minQ;
	public long volume;
	public float avg, median, max, min;
	
	public MarketStat(OrderType type, JsonObject stat){
		this.orderType = type;
		switch(type){
			case BUY:
				stat = stat.getJsonObject("buy");
				break;
			case ALL:
				stat = stat.getJsonObject("all");
				break;
			case SELL:
				stat = stat.getJsonObject("sell");
				break;
		}
		typeid = stat.getJsonObject("forQuery").getJsonArray("types").getInt(0);
		systemid = stat.getJsonObject("forQuery").getJsonArray("systems").getInt(0);
		minQ = stat.getJsonObject("forQuery").getInt("minq");
		volume = Long.parseLong(stat.get("volume").toString());
		avg = Float.parseFloat(stat.get("avg").toString());
		median = Float.parseFloat(stat.get("median").toString());
		max = Float.parseFloat(stat.get("max").toString());
		min = Float.parseFloat(stat.get("min").toString());
	}
	
	@Override
	public String toString(){
		ArrayList<Object> array = new ArrayList<Object>();
		array.add(orderType); array.add(typeid); array.add(systemid); array.add(minQ); array.add(volume);
		array.add(avg); array.add(median); array.add(max); array.add(min);
		return array.toString();
	}
}
