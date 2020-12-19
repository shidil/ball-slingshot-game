package com.mobezer.bmc.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mobezer.bmc.Assets;
import com.mobezer.bmc.Game;
import com.mobezer.bmc.GameWorld;
import com.mobezer.bmc.TextureDimensions;

public class HeroBall extends BoxCircleObject {
	float vX, vY;
	public boolean isFlying = false;
	public boolean noBody;
	public HeroBall(float radius,  float px,float py, float angle) {
		super(GameWorld.boxManager.GetNewObjectIndex(), 1, radius, BodyType.KinematicBody,1, 0.88f, px,
				py, angle, Assets.hero);
		SetTextureDimension(TextureDimensions.BALL_WIDTH,
				TextureDimensions.BALL_HEIGHT);
		body.setUserData("hero");
	}
	public void shoot(float shotPower, Vector2 vectorMade) {
		Gdx.app.log(Game.LOG, "shooooot"+vectorMade);
		double angle = Math.atan2(vectorMade.y, vectorMade.x);
		body.setType(BodyType.DynamicBody);
		vX = -(shotPower / 9.7f) * (float) Math.cos(angle);
		vY = -(shotPower / 9.7f) * (float) Math.sin(angle);
		body.setLinearVelocity(new Vector2(new Vector2(vX, vY)));
		body.setLinearDamping(0.14f);
		body.setAngularDamping(0.34f);
		Gdx.app.log(Game.LOG, "vX:" + vX + " vY:" + vY);
		isFlying = true;
		GameWorld.heroBallPoints.clear();
	}

	public void victory() {
		Gdx.app.log(Game.LOG, "Removing Body");
		noBody=true;
		//isFlying=false;
		IsAlive=false;
	}
}
