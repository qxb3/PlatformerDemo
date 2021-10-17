package com.qxbthree.platformerdemo.core.box2d;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.qxbthree.platformerdemo.core.Game;

public class CollisionManager implements ContactListener {
	private Box2DManager box2dManager;

	public CollisionManager(Box2DManager box2dManager) {
		this.box2dManager = box2dManager;
	}

	@Override
	public void beginContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();
		int fixtureBit = fixtureA.getFilterData().categoryBits | fixtureB.getFilterData().categoryBits;

		switch (fixtureBit) {
			case Game.PLAYER_BIT | Game.COIN_BIT:
				if (fixtureA.getFilterData().categoryBits == Game.COIN_BIT)
					box2dManager.getBodiesToDestroy().add(fixtureA.getBody());
				if (fixtureB.getFilterData().categoryBits == Game.COIN_BIT)
					box2dManager.getBodiesToDestroy().add(fixtureB.getBody());

				Game.assets.coinSound.play();
				break;
			default:
				break;
		}
	}

	@Override
	public void endContact(Contact contact) {
	}

	@Override
	public void preSolve(Contact contact, Manifold manifold) {
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
	}
}
