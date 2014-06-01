package com.mobezer.bmc.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mobezer.bmc.Assets;
import com.mobezer.bmc.Game;
import com.mobezer.bmc.GameWorld;
import com.mobezer.bmc.TextureDimensions;
import com.mobezer.bmc.TextureWrapper;

public class Target extends BoxCircleObject {
	float vX, vY;
	public boolean isLocked = false;
	public boolean noBody;
	private TextureWrapper texture1;
	public Target(float radius, float px,float py, float angle) {
		super(GameWorld.boxManager.GetNewObjectIndex(), 1, radius, BodyType.KinematicBody, 1, 0.86f, px,
				py, angle, Assets.ring);
		SetTextureDimension(TextureDimensions.TARGET_WIDTH,
				TextureDimensions.TARGET_HEIGHT);
		texture1=new TextureWrapper(Assets.ring1, new Vector2(px,py));
		texture1.SetDimension(TextureDimensions.VICTORY_WIDTH, TextureDimensions.VICTORY_HEIGHT);
		body.setUserData("target");
	}

	public void victory() {
		Gdx.app.log(Game.LOG, "Target");
		texture=texture1;
		noBody=true;
		isLocked=true;
	}
}
