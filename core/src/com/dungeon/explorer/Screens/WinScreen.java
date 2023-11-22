package com.dungeon.explorer.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dungeon.explorer.DungeonExplorer;

public class WinScreen implements Screen {
    private final DungeonExplorer game;
    private final Viewport viewport;
    private final SpriteBatch batch;
    private final BitmapFont font;

    public WinScreen(DungeonExplorer game) {
        this.game = game;
        viewport = new FitViewport(DungeonExplorer.V_WIDTH, DungeonExplorer.V_HEIGHT);
        batch = new SpriteBatch();
        font = new BitmapFont();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        font.draw(batch, "Win!", 80, 100); // You can adjust the position
        batch.end();

        if (Gdx.input.justTouched()) {
            game.setScreen(new IntroScreen(game));
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY) && (!Gdx.input.isKeyJustPressed(Input.Keys.SPACE))) {
            game.setScreen(new IntroScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        batch.dispose();
        font.dispose();
    }
}
