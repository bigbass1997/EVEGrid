package com.bigbass1997.evegrid.objects;

import java.util.Hashtable;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.bigbass1997.evegrid.graphics.Draw;

public class StageManager {
	
	private Stage stage;
	
	public Hashtable<String, TextField> textFields;
	public Hashtable<String, SelectBox<String>> selectBoxes;
	public Hashtable<String, Label> labels;
	
	public StageManager(){
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		textFields = new Hashtable<String, TextField>();
		selectBoxes = new Hashtable<String, SelectBox<String>>();
		labels = new Hashtable<String, Label>();
	}
	
	public void createTextField(Object id, String defaultText, Skin skin, Vector2 pos, Vector2 dim){
		TextField tF = new TextField(defaultText, skin);
		tF.setSize(dim.x, dim.y);
		tF.setPosition(pos.x, pos.y);
		textFields.put(id.toString(), tF);
		stage.addActor(tF);
	}
	public void createTextField(Object id, String defaultText, Skin skin, float x, float y, float width, float height){
		this.createTextField(id, defaultText, skin, new Vector2(x, y), new Vector2(width, height));
	}
	
	public void createSelectBox(Object id, Skin skin, Vector2 pos, Vector2 dim, String[] items){
		SelectBox<String> sB = new SelectBox<String>(skin);
		sB.setPosition(pos.x, pos.y);
		sB.setSize(dim.x, dim.y);
		sB.setItems(items);
		selectBoxes.put(id.toString(), sB);
		stage.addActor(sB);
	}
	public void createSelectBox(Object id, Skin skin, float x, float y, float width, float height, String[] items){
		this.createSelectBox(id, skin, new Vector2(x, y), new Vector2(width, height), items);
	}
	
	public void createLabel(Object id, String text, Skin skin, Vector2 pos, Vector2 dim, int alignment){
		Label label = new Label(text, skin);
		label.setPosition(pos.x, pos.y);
		label.setSize(dim.x, dim.y);
		label.setAlignment(alignment);
		labels.put(id.toString(), label);
		stage.addActor(label);
	}
	public void createLabel(Object id, String text, Skin skin, float x, float y, float width, float height, int alignment){
		this.createLabel(id, text, skin, new Vector2(x, y), new Vector2(width, height), alignment);
	}
	public void createLabel(Object id, String text, Skin skin, Vector2 pos, Vector2 dim){
		this.createLabel(id, text, skin, pos, dim, Align.center);
	}
	public void createLabel(Object id, String text, Skin skin, float x, float y, float width, float height){
		this.createLabel(id, text, skin, new Vector2(x, y), new Vector2(width, height));
	}
	
	public void render(ShapeRenderer sr){
		for(Entry<String, Label> entry : labels.entrySet()){
			float x = entry.getValue().getX(), y = entry.getValue().getY(), width = entry.getValue().getWidth(), height = entry.getValue().getHeight();
			Draw.rect(sr, x, y, width, height, ShapeType.Filled, 0x3D3D3DFF);
			Draw.boarder(sr, x, y, width, height, 1, 0xFFFFFFFF);
		}
		stage.draw();
	}
	
	public void update(float delta){
		stage.act(delta);
	}
	
	public void dispose(){
		stage.dispose();
	}
}
