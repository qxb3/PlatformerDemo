package com.qxbthree.platformerdemo.core.maps;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.qxbthree.platformerdemo.core.Game;

public class TiledMapObjects {
	private TiledMap map;

	public TiledMapObjects(TiledMap map, World world) {
		this.map = map;
		
		this.getGround(world);
	}
	
	private void getGround(World world) {
		for (MapObject object : map.getLayers().get("ground").getObjects()) {
			float[] vertices = ((PolygonMapObject) object).getPolygon().getTransformedVertices();
			float[] worldVertices = new float[vertices.length];
			for (int i = 0; i < vertices.length; i++)
				worldVertices[i] = vertices[i] / Game.PPM;

			PolygonShape shape = new PolygonShape();
			shape.set(worldVertices);

			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyDef.BodyType.StaticBody;

			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = shape;
			fixtureDef.density = 1000f;
			fixtureDef.filter.categoryBits = Game.GROUND_BIT;
			fixtureDef.filter.maskBits = Game.PLAYER_BIT;

			Body body = world.createBody(bodyDef);
			body.createFixture(fixtureDef);
			shape.dispose();
		}
	}
	
	public Vector2 getPlayerSpawn() {
		Vector2 position = new Vector2();
		for (MapObject object : map.getLayers().get("spawn").getObjects()) {
			Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
			position.set(rectangle.x / Game.PPM, rectangle.y / Game.PPM);
		}
		return position;
	}
	
	public Array<Vector2> getCoins() {
		Array<Vector2> positions = new Array<Vector2>();
		for (MapObject object : map.getLayers().get("coins").getObjects()) {
			Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
			positions.add(new Vector2(rectangle.x / Game.PPM, rectangle.y / Game.PPM));
		}
		return positions;
	}
}
