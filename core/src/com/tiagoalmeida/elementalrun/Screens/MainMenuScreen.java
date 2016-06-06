package com.tiagoalmeida.elementalrun.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tiagoalmeida.elementalrun.ElementalRun;

public class MainMenuScreen implements Screen {
    private final ElementalRun game;
    private Stage stage;
    private Table table;
    private Image title;

    private TextureRegionDrawable highScoresTexture;
    private ImageButton highScores, clickContinue, clickExit;

    private boolean debug;

    public MainMenuScreen(ElementalRun game) {
        this.game = game;
        this.stage = new Stage(new FitViewport(game.V_WIDTH, game.V_HEIGHT, game.camera));
        Gdx.input.setInputProcessor(stage);

        debug = false;

        this.table = new Table();
        this.table.setBounds(0, 0, game.V_WIDTH, game.V_HEIGHT);
        if(debug)
            this.table.debug();

        highScoresTexture = new TextureRegionDrawable();
        this.highScores = new ImageButton(highScoresTexture);

    }

    private void setHighScores() {
        game.setScreen(new HighScoresScreen(game));
    }
    private void setLevelsScreen() { game.setScreen(new LevelScreen(game)); }

    @Override
    public void show() {
        highScoresTexture.setRegion(new TextureRegion(game.getAssets().get("UI/cup.png", Texture.class)));
        highScores.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setHighScores();
            }
        });

        game.getAssets().get("UI/Title.png", Texture.class).setFilter(
                Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        title = new Image(game.getAssets().get("UI/Title.png", Texture.class));
        table.add(title).expandX().expandY().center().colspan(2).row();

        //Play button
        game.getAssets().get("UI/playbutton.png", Texture.class).setFilter(
                Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        clickContinue = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.getAssets().get("UI/playbutton.png", Texture.class))));
        clickContinue.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setLevelsScreen();
            }
        });
        Table insideTable = new Table();
        if(debug)
            insideTable.debug();
        insideTable.setTransform(true);
        insideTable.setOrigin(clickContinue.getWidth() / 2, clickContinue.getHeight() / 2);
        insideTable.addAction(Actions.forever(Actions.sequence(Actions.delay(5f),Actions.rotateBy(360f, 0.4f))));
        insideTable.add(clickContinue);
        table.add(insideTable).expandX();

        //Exit Button
        game.getAssets().get("UI/exitbutton.png", Texture.class).setFilter(
                Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        clickExit = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.getAssets().get("UI/exitbutton.png", Texture.class))));
        clickExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        insideTable = new Table();
        if(debug)
            insideTable.debug();
        insideTable.setTransform(true);
        insideTable.setOrigin(clickExit.getWidth() / 2, clickExit.getHeight() / 2);
        insideTable.addAction(Actions.forever(Actions.sequence(Actions.delay(5f), Actions.rotateBy(360f, 0.4f))));
        insideTable.add(clickExit);
        table.add(insideTable).expandX().row();

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
