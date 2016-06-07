package com.tiagoalmeida.elementalrun.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.tiagoalmeida.elementalrun.FutureRun;
import com.tiagoalmeida.elementalrun.Screens.PlayScreen;

/**
 * Player class.
 */
public class Player extends Sprite {
    public enum State {FALLING, JUMPING, STANDING, RUNNING};
    public State currentState;
    public State previousState;
    private PlayScreen screen;
    public Body b2Body;

    private Array<TextureRegion> playerBlueTexture;
    private Array<TextureRegion> playerOrangeTexture;

    private Animation orangePlayer;
    private Animation bluePlayer;

    private float stateTimer;
    private boolean runningRight;
    private boolean orange;
    private boolean winner;
    private boolean gameOver;
    private boolean cameraOn;

    /**
     * Player constructor.
     * @param screen Current play screen.
     */
    public Player(PlayScreen screen) {
        this.screen = screen;
        currentState = State.RUNNING;
        previousState = State.RUNNING;
        stateTimer = 0;
        runningRight = true;
        orange = true;
        winner = false;
        gameOver = false;
        cameraOn = true;

        playerBlueTexture = new Array<TextureRegion>();
        playerOrangeTexture = new Array<TextureRegion>();
        String s;

        for(int i = 1; i < 37 ; i++) {
            s = String.format("player%d",i);
            playerBlueTexture.add(screen.game.getAssets().get("Player/PlayerBlue.pack", TextureAtlas.class).findRegion(s));
            playerOrangeTexture.add(screen.game.getAssets().get("Player/PlayerOrange.pack", TextureAtlas.class).findRegion(s));

        }

        bluePlayer = new Animation(0.01f, playerBlueTexture);
        orangePlayer = new Animation(0.01f, playerOrangeTexture);

        definePlayer();
        setBounds(0, 0, 96  / FutureRun.PPM, 96 / FutureRun.PPM);
        setRegion(bluePlayer.getKeyFrame(0));
    }

    /**
     *
     * @return True if the current color is orange and false otherwise.
     */
    public boolean isOrange() {
        return orange;
    }

    /**
     *
     * @return True if the player won the game and false otherwise
     */
    public boolean isWinner() {
        return winner;
    }

    /**
     *
     * @return True if the game is over or false otherwise.
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Sets game over and sets winner to boolean passed as argument.
     * @param winner Argument to be passed to private variable winner.
     */
    public void setGameOver(boolean winner) {
        this.winner = winner;
        gameOver = true;
    }

    /**
     * Disables camera following player in the y direction.
     */
    public void turnOffCamera() {
        cameraOn = false;
    }

    /**
     *
     * @return True if the camera is following the player in the Y direction and false otherwise.
     */
    public boolean isCameraOn() {
        return cameraOn;
    }

    /**
     * Setter for color.
     * @param orange Boolean to be passed to private variable orange.
     */
    public void setOrange(boolean orange) {
        this.orange = orange;
    }

    /**
     *
     * @return the current state of the player.
     */
    public State getState() {
        
        if(b2Body.getLinearVelocity().y > 0)
            return State.JUMPING;
        else if(b2Body.getLinearVelocity().y < 0 && (previousState == State.JUMPING || previousState == State.FALLING))
            return State.FALLING;
        else if(b2Body.getLinearVelocity().y == 0 && previousState != State.JUMPING)
            return State.RUNNING;
        else
            return State.STANDING;
    }

    /**
     *
     * @param deltaTime Time passed since last call.
     * @return Returns the current frame, i.e, texture of the playe animation.
     */
    private TextureRegion getFrame(float deltaTime) {
        currentState = getState();

        TextureRegion region;
        region = isOrange() ? orangePlayer.getKeyFrame(stateTimer, true) : bluePlayer.getKeyFrame(stateTimer, true);

        if((b2Body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        }
        else if((b2Body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = stateTimer + deltaTime;
        previousState = currentState;

        return region;
    }

    /**
     * Constructs Box2d object for player.
     */
    public void definePlayer() {

        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / FutureRun.PPM, 500 / FutureRun.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2Body = screen.getWorld().createBody(bdef);
        b2Body.setLinearVelocity(new Vector2(7.5f, 0));

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(45 / FutureRun.PPM);
        fdef.filter.categoryBits = FutureRun.PLAYER_BIT;
        fdef.filter.maskBits = FutureRun.ORANGE_GROUND_BIT | FutureRun.BLACK_GROUND_BIT
                                | FutureRun.ORANGE_DIAMOND_BIT | FutureRun.BLUE_DIAMOND_BIT
                                | FutureRun.PORTAL_BIT | FutureRun.END_OF_WORLD;

        fdef.shape = shape;
        fdef.restitution = 0;
        b2Body.createFixture(fdef).setUserData(this);
        shape.dispose();
    }

    /**
     * Updates player position and color.
     * @param deltaTime Time passes since last call.
     */
    public void update(float deltaTime) {
        if(isOrange()) {
            Filter filter = b2Body.getFixtureList().first().getFilterData();
            filter.maskBits &= ~FutureRun.BLUE_GROUND_BIT;
            filter.maskBits |= FutureRun.ORANGE_GROUND_BIT;
            b2Body.getFixtureList().first().setFilterData(filter);
        } else {
            Filter filter = b2Body.getFixtureList().first().getFilterData();
            filter.maskBits &= ~FutureRun.ORANGE_GROUND_BIT;
            filter.maskBits |= FutureRun.BLUE_GROUND_BIT;
            b2Body.getFixtureList().first().setFilterData(filter);
        }

        if(getY() < (screen.getGameCam().position.y - screen.getGameCam().viewportHeight / 2)) {
            gameOver = true;
            winner = false;
        }

        if(getState() == State.RUNNING)
            b2Body.setLinearVelocity(new Vector2(7.5f, 0f));
        setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(deltaTime));
    }
}
