package com.mobezer.bmc;

import com.mobezer.bmc.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	static int hd=1;
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Ball May Cry";
		cfg.useGL20 = true;
		if (hd == 1) {
			cfg.width = 1280;
			cfg.height = 720;
		} else {
			cfg.width = 640;
			cfg.height = 360;
		}
		@SuppressWarnings("unused")
		int level = Integer.parseInt(args[0]);
		new LwjglApplication(new Game(), cfg);
	}
}
