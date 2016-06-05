package com.tiagoalmeida.elementalrun.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.tiagoalmeida.elementalrun.ElementalRun;
import com.tiagoalmeida.elementalrun.Tools.SaveHandler;

public class HighScoresScreen implements Screen  {

    private ElementalRun game;
    private Integer[] highScores;
    private BitmapFont font;

    public HighScoresScreen(ElementalRun game) {
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        this.game = game;

        SaveHandler.load();
        highScores = SaveHandler.gameData.getHighScores();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //clears the screen with white
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        game.batch.setProjectionMatrix(game.camera.combined);
        game.batch.begin();

        String s;
        GlyphLayout g = new GlyphLayout();

        s = "High Scores";
        g.setText(font, s);

        font.draw(game.batch, g, game.V_WIDTH / 2 - g.width / 2, game.V_HEIGHT - g.height /2);

        for(int i = 0; i < highScores.length; i++) {
            s = String.format("%06d", highScores[i]);
            g.setText(font, s);

            font.draw(game.batch, g, game.V_WIDTH / 2 - g.width / 2,
                    game.V_HEIGHT - ((i + 2) * game.V_HEIGHT / 6) - g.height / 2);
        }
        game.batch.end();
    }

    public void update(float delta) {
        handleInput();
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
        font.dispose();
    }
}
