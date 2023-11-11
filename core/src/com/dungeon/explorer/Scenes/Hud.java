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
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dungeon.explorer.DungeonExplorer;

import java.util.ArrayList;

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

    private Integer worldTimer;
    private float timeCount;
    private Integer score;
    private Integer level;
    private Integer dungeon;

    private static int lifePoints;
    private static ArrayList<Image> lifeImages;

    private static Table bottomTable;
    
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
        lifePoints = 3;
        lifeImages = new ArrayList<Image>();
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
        
        bottomTable = new Table();
        bottomTable.bottom();
        bottomTable.setFillParent(true);

        for (int i = 0; i < lifePoints; i++) {
            lifeImages.add(new Image(heartTexture));
            bottomTable.add(lifeImages.get(i)).padBottom(10);
        }
        
        stage.addActor(bottomTable);
    }
    
    public static void addLifePoints(int HP) {
        for (int i = 0; i < HP; i++) {
            lifePoints++;
            System.out.println("Your life points increased!");
            Texture heartTexture = new Texture("textures/heart.png");
            Image heartImage = new Image(heartTexture);
            lifeImages.add(heartImage);
            bottomTable.add(heartImage).padBottom(10);
        }

    }

    public void update(float dt) {
        timeCount += dt;
        if (timeCount >= 1) {
            worldTimer++;
            counterLabel.setText(String.format("%04d", worldTimer));
            timeCount = 0;
        }
    }

    public void addDungeon() {
        dungeon++;
        dungeonLabel.setText(String.format("%01d", dungeon));
    }

    public void addLevel() {
        level++;
        levelLabel.setText(String.format("%01d", level));
    }

    public void dispose() {
        stage.dispose();
    }
}
