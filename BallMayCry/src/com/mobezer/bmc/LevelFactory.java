package com.mobezer.bmc;

import java.util.ArrayList;

import com.mobezer.bmc.objects.BaseBoxObject;
import com.mobezer.bmc.objects.Box;
import com.mobezer.bmc.objects.BoxObjectManager;
import com.mobezer.bmc.objects.Disc;
import com.mobezer.bmc.objects.HeroBall;
import com.mobezer.bmc.objects.Target;

public class LevelFactory {
	 private ArrayList<ObjctPrototype> objects;
	 
	 public BaseBoxObject getObjects(){

		return null;	 
	 }

	public void loadObjects(BoxObjectManager boxManager) {
		int size = objects.size();
		for(int i=0;i<size;i++){
			final ObjctPrototype p = objects.get(i);
			if(p.name!=null){
				if(p.name.equals("hero")){
					boxManager.AddObject(new HeroBall(TextureDimensions.BALL_WIDTH, p.x, p.y, p.angle));
				}
				if(p.name.equals("target")){
					boxManager.AddObject(new Target(TextureDimensions.TARGET_WIDTH/2-9, p.x, p.y, p.angle));
				}
				if(p.name.equals("box")){
					if(p.rotation!=0){
						boxManager.AddObject(new Box(p.width,p.height, p.x, p.y, p.angle){
							@Override
							public void rotate() {
								setRotation(p.rotation);
							}
						});
					}
					else
						boxManager.AddObject(new Box(p.width,p.height, p.x, p.y, p.angle));
				}
				if(p.name.equals("disc")){
					boxManager.AddObject(new Disc(p.radius, p.x, p.y, p.angle));
				}
			}
		}
	}
}
