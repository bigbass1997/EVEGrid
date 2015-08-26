package com.bigbass1997.evegrid.states;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.bigbass1997.evegrid.commands.CommandChangeState;
import com.bigbass1997.evegrid.commands.CommandGetItemValues;
import com.bigbass1997.evegrid.graphics.fonts.FontID;
import com.bigbass1997.evegrid.graphics.skins.SkinID;
import com.bigbass1997.evegrid.graphics.skins.SkinManager;
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
	
	private DecimalFormat df;
	
	private boolean initComplete = false;
	
	public StateSellOreVsRefine(StateManager sm){
		super(sm, "SellOreVsRefine");
		
		sManager = new StageManager();

		values = new ArrayList<Float>();
		typeIDs = new ArrayList<Integer>();
		systemID = new ArrayList<Integer>();
		
		bFactory = new ButtonFactory();
		
		bFactory.createButton(new CommandGetItemValues(values, typeIDs, systemID), new Vector2(10, 10), new Vector2(80, 20), new FontID("fonts/computer.ttf", 20), 0x0000FFFF, 0xDDDDDDFF, "SUBMIT");
		
		df = new DecimalFormat("#");
		df.setMaximumFractionDigits(2);
		df.setMinimumIntegerDigits(40);
		
		SkinID skinc32 = new SkinID(new FontID("fonts/computer.ttf", 32));
		SkinID skinc24 = new SkinID(new FontID("fonts/computer.ttf", 24));
		float h = Gdx.graphics.getHeight();
		
		sManager.createLabel("Your % Input", "Your %", SkinManager.getSkin(skinc32), 15f, h-32f, 90f, 22f);
		sManager.createLabel("Ore Name", "Ore Name", SkinManager.getSkin(skinc32), 120f, h-32f, 185f, 22f);
		sManager.createLabel("Ore Quantity", "Ore Quantity", SkinManager.getSkin(skinc32), 310f, h-32f, 150f, 22f);
		
		sManager.createLabel("Output Quantity", "Output Quantity", SkinManager.getSkin(skinc32), 165f, h-120f, 245f, 22f);
		sManager.createLabel("Output Value", "Output Value", SkinManager.getSkin(skinc32), 415f, h-120f, 305f, 22f);
		sManager.createLabel("Grand Total Values", "Grand Total Values", SkinManager.getSkin(skinc32), 725f, h-120f, 460f, 22f);
		
		sManager.createLabel("Mineral Name", "Mineral Name", SkinManager.getSkin(skinc32), 10f, h-147f, 150f, 22f);
		
		sManager.createLabel("Output Quantity 100%", "100%", SkinManager.getSkin(skinc32), 165f, h-147f, 120f, 22f);
		sManager.createLabel("Output Quantity Your %", "Your %", SkinManager.getSkin(skinc32), 290f, h-147f, 120f, 22f);
		
		sManager.createLabel("Output Value 100%", "100%", SkinManager.getSkin(skinc32), 415f, h-147f, 150f, 22f);
		sManager.createLabel("Output Value Your %", "Your %", SkinManager.getSkin(skinc32), 570f, h-147f, 150f, 22f);
		
		sManager.createLabel("Grand Total Values 100%", "100%", SkinManager.getSkin(skinc32), 725f, h-147f, 150f, 22f);
		sManager.createLabel("Grand Total Values Your %", "Your %", SkinManager.getSkin(skinc32), 880f, h-147f, 150f, 22f);
		sManager.createLabel("Grand Total Values Ore", "Ore", SkinManager.getSkin(skinc32), 1035f, h-147f, 150f, 22f);
		
		minerals = new String[]{"Tritanium", "Pyerite", "Mexallon", "Isogen", "Nocxium", "Megacyte", "Zydrine", "Morphite"};
		for(int i = 0; i < minerals.length; i++){
			sManager.createLabel(minerals[i], minerals[i], SkinManager.getSkin(skinc24), 10f, h-174f-(25 * i), 150f, 20f);
		}
	}
	
	public void render(){
		sManager.render(sr);
		bFactory.render(sr, batch);
	}
	
	public void update(float delta){
		//typeIDs.clear();
		/*for(int i = 0; i < minerals.length; i++){
			typeIDs.add(Types.getTypeID(sManager.textFields.get("" + i).getText()));
		}*/
		
		//systemID.clear();
		//systemID.add(Systems.getSystemID(sManager.textFields.get(49).getText()));
		
		sManager.update(delta);
		bFactory.update(delta);
		
		if(!initComplete){
			bFactory.createButton(new CommandChangeState(sm, StateMainMenu.class), new Vector2(10, 35), new Vector2(80, 20), new FontID("fonts/computer.ttf", 20), 0x0000FFFF, 0xDDDDDDFF, "MAIN MENU");
			initComplete = true;
		}
	}
	
	public void dispose(){
		sManager.dispose();
	}
}
