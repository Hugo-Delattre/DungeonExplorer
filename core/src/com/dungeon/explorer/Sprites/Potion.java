package com.dungeon.explorer.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.dungeon.explorer.DungeonExplorer;
import com.dungeon.explorer.Scenes.Hud;

public class Potion extends InteractiveTileObject {
    public Potion(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(DungeonExplorer.POTION_BIT);
    }

    @Override
    public void onPlayerContact() {
        Gdx.app.log("Potion", "Collision");
        //TODO sound effect potion
        Hud.addLifePoints(3);
        setCategoryFilter(DungeonExplorer.DESTROYED_BIT);
        getCell().setTile(null);
    }
}
