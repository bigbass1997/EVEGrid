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
		int b = bFactory.createButton(new CommandGetItemValues(values, typeIDs, systemID), new Vector2(10, 10), new Vector2(80, 20), new FontID("bin/fonts/computer.ttf", 20), 0x0000FFFF, 0xDDDDDDFF, "SUBMIT");
		
		df = new DecimalFormat("#");
		df.setMaximumFractionDigits(2);
		df.setMinimumIntegerDigits(40);
		
		// Main Grid \\
		float rowOff = 20;
		for(int i = 0; i < 24; i++){
			sManager.createTextField(
					"0",
					SkinManager.getSkin(new SkinID(new FontID("bin/fonts/computer.ttf", 24))),
					new Vector2(200, Gdx.graphics.getHeight() - (30 + rowOff) - (i * 25)),
					new Vector2(120, 20)
			);
		}
		
		for(int i = 0; i < 24; i++){
			sManager.createTextField(
					"",
					SkinManager.getSkin(new SkinID(new FontID("bin/fonts/computer.ttf", 24))),
					new Vector2(10, Gdx.graphics.getHeight() - (30 + rowOff) - (i * 25)),
					new Vector2(185, 20)
			);
		}
		
		sManager.createLabel(
				"Item Name",
				SkinManager.getSkin(new SkinID(new FontID("bin/fonts/computer.ttf", 32))),
				new Vector2(10, Gdx.graphics.getHeight() - 24),
				new Vector2(185, 20),
				Align.center
		);
		
		sManager.createLabel(
				"Quantity",
				SkinManager.getSkin(new SkinID(new FontID("bin/fonts/computer.ttf", 32))),
				new Vector2(200, Gdx.graphics.getHeight() - 24),
				new Vector2(120, 20),
				Align.center
		);
		
		sManager.createLabel(
				"ISK/Unit",
				SkinManager.getSkin(new SkinID(new FontID("bin/fonts/computer.ttf", 32))),
				new Vector2(325, Gdx.graphics.getHeight() - 24),
				new Vector2(120, 20),
				Align.center
		);
		
		sManager.createLabel(
				"Before Tax",
				SkinManager.getSkin(new SkinID(new FontID("bin/fonts/computer.ttf", 32))),
				new Vector2(450, Gdx.graphics.getHeight() - 24),
				new Vector2(150, 20),
				Align.center
		);

		sManager.createLabel(
				"After Tax",
				SkinManager.getSkin(new SkinID(new FontID("bin/fonts/computer.ttf", 32))),
				new Vector2(605, Gdx.graphics.getHeight() - 24),
				new Vector2(150, 20),
				Align.center
		);
		
		// Tax Input \\
		sManager.createTextField(
				"0.015",
				SkinManager.getSkin(new SkinID(new FontID("bin/fonts/computer.ttf", 24))),
				new Vector2(Gdx.graphics.getWidth() - 80, Gdx.graphics.getHeight() - 30),
				new Vector2(70, 20)
		);
		
		sManager.createLabel(
				"Sales Tax:",
				SkinManager.getSkin(new SkinID(new FontID("bin/fonts/computer.ttf", 24))),
				new Vector2(Gdx.graphics.getWidth() - 170, Gdx.graphics.getHeight() - 30),
				new Vector2(85, 19),
				Align.center
		);
		
		// System Input \\
		sManager.createTextField(
				"Jita",
				SkinManager.getSkin(new SkinID(new FontID("bin/fonts/computer.ttf", 24))),
				new Vector2(Gdx.graphics.getWidth() - 80, Gdx.graphics.getHeight() - 55),
				new Vector2(70, 20)
		);
		
		sManager.createLabel(
				"System:",
				SkinManager.getSkin(new SkinID(new FontID("bin/fonts/computer.ttf", 24))),
				new Vector2(Gdx.graphics.getWidth() - 155, Gdx.graphics.getHeight() - 55),
				new Vector2(70, 19),
				Align.center
		);
		
		// TypeID input init text
		systemID.clear();
		systemID.add(0);
		
		typeIDs.clear();
		for(int i = 0; i < 24; i++){
			typeIDs.add(Types.getTypeID(sManager.textFields.get(24 + i).getText()));
		}
		
		bFactory.getButton(b).callCommand();
	}
	
	public void render(){
		sManager.render(sr);
		bFactory.render(sr, batch);
		
		Vector2 pos = new Vector2();
		float width, height;
		
		BigDecimal valPerUnit;
		BigInteger quantity;
		BigDecimal total = null;
		BigDecimal displayTotal;
		
		BigDecimal grandTotalBefore = BigDecimal.ZERO;
		BigDecimal grandTotalAfter = BigDecimal.ZERO;
		
		NumberFormat format = NumberFormat.getInstance();
		format.setMinimumFractionDigits(2);
		format.setMaximumFractionDigits(2);
		
		for(int i = 0; i < values.size(); i++){
			pos = new Vector2(330, Gdx.graphics.getHeight() - 34 - (i * 25));
			width = sManager.labels.get(2).getWidth();
			height = sManager.labels.get(2).getHeight();
			
			//render isk/unit values
			Draw.rect(sr, pos.x - 5, pos.y + 4 - height, width, height - 1, ShapeType.Filled, 0x3D3D3DFF);
			Draw.boarder(sr, pos.x - 5, pos.y + 4 - height, width, height - 1, 1, 0xFFFFFFFF);
			Draw.string(batch, values.get(i), pos.sub(0, 1), new FontID("bin/fonts/computer.ttf", 24), 0xFFFFFFFF);
			
			pos = new Vector2(455, Gdx.graphics.getHeight() - 34 - (i * 25));
			width = sManager.labels.get(3).getWidth();
			height = sManager.labels.get(3).getHeight();
			
			//render total values before tax (isk/unit x quantity)
			Draw.rect(sr, pos.x - 5, pos.y + 4 - height, width, height - 1, ShapeType.Filled, 0x3D3D3DFF);
			Draw.boarder(sr, pos.x - 5, pos.y + 4 - height, width, height - 1, 1, 0xFFFFFFFF);
			if(!sManager.textFields.get(i).getText().equalsIgnoreCase("")){
				valPerUnit = BigDecimal.valueOf(values.get(i));
				quantity = BigInteger.valueOf(Long.valueOf(sManager.textFields.get(i).getText()));
				total = valPerUnit.multiply(new BigDecimal(quantity));
				displayTotal = total.setScale(2, RoundingMode.HALF_EVEN);
				
				grandTotalBefore = grandTotalBefore.add(total);
				
				Draw.string(batch, format.format(displayTotal.doubleValue()), pos.sub(0, 1), new FontID("bin/fonts/computer.ttf", 24), 0xFFFFFFFF);
			} else {
				Draw.string(batch, "0.00", pos.sub(0, 1), new FontID("bin/fonts/computer.ttf", 24), 0xFFFFFFFF);
			}
			
			//render total values after tax ((isk/unit x quantity) - ((isk/unit x quantity) x tax))
			pos = new Vector2(610, Gdx.graphics.getHeight() - 34 - (i * 25));
			Draw.rect(sr, pos.x - 5, pos.y + 4 - height, width, height - 1, ShapeType.Filled, 0x3D3D3DFF);
			Draw.boarder(sr, pos.x - 5, pos.y + 4 - height, width, height - 1, 1, 0xFFFFFFFF);
			if(!sManager.textFields.get(i).getText().isEmpty() && !sManager.textFields.get(24).getText().isEmpty()){
				total = total.subtract(total.multiply(BigDecimal.valueOf(Double.valueOf(sManager.textFields.get(48).getText()))));
				displayTotal = total.setScale(2, RoundingMode.HALF_EVEN);

				grandTotalAfter = grandTotalAfter.add(total);
				
				Draw.string(batch, format.format(displayTotal.doubleValue()), pos.sub(0, 1), new FontID("bin/fonts/computer.ttf", 24), 0xFFFFFFFF);
			} else {
				Draw.string(batch, "0.00", pos.sub(0, 1), new FontID("bin/fonts/computer.ttf", 24), 0xFFFFFFFF);
			}
		}
		
		//render grand totals
		BigDecimal grandTotalBeforeDisplay = grandTotalBefore.setScale(2, RoundingMode.HALF_EVEN);
		BigDecimal grandTotalAfterDisplay = grandTotalAfter.setScale(2, RoundingMode.HALF_EVEN);
		
		Draw.fakeLabel(sr, batch, new Vector2(340, 165), 110f, 20f, "Grand Totals:", new FontID("bin/fonts/computer.ttf", 24));
		Draw.fakeLabel(sr, batch, new Vector2(455, 165), 150f, 20f, format.format(grandTotalBeforeDisplay.doubleValue()), new FontID("bin/fonts/computer.ttf", 24));
		Draw.fakeLabel(sr, batch, new Vector2(610, 165), 150f, 20f, format.format(grandTotalAfterDisplay.doubleValue()), new FontID("bin/fonts/computer.ttf", 24));
		
		//footer
		Draw.string(batch, "Market Responce Time: " + (bFactory.getButton(0).lastExecuteTime/1000000) + "ms", new Vector2(100, 46), new FontID("bin/fonts/computer.ttf", 18), 0xFFFFFFFF);
		Draw.string(batch, "All market data is retrieved through EVE-Central's Marketstat API.", new Vector2(100, 30), new FontID("bin/fonts/computer.ttf", 22), 0xFFFFFFFF);
		Draw.string(batch, "Notice: Large values will not be 100% accurate. Only use this as a close estimate based on current market values.", new Vector2(100, 18), new FontID("bin/fonts/computer.ttf", 22), 0xFFFFFFFF);
	}
	
	public void update(float delta){
		typeIDs.clear();
		for(int i = 0; i < 24; i++){
			typeIDs.add(Types.getTypeID(sManager.textFields.get(24 + i).getText()));
		}
		
		systemID.clear();
		systemID.add(Systems.getSystemID(sManager.textFields.get(49).getText()));
		
		sManager.update(delta);
		bFactory.update(delta);
		
		if(!initComplete){
			bFactory.createButton(new CommandChangeState(sm, StateMainMenu.class), new Vector2(10, 35), new Vector2(80, 20), new FontID("bin/fonts/computer.ttf", 20), 0x0000FFFF, 0xDDDDDDFF, "MAIN MENU");
			initComplete = true;
		}
	}
	
	public void dispose(){
		sManager.dispose();
	}
}
