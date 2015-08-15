package com.bigbass1997.evegrid.market;

import java.io.IOException;

import javax.json.JsonArray;

import com.jcabi.http.request.JdkRequest;
import com.jcabi.http.response.JsonResponse;

public class Market {
	
	public Market(){
		
	}
	
	public float getBuyMarketStat(int typeid, int systemid, int minQ){
		try {
			JsonArray json = new JdkRequest("http://api.eve-central.com/api/marketstat/json?typeid=" + typeid + "&usesystem=" + systemid + "&minQ=" + minQ).fetch().as(JsonResponse.class).json().readArray();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	public float getBuyMarketStat(int typeid, int regionid){
		return getBuyMarketStat(typeid, regionid, 1);
	}
}
