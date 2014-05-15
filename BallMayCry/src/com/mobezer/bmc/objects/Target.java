package com.mobezer.bmc.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mobezer.bmc.Assets;
import com.mobezer.bmc.Game;
import com.mobezer.bmc.TextureDimensions;
import com.mobezer.bmc.TextureWrapper;

public class Target extends BoxCircleObject {
	float vX, vY;
	public boolean isLocked = false;
	public boolean noBody;
	private TextureWrapper texture1;
	public Target(int bodyIndex, int collisionGroup, float radius,
			BodyType bodyType, float density, float restitution, float px,
			float py, float angle, TextureRegion texRegion) {
		super(bodyIndex, collisionGroup, radius, bodyType, density, restitution, px,
				py, angle, texRegion);
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
