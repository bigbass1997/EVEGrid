package com.bigbass1997.evegrid.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.bigbass1997.evegrid.graphics.fonts.FontID;
import com.bigbass1997.evegrid.graphics.fonts.FontManager;

/**
 * Contains a set of functions that will draw various
 * textures and colors to the screen.
 * <p>
 * All functions are static so no need to create a Draw object.
 * <p>
 * 
 * Utilizes the ShapeRenderer provided by LibGDX for quick rendering of simple shapes.
 * 
 * @see ShapeRenderer
 */
public class Draw {
	
	/**
	 * Draws a standard rectangle.
	 * 
	 * @param sr ShapeRenderer
	 * @param x x-axis position from the origin
	 * @param y y-axis position from the origin
	 * @param width width
	 * @param height height
	 * @param type ShapeType.Filled or ShapeType.Line
	 * @param color RGBA 0x + Hexadecimal code for each color. 0x000000FF would be black with 100% opacity
	 * 
	 * @see ShapeType
	 * @see Color
	 * @see Viewpoint
	 */
	public static void rect(ShapeRenderer sr, float x, float y, float width, float height, ShapeType type, int color){
		Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
		sr.begin(type);
		sr.setColor(new Color(color));
		if(type == ShapeType.Line){
			sr.line(x, y, x + width, y);
			sr.line(x + width, y, x + width, y + height);
			sr.line(x + width + 1.5f, y + height, x, y + height);
			sr.line(x, y + height, x, y);
		} else {
			sr.rect(x, y, width, height);
		}
		sr.end();
		Gdx.graphics.getGL20().glDisable(GL20.GL_BLEND);
	}
	
	/**
	 * Draws a string as text.
	 * 
	 * @param batch
	 * @param s String
	 * @param x x-axis position from the origin
	 * @param y y-axis position from the origin
	 * @param font Font
	 * @param color RGBA 0x + Hexadecimal code for each color. 0x000000FF would be black with 100% opacity
	 * 
	 * @see Color
	 */
	public static void string(SpriteBatch batch, Object s, Vector2 pos, FontID fontid, int color){
		BitmapFont font = FontManager.getFont(fontid).font;
		font.setColor(Color.WHITE); //Resets color
		font.setColor(new Color(color));
		batch.begin();
		font.draw(batch, s.toString(), pos.x, pos.y);
		batch.end();
	}
	
	public static void boarder(ShapeRenderer sr, float x, float y, float width, float height, int thickness, int color){
		for(int i = 0; i < thickness; i++){
			rect(sr, x + i, y + i, width - (i * 2), height - (i * 2), ShapeType.Line, color);
		}
	}
	
	public static void line(ShapeRenderer sr, float x1, float y1, float x2, float y2, int color){
		Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
		sr.begin(ShapeType.Filled);
		sr.setColor(new Color(color));
		sr.rectLine(x1, y1, x2, y2, 1);
		sr.end();
		Gdx.graphics.getGL20().glDisable(GL20.GL_BLEND);
	}
	
	public static void dot(ShapeRenderer sr, float x, float y, int color){
		rect(sr, x, y, 4.0f, 4.0f, ShapeType.Filled, color);
	}
	
	public static void fakeLabel(ShapeRenderer sr, SpriteBatch batch, Vector2 pos, float width, float height, Object text, FontID fontID){
		Draw.rect(sr, pos.x - 5, pos.y + 4 - height, width, height - 1, ShapeType.Filled, 0x3D3D3DFF);
		Draw.boarder(sr, pos.x - 5, pos.y + 4 - height, width, height - 1, 1, 0xFFFFFFFF);
		Draw.string(batch, text, pos.sub(0, 1), fontID, 0xFFFFFFFF);
	}
}
