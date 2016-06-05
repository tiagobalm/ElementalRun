package com.tiagoalmeida.elementalrun.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.tiagoalmeida.elementalrun.ElementalRun;
import com.tiagoalmeida.elementalrun.Screens.PlayScreen;


public class Player extends Sprite {
    public enum State {FALLING, JUMPING, STANDING, RUNNING, DEAD};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2Body;

    private Texture playerTexture;
    private TextureRegion[] playerTextureRegion;

    private Animation firePlayer;
    private TextureRegion firePlayerJump;

    private Animation waterPlayer;
    private TextureRegion waterPlayerJump;

    private float stateTimer;
    private boolean runningRight;
    private boolean isOrange;
    private boolean isDead;

    public Player(PlayScreen screen) {
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
        isOrange = true;
        isDead = false;

        playerTexture = screen.game.getAssets().get("Player/player.png", Texture.class);
        playerTextureRegion = new TextureRegion(playerTexture).split(130, 172)[1];

        waterPlayer = new Animation(0.08f, playerTextureRegion);
        waterPlayerJump = playerTextureRegion[playerTextureRegion.length - 1];

        playerTextureRegion = new TextureRegion(playerTexture).split(130, 172)[0];
        firePlayer = new Animation(0.08f, playerTextureRegion);
        firePlayerJump = playerTextureRegion[playerTextureRegion.length - 1];

        definePlayer();
        setBounds(0, 0, 130  / ElementalRun.PPM, 172 / ElementalRun.PPM);
        setRegion(waterPlayer.getKeyFrame(0));
    }

    public float getStateTimer() {
        return stateTimer;
    }

    public boolean isOrange() {
        return isOrange;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        b2Body.setLinearVelocity(new Vector2(0, 0));
        isDead = dead;
    }

    public void setOrange(boolean orange) {
        isOrange = orange;
    }

    public State getState() {

        if(isDead)
            return State.DEAD;
        else if(b2Body.getLinearVelocity().y > 0)
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
                region = isOrange() ? firePlayerJump : waterPlayerJump;
                break;
            case RUNNING:
                region = isOrange() ? firePlayer.getKeyFrame(stateTimer, true) : waterPlayer.getKeyFrame(stateTimer, true);
                break;
            case STANDING:
            default:
                region = isOrange() ? firePlayer.getKeyFrame(stateTimer, true) : waterPlayer.getKeyFrame(stateTimer, true);
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
        bdef.position.set(32 / ElementalRun.PPM, 1000 / ElementalRun.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2Body = world.createBody(bdef);
        b2Body.setLinearVelocity(new Vector2(5f, 0));

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(130 / ElementalRun.PPM / 2, 172 / ElementalRun.PPM / 2);
        fdef.filter.categoryBits = ElementalRun.PLAYER_BIT;
        fdef.filter.maskBits = ElementalRun.ORANGE_GROUND_BIT | ElementalRun.BLACK_GROUND_BIT
                                | ElementalRun.ORANGE_DIAMOND_BIT | ElementalRun.BLUE_DIAMOND_BIT;

        fdef.shape = shape;
        fdef.restitution = 0;
        b2Body.createFixture(fdef).setUserData(this);
        shape.dispose();
    }

    public void update(float deltaTime) {
        if(isOrange()) {
            Filter filter = new Filter();
            filter.categoryBits = ElementalRun.PLAYER_BIT;
            filter.maskBits = ElementalRun.ORANGE_GROUND_BIT | ElementalRun.BLACK_GROUND_BIT
                    | ElementalRun.ORANGE_DIAMOND_BIT | ElementalRun.BLUE_DIAMOND_BIT;
            b2Body.getFixtureList().first().setFilterData(filter);
        } else {
            Filter filter = new Filter();
            filter.categoryBits = ElementalRun.PLAYER_BIT;
            filter.maskBits = ElementalRun.BLUE_GROUND_BIT | ElementalRun.BLACK_GROUND_BIT
                    | ElementalRun.ORANGE_DIAMOND_BIT | ElementalRun.BLUE_DIAMOND_BIT;
            b2Body.getFixtureList().first().setFilterData(filter);
        }
        if(getY() < 0)
            isDead = true;
        setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(deltaTime));
    }
}
