package com.qxbthree.platformerdemo.core.box2d;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class Box2DManager implements Disposable {
	private static float STEP_TIME = 1 / 60f;
	private static float accumulator = 0f;
	
	private World world;
	private Box2DDebugRenderer debugRenderer;
	
	private Array<Body> bodiesToDestroy;
	
	public Box2DManager() {
		world = new World(new Vector2(0f, -9.8f), false);
		world.setContactListener(new CollisionManager(this));
		debugRenderer = new Box2DDebugRenderer();
		
		bodiesToDestroy = new Array<Body>();
	}
	
	public void update(float deltaTime) {
		float frameTime = Math.min(deltaTime, 0.25f);
		accumulator += frameTime;
		if (accumulator >= STEP_TIME) {
			world.step(STEP_TIME, 6, 2);
			accumulator -= STEP_TIME;
			
			//Deleting bodies
			for (Body body : bodiesToDestroy) {
				for (Fixture fixture : body.getFixtureList())
					body.destroyFixture(fixture);
				
				world.destroyBody(body);
				bodiesToDestroy.removeValue(body, true);
			}
		}
	}
	
	public void debug(Matrix4 mat4) {
		debugRenderer.render(world, mat4);
	}
	
	@Override
	public void dispose() {
		world.dispose();
		debugRenderer.dispose();
		bodiesToDestroy.clear();
	}
	
	public World getWorld() {
		return world;
	}
	
	public Box2DDebugRenderer getDebugRenderer() {
		return debugRenderer;
	}
	
	public Array<Body> getBodiesToDestroy() {
		return bodiesToDestroy;
	}
}
