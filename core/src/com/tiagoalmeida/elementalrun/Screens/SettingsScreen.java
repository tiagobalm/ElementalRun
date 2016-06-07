package com.tiagoalmeida.elementalrun.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tiagoalmeida.elementalrun.FutureRun;

/**
 * Settings screen class.
 */
public class SettingsScreen implements Screen  {

    private FutureRun game;
    private Table table;
    private Stage stage;
    private Image controls;
    private ImageButton back, sound;
    private Label soundLabel;

    public boolean debug;

    /**
     * Settings screen constructor.
     * @param game Main game.
     */
    public SettingsScreen(FutureRun game) {
        this.game = game;

        stage = new Stage(new FitViewport(game.V_WIDTH, game.V_HEIGHT, game.camera));
        Gdx.input.setInputProcessor(stage);
        table = new Table();

        debug = false;

        if(debug)
            table.debug();
        table.setBounds(0, 0, game.V_WIDTH, game.V_HEIGHT);

        game.getAssets().get("UI/Back.png", Texture.class).setFilter(
                Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        back = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.getAssets().get("UI/Back.png", Texture.class))));
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setMainMenuScreen();
            }
        });

        soundLabel = new Label(String.format("Sound"), new Label.LabelStyle(
                game.getAssets().get("size120.ttf", BitmapFont.class), Color.BLACK));

        if(game.withSound) {
            sound = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.getAssets().get("UI/Sound.png", Texture.class))));
            sound.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    setSound(false);
                }
            });
        } else {
            sound = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.getAssets().get("UI/NoSound.png", Texture.class))));
            sound.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    setSound(true);
                }
            });
        }

        game.getAssets().get("UI/Sound.png", Texture.class).setFilter(
                Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        game.getAssets().get("UI/NoSound.png", Texture.class).setFilter(
                Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        game.getAssets().get("UI/Controls.png", Texture.class).setFilter(
                Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        controls = new Image(game.getAssets().get("UI/Controls.png", Texture.class));
    }

    /**
     * Enables or disables sound according to argument.
     * @param withSound
     */
    private void setSound(boolean withSound) {
        table.clearChildren();
        stage.clear();

        game.withSound = withSound;

        if(withSound) {
            game.getAssets().get("Audio/Music/MainMenu.wav", Music.class).play();
            sound = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.getAssets().get("UI/Sound.png", Texture.class))));
            sound.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    setSound(false);
                }
            });
        } else {
            game.getAssets().get("Audio/Music/MainMenu.wav", Music.class).stop();
            sound = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.getAssets().get("UI/NoSound.png", Texture.class))));
            sound.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    setSound(true);
                }
            });
        }
        constructStage();
    }

    /**
     * Constructs settings stage.
     */
    private void constructStage() {
        table.add(back).expandX().top().left().pad(50, 100, 50, 0).row();
        table.add().row();
        table.add(soundLabel).expandX().right();
        table.add(sound).expandX().left().row();
        table.add(controls).expandX().colspan(2).center().pad(100,0,0,0);
        stage.addActor(table);
    }

    /**
     * Changes to main menu screen.
     */
    private void setMainMenuScreen() {
        stage.addAction(Actions.sequence(Actions.fadeOut(0.4f), Actions.run(new Runnable() {
            @Override
            public void run() {
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        })));
    }

    /**
     * Override of show method. Contructs the stage by calling construct stage.
     */
    @Override
    public void show() {
        constructStage();
        stage.addAction(Actions.sequence(Actions.alpha(0f), Actions.fadeIn(0.4f)));
    }

    /**
     * Override of render method. Draws the stage to the screen.
     * @param delta Time passed since last call.
     */
    @Override
    public void render(float delta) {
        //clears the screen with white
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);
        stage.draw();
    }

    /**
     * Updates the stage.
     * @param delta Time passed since last call.
     */
    public void update(float delta) {
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
