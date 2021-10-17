package com.qxbthree.platformerdemo.core.gameobjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.qxbthree.platformerdemo.core.Game;
import com.qxbthree.platformerdemo.core.utils.AnimationUtil;

public class Coin extends GameObject {
	private Animation<TextureRegion> animation;
	private float stateTime;
	
	public Coin(World world, Vector2 position) {
		super(world, position);
		
		TextureRegion[][] animationRegions = TextureRegion.split(Game.assets.coinStrip, Game.assets.coinStrip.getWidth() / 12, Game.assets.coinStrip.getHeight() / 1);
		animation = AnimationUtil.get(animationRegions, 12, 1, 0, 0, 0.1f);
		
		this.setBounds(0f, 0f, 0.18f, 0.18f);
		this.setOriginCenter();
	}
	
	@Override
	public void createBody() {
		CircleShape shape = new CircleShape();
		shape.setRadius(0.05f);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set(this.position);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.isSensor = true;
		fixtureDef.filter.categoryBits = Game.COIN_BIT;
		fixtureDef.filter.maskBits = Game.PLAYER_BIT;
		
		this.body = this.world.createBody(bodyDef);
		this.body.createFixture(fixtureDef);
		
		shape.dispose();
	}
	
	@Override
	public void update(float deltaTime) {
		stateTime += deltaTime;
		this.setRegion(animation.getKeyFrame(stateTime, true));
		this.setPosition(this.body.getPosition().x - this.getWidth() / 2f, this.body.getPosition().y - this.getHeight() / 2f);
	}
}
