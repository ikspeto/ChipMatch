package com.denkovski.mihail.les.quatres.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.denkovski.mihail.les.quatres.ActionResolver;
import com.denkovski.mihail.les.quatres.ChipMatch;

public class DesktopLauncher {
	
	static RevMobIntegration revMobIntegration;
	static ActionIntegration actionIntegration;
	
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		int xs[] = {480, 360, 600, 480, 540, 768,  1080, 320, 720,  1080};
		int ys[] = {854, 640, 800, 640, 960, 1024, 1778, 480, 1280, 1920};
        //            0    1    2    3    4     5     6    7     8     9
		int i = 5;
		
		config.width = xs[i];  //480, 360, 600, 480, 540,  768, 1080, 320,  720 
		config.height = ys[i]; //854, 640, 800, 640, 960, 1024, 1778, 480, 1280


        /// mdpi remove frame across screen

		// mdpi menu padding
		// 6. google play games
		// 7. ads
		// 8. how to play tutorial
		
		revMobIntegration = new RevMobIntegration();
		
		new LwjglApplication(new ChipMatch(actionIntegration, revMobIntegration), config);
	}
}
