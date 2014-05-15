package com.mobezer.bmc.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mobezer.bmc.Game;

public class Disc extends BoxCircleObject {
	float vX, vY;
	public boolean noBody;
	public Disc(int bodyIndex, int collisionGroup, float radius,
			BodyType bodyType, float density, float restitution, float px,
			float py, float angle, TextureRegion texRegion) {
		super(bodyIndex, collisionGroup, radius, bodyType, density, restitution, px,
				py, angle, texRegion);
		body.setUserData("disc");
	}

	public void victory() {
		Gdx.app.log(Game.LOG, "Target");
		noBody=true;
	}
}
