package com.mobezer.bmc.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mobezer.bmc.Game;
import com.mobezer.bmc.GameWorld;

public class HeroBall extends BoxCircleObject {
	float vX, vY;
	public boolean isFlying = false;
	public boolean noBody;
	public HeroBall(int bodyIndex, int collisionGroup, float radius,
			BodyType bodyType, float density, float restitution, float px,
			float py, float angle, TextureRegion texRegion) {
		super(bodyIndex, collisionGroup, radius, bodyType, density, restitution, px,
				py, angle, texRegion);
		body.setUserData("hero");
	}
	public void shoot(float shotPower, Vector2 vectorMade) {
		Gdx.app.log(Game.LOG, "shooooot"+vectorMade);
		double angle = Math.atan2(vectorMade.y, vectorMade.x);
		body.setType(BodyType.DynamicBody);
		vX = -(shotPower / 12.25f) * (float) Math.cos(angle);
		vY = -(shotPower / 12.25f) * (float) Math.sin(angle);
		body.setLinearVelocity(new Vector2(new Vector2(vX, vY)));
		body.setAngularDamping(1.54f);
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
