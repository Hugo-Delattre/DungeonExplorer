package com.dungeon.explorer.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

public class Player extends Sprite {
    public World world;
    public Body b2body;
    public static final float PPM = 100;

    public Player(World world){
        this.world = world;
        definePlayer();
    }

    public void definePlayer() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(200 / Player.PPM, 200/Player.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(15 / Player.PPM);

        fdef.shape= shape;
        b2body.createFixture(fdef);
    }
}
