package com.mobezer.bmc;

import com.badlogic.gdx.scenes.scene2d.InputListener;

public class DragShoot extends InputListener {

	/*@Override
	public boolean touchDown(InputEvent event, float x, float y,
			int pointer, int button) {
		if (x < heroBall.getX() + heroBall.getWidth()/2 && x > heroBall.getX() - heroBall.getWidth()/2
				&& y < heroBall.getY() + heroBall.getWidth()/2 && y > heroBall.getY() - heroBall.getWidth()/2) {
				if (!heroBall.isFlying){
				X1 = x;
				Y1 = y;
				X2 = x;
				Y2 = y;
				Gdx.app.log(Woody.LOG, "Ball Touched ");
				return true;
			}
		} else
			return false;
		return false;

	};

	@Override
	public void touchDragged(InputEvent event, float x, float y,
			int pointer) {
		// Update the end point and redraw the line
		X2 = x;
	    Y2 = y;
	    double angle;
	    float dist_x = X2-circle.x;
	    float dist_y = Y2-circle.y;
	    double distance = Math.sqrt(dist_x*dist_x+dist_y*dist_y);
	    if (distance>100) {
	        angle = Math.atan2(dist_y, dist_x);
	        X2 = (float) (circle.x+100*Math.cos(angle));
	        Y2 = (float) (circle.y+100*Math.sin(angle));
	    }
		// Update VectorMade
		vectorMade.set(X2 - X1, Y2 - Y1);
		shotPower = vectorMade.len();
		// Store value to reduce number of calculations
		pullArmBack = shotPower / 50;
		// Normalise vector to yield only directional info
		vectorMade.nor();
		{
			rotNeeded = (float) (245 + Math.toDegrees(Math.atan2(
					vectorMade.y, vectorMade.x)
					- Math.atan2(heroBall.getY(), heroBall.getX())));
			heroBall.body.setTransform(X2*GameScreen.WORLD_TO_BOX, Y2*GameScreen.WORLD_TO_BOX, rotNeeded*0);
			//Gdx.app.log(Woody.LOG, "Rotation calculated " + rotNeeded);
		}
	};

	@Override
	public void touchUp(InputEvent event, float x, float y,
			int pointer, int button) {
		if (shotPower < 10)
			;
		else {
			Gdx.app.log(Woody.LOG, "Fire!! " + shotPower);
			heroBall.shoot(shotPower, vectorMade);
			shotPower = 0;
			X1=0;X2=0;Y1=0;Y2=0;
		}
	};*/
}
