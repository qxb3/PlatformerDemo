package com.qxbthree.platformerdemo.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable {
	public Texture background;
	public Texture playerIdleStrip;
	public Texture playerRunStrip;
	public TextureRegion playerJump;
	public Texture coinStrip;
	public Sound coinSound;
	
	public Skin skin;
	
	public Assets() {
		background = new Texture("sprites/others/background.png");
		playerIdleStrip = new Texture("sprites/player/playerIdle_strip.png");
		playerRunStrip = new Texture("sprites/player/playerRun_strip.png");
		playerJump = new TextureRegion(new Texture("sprites/player/playerJump.png"));
		coinStrip = new Texture("sprites/others/coin_strip.png");
		coinSound = Gdx.audio.newSound(Gdx.files.internal("sfx/coin.wav"));
		
		skin = new Skin();
		skin.add("touchpad_background", new Texture("scenes/touchpad_background.png"));
		skin.add("touchpad_knob", new Texture("scenes/touchpad_knob.png"));
		skin.add("jumpButton", new Texture("scenes/jumpButton.png"));
		skin.add("jumpButton_touched", new Texture("scenes/jumpButton_touched.png"));
	}
	
	@Override
	public void dispose() {
		background.dispose();
		playerIdleStrip.dispose();
		playerRunStrip.dispose();
		playerJump.getTexture().dispose();
		coinStrip.dispose();
		coinSound.dispose();
		skin.dispose();
	}
}
