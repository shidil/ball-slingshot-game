package com.mobezer.bmc.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mobezer.bmc.Assets;
import com.mobezer.bmc.TextureWrapper;

public class BoxRectObject extends BaseBoxObject{
	TextureWrapper texture;
	TextureWrapper texture1;
	
	public BoxRectObject(int bodyIndex, int collisionGroup,float width,float height,
			BodyType bodyType,float density,float restitution,float px,float py,
			float angle,TextureRegion texRegion) {
		super(bodyIndex, collisionGroup);
		// TODO Auto-generated constructor stub
		Vector2 pos=new Vector2(px,py);
		MakeBody(width, height, 0, bodyType, density, restitution, pos, angle);
		texture=new TextureWrapper(texRegion, pos);
		texture1=new TextureWrapper(Assets.boxBg, pos);
		body.setUserData("box");
	}

	
	public void SetTextureDimension(int width,int height){
		texture.SetDimension(width, height);
		texture1.SetDimension(width+2, height+2);
	}
	@Override
	public void Draw(SpriteBatch sp) {
		// TODO Auto-generated method stub
		if(IsAlive){
			texture1.Draw(sp);
			texture.Draw(sp);
		}
	}
	
	public void Update(float dt){
		if(IsAlive){
			super.Update(dt);
			texture.Position.set(bodyWorldPosition);
			texture.rotation=GetBodyRotationInDegrees();
			texture1.rotation=GetBodyRotationInDegrees();
		}
		
	}
	
	public float GetBodyRotationInDegrees(){
		return body.getAngle()*MathUtils.radiansToDegrees;
	}


	@Override
	public void rotate() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}

}
