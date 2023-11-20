package com.dungeon.explorer.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.dungeon.explorer.DungeonExplorer;

public class IntroScreen extends ScreenAdapter {
    private DungeonExplorer game;
    private Texture backgroundTexture;
    private Texture buttonBeginTexture;
    private Texture logoDungeonExplorerTexture;
    private Stage stage;

    public IntroScreen(final DungeonExplorer game) {
        this.game = game;
        backgroundTexture = new Texture("assetsIntro/backgroundIntro.png");
        buttonBeginTexture = new Texture("assetsIntro/buttonBegin.png");
        logoDungeonExplorerTexture = new Texture("assetsIntro/logoDungeonExplorer.png");

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Image buttonBeginImage = new Image(buttonBeginTexture);
        buttonBeginImage.setPosition(500, 150); // Position du bouton
        buttonBeginImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PlayScreen(game));
            }

            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
            }

            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
            }
        });
        stage.addActor(buttonBeginImage);
    }

    @Override
    public void render(float delta) {
        game.batch.begin();
        game.batch.draw(backgroundTexture, 150, 125, DungeonExplorer.V_WIDTH, DungeonExplorer.V_HEIGHT);
        game.batch.draw(logoDungeonExplorerTexture, 300, 300);
        game.batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        buttonBeginTexture.dispose();
        logoDungeonExplorerTexture.dispose();
        stage.dispose();
    }
}
