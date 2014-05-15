package com.mobezer.bmc;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.badlogic.gdx.backends.android.AndroidApplication;

public class MainActivity extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
   	 super.onCreate(savedInstanceState);
    	// Do the stuff that initialize() would do for you
 		requestWindowFeature(Window.FEATURE_NO_TITLE);

 		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
 				WindowManager.LayoutParams.FLAG_FULLSCREEN);
 		getWindow().clearFlags(
 				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
 		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
 		initialize(new Game(), true);
    }
}