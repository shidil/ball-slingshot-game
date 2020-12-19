package com.mobezer.bmc.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mobezer.bmc.Assets;
import com.mobezer.bmc.Game;
import com.mobezer.bmc.GameWorld;

public class Box extends BoxRectObject {
	float vX, vY;
	public boolean noBody;
	public Box(float width,float height, float px,float py, float angle) {
		super(GameWorld.boxManager.GetNewObjectIndex(), 1, width,height, BodyType.StaticBody, 1, 0.05f, px,
				py, angle, Assets.box);
		SetTextureDimension((int)width, (int)height);
		body.setUserData("box");
	}

	public void victory() {
		Gdx.app.log(Game.LOG, "Target");
		noBody=true;
	}
}
