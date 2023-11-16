package com.dungeon.explorer.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.dungeon.explorer.Screens.PlayScreen;

public abstract class Enemy extends Sprite {
    protected World world;
    protected PlayScreen screen;
    public Body b2body;
    public Vector2 velocity;

    protected int lifePoints;
    protected boolean setToDestroy;
    protected boolean destroyed;
    protected float stateTime;
    protected Animation<TextureRegion> walkAnimation;
    protected Array<TextureRegion> frames;
    
    public static int instanceCount = 0;

    public Enemy(PlayScreen screen, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        instanceCount++;
        Gdx.app.log("Enemy", "Created. " + instanceCount + " instances.");
        defineEnemy();
        velocity = new Vector2(1, 0);
    }

    protected abstract void defineEnemy();
    public void hit() {
        lifePoints--;
        // Gdx.app.log("LifePoints", "- "+ lifePoints);
        if (lifePoints <= 0) {
            setToDestroy = true;
        }
    }
    
    public void dispose() {
        instanceCount--;
        Gdx.app.log("Enemy", "Disposed. " + instanceCount + " instances left.");
    }

    public void reverseVelocity(boolean x, boolean y) {
        if(x) {
            velocity.x = -velocity.x;
        }
        if(y) {
            velocity.y = -velocity.y;
        }
    }
}
