package com.tiagoalmeida.elementalrun.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
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

public class MainMenu implements Screen {
    private final ElementalRun game;
    private Stage stage;
    private OrthographicCamera camera;
    private Skin titleSkin, clickContinueSkin;
    private Table table, clickContinueTable;
    private Label title, clickContinue;
    private BitmapFont font24, font36;

    private TextureRegion[] fireTextureRegion;
    private AnimatedImage fireSprite;

    private TextureRegion[][] waterMatrix;
    private Array<TextureRegion> waterTextureRegion;
    private AnimatedImage waterSprite;

    private TextureRegionDrawable highScoresTexture;
    private ImageButton highScores;

    private boolean goPlayScreen, goHighScoreScreen;

    public MainMenu(ElementalRun game) {
        this.game = game;
        this.camera = game.camera;
        this.goPlayScreen = false;
        this.goHighScoreScreen = false;
        this.stage = new Stage(new FitViewport(game.V_WIDTH, game.V_HEIGHT, camera));
        Gdx.input.setInputProcessor(stage);

        this.table = new Table();
        this.table.setBounds(0, 0, game.V_WIDTH, game.V_HEIGHT);
        this.table.debug();

        this.clickContinueTable = new Table();
        this.clickContinueTable.debug();

        this.fireTextureRegion = TextureRegion.split(game.getAssets().get("firesheet.png", Texture.class), 77, 156)[0];
        this.fireSprite = new AnimatedImage(new Animation(0.15f, fireTextureRegion));

        this.waterMatrix = TextureRegion.split(game.getAssets().get("watersheet.png", Texture.class), 156, 156);
        this.waterTextureRegion = new Array<TextureRegion>();
        for(int i = 0; i < this.waterMatrix.length; i++)
            for(int j = 0; j < this.waterMatrix[i].length; j++)
                this.waterTextureRegion.add(waterMatrix[i][j]);
        this.waterSprite = new AnimatedImage(new Animation(0.1f, waterTextureRegion));

        highScoresTexture = new TextureRegionDrawable(
                new TextureRegion(game.getAssets().get("highScores.jpg", Texture.class)));
        this.highScores = new ImageButton(highScoresTexture);
        highScores.addListener(
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        //TODO Figure out why is it not being called!!!
                        goHighScoreScreen = true;
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
    private void setPlayScreen() {
        game.setScreen(new PlayScreen(game));
        dispose();
    }

    private void initFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/OpenSans-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameters = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameters.size = 24;
        parameters.color = Color.BLACK;
        font24 = generator.generateFont(parameters);

        parameters.size = 36;
        parameters.color = Color.BLACK;
        font36 = generator.generateFont(parameters);
    }

    private void initLayers() {
        this.titleSkin = new Skin();
        this.titleSkin.addRegions(game.getAssets().get("UI/uiskin.atlas", TextureAtlas.class));
        this.titleSkin.add("default-font", font36);
        this.titleSkin.load(Gdx.files.internal("UI/uiskin.json"));

        title = new Label("Elemental Run", titleSkin);
        table.add(title).colspan(2).expandX().center().row();

        this.clickContinueSkin = new Skin();
        this.clickContinueSkin.addRegions(game.getAssets().get("UI/uiskin.atlas", TextureAtlas.class));
        this.clickContinueSkin.add("default-font", font24);
        this.clickContinueSkin.load(Gdx.files.internal("UI/uiskin.json"));

        clickContinue = new Label("Tap to begin", clickContinueSkin);
        clickContinueTable.setTransform(true);
        clickContinueTable.add(clickContinue);
        clickContinueTable.setOrigin(clickContinue.getWidth() / 2, clickContinue.getHeight() / 2);
        clickContinueTable.addAction(Actions.forever(Actions.sequence(Actions.parallel(Actions.moveBy(0f, 10f, 1f), Actions.alpha(0.5f, 1f)),
                Actions.parallel(Actions.moveBy(0f, -10f, 1f), Actions.alpha(1f, 1f)))));
        table.add(clickContinueTable).colspan(2).expandY().row();

        table.add(highScores).width(100).height(100).colspan(2).right();
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
        game.batch.begin();
        game.batch.draw(fireSprite.getTexture(), game.V_WIDTH / 3 - fireSprite.getWidth() / 2, game.V_HEIGHT / 2);
        game.batch.draw(waterSprite.getTexture(), 2 * game.V_WIDTH / 3 - waterSprite.getWidth() / 2, game.V_HEIGHT / 2);
        game.batch.end();
        stage.draw();

        if(goHighScoreScreen) {
            System.out.println("Setting high score screen");
            setHighScores();
        }
        else if(!goHighScoreScreen && goPlayScreen) {
            System.out.println("Setting play score screen");
            setPlayScreen();
        }
    }

    private void update(float delta) {
        stage.act(delta);
        fireSprite.act(delta);
        waterSprite.act(delta);
        handleInputs();
    }

    private void handleInputs() {
        if(Gdx.input.isTouched()) {
            //goPlayScreen = true;
        }
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
        this.titleSkin.dispose();
        this.clickContinueSkin.dispose();
        Gdx.app.log("Disposed", "Main Menu");
    }
}
