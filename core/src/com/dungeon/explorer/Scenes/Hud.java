package com.dungeon.explorer.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dungeon.explorer.DungeonExplorer;

public class Hud {
    public Stage stage;
    private Viewport viewport;

    private Integer worldTimer;
    private float timeCount;
    private Integer score;
    private Integer level;
    private Integer dungeon;

    private int lifePoints;
    private Image[] lifeImages; 
    
    Label counterLabel;
    Label scoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label dungeonLabel;


    public Hud(SpriteBatch sb) {
        worldTimer = 0;
        timeCount = 0;
        score = 0;
        dungeon = 0;
        level = 0;
        lifePoints = 10;
        lifeImages = new Image[lifePoints];
        Texture heartTexture = new Texture("textures/heart.png");

        viewport = new FitViewport(DungeonExplorer.V_WIDTH, DungeonExplorer.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table topTable = new Table();
        topTable.top();
        topTable.setFillParent(true);

        counterLabel = new Label(String.format("%04d", worldTimer), new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%01d", score), new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIMER", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label(String.format("%01d", level), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        dungeonLabel = new Label("DUNGEON", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("ROOM", new Label.LabelStyle(new BitmapFont(), Color.WHITE));


        topTable.add(timeLabel).expandX().padTop(10);
        topTable.add(dungeonLabel).expandX().padTop(10);
        topTable.add(worldLabel).expandX().padTop(10);
        topTable.row();
        topTable.add(counterLabel).expandX();
        topTable.add(scoreLabel).expandX();
        topTable.add(levelLabel).expandX();

        stage.addActor(topTable);
        
        Table bottomTable = new Table();
        bottomTable.bottom();
        bottomTable.setFillParent(true);

        for (int i = 0; i < lifePoints; i++) {
            bottomTable.add(lifeImages[i] = new Image(heartTexture)).padBottom(10);
        }
        
        stage.addActor(bottomTable);
    }

}
