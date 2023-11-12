package com.dungeon.explorer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dungeon.explorer.Screens.PlayScreen;

public class DungeonExplorer extends Game {
//	public static final int V_WIDTH = 400;
//	public static final int	V_HEIGHT = 208;

	// 16:9 aspect ratio, 960x540 resolution
	// width = 30 tiles x 32 pixels = 960 pixels

	public static final short GROUND_BIT = 1;
	public static final short PLAYER_BIT = 2;
	public static final short POTION_BIT = 4;
	public static final short WALL_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	public static final short STONE_BIT = 32;
	public static final short ENEMY_BIT = 64;
	public static final short OBJECT_BIT = 128;
	public static final short ENEMY_BODY_BIT = 256;
	
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
