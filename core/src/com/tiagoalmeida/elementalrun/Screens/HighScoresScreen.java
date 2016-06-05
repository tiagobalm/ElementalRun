package com.tiagoalmeida.elementalrun.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tiagoalmeida.elementalrun.ElementalRun;
import com.tiagoalmeida.elementalrun.Tools.SaveHandler;

public class HighScoresScreen implements Screen  {

    private ElementalRun game;
    private Integer[] highScores;
    private Table table;
    private Stage stage;
    private Label title, firstScore, secondScore, thirdScore;

    public HighScoresScreen(ElementalRun game) {
        this.game = game;

        stage = new Stage(new FitViewport(game.V_WIDTH, game.V_HEIGHT, game.camera));
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.debug();
        table.setBounds(0, 0, game.V_WIDTH, game.V_HEIGHT);
        title = new Label("High Scores", new Label.LabelStyle(
                game.getAssets().get("size180.ttf", BitmapFont.class), Color.BLACK));

        SaveHandler.load();
        highScores = SaveHandler.gameData.getHighScores();

        firstScore = new Label(String.format("%d", highScores[0]), new Label.LabelStyle(
                game.getAssets().get("size120.ttf", BitmapFont.class), Color.BLACK));
        secondScore = new Label(String.format("%d", highScores[1]), new Label.LabelStyle(
                game.getAssets().get("size120.ttf", BitmapFont.class), Color.BLACK));
        thirdScore = new Label(String.format("%d", highScores[2]), new Label.LabelStyle(
                game.getAssets().get("size120.ttf", BitmapFont.class), Color.BLACK));
    }

    @Override
    public void show() {
        table.add(title).expandX().row();
        table.add().row();
        table.add(firstScore).center().row();
        table.add(secondScore).center().row();
        table.add(thirdScore).center().row();
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        //clears the screen with white
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);
        stage.draw();
    }

    public void update(float delta) {
        handleInput();
        stage.act();
    }

    private void handleInput() {
        if(Gdx.input.isTouched()) {
            game.setScreen(new MainMenuScreen(game));
            dispose();
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

    @Override
    public void dispose() {
        System.out.println("Disposing High Scores Screen");
        stage.dispose();
    }
}
