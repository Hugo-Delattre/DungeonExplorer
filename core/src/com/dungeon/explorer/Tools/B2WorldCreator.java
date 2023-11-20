package com.dungeon.explorer.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.dungeon.explorer.Screens.PlayScreen;
import com.dungeon.explorer.Sprites.Player;
import com.dungeon.explorer.Sprites.Potion;
import com.dungeon.explorer.Sprites.Stone;
import com.dungeon.explorer.Sprites.Wall;

import java.util.HashMap;

public class B2WorldCreator {

    private HashMap<String, Stone> stoneMap;

    public B2WorldCreator(PlayScreen screen) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        stoneMap = new HashMap<String, Stone>();


        //Potion
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Potion(screen, rect);
        }

        //Wall
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Wall(screen, rect);
        }

        //Stone
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Stone(screen, rect);
            Stone stone = new Stone(screen, rect);
            stoneMap.put(object.getName(), stone);

//            if ("S1".equals(object.getName()) && object instanceof Stone) {
//                Stone S1 = (Stone) object;
//            }
        }

        //Lava
        //TODO Implémenter la lave

    }

    public HashMap<String, Stone> getStoneMap() {
        return stoneMap;
    }
}
