package com.tiagoalmeida.elementalrun.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tiagoalmeida.elementalrun.FutureRun;
import com.tiagoalmeida.elementalrun.Scenes.HUD;
import com.tiagoalmeida.elementalrun.Sprites.Player;
import com.tiagoalmeida.elementalrun.Tools.B2WorldCreator;
import com.tiagoalmeida.elementalrun.Tools.WorldContactListener;

public class PlayScreen implements Screen {
    public FutureRun game;
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

    private boolean debugMode;

    //Level to play
    private int level;

    private Music playMusic;
    private Sound gameOverSound, portalSound, changeColorSound;

    public PlayScreen(FutureRun game, int level) {
        this.game = game;
        this.level = level;

        //Creates camera to follow mario
        gameCam = new OrthographicCamera();

        //initially set our gameCam to be centered correctly at the start of the game
        gameCam.setToOrtho(false, FutureRun.V_WIDTH / FutureRun.PPM, FutureRun.V_HEIGHT / FutureRun.PPM);

        //Creates a FitViewPort to maintain virtual aspect ratio despite window size
        gamePort = new FitViewport(FutureRun.V_WIDTH / FutureRun.PPM, FutureRun.V_HEIGHT / FutureRun.PPM, gameCam);

        //Creates game HUD
        hud = new HUD(game);

        //Load or map and setup our map renderer
        mapLoader = new TmxMapLoader();
        String s = String.format("Map/level%d.tmx", level);
        map = mapLoader.load(s);
        renderer = new OrthogonalTiledMapRenderer(map, 1 / FutureRun.PPM);

        //Gravity
        world = new World(new Vector2(0, -14), true);

        //Allows for debug lines
        b2dr = new Box2DDebugRenderer();

        creator = new B2WorldCreator(this);

        //Creates the player
        player = new Player(this);

        world.setContactListener(new WorldContactListener());
        debugMode = false;

        playMusic = game.getAssets().get("Audio/Music/PlayMusic.mp3", Music.class);
        playMusic.setLooping(true);

        gameOverSound = game.getAssets().get("Audio/Sounds/GameOver.mp3", Sound.class);
        portalSound = game.getAssets().get("Audio/Sounds/Portal.wav", Sound.class);
        changeColorSound = game.getAssets().get("Audio/Sounds/ChangingColor.wav", Sound.class);
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void show() {
        if(game.withSound)
            playMusic.play();
    }

    public void handleInput(float deltaTime) {

        //KeyBoard Input
        //UP Key Input (Jump)
        /*if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if(player.getState() != Player.State.JUMPING && player.getState() != Player.State.FALLING)
                player.b2Body.applyLinearImpulse(new Vector2(0, 8f), player.b2Body.getWorldCenter(), true);
        }

        //Changing color
        if(Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            if(player.isFire())
                player.setFire(false);
            else
                player.setFire(true);
        }*/

        //Touch Input
        if(Gdx.input.justTouched()) {
            if(Gdx.input.getX()< Gdx.graphics.getWidth() / 2) {
                if(game.withSound)
                    changeColorSound.play();
                if(player.isFire())
                    player.setFire(false);
                else
                    player.setFire(true);
            } else {
                if(player.getState() != Player.State.JUMPING && player.getState() != Player.State.FALLING)
                    player.b2Body.applyLinearImpulse(new Vector2(0, 8f), player.b2Body.getWorldCenter(), true);
            }
        }
    }


    public void update(float deltaTime) {
        handleInput(deltaTime);

        world.step(1/60f, 6, 2);

        player.update(deltaTime);

        hud.update(deltaTime);

        gameCam.position.x = player.b2Body.getPosition().x + game.V_WIDTH / 4 / FutureRun.PPM;
        gameCam.position.y = player.b2Body.getPosition().y + game.V_HEIGHT / 4 / FutureRun.PPM;

        gameCam.update();
        renderer.setView(gameCam);
    }

    @Override
    public void render(float delta) {
        update(delta);

        //Clears the screen with black
        Gdx.gl.glClearColor(1, 1, 1, 1);
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

        if(player.isGameOver()){
            if(player.isWinner()) {
                if(game.withSound)
                    portalSound.play();
                playMusic.stop();
                game.setScreen(new WinnerScreen(game, hud.getScore(), level));
                dispose();
            } else {
                if(game.withSound)
                    gameOverSound.play();
                playMusic.stop();
                game.setScreen(new GameOverScreen(game, level));
                dispose();
            }
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
