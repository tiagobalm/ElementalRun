package com.tiagoalmeida.elementalrun.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tiagoalmeida.elementalrun.ElementalRun;

public class SplashScreen implements Screen {
    private ElementalRun game;
    private Stage stage;

    private Image splashImage;

    public SplashScreen(ElementalRun game) {
        this.game = game;
        stage = new Stage(new FitViewport(game.V_WIDTH, game.V_HEIGHT, game.camera));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Texture splashTexture = game.getAssets().get("badlogic.jpg", Texture.class);
        splashImage = new Image(splashTexture);
        splashImage.setOrigin(splashTexture.getWidth() / 2, splashTexture.getHeight() / 2);

        Runnable transitionRunnable = new Runnable() {
            @Override
            public void run() {
                game.setScreen(new MainMenu(game));
            }
        };

        stage.addActor(splashImage);

        splashImage.setPosition(game.V_WIDTH / 2 - splashImage.getWidth() / 2, game.V_HEIGHT / 2 - splashImage.getHeight() / 2);
        splashImage.addAction(Actions.sequence(Actions.alpha(0f),
                Actions.scaleTo(0.5f, 0.5f),
                Actions.parallel(Actions.scaleTo(0.8f, 0.8f, 1.5f), Actions.alpha(1f, 1.5f)),
                Actions.delay(0.5f),
                Actions.parallel(Actions.scaleTo(0.5f, 0.5f, 1.5f), Actions.alpha(0f, 1.5f)), Actions.run(transitionRunnable)));
    }

    @Override
    public void render(float delta) {
        //Clears the screen with white
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        stage.draw();
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
        stage.dispose();
        Gdx.app.log("Disposed", "Splash Menu");
    }
}
