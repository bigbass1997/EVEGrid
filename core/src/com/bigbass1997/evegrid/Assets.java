package com.bigbass1997.evegrid;

import java.util.Hashtable;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

/**
 * Contains and loads all program assets.
 * <p>
 * Assets is the asset management class.
 * Framework inspired by libGDX's own Asset Management system.
 */
//public class Assets {
//	
//	/**
//	 * <ID, FONT>
//	 */
//	private static Hashtable<String, BitmapFont> fonts;
//	
//	/**
//	 * Gets the BitmapFont for use in drawing.
//	 * 
//	 * @param font Font number listed in Assets. (e.g. Assets.FONTSTRUCTION)
//	 * @param size Size number listed in Assets. (e.g. Assets.S12)
//	 * @return BitmapFont
//	 */
//	public static BitmapFont getFont(String id){
//		if(id == null || id.isEmpty() || fonts == null) return null;
//		
//		return fonts.get(id);
//	}
//	
//	public static void addFont(String path, int[] sizes){
//		if(fonts == null) fonts = new Hashtable<String, BitmapFont>();
//		
//		FileHandle file = Gdx.files.internal(path);
//		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(file);
//		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
//		for(int i = 0; i < sizes.length; i++){
//			parameter.size = sizes[i];
//			fonts.put(createFontID(file.name(), parameter.size), generator.generateFont(parameter));
//		}
//		generator.dispose();
//	}
//
//	/**
//	 * 
//	 * @param s
//	 * @param font
//	 * @return width of String s
//	 */
//	public static float getSWidth(String s, BitmapFont font){
//		GlyphLayout layout = new GlyphLayout();
//		layout.setText(font, s);
//		return layout.width;
//	}
//	
//	public static float getSWidth(String s, String fid){
//		GlyphLayout layout = new GlyphLayout();
//		layout.setText(getFont(fid), s);
//		return layout.width;
//	}
//	
//	/**
//	 * 
//	 * @param s
//	 * @param font
//	 * @return height of String s
//	 */
//	public static float getSHeight(String s, BitmapFont font){
//		GlyphLayout layout = new GlyphLayout();
//		layout.setText(font, s);
//		return layout.height;
//	}
//	
//	public static float getSHeight(String s, String fid){
//		GlyphLayout layout = new GlyphLayout();
//		layout.setText(getFont(fid), s);
//		return layout.height;
//	}
//	
//	public static void dispose(){
//		Set<String> keys = fonts.keySet();
//		
//		for(String key : keys){
//			fonts.get(key).dispose();
//		}
//	}
//	
//	private static String createFontID(String name, int size){
//		return name.concat(String.valueOf(size));
//	}
//}
