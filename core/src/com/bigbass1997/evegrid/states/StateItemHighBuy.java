package com.bigbass1997.evegrid.states;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.bigbass1997.evegrid.commands.CommandChangeState;
import com.bigbass1997.evegrid.commands.CommandGetItemValues;
import com.bigbass1997.evegrid.graphics.Draw;
import com.bigbass1997.evegrid.graphics.fonts.FontID;
import com.bigbass1997.evegrid.graphics.skins.SkinID;
import com.bigbass1997.evegrid.graphics.skins.SkinManager;
import com.bigbass1997.evegrid.market.Systems;
import com.bigbass1997.evegrid.market.Types;
import com.bigbass1997.evegrid.objects.ButtonFactory;
import com.bigbass1997.evegrid.objects.StageManager;

public class StateItemHighBuy extends State {

	private StageManager sManager;
	
	private int rows = 24;
	
	private ArrayList<Float> values;
	private ArrayList<Integer> typeIDs;
	private ArrayList<Integer> systemID;
	
	private ButtonFactory bFactory;
	
	private DecimalFormat df;
	
	private boolean initComplete = false;
	
	public StateItemHighBuy(StateManager sm) {
		super(sm, "ORE_BUY");
		
		sManager = new StageManager();
		
		values = new ArrayList<Float>();
		typeIDs = new ArrayList<Integer>();
		systemID = new ArrayList<Integer>();
		
		bFactory = new ButtonFactory();
		int b = bFactory.createButton(new CommandGetItemValues(values, typeIDs, systemID), new Vector2(10, 10), new Vector2(80, 20), new FontID("fonts/computer.ttf", 20), 0x0000FFFF, 0xDDDDDDFF, "SUBMIT");
		
		df = new DecimalFormat("#");
		df.setMaximumFractionDigits(2);
		df.setMinimumIntegerDigits(40);
		
		SkinID skinc24 = new SkinID(new FontID("fonts/computer.ttf", 24));
		SkinID skinc32 = new SkinID(new FontID("fonts/computer.ttf", 32));
		
		// Main Grid \\
		float rowOff = 20;
		for (int i = 0; i < rows; i++) {
			//item input
			sManager.createTextField("Item Input " + i, "", SkinManager.getSkin(skinc24), 10, Gdx.graphics.getHeight() - (30 + rowOff) - (i * 25), 185, 20);
			
			//quantity input
			sManager.createTextField("Quantity Input " + i, "", SkinManager.getSkin(skinc24), 200, Gdx.graphics.getHeight() - (30 + rowOff) - (i * 25), 120, 20);
			
			//ISK/Unit output
			sManager.createLabel("ISK/Unit Output " + i, "0.00", SkinManager.getSkin(skinc24), 325, Gdx.graphics.getHeight() - (30 + rowOff) - (i * 25), 120, 20, Align.left);
			
			//Before Tax output
			sManager.createLabel("Before Tax Output " + i, "0.00", SkinManager.getSkin(skinc24), 450, Gdx.graphics.getHeight() - (30 + rowOff) - (i * 25), 150, 20, Align.left);
			
			//After Tax output
			sManager.createLabel("After Tax Output " + i, "0.00", SkinManager.getSkin(skinc24), 605, Gdx.graphics.getHeight() - (30 + rowOff) - (i * 25), 150, 20, Align.left);
		}
		
		//column headers
		sManager.createLabel("Item Name", "Item Name", SkinManager.getSkin(skinc32), 10, Gdx.graphics.getHeight() - 24, 185, 20);
		sManager.createLabel("Quantity", "Quantity", SkinManager.getSkin(skinc32), 200, Gdx.graphics.getHeight() - 24, 120, 20);
		sManager.createLabel("ISK/Unit", "ISK/Unit", SkinManager.getSkin(skinc32), 325, Gdx.graphics.getHeight() - 24, 120, 20);
		sManager.createLabel("Before Tax", "Before Tax", SkinManager.getSkin(skinc32), 450, Gdx.graphics.getHeight() - 24, 150, 20);
		sManager.createLabel("After Tax", "After Tax", SkinManager.getSkin(skinc32), 605, Gdx.graphics.getHeight() - 24, 150, 20);

		// Tax Input \\
		sManager.createLabel("Sales Tax:", "Sales Tax:", SkinManager.getSkin(skinc24), Gdx.graphics.getWidth() - 170, Gdx.graphics.getHeight() - 30, 85, 19);
		sManager.createTextField("Sales Tax: Input", "0.015", SkinManager.getSkin(skinc24), Gdx.graphics.getWidth() - 80, Gdx.graphics.getHeight() - 30, 70, 20);

		// System Input \\
		sManager.createLabel("System:", "System:", SkinManager.getSkin(skinc24), Gdx.graphics.getWidth() - 155, Gdx.graphics.getHeight() - 55, 70, 19);
		sManager.createTextField("System: Input", "Jita", SkinManager.getSkin(skinc24), Gdx.graphics.getWidth() - 80, Gdx.graphics.getHeight() - 55, 70, 20);
		
		// Grand Total Labels \\
		sManager.createLabel("Grand Totals:", "Grand Totals:", SkinManager.getSkin(skinc24), 335, Gdx.graphics.getHeight() - 650, 110, 20);
		sManager.createLabel("Before Tax Total Output", "0.00", SkinManager.getSkin(skinc24), 450, Gdx.graphics.getHeight() - 650, 150, 20);
		sManager.createLabel("After Tax Total Output", "0.00", SkinManager.getSkin(skinc24), 605, Gdx.graphics.getHeight() - 650, 150, 20);
		
		// TypeID input init text
		systemID.clear();
		systemID.add(0);
		
		typeIDs.clear();
		for(int i = 0; i < 24; i++){
			typeIDs.add(Types.getTypeID(sManager.textFields.get("Item Input " + i).getText()));
		}
		
		bFactory.getButton(b).callCommand();
	}
	
	public void render(){
		sManager.render(sr);
		bFactory.render(sr, batch);
		
		BigDecimal valPerUnit;
		BigInteger quantity;
		BigDecimal total = BigDecimal.ZERO;
		BigDecimal displayTotal;
		
		BigDecimal grandTotalBefore = BigDecimal.ZERO;
		BigDecimal grandTotalAfter = BigDecimal.ZERO;
		
		NumberFormat format = NumberFormat.getInstance();
		format.setMinimumFractionDigits(2);
		format.setMaximumFractionDigits(2);
		//The quick brown fox jumps over the lazy dog.
		for(int i = 0; i < rows; i++){
			//update isk/unit values
			sManager.labels.get("ISK/Unit Output " + i).setText(values.get(i).toString());
			
			//update total values before tax (isk/unit x quantity)
			if(!sManager.textFields.get("Quantity Input " + i).getText().equalsIgnoreCase("")){
				valPerUnit = BigDecimal.valueOf(values.get(i));
				quantity = BigInteger.valueOf(Long.valueOf(sManager.textFields.get("Quantity Input " + i).getText()));
				total = valPerUnit.multiply(new BigDecimal(quantity));
				displayTotal = total.setScale(2, RoundingMode.HALF_EVEN);
				
				grandTotalBefore = grandTotalBefore.add(total);
				
				sManager.labels.get("Before Tax Output " + i).setText(format.format(displayTotal.doubleValue()));
			} else {
				sManager.labels.get("Before Tax Output " + i).setText("0.00");
			}
			
			//render total values after tax ((isk/unit x quantity) - ((isk/unit x quantity) x tax))
			if(sManager.labels.get("After Tax Output " + i).getText().length != 0 && !sManager.textFields.get("Sales Tax: Input").getText().isEmpty() && !sManager.labels.get("Before Tax Output " + i).textEquals("0.00")){
				
				total = total.subtract(total.multiply(BigDecimal.valueOf(Double.valueOf(sManager.textFields.get("Sales Tax: Input").getText()))));
				displayTotal = total.setScale(2, RoundingMode.HALF_EVEN);

				grandTotalAfter = grandTotalAfter.add(total);
				
				sManager.labels.get("After Tax Output " + i).setText(format.format(displayTotal.doubleValue()));
			} else {
				sManager.labels.get("After Tax Output " + i).setText("0.00");
			}
		}
		
		//update grand totals
		BigDecimal grandTotalBeforeDisplay = grandTotalBefore.setScale(2, RoundingMode.HALF_EVEN);
		BigDecimal grandTotalAfterDisplay = grandTotalAfter.setScale(2, RoundingMode.HALF_EVEN);
		
		sManager.labels.get("Before Tax Total Output").setText(format.format(grandTotalBeforeDisplay.doubleValue()));
		sManager.labels.get("After Tax Total Output").setText(format.format(grandTotalAfterDisplay.doubleValue()));
		
		//footer
		Draw.string(batch, "Market Responce Time: " + (bFactory.getButton(0).lastExecuteTime/1000000) + "ms", new Vector2(100, 46), new FontID("fonts/computer.ttf", 18), 0xFFFFFFFF);
		Draw.string(batch, "All market data is retrieved through EVE-Central's Marketstat API.", new Vector2(100, 30), new FontID("fonts/computer.ttf", 22), 0xFFFFFFFF);
		Draw.string(batch, "Notice: Large values will not be 100% accurate. Only use this as a close estimate based on current market values.", new Vector2(100, 18), new FontID("fonts/computer.ttf", 22), 0xFFFFFFFF);
	}
	
	public void update(float delta){
		typeIDs.clear();
		for(int i = 0; i < 24; i++){
			typeIDs.add(Types.getTypeID(sManager.textFields.get("Item Input " + i).getText()));
		}
		
		systemID.clear();
		systemID.add(Systems.getSystemID(sManager.textFields.get("System: Input").getText()));
		
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
