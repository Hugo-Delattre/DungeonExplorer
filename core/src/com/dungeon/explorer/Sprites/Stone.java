package com.dungeon.explorer.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;
import com.dungeon.explorer.DungeonExplorer;
import com.dungeon.explorer.Screens.PlayScreen;

public class Stone extends InteractiveTileObject {

    private PlayScreen screen;
    private static boolean hasStoneBeenRecentlyActivated = false;

    public Stone(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(DungeonExplorer.STONE_BIT);
        this.screen = screen;
    }

    @Override
    public void onPlayerContact() {
        Gdx.app.log("Stone", "Collision");
        //TODO sound effect stone destroyed
        if (Enemy.enemyCounter == 0 && !hasStoneBeenRecentlyActivated) {
            hasStoneBeenRecentlyActivated = true;
//            PlayScreen.currentLevel++;
            Gdx.app.log("Stone", "Reached");
            setCategoryFilter(DungeonExplorer.DESTROYED_BIT);
            screen.setShouldMoveCamera(true);
            switch (PlayScreen.currentLevel) {
                case 1:
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            Enemy.enemyCounter = 6; // When changing level, the enemy counter updates
                            PlayScreen.currentLevel++;
                            System.out.println("Enemy counter: " + Enemy.enemyCounter);
                        }
                    }, 2);
                    break;

                case 2:
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            Enemy.enemyCounter = 1; // There's only the boss in the final room
                            PlayScreen.currentLevel++;
                            System.out.println("Enemy counter: " + Enemy.enemyCounter);
                        }
                    }, 2);
                    break;
            }
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    hasStoneBeenRecentlyActivated = false;
                    System.out.println("Stone can be activated again");
                }
            }, 4);
        } else if (Enemy.enemyCounter <= 0 && hasStoneBeenRecentlyActivated) {
            setCategoryFilter(DungeonExplorer.DESTROYED_BIT);
        }


    }

    public void breakStone() {
        if (Enemy.enemyCounter == 0) {
            Gdx.app.log("STONE", "BREAK");

            if (body != null) {
                world.destroyBody(body);
            }

            if (PlayScreen.currentLevel == 2) {
                System.out.println("First stone to remove, " + PlayScreen.currentLevel);
                TiledMapTileLayer layerToRemove = (TiledMapTileLayer) map.getLayers().get(8);
                if (layerToRemove != null) {
                    map.getLayers().remove(layerToRemove);
                }
            } else if (PlayScreen.currentLevel == 3) {
                System.out.println("Second stone to remove, " + PlayScreen.currentLevel);
                TiledMapTileLayer layerToRemove = (TiledMapTileLayer) map.getLayers().get(9);
                if (layerToRemove != null) {
                    map.getLayers().remove(layerToRemove);
                }
            } else if (PlayScreen.currentLevel == 4) {
                System.out.println("Third stone to remove, " + PlayScreen.currentLevel);
                TiledMapTileLayer layerToRemove = (TiledMapTileLayer) map.getLayers().get(10);
                if (layerToRemove != null) {
                    map.getLayers().remove(layerToRemove);
                }
            } else {
                System.out.println("No stone to remove");
            }


        }
    }
}

