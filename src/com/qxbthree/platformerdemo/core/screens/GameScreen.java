package com.qxbthree.platformerdemo.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.qxbthree.platformerdemo.core.Game;
import com.qxbthree.platformerdemo.core.box2d.Box2DManager;
import com.qxbthree.platformerdemo.core.gameobjects.Coin;
import com.qxbthree.platformerdemo.core.gameobjects.Player;
import com.qxbthree.platformerdemo.core.maps.TiledMapManager;
import com.qxbthree.platformerdemo.core.scenes.HUD;

public class GameScreen extends ScreenAdapter {
	private Game game;
	
	private OrthographicCamera camera;
	private Viewport viewport;
	
	private Box2DManager box2dManager;
	private TiledMapManager tiledmapManager;
	
	private Player player;
	private Array<Coin> coins;
	
	private HUD hud;
	
	public GameScreen(Game game) {
		this.game = game;
		
		camera = new OrthographicCamera();
		camera.position.set(Game.WIDTH / 2f, Game.HEIGHT / 2f, 0f);
		viewport = new ExtendViewport(Game.WIDTH, Game.HEIGHT, camera);
		
		box2dManager = new Box2DManager();
		tiledmapManager = new TiledMapManager(box2dManager.getWorld());
		
		player = new Player(box2dManager.getWorld(), tiledmapManager.getMapObjects().getPlayerSpawn());
		coins = new Array<Coin>();
		
		for (int i = 0; i < tiledmapManager.getMapObjects().getCoins().size; i++) {
			coins.add(new Coin(box2dManager.getWorld(), tiledmapManager.getMapObjects().getCoins().get(i)));
		}
		
		hud = new HUD(player);
	}
	
	@Override
	public void render(float deltaTime) {
		this.update(deltaTime);
		Gdx.gl.glClearColor(0.1f, 0.5f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Game.batch.setProjectionMatrix(camera.combined);
		Game.batch.begin();

		Game.batch.draw(Game.assets.background, camera.position.x - Game.WIDTH / 2f, 0, Game.HEIGHT * 2, tiledmapManager.getMapHeight() / Game.PPM);
		player.draw(Game.batch);
		
		for (Coin coin : coins) {
			coin.draw(Game.batch);
		}
		
		Game.batch.end();
		
		tiledmapManager.render(camera);
		box2dManager.debug(camera.combined);
		hud.render(deltaTime);
	}
	
	private void update(float deltaTime) {
		box2dManager.update(deltaTime);
		updateCamera(deltaTime);
		player.update(deltaTime);
		if (player.getBody().getPosition().y < 0)
			respawn();
		
		for (Coin coin : coins) {
			coin.update(deltaTime);
			if (coin.getBody() == null)
				coins.removeValue(coin, true);
		}
	}
	
	private void updateCamera(float deltaTime) {
		float deltaX = camera.position.x - player.getBody().getPosition().x;
		float deltaY = camera.position.y - player.getBody().getPosition().y;
		float distance = (float) Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
		Vector3 lerp = camera.position.lerp(new Vector3(player.getBody().getPosition().x, player.getBody().getPosition().y, 0f), deltaTime + distance / 3.4f);
		camera.position.x = MathUtils.clamp(lerp.x, camera.viewportWidth / 2, tiledmapManager.getTransformedMapWidth() - camera.viewportWidth / 2);
		camera.position.y = MathUtils.clamp(lerp.y, camera.viewportHeight / 2, tiledmapManager.getTransformedMapHeight() - camera.viewportHeight / 2);
		camera.update();
	}
	
	private void respawn() {
		player.getBody().setTransform(tiledmapManager.getMapObjects().getPlayerSpawn(), 0f);
	}
	
	@Override
	public void resize(int screenWidth, int screenHeight) {
		viewport.update(screenWidth, screenHeight);
		hud.resize(screenWidth, screenHeight);
	}
	
	@Override
	public void dispose() {
		box2dManager.dispose();
		tiledmapManager.dispose();
		hud.dispose();
	}
}
