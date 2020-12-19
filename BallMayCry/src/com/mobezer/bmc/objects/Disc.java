package com.mobezer.bmc.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mobezer.bmc.Assets;
import com.mobezer.bmc.Game;
import com.mobezer.bmc.GameWorld;

public class Disc extends BoxCircleObject {
	float vX, vY;
	public boolean noBody;
	public Disc(float radius,  float px,float py, float angle) {
		super(GameWorld.boxManager.GetNewObjectIndex(), 1, radius, BodyType.StaticBody, 0, 0.05f, px,
				py, angle, Assets.disc);
		SetTextureDimension((int)radius*2, (int)radius*2);
		body.setUserData("disc");
	}

	public void victory() {
		Gdx.app.log(Game.LOG, "Target");
		noBody=true;
	}
}
