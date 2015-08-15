package com.bigbass1997.evegrid.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.bigbass1997.evegrid.commands.Command;
import com.bigbass1997.evegrid.graphics.Draw;

public class Button {
	
	private Command command;
	public Vector2 pos, dim;
	private int color;
	private String text;
	private boolean ready = true;
	
	public Button(Command command, Vector2 pos, Vector2 dim, int color, String text){
		this.command = command;
		this.pos = pos;
		this.dim = dim;
		this.color = color;
		this.text = text;
	}
	
	public void render(ShapeRenderer sr, SpriteBatch batch){
		Draw.rect(sr, pos.x, pos.y, dim.x, dim.y, ShapeType.Filled, color);
	}
	
	public void update(float delta){
		Input input = Gdx.input;
		float mx = input.getX();
		float my = -input.getY() + Gdx.graphics.getHeight();
		
		if(!ready && !input.isButtonPressed(Buttons.LEFT)) ready = true;
		
		if(ready){
			if(input.isButtonPressed(Buttons.LEFT) && mx >= pos.x && mx <= pos.x + dim.x && my >= pos.y && my <= pos.y + dim.y){
				callCommand();
				ready = false;
			}
		}
	}
	
	public void callCommand(){
		command.execute();
	}
}
