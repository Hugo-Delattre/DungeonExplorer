package com.dungeon.explorer.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dungeon.explorer.DungeonExplorer;
import com.dungeon.explorer.Scenes.Hud;
import com.dungeon.explorer.Sprites.*;
import com.dungeon.explorer.Tools.B2WorldCreator;
import com.dungeon.explorer.Tools.WorldContactListener;

import java.util.HashMap;
import java.util.Iterator;

public class PlayScreen implements Screen {
    private DungeonExplorer game;
    private OrthographicCamera gameCam;

    private Viewport gamePort;
    private Hud hud;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;

    private Player player;
    private Ninja ninja;
    private Ninja ninja2;
    private Ninja ninja3;
    private Ninja ninja4;
    private Ninja ninja5;
    private Men men;
    private Men men2;
    private Men men3;
    private Men men4;
    private Men men5;
    private PinkFish bobby;
    private TextureAtlas atlas;
    private Music backgroundMusic;

    private Array<EnemyProjectile> enemyProjectiles;
    //    private Array<Enemy> enemies;
    private B2WorldCreator worldCreator;
    public static int currentLevel = 1;
    private boolean shouldMoveCamera = false;
    private float cameraMoveTime = 0;

    private HashMap<Integer, Integer> levelToStoneLayerMap;
    private boolean hasLevelChangedRecently = false;


    public PlayScreen(DungeonExplorer game) {
        Gdx.input.setInputProcessor(null);
        atlas = new TextureAtlas("linkAndEnemies.atlas");
        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(DungeonExplorer.V_WIDTH / Player.PPM, DungeonExplorer.V_HEIGHT / Player.PPM,
                gameCam);
        hud = new Hud(game.batch);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("maps/DungeonRoom.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Player.PPM);
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        initializeLevelToLayerMap();

        world = new World(new Vector2(0, 0), true);
        createBorders();

        // Uncomment this line and the b2dr.render(world, gameCam.combined); below to
        // see the Box2D debug lines
//        b2dr = new Box2DDebugRenderer();
        new B2WorldCreator(this, player);

        player = new Player(this);
        world.setContactListener(new WorldContactListener(player));
//        enemies = new Array<Enemy>();

        //Level 1
        ninja = new Ninja(this, 2.92f, 2.92f);
        ninja2 = new Ninja(this, 6.92f, 3.92f);
        men = new Men(this, 4.92f, 4.92f);
        men2 = new Men(this, 8.92f, 4.92f);

//        Level 2
        ninja3 = new Ninja(this, 2.92f, 9.92f);
        ninja4 = new Ninja(this, 6.92f, 9.92f);
        ninja5 = new Ninja(this, 6.92f, 9.92f);
        men3 = new Men(this, 4.92f, 10.92f);
        men4 = new Men(this, 3.42f, 10.92f);
        men5 = new Men(this, 7.52f, 10.92f);


        //Level 3 - Boss Level
        bobby = new PinkFish(this, 6.92f, 15.22f, player);


        enemyProjectiles = new Array<EnemyProjectile>();
        worldCreator = new B2WorldCreator(this, player);
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public void setShouldMoveCamera(boolean moveCamera) {
        this.shouldMoveCamera = moveCamera;
        if (moveCamera) {
            cameraMoveTime = 0; // Réinitialiser le compteur de temps quand on commence à déplacer
        }
    }

    public void addEnemyProjectile(EnemyProjectile projectile) {
        enemyProjectiles.add(projectile);
    }

    @Override
    public void show() {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/dungeonBoss.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.play();
    }

    public void handleInput(float dt) {
        float velocity = 150.0f; // Vitesse du personnage
        Vector2 movement = new Vector2();

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            movement.y += velocity;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            movement.x += velocity;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            movement.x -= velocity;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            movement.y -= velocity;
        }

        player.b2body.setLinearVelocity(movement.scl(dt)); // Appliquer la vitesse multipliée par le delta pour un
        // mouvement fluide
    }

    public void update(float dt) {
        handleInput(dt);



//        System.out.println(currentLevel);
//
//        if (Enemy.enemyCounter <= 0 && currentLevel == 1 && !hasLevelChangedRecently) {
//            hasLevelChangedRecently = true;
////            System.out.println(levelToStoneLayerMap.size());
////            removeStoneForCurrentLevel();
//            removeStoneForFistLevel();
//            currentLevel++;
////            Enemy.enemyCounter = 6;
//
//            Timer.schedule(new Timer.Task() {
//                @Override
//                public void run() {
//                    hasLevelChangedRecently = false;
//                    System.out.println("Stone can be activated again");
//                }
//            }, 5);
        if (Enemy.enemyCounter <= 0 && currentLevel == 1) {
            Gdx.app.log("EnemyCounter", "All enemies have been destroyed!");
            currentLevel++;
            // Get stones from B2WorldCreator
            HashMap<String, Stone> stoneMap = worldCreator.getStoneMap();
            Gdx.app.log("StoneCounter", "There are " + stoneMap.size() + " stones in the map.");


            if (stoneMap.size() > 0) {
                //get first element of the hashMap
                String firstStoneKey = (String) stoneMap.keySet().toArray()[0];
                Stone stone = stoneMap.get(firstStoneKey);

                System.out.println(stone);
                System.out.println(stoneMap);

                if (stone != null) {
                    // Appeler la méthode pour casser la pierre
                    stone.breakStone();

                    // Supprimer la première pierre de la stoneMap
                    stoneMap.remove(firstStoneKey);
                }

                System.out.println(stoneMap);


            }
        }
//        if (Enemy.enemyCounter <= 0 && currentLevel == 3) {
//            currentLevel++;
//            System.out.println(levelToStoneLayerMap.size());
//            removeStoneForSecondLevel();
//        }
//        if (Enemy.enemyCounter <= 0 && currentLevel == 3) {
//            currentLevel++;
//            System.out.println(levelToStoneLayerMap.size());
//            removeStoneForCurrentLevel();
//        }
//        if (Enemy.enemyCounter <= 0 && currentLevel >= 2) {
//            stone.breakStone();
//        }

        if (shouldMoveCamera) {
            cameraMoveTime += dt;
            if (cameraMoveTime <= 2f) {
                float targetY = gameCam.position.y + 0.1f; // Par exemple, 5 unités plus haut que la position actuelle

                Vector3 targetPosition = new Vector3(gameCam.position.x, targetY, 0);
                gameCam.position.lerp(targetPosition, cameraMoveTime / 2);
//                gameCam.position.set(0, 0, 30);
            } else {
                cameraMoveTime = 0;
                shouldMoveCamera = false;
            }
        }


        world.step(1 / 60f, 6, 2);
        player.update(dt);
        ninja.update(dt, player);
        ninja2.update(dt, player);
        ninja3.update(dt, player);
        ninja4.update(dt, player);
        ninja5.update(dt, player);
        men.update(dt, player);
        men2.update(dt, player);
        men3.update(dt, player);
        men4.update(dt, player);
        men5.update(dt, player);
        bobby.update(dt, player);


        hud.update(dt);
        gameCam.update();
        renderer.setView(gameCam);

//        for (Projectile projectile : player.getProjectiles()) {
//            if (projectile.isDestroyed()) {
//                projectile.destroyBody(); // Méthode pour détruire le corps
//            }
//        }

        for (Iterator<EnemyProjectile> iter = enemyProjectiles.iterator(); iter.hasNext(); ) {
            EnemyProjectile projectile = iter.next();
            projectile.update(dt);
            if (projectile.isDestroyed()) {
                iter.remove(); // Supprime le projectile de la liste
                projectile.destroyBody();
            }
        }

//        for (EnemyProjectile projectile : enemyProjectiles) {
//            projectile.update(dt);
//            if (projectile.isDestroyed()) {
//                enemyProjectiles.removeValue(projectile, true);
//                projectile.destroyBody();
//            }
//        }

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
//        b2dr.render(world, gameCam.combined);

        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();

        player.draw(game.batch);
        ninja.draw(game.batch);
        ninja2.draw(game.batch);
        ninja3.draw(game.batch);
        ninja4.draw(game.batch);
        ninja5.draw(game.batch);
        men.draw(game.batch);
        men2.draw(game.batch);
        men3.draw(game.batch);
        men4.draw(game.batch);
        men5.draw(game.batch);
        bobby.draw(game.batch);
        for (Projectile projectile : player.getProjectiles()) {
            projectile.draw(game.batch);
        }
        for (EnemyProjectile projectile : enemyProjectiles) {
            projectile.draw(game.batch);
        }

        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        if (gameOver()) {
            System.out.println("game over true");
            game.setScreen(new GameOverScreen(game));
            dispose();
        }


    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
        // gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight()
        // / 2, 0);
        // gameCam.update();
        // game.batch.setProjectionMatrix(gameCam.combined);
    }

    public TiledMap getMap() {
        return map;
    }

    private void createBorders() {
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        EdgeShape edge = new EdgeShape();

        bdef.type = BodyDef.BodyType.StaticBody;

        // Créé frontières
        float mapWidth = DungeonExplorer.V_WIDTH / Player.PPM;
        float mapHeight = DungeonExplorer.V_HEIGHT / Player.PPM;

        // Créer les bords
        Body body = world.createBody(bdef);

        // Haut
//        edge.set(new Vector2(0, mapHeight), new Vector2(mapWidth, mapHeight));
//        fdef.shape = edge;
//        body.createFixture(fdef);

        // Bas
        edge.set(new Vector2(0, 0), new Vector2(mapWidth, 0));
        fdef.shape = edge;
        body.createFixture(fdef);

        // Gauche
        edge.set(new Vector2(0, 0), new Vector2(0, mapHeight));
        fdef.shape = edge;
        body.createFixture(fdef);

        // Droite
        edge.set(new Vector2(mapWidth, 0), new Vector2(mapWidth, mapHeight));
        fdef.shape = edge;
        body.createFixture(fdef);

        edge.dispose();
    }

    public World getWorld() {
        return world;
    }

    public boolean gameOver() {
        if (player.isDead() && player.getStateTimer() > 0) { //we might move this value to 2 or 3 if we add a tomb sprite
            return true;
        }
        return false;
    }

    private void initializeLevelToLayerMap() {
        levelToStoneLayerMap = new HashMap<>();
        levelToStoneLayerMap.put(2, 8); // Niveau 2, Layer 8
        levelToStoneLayerMap.put(3, 9); // Niveau 3, Layer 9
        levelToStoneLayerMap.put(4, 10); // Niveau 4, Layer 10
    }

//    private void removeStoneForCurrentLevel() {
//        Integer layerIndex = levelToStoneLayerMap.get(currentLevel); //get 2 donnera 8, get 3 donnera 9, etc.
//        if (layerIndex != null) {
//            TiledMapTileLayer layerToRemove = (TiledMapTileLayer) map.getLayers().get(layerIndex);
//            if (layerToRemove != null) {
//                map.getLayers().remove(layerToRemove);
//            }
//        }
//    }

    private void removeStoneForFistLevel() {
        Integer layerIndex = levelToStoneLayerMap.get(8); //get 2 donnera 8, get 3 donnera 9, etc.
        if (layerIndex != null) {
            TiledMapTileLayer layerToRemove = (TiledMapTileLayer) map.getLayers().get(layerIndex);
            if (layerToRemove != null) {
                map.getLayers().remove(layerToRemove);
            }
        }
    }

    private void removeStoneForSecondLevel() {
        Integer layerIndex = levelToStoneLayerMap.get(9); //get 2 donnera 8, get 3 donnera 9, etc.
        if (layerIndex != null) {
            TiledMapTileLayer layerToRemove = (TiledMapTileLayer) map.getLayers().get(layerIndex);
            if (layerToRemove != null) {
                map.getLayers().remove(layerToRemove);
            }
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
        backgroundMusic.dispose();
        ninja.dispose();
        ninja2.dispose();
        ninja3.dispose();
        ninja4.dispose();
        ninja5.dispose();
        men.dispose();
        men2.dispose();
        men3.dispose();
        men4.dispose();
        men5.dispose();
        bobby.dispose();
    }
}