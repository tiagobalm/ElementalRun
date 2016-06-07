package com.tiagoalmeida.elementalrun.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tiagoalmeida.elementalrun.FutureRun;

public class MainMenuScreen implements Screen {
    private final FutureRun game;
    private Stage stage;
    private Table table;
    private Image title;

    private Array<Image> background;

    private TextureRegionDrawable highScoresTexture;
    private ImageButton highScores, clickContinue, clickExit, settings;

    private Array<TextureRegion> playerTexture;
    private Sprite player;
    private int playerCurrentTexture;
    private boolean jumped, backwards;

    private boolean debug;

    public MainMenuScreen(FutureRun game) {
        this.game = game;
        this.stage = new Stage(new FitViewport(game.V_WIDTH, game.V_HEIGHT, game.camera));
        Gdx.input.setInputProcessor(stage);

        debug = false;
        jumped = false;
        backwards = false;

        this.table = new Table();
        this.table.setBounds(0, 0, game.V_WIDTH, game.V_HEIGHT);
        if(debug)
            this.table.debug();

        background = new Array<Image>();

        String s;
        for(int i = 1; i < 7; i++) {
            s = String.format("UI/Background/background%d.png", i);
            background.add(new Image(game.getAssets().get(s, Texture.class)));
            background.get(i - 1).setX((i - 1) * background.get(i - 1).getWidth());
        }

        highScoresTexture = new TextureRegionDrawable();
        this.highScores = new ImageButton(highScoresTexture);
        playerTexture = new Array<TextureRegion>();
        playerCurrentTexture = 0;

        for(int i = 1; i < 37 ; i++) {
            s = String.format("player%d",i);
            playerTexture.add(game.getAssets().get("UI/MainMenuPlayer.pack", TextureAtlas.class).findRegion(s));
        }
        player = new Sprite(playerTexture.get(playerCurrentTexture));
        player.setPosition(0, 3 * game.V_HEIGHT / 4);
    }

    @Override
    public void show() {
        game.getAssets().get("UI/leaderboard.png", Texture.class).setFilter(
                Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        highScoresTexture.setRegion(new TextureRegion(game.getAssets().get("UI/leaderboard.png", Texture.class)));
        highScores.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setHighScores();
            }
        });

        game.getAssets().get("UI/Title.png", Texture.class).setFilter(
                Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        title = new Image(game.getAssets().get("UI/Title.png", Texture.class));
        table.add(title).expandY().center().colspan(2).top().pad(10, 100, 0, 100).row();

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
        table.add(insideTable).expandX().expandY();

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

        game.getAssets().get("UI/Settings.png", Texture.class).setFilter(
                Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        settings = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.getAssets().get("UI/Settings.png", Texture.class))));
        settings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setSettigsScreen();
            }
        });
        table.add(settings).bottom().left();
        table.add(highScores).width(game.V_WIDTH / 4).height(game.V_HEIGHT / 4).colspan(2).right();
        stage.addActor(table);

        stage.addAction(Actions.sequence(Actions.alpha(0f), Actions.fadeIn(0.2f)));
    }

    private void setHighScores() {
        stage.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.run(new Runnable() {
            @Override
            public void run() {
                game.setScreen(new HighScoresScreen(game));
                dispose();
            }
        })));
    }
    private void setLevelsScreen() {
        stage.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.run(new Runnable() {
            @Override
            public void run() {
                game.setScreen(new LevelScreen(game));
                dispose();
            }
        })));
    }

    private void setSettigsScreen() {
        stage.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.run(new Runnable() {
            @Override
            public void run() {
                game.setScreen(new SettingsScreen(game));
                dispose();
            }
        })));
    }

    @Override
    public void render(float delta) {
        //clears the screen with white
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        game.batch.setProjectionMatrix(game.camera.combined);
        game.batch.begin();
        player.draw(game.batch);

        for(int i = 0; i < 6; i++)
            background.get(i).draw(game.batch, 0.5f);

        game.batch.end();

        stage.draw();

    }

    private void update(float delta) {
        playerCurrentTexture = ++playerCurrentTexture % playerTexture.size;
        player.setRegion(playerTexture.get(playerCurrentTexture));

        if (player.getX() < 100 && backwards)
            backwards = false;
        if(player.getX() > (game.V_WIDTH - 100 - player.getWidth() / 2) && !backwards)
            backwards = true;

        if(!backwards) {
            player.setX(player.getX() + 8);
        }
        else {
            player.flip(true, false);
            player.setX(player.getX() - 8);
        }

        for(int i = 0; i < 6; i++) {

            background.get(i).setX(background.get(i).getX() - 10);
            if(background.get(i).getX() + background.get(i).getWidth() <= 0)
                background.get(i).setX(background.get(i).getX() + background.size * background.get(i).getWidth());
        }

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
