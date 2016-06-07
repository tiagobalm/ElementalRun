package com.tiagoalmeida.elementalrun.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tiagoalmeida.elementalrun.FutureRun;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1980;
		config.height = 1020;
		new LwjglApplication(new FutureRun(), config);
	}
}
