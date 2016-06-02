package com.tiagoalmeida.elementalrun.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.tiagoalmeida.elementalrun.ElementalRun;

/**
 * Created by tiago on 02-06-2016.
 */
public class MainMenu implements Screen {
    private ElementalRun  game;
    private Stage stage;

    public MainMenu(ElementalRun game) {
        this.game = game;
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //Clears the screen with black
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        stage.draw();

        game.batch.begin();
        game.font.draw(game.batch, "Main Menu", 120, 120);
        game.batch.end();
    }

    private void update(float delta) {
        stage.act(delta);
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

    }
}
