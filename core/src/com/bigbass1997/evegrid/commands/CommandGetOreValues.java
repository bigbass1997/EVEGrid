package com.bigbass1997.evegrid.commands;

import java.util.ArrayList;

import com.bigbass1997.evegrid.market.Market;
import com.bigbass1997.evegrid.market.MarketStat;
import com.bigbass1997.evegrid.market.MarketStat.OrderType;

public class CommandGetOreValues implements Command {
	
	private ArrayList<Float> values;
	private ArrayList<Integer> typeIDs;
	
	public CommandGetOreValues(ArrayList<Float> values, ArrayList<Integer> typeIDs){
		this.values = values;
		this.typeIDs = typeIDs;
	}
	
	@Override
	public void execute(){
		long startTime = System.nanoTime();
		Market market = new Market();
		values.clear();
		for(int i = 0; i < typeIDs.size(); i++){
			MarketStat stat = market.getMarketStat(OrderType.BUY, typeIDs.get(i), 30000142);
			if(stat != null) values.add(stat.max);
			else values.add(0f);
		}
		System.out.println("Data loaded in: " + ((System.nanoTime() - startTime) / 1000000) + "ms");
	}
}
