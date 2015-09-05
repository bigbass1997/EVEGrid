package com.bigbass1997.evegrid.states;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.bigbass1997.evegrid.commands.CommandChangeState;
import com.bigbass1997.evegrid.commands.CommandGetItemValues;
import com.bigbass1997.evegrid.graphics.fonts.FontID;
import com.bigbass1997.evegrid.graphics.skins.SkinID;
import com.bigbass1997.evegrid.graphics.skins.SkinManager;
import com.bigbass1997.evegrid.market.OreMinerals;
import com.bigbass1997.evegrid.market.Systems;
import com.bigbass1997.evegrid.market.Types;
import com.bigbass1997.evegrid.objects.ButtonFactory;
import com.bigbass1997.evegrid.objects.StageManager;

public class StateSellOreVsRefine extends State {
	
	private StageManager sManager;
	
	private String[] minerals;

	private ArrayList<Float> values;
	private ArrayList<Integer> typeIDs;
	private ArrayList<Integer> systemID;

	private ButtonFactory bFactory;
	
	private NumberFormat qtyFormat;
	private NumberFormat valFormat;
	
	private boolean initComplete = false;
	
	public StateSellOreVsRefine(StateManager sm){
		super(sm, "SellOreVsRefine");
		
		sManager = new StageManager();

		values = new ArrayList<Float>();
		typeIDs = new ArrayList<Integer>();
		systemID = new ArrayList<Integer>();
		
		bFactory = new ButtonFactory();
		
		bFactory.createButton(new CommandGetItemValues(values, typeIDs, systemID), new Vector2(10, 10), new Vector2(80, 20), new FontID("fonts/computer.ttf", 20), 0x0000FFFF, 0xDDDDDDFF, "SUBMIT", true);

		qtyFormat = NumberFormat.getInstance();
		qtyFormat.setMinimumFractionDigits(0);
		qtyFormat.setMaximumFractionDigits(2);
		valFormat = NumberFormat.getInstance();
		valFormat.setMinimumFractionDigits(2);
		valFormat.setMaximumFractionDigits(2);
		
		SkinID skinc32 = new SkinID(new FontID("fonts/computer.ttf", 32));
		SkinID skinc24 = new SkinID(new FontID("fonts/computer.ttf", 24));
		float h = Gdx.graphics.getHeight();
		
		//header labels
		sManager.createLabel("Your %", "Your %", SkinManager.getSkin(skinc32), 15, h-32, 90, 22);
		sManager.createLabel("Ore Name", "Ore Name", SkinManager.getSkin(skinc32), 120, h-32, 185, 22);
		sManager.createLabel("Ore Quantity", "Ore Quantity", SkinManager.getSkin(skinc32), 310, h-32, 150, 22);
		sManager.createLabel("System Name", "System Name", SkinManager.getSkin(skinc32), 465, h-32, 150, 22);
		
		sManager.createLabel("Output Quantity", "Output Quantity", SkinManager.getSkin(skinc32), 165, h-120, 245, 22);
		sManager.createLabel("Output Value", "Output Value", SkinManager.getSkin(skinc32), 415, h-120, 305, 22);
		sManager.createLabel("Grand Total Values", "Grand Total Values", SkinManager.getSkin(skinc32), 725, h-120, 460, 22);
		
		sManager.createLabel("Mineral Name", "Mineral Name", SkinManager.getSkin(skinc32), 10, h-147, 150, 22);
		
		sManager.createLabel("Output Quantity 100%", "100%", SkinManager.getSkin(skinc32), 165, h-147, 120, 22);
		sManager.createLabel("Output Quantity Your %", "Your %", SkinManager.getSkin(skinc32), 290, h-147, 120, 22);
		
		sManager.createLabel("Output Value 100%", "100%", SkinManager.getSkin(skinc32), 415, h-147, 150, 22);
		sManager.createLabel("Output Value Your %", "Your %", SkinManager.getSkin(skinc32), 570, h-147, 150, 22);
		
		sManager.createLabel("Grand Total Values 100%", "100%", SkinManager.getSkin(skinc32), 725, h-147, 150, 22);
		sManager.createLabel("Grand Total Values Your %", "Your %", SkinManager.getSkin(skinc32), 880, h-147, 150, 22);
		sManager.createLabel("Grand Total Values Ore", "Ore", SkinManager.getSkin(skinc32), 1035, h-147, 150, 22);
		
		minerals = new String[]{"Tritanium", "Pyerite", "Mexallon", "Isogen", "Nocxium", "Megacyte", "Zydrine", "Morphite"};
		for(int i = 0; i < minerals.length; i++){
			sManager.createLabel(minerals[i], minerals[i], SkinManager.getSkin(skinc24), 10, h-174-(25 * i), 150, 20);
		}
		
		//inputs
		sManager.createTextField("Your % Input", "0.50", SkinManager.getSkin(skinc24), 15, h-57, 90, 20);
		sManager.createTextField("Ore Name Input", "", SkinManager.getSkin(skinc24), 120, h-57, 185, 20);
		sManager.createTextField("Ore Quantity Input", "", SkinManager.getSkin(skinc24), 310, h-57, 150, 20);
		sManager.createTextField("System Name Input", "Jita", SkinManager.getSkin(skinc24), 465, h-57, 150, 20);
		
		//output labels
		for(int i = 0; i < minerals.length; i++){
			sManager.createLabel("Output Quantity 100% Output " + i, "0", SkinManager.getSkin(skinc24), 165, h-174-(25 * i), 120, 20);
			sManager.createLabel("Output Quantity Your % Output " + i, "0", SkinManager.getSkin(skinc24), 290, h-174-(25 * i), 120, 20);

			sManager.createLabel("Output Value 100% Output " + i, "0.00", SkinManager.getSkin(skinc24), 415, h-174-(25 * i), 150, 20);
			sManager.createLabel("Output Value Your % Output " + i, "0.00", SkinManager.getSkin(skinc24), 570, h-174-(25 * i), 150, 20);
		}

		sManager.createLabel("Grand Total Values 100% Output", "0.00", SkinManager.getSkin(skinc24), 725, h-174, 150, 20);
		sManager.createLabel("Grand Total Values Your % Output", "0.00", SkinManager.getSkin(skinc24), 880, h-174, 150, 20);
		sManager.createLabel("Grand Total Values Ore Output", "0.00", SkinManager.getSkin(skinc24), 1035, h-174, 150, 20);
		

		//flush and set typeIDs
		typeIDs.clear();
		for(int i = 0; i < minerals.length; i++){
			typeIDs.add(Types.getTypeID(minerals[i]));
		}
		typeIDs.add(Types.getTypeID(sManager.textFields.get("Ore Name Input").getText()));
		
		//flush and set systemID
		systemID.clear();
		systemID.add(Systems.getSystemID(sManager.textFields.get("System Name Input").getText()));
		
		//init values list
		bFactory.getButton(0).callCommand();
	}
	
	public void render(){
		sManager.render(sr);
		bFactory.render(sr, batch);
	}
	
	public void update(float delta){
		//flush and set typeIDs
		typeIDs.clear();
		for(int i = 0; i < minerals.length; i++){
			typeIDs.add(Types.getTypeID(minerals[i]));
		}
		typeIDs.add(Types.getTypeID(sManager.textFields.get("Ore Name Input").getText()));
		
		//flush and set systemID
		systemID.clear();
		systemID.add(Systems.getSystemID(sManager.textFields.get("System Name Input").getText()));
		
		//update stage manager and buttons
		sManager.update(delta);
		bFactory.update(delta);
		
		if(!initComplete){
			bFactory.createButton(new CommandChangeState(sm, StateMainMenu.class), new Vector2(10, 35), new Vector2(80, 20), new FontID("fonts/computer.ttf", 20), 0x0000FFFF, 0xDDDDDDFF, "MAIN MENU");
			initComplete = true;
		}
		
		//update table values
		BigDecimal grandTotal100 = BigDecimal.ZERO;
		BigDecimal grandTotalYour = BigDecimal.ZERO;
		
		BigDecimal oreQty = BigDecimal.ZERO;
		BigDecimal total = BigDecimal.ZERO;
		String oreName = "";
		float percent = 0f;
		for(int i = 0; i < minerals.length; i++){
			if(sManager.textFields.get("Ore Quantity Input").getText().isEmpty()) oreQty = BigDecimal.ZERO;
			else oreQty = BigDecimal.valueOf(Long.valueOf(sManager.textFields.get("Ore Quantity Input").getText()));
			oreName = sManager.textFields.get("Ore Name Input").getText();
			percent = Float.valueOf(sManager.textFields.get("Your % Input").getText());
			
			//Output Quantity > 100%
			//quantity = ((mineralMax * (oreQty / 100)) * 1.00)
			if(OreMinerals.getOre(oreName) != null){
				total = BigDecimal.ONE.multiply(oreQty.divide(BigDecimal.valueOf(100.0))).multiply(BigDecimal.valueOf((OreMinerals.getOre(oreName).minerals.get(minerals[i]))));
				
				sManager.labels.get("Output Quantity 100% Output " + i).setText(
						qtyFormat.format( total.setScale(2, RoundingMode.HALF_EVEN).doubleValue() )
				);
			} else {
				sManager.labels.get("Output Quantity 100% Output " + i).setText("0");
			}
			
			//Output Quantity > Your %
			//quantity = ((mineralMax * (oreQty / 100)) * Your%)
			if(OreMinerals.getOre(oreName) != null){
				total = BigDecimal.valueOf(percent).multiply(oreQty.divide(BigDecimal.valueOf(100.0))).multiply(BigDecimal.valueOf((OreMinerals.getOre(oreName).minerals.get(minerals[i]))));
				
				sManager.labels.get("Output Quantity Your % Output " + i).setText(
						qtyFormat.format( total.setScale(2, RoundingMode.HALF_EVEN).doubleValue() )
				);
			} else {
				sManager.labels.get("Output Quantity Your % Output " + i).setText("0");
			}
			
			//Output Value > 100%
			//value = (minQty * minValue)
			if(OreMinerals.getOre(oreName) != null){
				total = removeCommas(sManager.labels.get("Output Quantity 100% Output " + i).getText().toString()).multiply(BigDecimal.valueOf(values.get(i)));
				
				sManager.labels.get("Output Value 100% Output " + i).setText(
						valFormat.format( total.setScale(2, RoundingMode.HALF_EVEN).doubleValue() )
				);
				
				grandTotal100 = grandTotal100.add(total);
			} else {
				sManager.labels.get("Output Value 100% Output " + i).setText("0.00");
			}

			//Output Value > Your %
			//value = (minQty * minValue)
			if(OreMinerals.getOre(oreName) != null){
				total = removeCommas(sManager.labels.get("Output Quantity Your % Output " + i).getText().toString()).multiply(BigDecimal.valueOf(values.get(i)));
				
				sManager.labels.get("Output Value Your % Output " + i).setText(
						valFormat.format( total.setScale(2, RoundingMode.HALF_EVEN).doubleValue() )
				);
				grandTotalYour = grandTotalYour.add(total);
			} else {
				sManager.labels.get("Output Value Your % Output " + i).setText("0.00");
			}
		}
		
		//Grand Total Values
		sManager.labels.get("Grand Total Values 100% Output").setText(valFormat.format( grandTotal100 ));
		sManager.labels.get("Grand Total Values Your % Output").setText(valFormat.format( grandTotalYour ));
		sManager.labels.get("Grand Total Values Ore Output").setText(
				valFormat.format( oreQty.multiply(BigDecimal.valueOf(values.get(8))).setScale(2, RoundingMode.HALF_EVEN).doubleValue() )
		);
	}
	
	private BigDecimal removeCommas(String value){
		return new BigDecimal(value.replaceAll(",", ""));
	}
	
	public void dispose(){
		sManager.dispose();
	}
}
