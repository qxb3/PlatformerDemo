package com.qxbthree.platformerdemo.core.maps;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.qxbthree.platformerdemo.core.Game;

public class TiledMapManager implements Disposable {
	private TiledMap map;
	private TiledMapObjects mapObjects;
	private OrthogonalTiledMapRenderer mapRenderer;
	
	private int tileWidth, tileHeight;
	private int mapWidth, mapHeight;
	private float transformedMapWidth, transformedMapHeight;
	
	public TiledMapManager(World world) {
		map = new TmxMapLoader().load("maps/Map01.tmx");
		mapObjects = new TiledMapObjects(map, world);
		mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / Game.PPM);
		
		MapProperties properties = map.getProperties();
		tileWidth = properties.get("tilewidth", Integer.class);
		tileHeight = properties.get("tileheight", Integer.class);
		mapWidth = tileWidth * properties.get("width", Integer.class);
		mapHeight = tileHeight * properties.get("height", Integer.class);
		transformedMapWidth = mapWidth / Game.PPM;
		transformedMapHeight = mapHeight / Game.PPM;
	}
	
	public void render(OrthographicCamera camera) {
		mapRenderer.setView(camera);
		mapRenderer.render();
	}
	
	@Override
	public void dispose() {
		map.dispose();
		mapRenderer.dispose();
	}
	
	public TiledMapObjects getMapObjects() {
		return mapObjects;
	}
	
	public int getTileWidth() {
		return tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}

	public int getMapWidth() {
		return mapWidth;
	}

	public int getMapHeight() {
		return mapHeight;
	}

	public float getTransformedMapWidth() {
		return transformedMapWidth;
	}

	public float getTransformedMapHeight() {
		return transformedMapHeight;
	}
}
