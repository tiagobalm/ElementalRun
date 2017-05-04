package com.tiagoalmeida.elementalrun.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tiagoalmeida.elementalrun.FutureRun;
import com.tiagoalmeida.elementalrun.Scenes.HUD;
import com.tiagoalmeida.elementalrun.Sprites.Player;
import com.tiagoalmeida.elementalrun.Tools.B2WorldCreator;
import com.tiagoalmeida.elementalrun.Tools.WorldContactListener;

import static com.badlogic.gdx.Gdx.input;

/**
 * Play screen class.
 */
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

    /**
     * Play screen constructor
     * @param game Main game.
     * @param level Level to be played.
     */
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

    /**
     *
     * @return Level map.
     */
    public TiledMap getMap() {
        return map;
    }

    /**
     *
     * @return Level world.
     */
    public World getWorld() {
        return world;
    }

    /**
     *
     * @return The camera of the play screen.
     */
    public OrthographicCamera getGameCam() { return gameCam; }
    /**
     * Start play music.
     */
    @Override
    public void show() {
        if(game.withSound)
            playMusic.play();
    }

    /**
     * Handles touch input.
     * @param deltaTime Time passed since last call.
     */
    public void handleInput(float deltaTime) {
        //Touch Input
        /*if(input.justTouched()) {
            if(input.getX()< Gdx.graphics.getWidth() / 2) {
                if(game.withSound)
                    changeColorSound.play();
                if(player.isOrange())
                    player.setOrange(false);
                else
                    player.setOrange(true);
            } else {
                if(player.getState() != Player.State.JUMPING && player.getState() != Player.State.FALLING)
                    player.b2Body.applyLinearImpulse(new Vector2(0, 8f), player.b2Body.getWorldCenter(), true);
            }
        }*/

        //Keyboard input
        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case Input.Keys.SPACE:
                        if(player.getState() != Player.State.JUMPING && player.getState() != Player.State.FALLING)
                            player.b2Body.applyLinearImpulse(new Vector2(0, 8f), player.b2Body.getWorldCenter(), true);
                        break;
                    case Input.Keys.E:
                        if(game.withSound)
                            changeColorSound.play();
                        if(player.isOrange())
                            player.setOrange(false);
                        else
                            player.setOrange(true);
                        break;
                    default:
                        break;
                }
                return true;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                return false;
            }
        });
    }

    /**
     * Updates the game, i.e, player position, hud, camera position, etc.
     * @param deltaTime Time passed since last call.
     */
    public void update(float deltaTime) {
        handleInput(deltaTime);

        world.step(1/60f, 6, 2);

        player.update(deltaTime);

        hud.update(deltaTime);

        gameCam.position.x = player.b2Body.getPosition().x + game.V_WIDTH / 4 / FutureRun.PPM;
        if(player.isCameraOn())
            gameCam.position.y = player.b2Body.getPosition().y + game.V_HEIGHT / 20 / FutureRun.PPM;

        gameCam.update();
        renderer.setView(gameCam);
    }

    /**
     * Override of method render. Calls update and draws game to the screen.
     * @param delta Time passed since last call.
     */
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

    /**
     * Resizes viewport if the windows size is changed.
     * @param width Window width.
     * @param height Window height.
     */
    @Override
    public void resize(int width, int height) {
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

    /**
     * Override of dispose method. Disposes map, renderer, world, world debugger and hud.
     */
    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
