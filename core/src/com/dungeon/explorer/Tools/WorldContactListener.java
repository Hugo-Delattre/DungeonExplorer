package com.dungeon.explorer.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.dungeon.explorer.DungeonExplorer;
import com.dungeon.explorer.Scenes.Hud;
import com.dungeon.explorer.Sprites.Enemy;
import com.dungeon.explorer.Sprites.InteractiveTileObject;
import com.dungeon.explorer.Sprites.Player;

public class WorldContactListener implements ContactListener {
    
    private Player player;
    
    public WorldContactListener(Player player) {
        this.player = player;
    }
    
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        int cDef = fixtureA.getFilterData().categoryBits | fixtureB.getFilterData().categoryBits;

        if (fixtureA.getUserData() == "playerBody" || fixtureB.getUserData() == "playerBody") {
            Fixture playerBody = fixtureA.getUserData() == "playerBody" ? fixtureA : fixtureB;
            Fixture object = playerBody == fixtureA ? fixtureB : fixtureA;

            if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
                ((InteractiveTileObject) object.getUserData()).onPlayerContact();
            }
        }

        switch (cDef) {
            case DungeonExplorer.ENEMY_BODY_BIT | DungeonExplorer.OBJECT_BIT:
                if (fixtureA.getFilterData().categoryBits == DungeonExplorer.ENEMY_BODY_BIT) {
                    ((Enemy) fixtureA.getUserData()).hit();
                } else {
                    ((InteractiveTileObject) fixtureA.getUserData()).onPlayerContact();
                }
                break;
            case DungeonExplorer.ENEMY_BIT | DungeonExplorer.OBJECT_BIT:
                if (fixtureA.getFilterData().categoryBits == DungeonExplorer.ENEMY_BIT) {
                    ((Enemy) fixtureA.getUserData()).reverseVelocity(true, false);
                } else {
                    ((Enemy) fixtureB.getUserData()).reverseVelocity(true, false);
                }
                break;
            case DungeonExplorer.PLAYER_BIT | DungeonExplorer.ENEMY_BODY_BIT:
                player.loseLifePoint();
                break;
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
