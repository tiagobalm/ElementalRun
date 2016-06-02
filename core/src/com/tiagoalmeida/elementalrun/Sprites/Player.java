package com.tiagoalmeida.elementalrun.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.tiagoalmeida.elementalrun.ElementalRun;
import com.tiagoalmeida.elementalrun.Screens.PlayScreen;

/**
 * Created by tiago on 01-06-2016.
 */
public class Player extends Sprite {
    public enum State {FALLING, JUMPING, STANDING, RUNNING, DEAD};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2Body;

    private TextureRegion marioStand;
    private Animation marioRun;
    private TextureRegion marioJump;

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

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 1; i < 4; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("little_mario"), i * 16, 0, 16, 16));
        marioRun = new Animation(0.1f, frames);
        frames.clear();

        marioJump = new TextureRegion(screen.getAtlas().findRegion("little_mario"), 80, 0, 16, 16);
        marioStand = new TextureRegion(screen.getAtlas().findRegion("little_mario"), 0, 0, 16, 16);

        defineMario();
        setBounds(0, 0, 16 / ElementalRun.PPM, 16 / ElementalRun.PPM);
        setRegion(marioStand);
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
        else if(b2Body.getLinearVelocity().y < 0 && previousState == State.JUMPING)
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
                region = marioJump;
                break;
            case RUNNING:
                region =marioRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = marioStand;
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

    public void defineMario() {

        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / ElementalRun.PPM, 100 / ElementalRun.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2Body = world.createBody(bdef);
        b2Body.setLinearVelocity(new Vector2(1f, 0));

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / ElementalRun.PPM);
        fdef.filter.categoryBits = ElementalRun.PLAYER_BIT;
        fdef.filter.maskBits = ElementalRun.ORANGE_GROUND_BIT| ElementalRun.BLUE_GROUND_BIT | ElementalRun.BLACK_GROUND_BIT
                                | ElementalRun.ORANGE_DIAMOND_BIT | ElementalRun.BLUE_DIAMOND_BIT;

        fdef.shape = shape;
        fdef.restitution = 0;
        b2Body.createFixture(fdef).setUserData(this);
        shape.dispose();
    }

    public void update(float deltaTime) {
        setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(deltaTime));
    }
}
