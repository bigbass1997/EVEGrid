package com.bigbass1997.evegrid.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.bigbass1997.evegrid.commands.Command;
import com.bigbass1997.evegrid.graphics.Draw;
import com.bigbass1997.evegrid.graphics.fonts.FontID;
import com.bigbass1997.evegrid.graphics.fonts.FontManager;

public class Button {
	
	private Command command;
	public Vector2 pos, dim;
	private FontID fontID;
	private int tColor, bColor;
	private String text;
	private boolean ready = true;
	private boolean activatedWithEnter;
	
	private GlyphLayout layout;

	public long lastExecuteTime = 0;
	
	public Button(Command command, Vector2 pos, Vector2 dim, FontID fontID, int tColor, int bColor, String text, boolean activatedWithEnter){
		this.command = command;
		this.pos = pos;
		this.dim = dim;
		this.fontID = fontID;
		this.tColor = tColor;
		this.bColor = bColor;
		this.text = text;
		this.activatedWithEnter = activatedWithEnter;
		
		layout = new GlyphLayout(FontManager.getFont(fontID).font, text);
	}
	
	public void render(ShapeRenderer sr, SpriteBatch batch){
		Draw.rect(sr, pos.x, pos.y, dim.x, dim.y, ShapeType.Filled, bColor);
		Draw.string(batch, text, new Vector2(pos.x + (dim.x / 2) - (layout.width / 2), pos.y + (dim.y / 2) - (layout.height / 2) + layout.height), fontID, tColor);
	}
	
	public void update(float delta){
		Input input = Gdx.input;
		float mx = input.getX();
		float my = -input.getY() + Gdx.graphics.getHeight();
		
		if(!ready && !input.isButtonPressed(Buttons.LEFT) && (input.isKeyPressed(Keys.ENTER) && activatedWithEnter)) ready = true;
		
		if(ready){
			if((input.isButtonPressed(Buttons.LEFT) && mx >= pos.x && mx <= pos.x + dim.x && my >= pos.y && my <= pos.y + dim.y) ||
					(input.isKeyPressed(Keys.ENTER) && activatedWithEnter)){
				callCommand();
				ready = false;
			}
		}
	}
	
	public void callCommand(){
		long startTime = System.nanoTime();
		command.execute();
		lastExecuteTime = System.nanoTime() - startTime;
	}
}
