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
import com.tiagoalmeida.elementalrun.ElementalRun;
import com.tiagoalmeida.elementalrun.Screens.PlayScreen;


public class Player extends Sprite {
    public enum State {FALLING, JUMPING, STANDING, RUNNING};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2Body;

    private Array<TextureRegion> playerBlueTexture;
    private Array<TextureRegion> playerOrangeTexture;

    private Animation firePlayer;
    private Animation waterPlayer;

    private float stateTimer;
    private boolean runningRight;
    private boolean fire;
    private boolean winner;
    private boolean gameOver;

    public Player(PlayScreen screen) {
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
        fire = true;
        winner = false;
        gameOver = false;

        playerBlueTexture = new Array<TextureRegion>();
        playerOrangeTexture = new Array<TextureRegion>();
        String s;

        for(int i = 1; i < 37 ; i++) {
            s = String.format("player%d",i);
            playerBlueTexture.add(screen.game.getAssets().get("Player/PlayerBlue.pack", TextureAtlas.class).findRegion(s));
            playerOrangeTexture.add(screen.game.getAssets().get("Player/PlayerOrange.pack", TextureAtlas.class).findRegion(s));

        }

        waterPlayer = new Animation(0.01f, playerBlueTexture);
        firePlayer = new Animation(0.01f, playerOrangeTexture);

        definePlayer();
        setBounds(0, 0, 128  / ElementalRun.PPM, 128 / ElementalRun.PPM);
        setRegion(waterPlayer.getKeyFrame(0));
    }

    public boolean isFire() {
        return fire;
    }

    public boolean isWinner() {
        return winner;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean winner) {
        System.out.println("Setting gameover");
        this.winner = winner;
        gameOver = true;
    }

    public void setFire(boolean fire) {
        this.fire = fire;
    }

    public State getState() {
        
        if(b2Body.getLinearVelocity().y > 0)
            return State.JUMPING;
        else if(b2Body.getLinearVelocity().y < 0 && (previousState == State.JUMPING || previousState == State.FALLING))
            return State.FALLING;
        else if(b2Body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }

    private TextureRegion getFrame(float deltaTime) {
        currentState = getState();

        TextureRegion region;
        switch (currentState) {
            case JUMPING:
            case FALLING:
            case RUNNING:
            case STANDING:
            default:
                region = isFire() ? firePlayer.getKeyFrame(stateTimer, true) : waterPlayer.getKeyFrame(stateTimer, true);
                break;
        }

        if((b2Body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        }
        else if((b2Body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + deltaTime : 0;
        previousState = currentState;

        return region;
    }

    public void definePlayer() {

        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / ElementalRun.PPM, 600 / ElementalRun.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2Body = world.createBody(bdef);
        b2Body.setLinearVelocity(new Vector2(7f, 0));

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(64 / ElementalRun.PPM);
        fdef.filter.categoryBits = ElementalRun.PLAYER_BIT;
        fdef.filter.maskBits = ElementalRun.ORANGE_GROUND_BIT | ElementalRun.BLACK_GROUND_BIT
                                | ElementalRun.ORANGE_DIAMOND_BIT | ElementalRun.BLUE_DIAMOND_BIT
                                | ElementalRun.PORTAL_BIT;

        fdef.shape = shape;
        fdef.restitution = 0;
        b2Body.createFixture(fdef).setUserData(this);
        shape.dispose();
    }

    public void update(float deltaTime) {
        if(isFire()) {
            Filter filter = b2Body.getFixtureList().first().getFilterData();
            filter.maskBits &= ~ElementalRun.BLUE_GROUND_BIT;
            filter.maskBits |= ElementalRun.ORANGE_GROUND_BIT;
            b2Body.getFixtureList().first().setFilterData(filter);
        } else {
            Filter filter = b2Body.getFixtureList().first().getFilterData();
            filter.maskBits &= ~ElementalRun.ORANGE_GROUND_BIT;
            filter.maskBits |= ElementalRun.BLUE_GROUND_BIT;
            b2Body.getFixtureList().first().setFilterData(filter);
        }
        if(getY() < 0) {
            gameOver = true;
            winner = false;
        }
        setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(deltaTime));
    }
}
