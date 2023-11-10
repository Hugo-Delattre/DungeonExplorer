package com.dungeon.explorer.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.dungeon.explorer.Screens.PlayScreen;

public class Player extends Sprite {
    public World world;
    public Body b2body;
    private TextureRegion playerStand;
    public static final float PPM = 100;

    public Player(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("pinkFish"));
        this.world = world;
        definePlayer();
        playerStand = new TextureRegion(getTexture(), 1028,1267,350,350);
        setBounds(0, 0, 32 / Player.PPM, 32 / Player.PPM);
        setRegion(playerStand);
    }

    public void update(float dt){
//TODO vérifier si besoin de ça sachant que la cam ne follow pas le perso
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
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
