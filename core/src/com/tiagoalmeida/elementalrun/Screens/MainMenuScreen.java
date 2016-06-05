package com.tiagoalmeida.elementalrun.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tiagoalmeida.elementalrun.ElementalRun;
import com.tiagoalmeida.elementalrun.Tools.AnimatedImage;

public class MainMenuScreen implements Screen {
    private final ElementalRun game;
    private Stage stage;
    private OrthographicCamera camera;
    private Table table;
    private Label title, clickContinue, clickExit;
    private BitmapFont font120, font180;

    private TextureRegion[] fireTextureRegion;
    private AnimatedImage fireSprite;

    private TextureRegion[][] waterMatrix;
    private Array<TextureRegion> waterTextureRegion;
    private AnimatedImage waterSprite;

    private TextureRegionDrawable highScoresTexture;
    private ImageButton highScores;

    public MainMenuScreen(ElementalRun game) {
        this.game = game;
        this.camera = game.camera;
        this.stage = new Stage(new FitViewport(game.V_WIDTH, game.V_HEIGHT, camera));
        Gdx.input.setInputProcessor(stage);

        this.table = new Table();
        this.table.setBounds(0, 0, game.V_WIDTH, game.V_HEIGHT);
        this.table.debug();

        this.fireTextureRegion = TextureRegion.split(game.getAssets().get("firesheet.png", Texture.class), 77, 156)[0];
        this.fireSprite = new AnimatedImage(new Animation(0.15f, fireTextureRegion));

        this.waterMatrix = TextureRegion.split(game.getAssets().get("watersheet.png", Texture.class), 156, 156);
        this.waterTextureRegion = new Array<TextureRegion>();
        for(int i = 0; i < this.waterMatrix.length; i++)
            for(int j = 0; j < this.waterMatrix[i].length; j++)
                this.waterTextureRegion.add(waterMatrix[i][j]);
        this.waterSprite = new AnimatedImage(new Animation(0.1f, waterTextureRegion));

        highScoresTexture = new TextureRegionDrawable(
                new TextureRegion(game.getAssets().get("highScores.png", Texture.class)));
        this.highScores = new ImageButton(highScoresTexture);
        highScores.addListener(new ClickListener() {
           @Override
           public void clicked(InputEvent event, float x, float y) {
              setHighScores();
           }
       }
        );
        initFonts();
        initLayers();
    }

    private void setHighScores() {
        game.setScreen(new HighScoresScreen(game));
        dispose();
    }
    private void setLevelsScreen() {
        game.setScreen(new LevelScreen(game));
        dispose();
    }

    private void initFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/OpenSans-Light.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameters = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameters.size = 120;
        parameters.color = Color.BLACK;
        parameters.minFilter = Texture.TextureFilter.Linear;
        parameters.magFilter = Texture.TextureFilter.Linear;
        font120 = generator.generateFont(parameters);

        parameters.size = 180;
        parameters.color = Color.BLACK;
        parameters.minFilter = Texture.TextureFilter.Linear;
        parameters.magFilter = Texture.TextureFilter.Linear;
        font180 = generator.generateFont(parameters);
    }

    private void initLayers() {
        title = new Label("Elemental Run", new Label.LabelStyle(font180, Color.BLACK));
        table.add(title).expandX().center().colspan(2).row();

        clickContinue = new Label("Start", new Label.LabelStyle(font120, Color.BLACK));
        clickContinue.addAction(Actions.forever(Actions.sequence(Actions.parallel(Actions.moveBy(0f, 10f, 1f), Actions.alpha(0.5f, 1f)),
                Actions.parallel(Actions.moveBy(0f, -10f, 1f), Actions.alpha(1f, 1f)))));
        clickContinue.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setLevelsScreen();
            }
        });
        table.add(clickContinue).expandY();

        clickExit = new Label("Exit", new Label.LabelStyle(font120, Color.BLACK));
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
    public void show() {
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
        fireSprite.act(delta);
        waterSprite.act(delta);
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
        this.stage.dispose();
        Gdx.app.log("Disposed", "Main Menu");
    }
}
