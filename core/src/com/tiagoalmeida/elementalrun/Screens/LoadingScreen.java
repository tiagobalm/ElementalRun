package com.tiagoalmeida.elementalrun.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.tiagoalmeida.elementalrun.ElementalRun;
import com.tiagoalmeida.elementalrun.Tools.SaveHandler;

public class LoadingScreen implements Screen {

    private ElementalRun game;
    private ShapeRenderer shapeRenderer;
    private float progress;


    public LoadingScreen(ElementalRun game) {
        this.game = game;
        this.shapeRenderer = new ShapeRenderer();
        SaveHandler.load();
    }

    @Override
    public void show() {
        progress = 0f;
        queueAssests();
    }

    @Override
    public void render(float delta) {
        //Clears the screen with white
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.circle(game.camera.viewportWidth / 2, game.camera.viewportHeight / 2, 50);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.circle(game.camera.viewportWidth / 2, game.camera.viewportHeight / 2, progress * 50);
        shapeRenderer.end();

        game.batch.begin();
        game.font24.draw(game.batch, "Loading...", game.camera.viewportWidth / 2 - 42, game.camera.viewportHeight / 2 - 60);
        game.batch.end();
    }

    private void update(float delta) {
        progress = MathUtils.lerp(progress, game.getAssets().getProgress(), 0.1f);

        if(game.getAssets().update() && progress >= game.getAssets().getProgress() - 0.0005f) {
            game.setScreen(game.splashScreen);
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
        shapeRenderer.dispose();
    }

    private void queueAssests() {
        game.getAssets().load("TariLogo.png", Texture.class);
        game.getAssets().load("Player/player.png", Texture.class);
        game.getAssets().load("firesheet.png", Texture.class);
        game.getAssets().load("watersheet.png", Texture.class);
        game.getAssets().load("highScores.png", Texture.class);
        game.getAssets().load("Levels/Unlocked/levels_unlocked.pack", TextureAtlas.class);
        game.getAssets().load("Levels/Locked/levels_locked.pack", TextureAtlas.class);
    }
}
