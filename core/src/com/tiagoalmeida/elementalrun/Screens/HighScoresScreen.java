package com.tiagoalmeida.elementalrun.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tiagoalmeida.elementalrun.FutureRun;
import com.tiagoalmeida.elementalrun.Tools.SaveHandler;

/**
 * High scores screen class.
 */
public class HighScoresScreen implements Screen  {

    private FutureRun game;
    private Integer[] highScores;
    private Table table;
    private Stage stage;
    private Image title;
    private Label firstScore, secondScore, thirdScore;

    public boolean debug;

    /**
     * High scores screen constructor.
     * @param game Main game.
     */
    public HighScoresScreen(FutureRun game) {
        this.game = game;

        stage = new Stage(new FitViewport(game.V_WIDTH, game.V_HEIGHT, game.camera));
        Gdx.input.setInputProcessor(stage);

        debug = false;

        table = new Table();
        if(debug)
            table.debug();
        table.setBounds(0, 0, game.V_WIDTH, game.V_HEIGHT);
        title = new Image(game.getAssets().get("UI/highScores.png", Texture.class));

        SaveHandler.load();
        highScores = SaveHandler.gameData.getHighScores();

        firstScore = new Label(String.format("%d", highScores[0]), new Label.LabelStyle(
                game.getAssets().get("size120.ttf", BitmapFont.class), Color.BLACK));
        secondScore = new Label(String.format("%d", highScores[1]), new Label.LabelStyle(
                game.getAssets().get("size120.ttf", BitmapFont.class), Color.BLACK));
        thirdScore = new Label(String.format("%d", highScores[2]), new Label.LabelStyle(
                game.getAssets().get("size120.ttf", BitmapFont.class), Color.BLACK));
    }

    /**
     * Override of show method. Creates the stage.
     */
    @Override
    public void show() {
        table.add(title).expandX().top().row();
        table.add().row();
        table.add(firstScore).center().pad(200, 0, 50, 0).row();
        table.add(secondScore).center().pad(50, 0, 50, 0).row();
        table.add(thirdScore).center().pad(50, 0, 50, 0).row();
        stage.addActor(table);

        stage.addAction(Actions.sequence(Actions.alpha(0f), Actions.fadeIn(0.4f)));

    }

    /**
     * Override of render method. Cleears the screen with white, updates and draws the stage to the screen.
     * @param delta Time passed since last call.
     */
    @Override
    public void render(float delta) {
        //clears the screen with white
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);
        stage.draw();
    }

    /**
     * Updates the stage.
     * @param delta Time passed since last call.
     */
    public void update(float delta) {
        handleInput();
        stage.act();
    }

    /**
     * Handles touch input.
     */
    private void handleInput() {
        if(Gdx.input.justTouched()) {
            stage.addAction(Actions.sequence(Actions.fadeOut(0.4f), Actions.run(new Runnable() {
                @Override
                public void run() {
                    game.setScreen(new MainMenuScreen(game));
                    dispose();
                }
            })));
        }
    }

    @Override
    public void resize(int width, int height) {

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

    /**
     * Override of dispose method. Disposes stage.
     */
    @Override
    public void dispose() {
        stage.dispose();
    }
}
