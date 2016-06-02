package com.tiagoalmeida.elementalrun.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tiagoalmeida.elementalrun.ElementalRun;
import com.tiagoalmeida.elementalrun.Scenes.HUD;
import com.tiagoalmeida.elementalrun.Sprites.Player;
import com.tiagoalmeida.elementalrun.Tools.B2WorldCreator;
import com.tiagoalmeida.elementalrun.Tools.WorldContactListener;

/**
 * Created by tiago on 01-06-2016.
 */
public class PlayScreen implements Screen {
    private ElementalRun game;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private HUD hud;

    //Tiled map variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2D variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    //Sprites
    private Player player;

    private TextureAtlas atlas;

    private Music music;
    private boolean debugMode;


    public PlayScreen(ElementalRun game) {
        this.game = game;

        //Creates camera to follow mario
        gameCam = new OrthographicCamera();

        //Creates a FitViewPort to maintain virtual aspect ratio despite window size
        gamePort = new FitViewport(ElementalRun.V_WIDTH / ElementalRun.PPM, ElementalRun.V_HEIGHT / ElementalRun.PPM, gameCam);

        //Creates game HUD
        hud = new HUD(game.batch);

        //Load or map and setup our map renderer
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("Map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1  / ElementalRun.PPM);

        //initially set our gameCam to be centered correctly at the start of the game
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        //Gravity
        world = new World(new Vector2(0, -10), true);

        //Allows for debug lines
        b2dr = new Box2DDebugRenderer();

        //Sprite sheet
        atlas = new TextureAtlas("Mario_and_Enemies.pack");

        creator = new B2WorldCreator(this);

        //Creates the player
        player = new Player(this);

        world.setContactListener(new WorldContactListener());
        music = ElementalRun.getAssets().get("audio/music/mario_music.ogg", Music.class);
        music.setLooping(true);
        music.play();
        debugMode = false;

    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void show() {

    }

    public boolean gameOver(){
        if(player.currentState == Player.State.DEAD && player.getStateTimer() > 3){
            return true;
        }
        return false;
    }

    public void handleInput(float deltaTime) {

        //UP Key Input (Jump)
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            player.b2Body.applyLinearImpulse(new Vector2(0, 4f), player.b2Body.getWorldCenter(), true);

        //Changing color
        if(Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            if(player.isOrange())
                player.setOrange(false);
            else
                player.setOrange(true);
        }
    }


    public void update(float deltaTime) {
        handleInput(deltaTime);

        world.step(1/60f, 6, 2);

        player.update(deltaTime);

        hud.update(deltaTime);

        gameCam.position.x = player.b2Body.getPosition().x;

        gameCam.update();
        renderer.setView(gameCam);
    }

    @Override
    public void render(float delta) {
        update(delta);

        //Clears the screen with black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render our game map
        renderer.render();

        //render our Box2DDebugLines
        if(debugMode)
            b2dr.render(world, gameCam.combined);

        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

        //set our batch to now drat what the hub camera sees.
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        if(gameOver()){
            game.setScreen(new GameOverScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        //Updates our game viewport
        gamePort.update(width, height);
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
