package com.tiagoalmeida.elementalrun.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tiagoalmeida.elementalrun.FutureRun;
import com.tiagoalmeida.elementalrun.Tools.SaveHandler;

public class WinnerScreen implements Screen {
    private Viewport viewport;

    private FutureRun game;
    private int level;
    private Integer score;
    private Image win;
    private ImageButton replay, mainmenu, nextLevel;
    private Label scoreLabel;
    private Table table;
    private Stage stage;

    private boolean debug;

    public WinnerScreen(FutureRun game, Integer score, int level){
        this.game = game;
        this.score = score;
        this.level = level;
        viewport = new FitViewport(FutureRun.V_WIDTH, FutureRun.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);
        Gdx.input.setInputProcessor(stage);

        this.debug = false;

        table = new Table();
        this.table.setBounds(0, 0, game.V_WIDTH, game.V_HEIGHT);
        table.center();
        if(debug)
            table.debug();

        SaveHandler.gameData.addHighScore(score);
        SaveHandler.gameData.setCurrentLevel(level + 1);
        SaveHandler.save();
    }

    @Override
    public void show() {

        //You win Image
        game.getAssets().get("UI/win.png", Texture.class).setFilter(
                Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        win = new Image(game.getAssets().get("UI/win.png", Texture.class));
        table.add(win).expandX().center().colspan(3).row();

        //Score label
        scoreLabel = new Label(score + "", new Label.LabelStyle(
                game.getAssets().get("size100.ttf", BitmapFont.class), Color.BLACK));
        table.add(scoreLabel).expandX().center().colspan(4).row();

        //Replay Button
        game.getAssets().get("UI/replay.png", Texture.class).setFilter(
                Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        replay = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.getAssets().get("UI/replay.png", Texture.class))));
        replay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setPlayScreen(level);
            }
        });
        table.add(replay).expandX();

        game.getAssets().get("UI/nextLevel.png", Texture.class).setFilter(
                Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        nextLevel = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.getAssets().get("UI/nextLevel.png", Texture.class))));
        nextLevel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setPlayScreen(level + 1);
            }
        });
        table.add(nextLevel).expandX();

        //Main Menu Button
        game.getAssets().get("UI/mainmenu.png", Texture.class).setFilter(
                Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        mainmenu = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.getAssets().get("UI/mainmenu.png", Texture.class))));
        mainmenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setMainMenuScreen();
            }
        });
        table.add(mainmenu).expandX();

        stage.addActor(table);
    }

    private void setMainMenuScreen() {
        game.setScreen(new MainMenuScreen(game));
        dispose();
    }

    private void setPlayScreen(int level) {
        if(level < 4 && level <= SaveHandler.gameData.getCurrentLevel())
            game.setScreen(new PlayScreen(game, level));
        dispose();
    }

    @Override
    public void render(float delta) {
        //Background color white
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        stage.draw();
    }

    private void update(float delta) {
        stage.act();
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
    }
}
