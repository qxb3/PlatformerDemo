package com.qxbthree.platformerdemo.core.gameobjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class GameObject extends Sprite {
	protected World world;
	protected Vector2 position;
	protected Body body;

	public GameObject(World world, Vector2 position) {
		this.world = world;
		this.position = position;
		
		this.createBody();
	}
	
	public abstract void createBody()
	public abstract void update(float deltaTime)

	public Body getBody() {
		return body;
	}
}
