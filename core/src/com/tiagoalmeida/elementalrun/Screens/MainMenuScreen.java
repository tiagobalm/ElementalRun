package com.tiagoalmeida.elementalrun.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tiagoalmeida.elementalrun.ElementalRun;

public class MainMenuScreen implements Screen {
    private final ElementalRun game;
    private Stage stage;
    private Table table;
    private Label title, clickContinue, clickExit;

    private TextureRegionDrawable highScoresTexture;
    private ImageButton highScores;

    public MainMenuScreen(ElementalRun game) {
        this.game = game;
        this.stage = new Stage(new FitViewport(game.V_WIDTH, game.V_HEIGHT, game.camera));
        Gdx.input.setInputProcessor(stage);

        this.table = new Table();
        this.table.setBounds(0, 0, game.V_WIDTH, game.V_HEIGHT);
        this.table.debug();

        highScoresTexture = new TextureRegionDrawable();
        this.highScores = new ImageButton(highScoresTexture);
        highScores.addListener(new ClickListener() {
           @Override
           public void clicked(InputEvent event, float x, float y) {
              setHighScores();
           }
       });
    }

    private void setHighScores() {
        game.setScreen(new HighScoresScreen(game));
    }
    private void setLevelsScreen() {
        game.setScreen(new LevelScreen(game));
    }

    @Override
    public void show() {
        highScoresTexture.setRegion(new TextureRegion(game.getAssets().get("highScores.png", Texture.class)));

        title = new Label("Elemental Run", new Label.LabelStyle(
                game.getAssets().get("size180.ttf", BitmapFont.class), Color.BLACK));
        table.add(title).expandX().center().colspan(2).row();

        clickContinue = new Label("Start", new Label.LabelStyle(
                game.getAssets().get("size120.ttf", BitmapFont.class), Color.BLACK));
        clickContinue.addAction(Actions.forever(Actions.sequence(Actions.parallel(Actions.moveBy(0f, 10f, 1f), Actions.alpha(0.5f, 1f)),
                Actions.parallel(Actions.moveBy(0f, -10f, 1f), Actions.alpha(1f, 1f)))));
        clickContinue.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setLevelsScreen();
            }
        });
        table.add(clickContinue).expandY();

        clickExit = new Label("Exgt", new Label.LabelStyle(
                game.getAssets().get("size120.ttf", BitmapFont.class), Color.BLACK));
        clickExit.addAction(Actions.forever(Actions.sequence(Actions.parallel(Actions.moveBy(0f, 10f, 1f), Actions.alpha(0.5f, 1f)),
                Actions.parallel(Actions.moveBy(0f, -10f, 1f), Actions.alpha(1f, 1f)))));
        clickExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        table.add(clickExit).expandY().row();

        table.add(highScores).width(game.V_WIDTH / 4).height(game.V_HEIGHT / 4).colspan(2).right();
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
        System.out.println("Disposing Main Menu Screen");
        this.stage.dispose();
    }
}
