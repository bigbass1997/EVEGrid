package com.bigbass1997.evegrid.states;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.bigbass1997.evegrid.commands.CommandGetOreValues;
import com.bigbass1997.evegrid.graphics.Draw;
import com.bigbass1997.evegrid.graphics.fonts.FontID;
import com.bigbass1997.evegrid.graphics.skins.SkinID;
import com.bigbass1997.evegrid.graphics.skins.SkinManager;
import com.bigbass1997.evegrid.objects.ButtonFactory;
import com.bigbass1997.evegrid.objects.StageManager;

public class StateOreBuy extends State {

	private StageManager sManager;
	
	private ArrayList<Float> values;
	private ArrayList<Integer> typeIDs;
	
	private ButtonFactory bFactory;
	
	
	public StateOreBuy(StateManager sm) {
		super(sm, "ORE_BUY");
		
		sManager = new StageManager();
		
		values = new ArrayList<Float>();
		typeIDs = new ArrayList<Integer>();
		
		bFactory = new ButtonFactory();
		bFactory.createButton(new CommandGetOreValues(values, typeIDs), new Vector2(10, 10), new Vector2(80, 20), 0xDDDDDDFF, "SUBMIT");
		
		typeIDs.add(0);
		typeIDs.add(0);
		typeIDs.add(0);
		typeIDs.add(0);
		typeIDs.add(0);
		
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
	}
	
	public void render(){
		sManager.render();
		bFactory.render(sr, batch);
		
		for(int i = 0; i < values.size(); i++){
			Draw.string(batch, "i = " + values.get(i), new Vector2(330, Gdx.graphics.getHeight() - 36 - (i * 25)), new FontID("bin/fonts/computer.ttf", 24), 0xFFFFFFFF);
		}
	}
	
	public void update(float delta){
		sManager.update(delta);
		bFactory.update(delta);
	}
	
	public void dispose(){
		sManager.dispose();
	}
}
