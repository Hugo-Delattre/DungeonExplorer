package com.dungeon.explorer.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.dungeon.explorer.DungeonExplorer;

public class Stone extends InteractiveTileObject {
    public Stone(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(DungeonExplorer.WALL_BIT);
    }

    @Override
    public void onPlayerContact() {
        Gdx.app.log("Stone", "Collision");
    }
}
