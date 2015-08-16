package com.bigbass1997.evegrid.market;

import java.io.IOException;

import javax.json.JsonArray;

import com.bigbass1997.evegrid.market.MarketStat.OrderType;
import com.jcabi.http.request.JdkRequest;
import com.jcabi.http.response.JsonResponse;

public class Market {
	
	public Market(){
		
	}
	
	public MarketStat getMarketStat(OrderType orderType, int typeid, int systemid, int minQ){
		if(typeid < 0) return null;
		
		try {
			JsonArray json = new JdkRequest("http://api.eve-central.com/api/marketstat/json?typeid=" + typeid + "&usesystem=" + systemid + "&minQ=" + minQ).fetch().as(JsonResponse.class).json().readArray();
			return new MarketStat(orderType, json.getJsonObject(0));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public MarketStat getMarketStat(OrderType orderType, int typeid, int systemid){
		return getMarketStat(orderType, typeid, systemid, 10001);
	}
}
