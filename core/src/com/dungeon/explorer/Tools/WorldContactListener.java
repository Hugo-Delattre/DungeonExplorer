package com.dungeon.explorer.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.dungeon.explorer.DungeonExplorer;
import com.dungeon.explorer.Sprites.Enemy;
import com.dungeon.explorer.Sprites.InteractiveTileObject;
import com.dungeon.explorer.Sprites.Player;
import com.dungeon.explorer.Sprites.Projectile;

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

        if ((fixtureA.getUserData() instanceof Projectile && fixtureB.getUserData() instanceof Enemy)
                || (fixtureA.getUserData() instanceof Enemy && fixtureB.getUserData() instanceof Projectile)) {
            System.out.println("Collision détectée !");
            Gdx.app.log("Projectile collision", "Projectile hit enemy");
            // Autres actions à effectuer lors de la collision
        }

        switch (cDef) {
//            case DungeonExplorer.ENEMY_BODY_BIT | DungeonExplorer.OBJECT_BIT:
//                if (fixtureA.getFilterData().categoryBits == DungeonExplorer.ENEMY_BODY_BIT) {
//                    ((Enemy) fixtureA.getUserData()).hit();
//                } else {
//                    ((InteractiveTileObject) fixtureA.getUserData()).onPlayerContact();
//                }
//                break;
            case DungeonExplorer.ENEMY_BIT | DungeonExplorer.OBJECT_BIT:
                if (fixtureA.getFilterData().categoryBits == DungeonExplorer.ENEMY_BIT) {
                    ((Enemy) fixtureA.getUserData()).reverseVelocity(true, false);
                } else {
                    ((Enemy) fixtureB.getUserData()).reverseVelocity(true, false);
                }
                break;
            case DungeonExplorer.PLAYER_BIT | DungeonExplorer.ENEMY_BIT:
                player.loseLifePoint();
                break;
            case DungeonExplorer.PROJECTILE_BIT | DungeonExplorer.ENEMY_BIT:
                if (fixtureA.getFilterData().categoryBits == DungeonExplorer.ENEMY_BIT) {
                    ((Enemy) fixtureA.getUserData()).hit();
                } else {
                    ((Enemy) fixtureB.getUserData()).hit();
                }
                Gdx.app.log("Enemy collision", "Enemy hit by projectile");
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
