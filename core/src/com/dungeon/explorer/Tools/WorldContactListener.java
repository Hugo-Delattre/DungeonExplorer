package com.dungeon.explorer.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.dungeon.explorer.Sprites.InteractiveTileObject;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if(fixtureA.getUserData() == "playerBody" || fixtureB.getUserData() == "playerBody") {
            Fixture playerBody = fixtureA.getUserData() == "playerBody" ? fixtureA : fixtureB;
            Fixture object = playerBody == fixtureA ? fixtureB : fixtureA;

            if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
                ((InteractiveTileObject) object.getUserData()).onPlayerContact();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
