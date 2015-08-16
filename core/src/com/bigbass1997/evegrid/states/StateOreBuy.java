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
import com.bigbass1997.evegrid.commands.CommandGetOreValues;
import com.bigbass1997.evegrid.graphics.Draw;
import com.bigbass1997.evegrid.graphics.fonts.FontID;
import com.bigbass1997.evegrid.graphics.skins.SkinID;
import com.bigbass1997.evegrid.graphics.skins.SkinManager;
import com.bigbass1997.evegrid.market.Types;
import com.bigbass1997.evegrid.objects.ButtonFactory;
import com.bigbass1997.evegrid.objects.StageManager;

public class StateOreBuy extends State {

	private StageManager sManager;
	
	private ArrayList<Float> values;
	private ArrayList<Integer> typeIDs;
	
	private ButtonFactory bFactory;
	
	private DecimalFormat df;
	
	public StateOreBuy(StateManager sm) {
		super(sm, "ORE_BUY");
		
		sManager = new StageManager();
		
		values = new ArrayList<Float>();
		typeIDs = new ArrayList<Integer>();
		
		bFactory = new ButtonFactory();
		int b = bFactory.createButton(new CommandGetOreValues(values, typeIDs), new Vector2(10, 10), new Vector2(80, 20), 0xDDDDDDFF, "SUBMIT");
		
		df = new DecimalFormat("#");
		df.setMaximumFractionDigits(2);
		df.setMinimumIntegerDigits(40);
		
		float rowOff = 20;
		for(int i = 0; i < 24; i++){
			sManager.createTextField(
					"0",
					SkinManager.getSkin(new SkinID(new FontID("bin/fonts/computer.ttf", 24))),
					new Vector2(200, Gdx.graphics.getHeight() - (30 + rowOff) - (i * 25)),
					new Vector2(120, 20)
			);
			
			sManager.createSelectBox(
					SkinManager.getSkin(new SkinID(new FontID("bin/fonts/computer.ttf", 24))),
					new Vector2(10, Gdx.graphics.getHeight() - (30 + rowOff) - (i * 25)),
					new Vector2(185, 20),
					new String[]{"N/A", "Veldspar", "Concentrated Veldspar"}
			);
		}
		
		sManager.createLabel(
				"Ore Name",
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
				"Total",
				SkinManager.getSkin(new SkinID(new FontID("bin/fonts/computer.ttf", 32))),
				new Vector2(450, Gdx.graphics.getHeight() - 24),
				new Vector2(150, 20),
				Align.center
		);
		
		typeIDs.clear();
		for(int i = 0; i < 24; i++){
			typeIDs.add(Types.getTypeID(sManager.selectBoxes.get(i).getSelected()));
		}
		bFactory.getButton(b).callCommand();
		//System.out.println(market.getMarketStat(OrderType.BUY, Types.getTypeID("Veldspar"), 30000142));
	}
	
	public void render(){
		sManager.render(sr);
		bFactory.render(sr, batch);
		
		typeIDs.clear();
		for(int i = 0; i < 24; i++){
			typeIDs.add(Types.getTypeID(sManager.selectBoxes.get(i).getSelected()));
		}
		
		Vector2 pos = new Vector2();
		float width, height;
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
			
			//render total values (isk/unit x quantity)
			Draw.rect(sr, pos.x - 5, pos.y + 4 - height, width, height - 1, ShapeType.Filled, 0x3D3D3DFF);
			Draw.boarder(sr, pos.x - 5, pos.y + 4 - height, width, height - 1, 1, 0xFFFFFFFF);
			if(!sManager.textFields.get(i).getText().equalsIgnoreCase("")){
				BigDecimal valPerUnit = BigDecimal.valueOf(values.get(i));
				BigInteger quantity = BigInteger.valueOf(Long.valueOf(sManager.textFields.get(i).getText()));
				BigDecimal total = valPerUnit.multiply(new BigDecimal(quantity));
				BigDecimal displayTotal = total.setScale(2, RoundingMode.HALF_EVEN);
				
				NumberFormat format = NumberFormat.getInstance();
				format.setMinimumFractionDigits(2);
				format.setMaximumFractionDigits(2);
				
				Draw.string(batch, format.format(displayTotal.doubleValue()), pos.sub(0, 1), new FontID("bin/fonts/computer.ttf", 24), 0xFFFFFFFF);
			} else {
				Draw.string(batch, "0.00", pos.sub(0, 1), new FontID("bin/fonts/computer.ttf", 24), 0xFFFFFFFF);
			}
		}
		
		Draw.string(batch, "Notice: Large values will not be 100% accurate. Only use this as a close estimate based on current market values.", new Vector2(10, 100), new FontID("bin/fonts/computer.ttf", 22), 0xFFFFFFFF);
		
		Draw.string(batch, "Market Responce Time: " + (bFactory.getButton(0).lastExecuteTime/1000000) + "ms", new Vector2(10, 50), new FontID("bin/fonts/computer.ttf", 20), 0xFFFFFFFF);
	}
	
	public void update(float delta){
		sManager.update(delta);
		bFactory.update(delta);
	}
	
	public void dispose(){
		sManager.dispose();
	}
}
