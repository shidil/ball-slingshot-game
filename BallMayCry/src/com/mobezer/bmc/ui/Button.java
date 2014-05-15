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
		BackTexture = backTex;
		ClickedTexture = clickTex;
		if(text!=null)
			Text = new TextWrapper(text, Assets.Shemlock, pos);
	}

	@Override
	public void Draw(SpriteBatch sp) {
		if (!IsClicked) {
			if (BackTexture != null)
				BackTexture.Draw(sp);
		} else {
			if (ClickedTexture != null)
				ClickedTexture.Draw(sp);
		}
		if (Text != null)
			Text.Draw(sp);
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
		float px =  (BackTexture.Position.x - BackTexture.GetWidth() / 2);
		float py =  (BackTexture.Position.y - BackTexture.GetHeight() / 2);
		float width =  (BackTexture.Position.x + BackTexture.GetWidth() / 2);
		float height =  (BackTexture.Position.y + BackTexture.GetHeight() / 2);
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