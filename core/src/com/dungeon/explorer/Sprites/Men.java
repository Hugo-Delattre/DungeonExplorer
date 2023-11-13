package com.dungeon.explorer.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.dungeon.explorer.DungeonExplorer;
import com.dungeon.explorer.Screens.PlayScreen;
import org.w3c.dom.Text;

public class Men extends Enemy {

    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;

    public Men(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
//        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i <= 0; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("men"), i * 60, 75, 50, 80));
        }
        walkAnimation = new Animation(0.8f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 75 / Player.PPM, 90 / Player.PPM);
        setToDestroy = false;
        destroyed = false;
    }

    public void update(float dt) {
        stateTime += dt;
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(walkAnimation.getKeyFrame(stateTime, true));
        if(setToDestroy && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
            //Texture of dying men
            setRegion(new TextureRegion(screen.getAtlas().findRegion("men"), 60, 80, 90, 100));
            stateTime = 0;
        } else if(!destroyed) {
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));
        }
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
//        bdef.position.set(200 / Player.PPM, 400 / Player.PPM);
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(25 / Player.PPM);
        fdef.filter.categoryBits = DungeonExplorer.ENEMY_BIT;
        fdef.filter.maskBits = DungeonExplorer.GROUND_BIT | DungeonExplorer.POTION_BIT | DungeonExplorer.WALL_BIT | DungeonExplorer.ENEMY_BIT | DungeonExplorer.OBJECT_BIT | DungeonExplorer.PLAYER_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef);

        PolygonShape menBody = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-25, 25).scl(1 / Player.PPM);
        vertice[1] = new Vector2(25, 25).scl(1 / Player.PPM);
        vertice[2] = new Vector2(-25, -25).scl(1 / Player.PPM);
        vertice[3] = new Vector2(25, -25).scl(1 / Player.PPM);
        menBody.set(vertice);

        fdef.shape = menBody;
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = DungeonExplorer.ENEMY_BODY_BIT;
        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void hit() {

    }
}
