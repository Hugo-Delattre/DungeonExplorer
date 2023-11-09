package com.dungeon.explorer;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.dungeon.explorer.DungeonExplorer;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Dungeon Explorer");
		config.setWindowedMode(1280, 720);
		new Lwjgl3Application(new DungeonExplorer(), config);
	}
}
