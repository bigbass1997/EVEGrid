package com.bigbass1997.evegrid.objects;

import java.util.ArrayList;

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
	public ArrayList<TextField> textFields; private int tfID = 0;
	public ArrayList<SelectBox<String>> selectBoxes; private int sbID = 0;
	public ArrayList<Label> labels; private int lID = 0;
	
	public StageManager(){
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		textFields = new ArrayList<TextField>();
		selectBoxes = new ArrayList<SelectBox<String>>();
		labels = new ArrayList<Label>();
	}
	
	public int createTextField(String defaultText, Skin skin, Vector2 pos, Vector2 dim){
		TextField tF = new TextField(defaultText, skin);
		tF.setSize(dim.x, dim.y);
		tF.setPosition(pos.x, pos.y);
		textFields.add(tF);
		stage.addActor(tF);
		return tfID++;
	}
	
	public int createSelectBox(Skin skin, Vector2 pos, Vector2 dim, String[] items){
		SelectBox<String> sB = new SelectBox<String>(skin);
		sB.setPosition(pos.x, pos.y);
		sB.setSize(dim.x, dim.y);
		sB.setItems(items);
		selectBoxes.add(sB);
		stage.addActor(sB);
		return sbID++;
	}
	
	public int createLabel(String text, Skin skin, Vector2 pos, Vector2 dim, int alignment){
		Label label = new Label(text, skin);
		label.setPosition(pos.x, pos.y);
		label.setSize(dim.x, dim.y);
		label.setAlignment(alignment);
		labels.add(label);
		stage.addActor(label);
		return lID++;
	}
	public void createLabel(String text, Skin skin, Vector2 pos, Vector2 dim){
		createLabel(text, skin, pos, dim, Align.left);
	}
	
	public void render(ShapeRenderer sr){
		for(Label label : labels){
			float x = label.getX(), y = label.getY(), width = label.getWidth(), height = label.getHeight();
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
