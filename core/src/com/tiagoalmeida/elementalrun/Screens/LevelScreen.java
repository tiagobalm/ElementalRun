package com.tiagoalmeida.elementalrun.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tiagoalmeida.elementalrun.FutureRun;
import com.tiagoalmeida.elementalrun.Tools.SaveHandler;

public class LevelScreen implements Screen {

    private final FutureRun game;
    private Array<TextureRegion> levelsLocked, levelsUnlocked;
    private Stage stage;
    private Table table;
    private Array<ImageButton> imageButtonsArray;
    private ImageButton back;

    private boolean debug;

    public LevelScreen(FutureRun game) {
        this.game = game;
        imageButtonsArray = new Array<ImageButton>();

        levelsLocked = new Array<TextureRegion>();
        levelsUnlocked = new Array<TextureRegion>();

        debug = false;

        table = new Table();
        this.table.setBounds(0, 0, game.V_WIDTH, game.V_HEIGHT);
        if(debug)
            table.debug();

        //Play button
        game.getAssets().get("UI/Back.png", Texture.class).setFilter(
                Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        back = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.getAssets().get("UI/Back.png", Texture.class))));
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setMainMenuScreen();
            }
        });
        table.add(back).left().top().pad(50, 0, 0, 0).expandX().row();

        stage = new Stage(new FitViewport(game.V_WIDTH, game.V_HEIGHT, game.camera));
        String s;

        for(int i = 1; i < 26; i++) {

            s = String.format("Level%d", i);
            levelsUnlocked.add(game.getAssets().get("Levels/Unlocked/levels_unlocked.pack", TextureAtlas.class).findRegion(s));
            levelsUnlocked.get(i - 1).getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

            s = String.format("Level%dLocked", i);
            levelsLocked.add(game.getAssets().get("Levels/Locked/levels_locked.pack", TextureAtlas.class).findRegion(s));
            levelsLocked.get(i - 1).getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }

        int currentLevel = SaveHandler.gameData.getCurrentLevel();
        int changeRow = 0;

        for(int i = 0; i < currentLevel; i++) {
            imageButtonsArray.add(new ImageButton(new TextureRegionDrawable(levelsUnlocked.get(i))));
            imageButtonsArray.get(i).addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    setPlayScreen(event);
                }
            });
            table.add(imageButtonsArray.get(i)).pad(10, 10, 10, 10).expandX();
            changeRow++;
            if(changeRow == 5) {
                table.row();
                changeRow = 0;
            }
        }

        for(int i = currentLevel; i < 25; i++) {
            table.add(new ImageButton(new TextureRegionDrawable(levelsLocked.get(i)))).pad(10, 10, 10, 10).expandX();
            changeRow++;
            if(changeRow == 5) {
                table.row();
                changeRow = 0;
            }
        }

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    private void setMainMenuScreen() {
        stage.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.run(new Runnable() {
            @Override
            public void run() {
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        })));
    }

    private void setPlayScreen(InputEvent event) {
        System.out.println("Set play screen");

        int level = 1;
        for (ImageButton img : imageButtonsArray) {
            if (event.getTarget() == img.getImage()) {
                game.setScreen(new PlayScreen(game, level));
                dispose();
            }
            level++;
        }
    }

    @Override
    public void show() {
        stage.addAction(Actions.sequence(Actions.alpha(0f), Actions.fadeIn(0.2f)));
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
