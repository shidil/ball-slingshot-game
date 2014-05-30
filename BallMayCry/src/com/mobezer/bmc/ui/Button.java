package com.mobezer.bmc.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mobezer.bmc.Assets;
import com.mobezer.bmc.TextWrapper;
import com.mobezer.bmc.TextureWrapper;

public class Button extends Widget {
	public TextureWrapper BackTexture;
	public TextureWrapper ClickedTexture;
	public TextWrapper Text;

	public Button(TextureWrapper backTex, TextureWrapper clickTex, String text,
			Vector2 pos) {
		super(pos);
		isTouchable =true;
		BackTexture = backTex;
		ClickedTexture = clickTex;
		if(BackTexture!=null) BackTexture.setPosition(pos);
		if(ClickedTexture!=null) BackTexture.setPosition(pos);
		if(text!=null)
			Text = new TextWrapper(text, Assets.Shemlock, pos);
	}

	@Override
	public void Draw(SpriteBatch sp) {
		if (!IsClicked) {
			if (BackTexture != null){
				//BackTexture.setPosition(position);
				BackTexture.Draw(sp);
			}
				
		} else {
			if (ClickedTexture != null){
				//ClickedTexture.setPosition(position);
				ClickedTexture.Draw(sp);
			}
		}
		if (Text != null){
			//Text.Position.set(position);
			Text.Draw(sp);
		}
	}
	@Override
	public void touch(float tapx, float tapy) {
		IsClicked = IsInside(tapx, tapy);
	}
	@Override
	public void update(float delta) {
		super.update(delta);
	}
	@Override
	public Boolean IsInside(float tapx, float tapy) {
		float px=0,py=0,width=0,height=0;
		if (BackTexture != null){
		px =  (BackTexture.Position.x - BackTexture.GetWidth() / 2);
		py =  (BackTexture.Position.y - BackTexture.GetHeight() / 2);
		width =  (BackTexture.Position.x + BackTexture.GetWidth() / 2);
		height =  (BackTexture.Position.y + BackTexture.GetHeight() / 2);
		}
		else if(Text!=null){	
			px =  (Text.Position.x - Text.GetWidth() / 2);
			py =  (Text.Position.y - Text.GetHeight() / 1);
			width =  (Text.Position.x + Text.GetWidth() / 2);
			height =  (Text.Position.y + Text.GetHeight() / 2);
		}
		if (tapx >= px && tapx <= (/*px +*/ width) && tapy >= py && tapy <= (/*py + */height))
			return true;
		return false;
	}
	@Override
	public void tap() {
		// TODO Auto-generated method stub
		super.tap();
	}
}