package com.bigbass1997.evegrid.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bigbass1997.evegrid.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.width = 1200;
		config.height = 800;
		config.resizable = false;
		config.title = "EVE-Online Market Multi-Tool";
		
		new LwjglApplication(new Main(), config);
	}
}
