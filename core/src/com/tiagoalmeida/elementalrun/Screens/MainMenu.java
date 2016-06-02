package com.tiagoalmeida.elementalrun.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tiagoalmeida.elementalrun.ElementalRun;

public class MainMenu implements Screen {
    private ElementalRun game;
    private Stage stage;
    private ShapeRenderer shapeRenderer;
    private Skin skin;

    private TextButton buttonPlay, buttonExit;

    public MainMenu(ElementalRun game) {
        this.game = game;
        this.stage = new Stage(new FitViewport(game.V_WIDTH, game.V_HEIGHT, game.camera));
        Gdx.input.setInputProcessor(stage);

        this.shapeRenderer = new ShapeRenderer();
        this.skin = new Skin();
        this.skin.addRegions(game.getAssets().get("UI/uiskin.atlas", TextureAtlas.class));
        this.skin.add("default-font", game.font24);
        this.skin.load(Gdx.files.internal("UI/uiskin.json"));
        
        initButtons();
    }

    private void initButtons() {

        buttonPlay = new TextButton("Play", skin, "default");
        buttonPlay.setSize(280, 60);
        buttonPlay.setPosition(game.V_WIDTH / 2 - buttonPlay.getWidth() / 2, game.V_WIDTH / 2 - buttonPlay.getHeight() / 2 + 50);
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PlayScreen(game));
                dispose();
            }
        });
        stage.addActor(buttonPlay);

        buttonExit = new TextButton("Exit", skin, "default");
        buttonExit.setSize(280, 60);
        buttonExit.setPosition(game.V_WIDTH / 2 - buttonExit.getWidth() / 2, game.V_WIDTH / 2 - buttonExit.getHeight() / 2 - 50);
        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        stage.addActor(buttonExit);
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

        game.batch.begin();
        game.font24.draw(game.batch, "Main Menu", 20, 20);
        game.batch.end();

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
        Gdx.app.log("Disposed", "Main Menu");
    }
}
