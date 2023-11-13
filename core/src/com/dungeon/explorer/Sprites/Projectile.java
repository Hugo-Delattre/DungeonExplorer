package com.dungeon.explorer.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.dungeon.explorer.DungeonExplorer;
import com.dungeon.explorer.Screens.PlayScreen;

public class Projectile extends Sprite {

    private World world;
    private Body b2body;
    private boolean toDestroy;
    private Texture texture;
    private PlayScreen screen;

    public Projectile(PlayScreen screen, float x, float y, float directionX, float directionY) {
        super(screen.getAtlas().findRegion("link"));
//        this.texture = atlas.findRegion("link").getTexture();
        this.world = screen.getWorld();
        defineProjectile(x, y);
        setBounds(0, 0, 16 / Player.PPM, 16 / Player.PPM); // Adjust the size as needed
        toDestroy = false;
        b2body.setLinearVelocity(new Vector2(directionX, directionY)); // Set the velocity based on the player's input
    }

    private void defineProjectile(float x, float y) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x, y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / Player.PPM); // Ajustez le rayon selon vos besoins
        fdef.filter.categoryBits = DungeonExplorer.PROJECTILE_BIT;
        fdef.filter.maskBits = DungeonExplorer.ENEMY_BIT | DungeonExplorer.WALL_BIT; // Ajustez les bits de masque selon vos besoins

        fdef.shape = shape; // Assurez-vous que la forme est définie avant de l'utiliser
        b2body.createFixture(fdef);

        // N'oubliez pas de libérer les ressources de la forme une fois que vous avez terminé avec elle
        shape.dispose();
    }

    public void update(float dt) {
        if (toDestroy && !world.isLocked()) {
            world.destroyBody(b2body);
            setToDestroy();
        }
    }

    public void setToDestroy() {
        toDestroy = true;
    }

    public boolean isDestroyed() {
        return toDestroy;
    }
}
