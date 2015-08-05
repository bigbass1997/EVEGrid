package com.bigbass1997.evegrid.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.bigbass1997.evegrid.graphics.fonts.FontID;
import com.bigbass1997.evegrid.graphics.skins.SkinID;
import com.bigbass1997.evegrid.graphics.skins.SkinManager;
import com.bigbass1997.evegrid.market.Types;

public class StateOreBuy extends State {
	
	private Stage stage;
	
	public StateOreBuy(StateManager sm) {
		super(sm, "ORE_BUY");
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		float rowOff = 20;
		for(int i = 0; i < 24; i++){
			TextField tF = new TextField("0", SkinManager.getSkin(new SkinID(new FontID("bin/fonts/computer.ttf", 24))));
			tF.setSize(120, 20);
			tF.setPosition(200, Gdx.graphics.getHeight() - (10 + tF.getHeight() + rowOff) - (i * (tF.getHeight() + 5)));
			stage.addActor(tF);
			
			SelectBox<String> sB = new SelectBox<String>(SkinManager.getSkin(new SkinID(new FontID("bin/fonts/computer.ttf", 24))));
			sB.setSize(185, 20);
			sB.setPosition(10, Gdx.graphics.getHeight() - (10 + sB.getHeight() + rowOff) - (i * (sB.getHeight() + 5)));
			sB.setItems(new String[]{"N/A", "Veldspar", "Concentrated Veldspar"});
			stage.addActor(sB);
		}
		
		Label label = new Label("Ore Name", SkinManager.getSkin(new SkinID(new FontID("bin/fonts/computer.ttf", 32))));
		label.setPosition(10, Gdx.graphics.getHeight() - 24);
		label.setSize(185, 20);
		label.setAlignment(Align.center);
		stage.addActor(label);
		
		label = new Label("Quantity", SkinManager.getSkin(new SkinID(new FontID("bin/fonts/computer.ttf", 32))));
		label.setPosition(200, Gdx.graphics.getHeight() - 24);
		label.setSize(120, 20);
		label.setAlignment(Align.center);
		stage.addActor(label);
	}
	
	public void render(){
		stage.draw();
	}
	
	public void update(float delta){
		stage.act(delta);
	}
}
