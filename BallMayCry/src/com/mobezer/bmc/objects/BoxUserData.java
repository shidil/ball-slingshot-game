package com.mobezer.bmc.objects;

public class BoxUserData {
	public int BodyIndex;
	public int BodyCollisionGroup;
	
	
	
	public BoxUserData(int index,int group)
	{
		SetIndex(index);
		SetCollisionGroup(group);
	}
	
	public void SetIndex(int index){
		BodyIndex=index;
	}
	
	public void SetCollisionGroup(int group){
		BodyCollisionGroup=group;
	}
}
