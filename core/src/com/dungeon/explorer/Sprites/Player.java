package com.dungeon.explorer.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.dungeon.explorer.DungeonExplorer;
import com.dungeon.explorer.Screens.PlayScreen;

public class Player extends Sprite {
    public enum State {GOINGUP, GOINGDOWN, GOINGRIGHT, GOINGLEFT, STANDINGDOWN}

    ;
    // TODO: Implémenter STANDINGRIGHT, UP, LEFT en utilisant previous state cf 9:00 dans la partie 11
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion playerStand;

    public static final float PPM = 100;

    private Animation playerStandingDown;
    private Animation playerGoingUp;
    private Animation playerGoingDown;
    private Animation playerGoingRight;
    private Animation playerGoingLeft;
    private float stateTimer;
    private boolean runningRight;


    public Player(World world, PlayScreen screen) {
        super(screen.getAtlas().findRegion("link"));
        this.world = world;
        currentState = State.STANDINGDOWN;
        previousState = State.STANDINGDOWN;
        stateTimer = 0;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        //STANDING DOWN
        for (int i = 1; i < 4; i++) {
            frames.add(new TextureRegion(getTexture(), i * 96, 1016, 96, 106));
        }
        playerStandingDown = new Animation(0.1f, frames);
        frames.clear();

        //GOING DOWN
        for (int i = 0; i < 10; i++) {
            frames.add(new TextureRegion(getTexture(), i * 96, 1440, 96, 106));
        }
        playerGoingDown = new Animation(0.1f, frames);
        frames.clear();

        //GOING RIGHT
        for (int i = 0; i < 10; i++) {
            frames.add(new TextureRegion(getTexture(), i * 96, 1744, 96, 106));
        }
        playerGoingRight = new Animation(0.1f, frames);
        frames.clear();

        //GOING LEFT
        for (int i = 0; i < 10; i++) {
            frames.add(new TextureRegion(getTexture(), i * 96, 1538, 96, 106));
        }
        playerGoingLeft = new Animation(0.1f, frames);
        frames.clear();

        //GOING UP
        for (int i = 0; i < 10; i++) {
            frames.add(new TextureRegion(getTexture(), i * 96, 1646, 96, 106));
        }
        playerGoingUp = new Animation(0.1f, frames);
        frames.clear();

        definePlayer();
        playerStand = new TextureRegion(getTexture(), 2, 1016, 96, 106);
        setBounds(0, 0, 64 / Player.PPM, 64 / Player.PPM);
        setRegion(playerStand);
    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region;
        switch (currentState) {
            case GOINGUP:
                region = (TextureRegion) playerGoingUp.getKeyFrame(stateTimer, true);
                break;
            case GOINGDOWN:
                region = (TextureRegion) playerGoingDown.getKeyFrame(stateTimer, true);
                break;
            case GOINGRIGHT:
                region = (TextureRegion) playerGoingRight.getKeyFrame(stateTimer, true);
                break;
            case GOINGLEFT:
                region = (TextureRegion) playerGoingLeft.getKeyFrame(stateTimer, true);
                break;
            case STANDINGDOWN:
                region = (TextureRegion) playerStandingDown.getKeyFrame(stateTimer, true);
            default:
                region = playerStand;
                break;
        }

        //POUR POISSON
        //if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            //region.flip(true, false);
            //runningRight = false;
        //} else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            //region.flip(true, false);
            //runningRight = true;
        //}

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }


    public State getState() {
        if (b2body.getLinearVelocity().y < 0) {
            return State.GOINGDOWN;
        } else if (b2body.getLinearVelocity().y > 0) {
            return State.GOINGUP;
        } else if (b2body.getLinearVelocity().x > 0) {
            return State.GOINGRIGHT;
        } else if (b2body.getLinearVelocity().x < 0) {
            return State.GOINGLEFT;
        } else {
            return State.STANDINGDOWN;
        }

    }

    public void definePlayer() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(200 / Player.PPM, 200 / Player.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(25 / Player.PPM);
        fdef.filter.categoryBits = DungeonExplorer.PLAYER_BIT;
        fdef.filter.maskBits = DungeonExplorer.DEFAULT_BIT | DungeonExplorer.POTION_BIT | DungeonExplorer.WALL_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef);

        PolygonShape playerBody = new PolygonShape();
        playerBody.setAsBox(25 / Player.PPM, 25 / Player.PPM);
        fdef.shape = playerBody;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData("playerBody");

    }
}
