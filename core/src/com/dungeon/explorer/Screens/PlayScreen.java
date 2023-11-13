package com.dungeon.explorer.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dungeon.explorer.DungeonExplorer;
import com.dungeon.explorer.Scenes.Hud;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dungeon.explorer.Sprites.Player;
import com.dungeon.explorer.Tools.B2WorldCreator;
import com.dungeon.explorer.Tools.WorldContactListener;

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

    private TextureAtlas atlas;
    private Music backgroundMusic;


    public PlayScreen(DungeonExplorer game) {
        atlas = new TextureAtlas("linkAndEnemies.atlas");

        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(DungeonExplorer.V_WIDTH / Player.PPM, DungeonExplorer.V_HEIGHT / Player.PPM, gameCam);
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("maps/DungeonRoom.tmx");
        renderer = new OrthogonalTiledMapRenderer(map,  1 / Player.PPM);
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, 0), true);

//        Uncomment this line and the b2dr.render(world, gameCam.combined); below to see the Box2D debug lines
        b2dr = new Box2DDebugRenderer();
        new B2WorldCreator(world, map);

        player = new Player(world, this);

        world.setContactListener(new WorldContactListener());
    }

    public TextureAtlas getAtlas() {
        return atlas;
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

        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            movement.y += velocity;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            movement.x += velocity;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            movement.x -= velocity;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            movement.y -= velocity;
        }

        player.b2body.setLinearVelocity(movement.scl(dt)); // Appliquer la vitesse multipliée par le delta pour un mouvement fluide
    }

    public void update(float dt) {
        handleInput(dt);

        world.step(1/60f, 6, 2);
        player.update(dt);
        hud.update(dt);
        gameCam.update();
        renderer.setView(gameCam);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        b2dr.render(world, gameCam.combined);

        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();

        player.draw(game.batch);

        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
//        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
//        gameCam.update();
//        game.batch.setProjectionMatrix(gameCam.combined);
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
    }
}