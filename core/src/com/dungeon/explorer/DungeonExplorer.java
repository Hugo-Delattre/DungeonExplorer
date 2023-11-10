package com.dungeon.explorer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.dungeon.explorer.Screens.PlayScreen;

public class DungeonExplorer extends Game {
//	public static final int V_WIDTH = 400;
//	public static final int	V_HEIGHT = 208;

	// 16:9 aspect ratio, 960x540 resolution
	// width = 30 tiles x 32 pixels = 960 pixels
	public static final int V_WIDTH = 960;
	public static final int V_HEIGHT = 700;

	public SpriteBatch batch;
  
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
//		img.dispose();
	}
}
