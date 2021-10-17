package com.qxbthree.platformerdemo.core.gameobjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.qxbthree.platformerdemo.core.Game;
import com.qxbthree.platformerdemo.core.scenes.uis.ImageButton;
import com.qxbthree.platformerdemo.core.scenes.uis.Touchpad;
import com.qxbthree.platformerdemo.core.utils.AnimationUtil;

public class Player extends GameObject {
	private Touchpad movementTouchpad;
	private ImageButton jumpButton;
	
	private Animation<TextureRegion> idleAnimation;
	private Animation<TextureRegion> runAnimation;
	
	private State state;
	private float stateTime;
	
	public Player(World world, Vector2 position) {
		super(world, position);
		
		movementTouchpad = new Touchpad(0, Game.assets.skin.getDrawable("touchpad_background"), Game.assets.skin.getDrawable("touchpad_knob"));
		jumpButton = new ImageButton(Game.assets.skin.getDrawable("jumpButton"), Game.assets.skin.getDrawable("jumpButton_touched"));
		jumpButton.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				jumpButton.isTouched = true;
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				jumpButton.isTouched = false;
			}
		});
		
		TextureRegion[][] idleRegions = TextureRegion.split(Game.assets.playerIdleStrip, Game.assets.playerIdleStrip.getWidth() / 4, Game.assets.playerIdleStrip.getHeight() / 1);
		TextureRegion[][] runRegions = TextureRegion.split(Game.assets.playerRunStrip, Game.assets.playerRunStrip.getWidth() / 7, Game.assets.playerRunStrip.getHeight() / 1);
		
		idleAnimation = AnimationUtil.get(idleRegions, 4, 1, 0, 0, 0.1f);
		runAnimation = AnimationUtil.get(runRegions, 7, 1, 0, 0, 0.1f);
		state = State.IDLE;
		
		this.setRegion(Game.assets.playerJump);
		this.setBounds(0f, 0f, 0.27f, 0.27f);
		this.setOriginCenter();
	}
	
	@Override
	public void createBody() {
		CircleShape shape = new CircleShape();
		shape.setRadius(0.1f);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(this.position);
		bodyDef.fixedRotation = true;
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1f;
		fixtureDef.filter.categoryBits = Game.PLAYER_BIT;
		fixtureDef.filter.maskBits = Game.GROUND_BIT | Game.COIN_BIT;
		
		this.body = this.world.createBody(bodyDef);
		this.body.createFixture(fixtureDef);
		
		shape.dispose();
	}
	
	@Override
	public void update(float deltaTime) {
		movement();
		getAnimation(deltaTime);
		this.setPosition(this.body.getPosition().x - this.getWidth() / 2f, this.body.getPosition().y - this.getHeight() / 2f);
	}
	
	private void movement() {
		if (movementTouchpad.getKnobPercentX() == 0)
			this.body.setLinearVelocity(0f, this.body.getLinearVelocity().y);
		else if (movementTouchpad.getKnobPercentX() < 0)
			this.body.setLinearVelocity(-0.9f, this.body.getLinearVelocity().y);
		else if (movementTouchpad.getKnobPercentX() > 0)
			this.body.setLinearVelocity(0.9f, this.body.getLinearVelocity().y);
			
		if (jumpButton.isTouched && this.body.getLinearVelocity().y == 0) {
			this.body.applyLinearImpulse(new Vector2(0f, 0.1f), this.body.getWorldCenter(), true);
			jumpButton.isTouched = false;
		}
	}
	
	private void getAnimation(float deltaTime) {
		state = getState();
		stateTime += deltaTime;
		
		switch(state) {
			case RUN:
				this.setRegion(runAnimation.getKeyFrame(stateTime, true));
				break;
			case JUMP:
				this.setRegion(Game.assets.playerJump);
				break;
			case IDLE:
				this.setRegion(idleAnimation.getKeyFrame(stateTime, true));
				break;
			default:
				break;
		}
		
		if (movementTouchpad.getKnobPercentX() < 0 && !this.isFlipX()) {
            this.flip(true, false);
        } else if (movementTouchpad.getKnobPercentX() > 0 && this.isFlipX()) {
            this.flip(true, false);
        }
	}
	
	private State getState() {
		if (this.body.getLinearVelocity().x < 0f || this.body.getLinearVelocity().x > 0f) {
			return State.RUN;
		} else if (this.body.getLinearVelocity().y > 0f) {
			return State.JUMP;
		} else {
			return State.IDLE;
		}
	}
	
	public Touchpad getMovementTouchpad() {
		return movementTouchpad;
	}
	
	public ImageButton getJumpButton() {
		return jumpButton;
	}
	
	public enum State {
		IDLE, RUN, JUMP
	}
}
