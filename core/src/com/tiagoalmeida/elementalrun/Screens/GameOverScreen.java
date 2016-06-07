package com.tiagoalmeida.elementalrun.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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

/**
 * Game over screen class.
 */
public class GameOverScreen implements Screen {
    private Viewport viewport;

    private FutureRun game;
    private int level;
    private Image win;
    private ImageButton replay, mainmenu;
    private Label scoreLabel;
    private Table table;
    private Stage stage;

    private boolean debug;

    /**
     * Game over screen constructor.
     * @param game Main game.
     * @param level Level played.
     */
    public GameOverScreen(FutureRun game, int level){
        this.game = game;
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

    }

    /**
     * Creates stage of the screen.
     */
    @Override
    public void show() {

        //You win Image
        game.getAssets().get("UI/gameover.png", Texture.class).setFilter(
                Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        win = new Image(game.getAssets().get("UI/gameover.png", Texture.class));
        table.add(win).expandX().center().colspan(4).row();

        //Replay Button
        game.getAssets().get("UI/replay.png", Texture.class).setFilter(
                Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        replay = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.getAssets().get("UI/replay.png", Texture.class))));
        replay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setPlayScreen();
            }
        });
        table.add(replay);
        table.add();

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
        table.add();
        table.add(mainmenu);

        stage.addActor(table);

    }

    /**
     * Changes to main menu screen.
     */
    private void setMainMenuScreen() {
        game.setScreen(new MainMenuScreen(game));
        dispose();
    }

    /**
     * Changes to play screen.
     */
    private void setPlayScreen() {
        game.setScreen(new PlayScreen(game, level));
        dispose();
    }

    /**
     * Override of render method. Clears the screen to white, updates and draws the stage to the screen.
     * @param delta Time passed since last call.
     */
    @Override
    public void render(float delta) {
        //Background color white
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        stage.draw();
    }

    /**
     * Updates the stage.
     * @param delta Time passed since last call.
     */
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

    /**
     * Override of dispose method. Disposes stage.
     */
    @Override
    public void dispose() {
        stage.dispose();
    }
}
