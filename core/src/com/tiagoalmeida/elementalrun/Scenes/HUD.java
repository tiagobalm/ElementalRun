package com.tiagoalmeida.elementalrun.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tiagoalmeida.elementalrun.ElementalRun;

public class HUD implements Disposable {
    private ElementalRun game;
    public Stage stage;

    private Integer worldTimer;
    private float timeCount;
    private static Integer score;

    private static Label scoreLabel;
    private Label timeLabel, timeTextLabel, scoreTextLabel;

    public HUD(ElementalRun game) {
        this.game = game;
        worldTimer = 0;
        timeCount = 0;
        score = 0;

        stage = new Stage(new FitViewport(ElementalRun.V_WIDTH, ElementalRun.V_HEIGHT, new OrthographicCamera()));

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        scoreTextLabel = new Label("Score", new Label.LabelStyle(
                game.getAssets().get("size60.ttf", BitmapFont.class), Color.BLACK));
        timeTextLabel = new Label("Time", new Label.LabelStyle(
                game.getAssets().get("size60.ttf", BitmapFont.class), Color.BLACK));
        scoreLabel = new Label(String.format("%d", score), new Label.LabelStyle(
                game.getAssets().get("size60.ttf", BitmapFont.class), Color.BLACK));
        timeLabel = new Label(String.format("%d", worldTimer), new Label.LabelStyle(
                game.getAssets().get("size60.ttf", BitmapFont.class), Color.BLACK));

        table.add(timeTextLabel).expandX().padTop(10);
        table.add(scoreTextLabel).expandX().row();
        table.add(timeLabel).expandX().padTop(10);
        table.add(scoreLabel).expandX();

        stage.addActor(table);

    }

    public Integer getScore() { return score; }

    public void update(float deltaTime) {
        timeCount += deltaTime;

        if(timeCount >= 1) {
            worldTimer++;
            timeLabel.setText(String.format("%d", worldTimer));
            timeCount = 0;
        }
    }

    public static void addScore(int value) {
        score += value;
        scoreLabel.setText(String.format("%d", score));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
