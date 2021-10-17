package com.qxbthree.platformerdemo.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.qxbthree.platformerdemo.core.screens.GameScreen;

public class Game extends Game {
	public static final float WIDTH = 3.80f;
	public static final float HEIGHT = 2.0f;
	public static final float PPM = 100f;
	
	public static final short GROUND_BIT = 1;
	public static final short PLAYER_BIT = 2;
	public static final short COIN_BIT = 4;
	
	public static SpriteBatch batch;
	public static Assets assets;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		assets = new Assets();
		
		this.setScreen(new GameScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
		assets.dispose();
	}
}
