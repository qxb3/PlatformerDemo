package com.qxbthree.platformerdemo.core.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.qxbthree.platformerdemo.core.gameobjects.Player;

public class HUD implements Disposable {
	private OrthographicCamera camera;
	private Viewport viewport;
	private Stage stage;
	
	public HUD(Player player) {
		camera = new OrthographicCamera();
		viewport = new ExtendViewport(264, 132, camera);
		stage = new Stage(viewport);
		
		Table table = new Table();
		table.setFillParent(true);
		table.pad(8);
		
		table.add(player.getMovementTouchpad()).size(42, 42).bottom().left().expand();
		table.add(player.getJumpButton()).size(42, 42).bottom().right().expand();
		
		stage.addActor(table);
		Gdx.input.setInputProcessor(stage);
	}
	
	public void render(float deltaTime) {
		stage.act(deltaTime);
		stage.draw();
	}
	
	public void resize(int screenWidth, int screenHeight) {
		viewport.update(screenWidth, screenHeight);
	}
	
	@Override
	public void dispose() {
		stage.dispose();
	}
}
