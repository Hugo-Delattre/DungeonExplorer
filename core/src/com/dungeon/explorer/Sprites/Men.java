package com.dungeon.explorer.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.dungeon.explorer.DungeonExplorer;
import com.dungeon.explorer.Screens.PlayScreen;

public class Men extends Enemy {

    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    private float moveTimer;
    private float moveInterval = 1.8f;
    private float moveSpeed = 1f;

    public Men(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
//        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i <= 7; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("men"), i * 70-20, 0, 70, 80));
        }
        walkAnimation = new Animation(0.2f, frames); // ?
        stateTime = 0;
        setBounds(getX(), getY(), 75 / Player.PPM, 90 / Player.PPM);
        setToDestroy = false;
        destroyed = false;

    }

    public void update(float dt, Player player) {
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

        moveTimer += dt;

        if (moveTimer > moveInterval) {
            moveRandomly();
            moveTimer = 0;
        }

        if (!setToDestroy && !destroyed) {
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));
        }

        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);

        TextureRegion region = walkAnimation.getKeyFrame(stateTime, true);

        if (b2body.getLinearVelocity().x < 0 && !region.isFlipX()) {
            region.flip(true, false);
        }
        else if (b2body.getLinearVelocity().x > 0 && region.isFlipX()) {
            region.flip(true, false);
        }
        setRegion(region);
    }

    private void moveRandomly() {
        float randomAngle = MathUtils.random(0f, 2 * MathUtils.PI);
        Vector2 movement = new Vector2(MathUtils.cos(randomAngle), MathUtils.sin(randomAngle)).scl(moveSpeed);
        b2body.setLinearVelocity(movement);
    }

    @Override
    protected void defineEnemy() {
        // TODO : modifier les masks pour que Link ne puisse pas pousser les ennemis
        BodyDef bdef = new BodyDef();
//        bdef.position.set(200 / Player.PPM, 400 / Player.PPM);
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        fdef.density = 1000f;
        CircleShape shape = new CircleShape();
        shape.setRadius(25 / Player.PPM);
        fdef.filter.categoryBits = DungeonExplorer.ENEMY_BIT;
        fdef.filter.maskBits = DungeonExplorer.GROUND_BIT | DungeonExplorer.POTION_BIT | DungeonExplorer.WALL_BIT | DungeonExplorer.ENEMY_BIT | DungeonExplorer.OBJECT_BIT | DungeonExplorer.PLAYER_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef);

        PolygonShape menBody = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-25, 40).scl(1 / Player.PPM);
        vertice[1] = new Vector2(25, 40).scl(1 / Player.PPM);
        vertice[2] = new Vector2(-25, -40).scl(1 / Player.PPM);
        vertice[3] = new Vector2(25, -40).scl(1 / Player.PPM);
        menBody.set(vertice);

        fdef.shape = menBody;
        fdef.restitution = 0f;
        fdef.filter.categoryBits = DungeonExplorer.ENEMY_BODY_BIT;
        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void hit() {

    }
}
