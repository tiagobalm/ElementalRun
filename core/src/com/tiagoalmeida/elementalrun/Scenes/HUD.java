package com.tiagoalmeida.elementalrun.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tiagoalmeida.elementalrun.FutureRun;

/**
 * Game HUD class.
 */
public class HUD implements Disposable {
    private FutureRun game;
    public Stage stage;

    private Integer worldTimer;
    private float timeCount;
    private static Integer score;

    private static Label scoreLabel;
    private Label timeLabel, timeTextLabel, scoreTextLabel;

    /**
     * HUD constructor.
     * @param game Main game.
     */
    public HUD(FutureRun game) {
        this.game = game;
        worldTimer = 0;
        timeCount = 0;
        score = 0;

        stage = new Stage(new FitViewport(FutureRun.V_WIDTH, FutureRun.V_HEIGHT, new OrthographicCamera()));

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

    /**
     *
     * @return the score of the player.
     */
    public Integer getScore() { return score; }

    /**
     * Updates the stage labels.
     * @param deltaTime Time passed since last call.
     */
    public void update(float deltaTime) {
        timeCount += deltaTime;

        if(timeCount >= 1) {
            worldTimer++;
            timeLabel.setText(String.format("%d", worldTimer));
            timeCount = 0;
        }
    }

    /**
     * Adds to the score the value passed as argument.
     * @param value Value to add to the score.
     */
    public static void addScore(int value) {
        score += value;
        scoreLabel.setText(String.format("%d", score));
    }

    /**
     * Override of dispose method. Disposed the stage.
     */
    @Override
    public void dispose() {
        stage.dispose();
    }
}
