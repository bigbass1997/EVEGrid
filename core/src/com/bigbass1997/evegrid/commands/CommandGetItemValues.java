package com.bigbass1997.evegrid.commands;

import java.util.ArrayList;

import com.bigbass1997.evegrid.market.Market;
import com.bigbass1997.evegrid.market.MarketStat;
import com.bigbass1997.evegrid.market.MarketStat.OrderType;

public class CommandGetItemValues implements Command {
	
	private ArrayList<Float> values;
	private ArrayList<Integer> typeIDs;
	private ArrayList<Integer> systemID;
	
	public CommandGetItemValues(ArrayList<Float> values, ArrayList<Integer> typeIDs, ArrayList<Integer> systemID){
		this.values = values;
		this.typeIDs = typeIDs;
		this.systemID = systemID;
	}
	
	@Override
	public void execute(){
		System.out.println(systemID);
		
		Market market = new Market();
		values.clear();
		for(int i = 0; i < typeIDs.size(); i++){
			MarketStat stat = market.getMarketStat(OrderType.BUY, typeIDs.get(i), systemID.get(0));
			if(stat != null) values.add(stat.max);
			else values.add(0f);
		}
	}
}
