package com.tiagoalmeida.elementalrun.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tiagoalmeida.elementalrun.FutureRun;

/**
 * Splash screen class.
 */
public class SplashScreen implements Screen {
    private FutureRun game;
    private Stage stage;

    private Image splashImage;
    private Runnable run, setPlayScreen;

    /**
     * Splash screen constructor.
     * @param game Main game.
     */
    public SplashScreen(FutureRun game) {
        this.game = game;
        stage = new Stage(new FitViewport(game.V_WIDTH, game.V_HEIGHT, game.camera));
        run = new Runnable() {
            @Override
            public void run() {
                queueAssets();
            }
        };
        setPlayScreen = new Runnable() {
            @Override
            public void run() {
                setMainMenuScreenMethod();
            }
        };
        game.getAssets().load("UI/TariLogo.png", Texture.class);
        game.getAssets().finishLoadingAsset("UI/TariLogo.png");
        game.getAssets().get("UI/TariLogo.png", Texture.class).setFilter(
                Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        splashImage = new Image(game.getAssets().get("UI/TariLogo.png", Texture.class));
    }

    /**
     * Changes to main menu.
     */
    private void setMainMenuScreenMethod() {
        game.setScreen(new MainMenuScreen(game));
    }

    /**
     * Loads all the assests used by the game.
     */
    private void queueAssets() {
        game.getAssets().load("Player/PlayerBlue.pack", TextureAtlas.class);
        game.getAssets().load("Player/PlayerOrange.pack", TextureAtlas.class);
        game.getAssets().load("UI/leaderboard.png", Texture.class);
        game.getAssets().load("Levels/Unlocked/levels_unlocked.pack", TextureAtlas.class);
        game.getAssets().load("Levels/Locked/levels_locked.pack", TextureAtlas.class);
        game.getAssets().load("UI/Title.png", Texture.class);
        game.getAssets().load("UI/exitbutton.png", Texture.class);
        game.getAssets().load("UI/playbutton.png", Texture.class);
        game.getAssets().load("UI/highScores.png", Texture.class);
        game.getAssets().load("UI/win.png", Texture.class);
        game.getAssets().load("UI/gameover.png", Texture.class);
        game.getAssets().load("UI/replay.png", Texture.class);
        game.getAssets().load("UI/mainmenu.png", Texture.class);
        game.getAssets().load("UI/Back.png", Texture.class);
        game.getAssets().load("UI/Controls.png", Texture.class);
        game.getAssets().load("UI/nextLevel.png", Texture.class);
        game.getAssets().load("UI/MainMenuPlayer.pack", TextureAtlas.class);
        game.getAssets().load("UI/Background/background1.png", Texture.class);
        game.getAssets().load("UI/Background/background2.png", Texture.class);
        game.getAssets().load("UI/Background/background3.png", Texture.class);
        game.getAssets().load("UI/Background/background4.png", Texture.class);
        game.getAssets().load("UI/Background/background5.png", Texture.class);
        game.getAssets().load("UI/Background/background6.png", Texture.class);
        game.getAssets().load("UI/NoSound.png", Texture.class);
        game.getAssets().load("UI/Sound.png", Texture.class);
        game.getAssets().load("UI/Settings.png", Texture.class);
        game.getAssets().load("Audio/Music/PlayMusic.mp3", Music.class);
        game.getAssets().load("Audio/Music/MainMenu.wav", Music.class);
        game.getAssets().load("Audio/Sounds/ChangingColor.wav", Sound.class);
        game.getAssets().load("Audio/Sounds/Portal.wav", Sound.class);
        game.getAssets().load("Audio/Sounds/GameOver.mp3", Sound.class);
        game.getAssets().load("Audio/Sounds/Star.wav", Sound.class);

        FreetypeFontLoader.FreeTypeFontLoaderParameter parameters = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        parameters.fontFileName = "Fonts/ethnocentric.ttf";
        parameters.fontParameters.size = 120;
        parameters.fontParameters.color = Color.BLACK;
        parameters.fontParameters.minFilter = Texture.TextureFilter.Linear;
        parameters.fontParameters.magFilter = Texture.TextureFilter.Linear;
        game.getAssets().load("size120.ttf", BitmapFont.class, parameters);

        parameters.fontFileName = "Fonts/ethnocentric.ttf";
        parameters.fontParameters.size = 60;
        parameters.fontParameters.color = Color.BLACK;
        parameters.fontParameters.minFilter = Texture.TextureFilter.Linear;
        parameters.fontParameters.magFilter = Texture.TextureFilter.Linear;
        game.getAssets().load("size60.ttf", BitmapFont.class, parameters);

        parameters.fontFileName = "Fonts/ethnocentric.ttf";
        parameters.fontParameters.size = 100;
        parameters.fontParameters.color = Color.BLACK;
        parameters.fontParameters.minFilter = Texture.TextureFilter.Linear;
        parameters.fontParameters.magFilter = Texture.TextureFilter.Linear;
        game.getAssets().load("size100.ttf", BitmapFont.class, parameters);

        game.getAssets().finishLoading();
    }

    /**
     * Creates the splash screen animation.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        splashImage.setOrigin(splashImage.getWidth() / 2, splashImage.getHeight() / 2);

        stage.addActor(splashImage);

        splashImage.setPosition(game.V_WIDTH / 2 - splashImage.getWidth() / 2, game.V_HEIGHT / 2 - splashImage.getHeight() / 2);
        splashImage.addAction(Actions.sequence(
                Actions.parallel(
                        Actions.sequence(Actions.alpha(0f),
                            Actions.scaleTo(0.5f, 0.5f),
                            Actions.parallel(
                                    Actions.scaleTo(0.8f, 0.8f, 1.5f), Actions.alpha(1f, 1.5f)),
                                Actions.delay(0.5f),
                                Actions.parallel(Actions.scaleTo(0.5f, 0.5f, 1.5f), Actions.alpha(0f, 1.5f))), Actions.run(run)), Actions.run(setPlayScreen)));
    }

    /**
     * Draws the stage to the screen.
     * @param delta Time since last call.
     */
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

    /**
     * Override of dispose method. Disposes the stage.
     */
    @Override
    public void dispose() {
        stage.dispose(); }
}
