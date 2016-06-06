package com.tiagoalmeida.elementalrun.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.tiagoalmeida.elementalrun.ElementalRun;

public class SplashScreen implements Screen {
    private ElementalRun game;
    private Stage stage;

    private Image splashImage;
    private Runnable run, setPlayScreen;

    public SplashScreen(ElementalRun game) {
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
        game.getAssets().load("TariLogo.png", Texture.class);
        game.getAssets().finishLoadingAsset("TariLogo.png");
        splashImage = new Image(game.getAssets().get("TariLogo.png", Texture.class));
    }

    private void setMainMenuScreenMethod() {
        game.setScreen(new MainMenuScreen(game));
    }

    private void queueAssets() {
        game.getAssets().load("Player/player.png", Texture.class);
        game.getAssets().load("firesheet.png", Texture.class);
        game.getAssets().load("watersheet.png", Texture.class);
        game.getAssets().load("cup.png", Texture.class);
        game.getAssets().load("Levels/Unlocked/levels_unlocked.pack", TextureAtlas.class);
        game.getAssets().load("Levels/Locked/levels_locked.pack", TextureAtlas.class);
        game.getAssets().load("Title.png", Texture.class);
        game.getAssets().load("Buttons/exitbutton.png", Texture.class);
        game.getAssets().load("Buttons/playbutton.png", Texture.class);
        game.getAssets().load("highScores.png", Texture.class);

        FreetypeFontLoader.FreeTypeFontLoaderParameter parameters = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        parameters.fontFileName = "Fonts/OpenSans-Light.ttf";
        parameters.fontParameters.size = 120;
        parameters.fontParameters.color = Color.BLACK;
        parameters.fontParameters.minFilter = Texture.TextureFilter.Linear;
        parameters.fontParameters.magFilter = Texture.TextureFilter.Linear;
        game.getAssets().load("size120.ttf", BitmapFont.class, parameters);

        parameters.fontFileName = "Fonts/OpenSans-Light.ttf";
        parameters.fontParameters.size = 180;
        parameters.fontParameters.color = Color.BLACK;
        parameters.fontParameters.minFilter = Texture.TextureFilter.Linear;
        parameters.fontParameters.magFilter = Texture.TextureFilter.Linear;
        game.getAssets().load("size180.ttf", BitmapFont.class, parameters);
        game.getAssets().finishLoading();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        splashImage.setOrigin(game.getAssets().get("TariLogo.png", Texture.class).getWidth() / 2,
                game.getAssets().get("TariLogo.png", Texture.class).getHeight() / 2);

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

    @Override
    public void dispose() {
        System.out.println("Disposing Splash Screen");
        stage.dispose(); }
}
