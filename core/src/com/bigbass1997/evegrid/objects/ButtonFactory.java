package com.bigbass1997.evegrid.objects;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.bigbass1997.evegrid.commands.Command;
import com.bigbass1997.evegrid.graphics.fonts.FontID;

public class ButtonFactory {
	
	private ArrayList<Button> buttonList;
	private int id;
	
	public ButtonFactory(){
		buttonList = new ArrayList<Button>();
		id = -1;
	}
	
	public int createButton(Command command, Vector2 pos, Vector2 dim, FontID fontID, int tColor, int bColor, String text, boolean activatedWithEnter){
		Button b = new Button(command, pos, dim, fontID, tColor, bColor, text, activatedWithEnter);
		buttonList.add(b);
		
		id++;
		return id;
	}
	
	public int createButton(Command command, Vector2 pos, Vector2 dim, FontID fontID, int tColor, int bColor, String text){
		return this.createButton(command, pos, dim, fontID, tColor, bColor, text, false);
	}
	
	public void render(ShapeRenderer sr, SpriteBatch batch){
		for(Button b : buttonList){
			b.render(sr, batch);
		}
	}
	
	public void update(float delta){
		for(Button b : buttonList){
			b.update(delta);
		}
	}
	
	public Button getButton(int id){
		return buttonList.get(id);
	}
}
