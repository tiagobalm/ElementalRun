package com.tiagoalmeida.elementalrun.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.tiagoalmeida.elementalrun.ElementalRun;
import com.tiagoalmeida.elementalrun.Screens.PlayScreen;


public class Player extends Sprite {
    public enum State {FALLING, JUMPING, STANDING, RUNNING, DEAD};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2Body;

    private TextureRegion firecatStand;
    private Animation firecat;
    private Animation firecatJump;

    private TextureRegion watercatStand;
    private Animation watercat;
    private Animation watercatJump;

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
        for(int i = 0; i < 5; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("watercat"), i * 23, 0, 23, 16));
        watercat = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 5; i < 12; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("watercat"), i * 23, 0, 23, 16));
        watercatJump = new Animation(0.1f, frames);
        frames.clear();

        watercatStand = new TextureRegion(screen.getAtlas().findRegion("watercat"), 0, 0, 23, 16);

        for(int i = 0; i < 5; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("firecat"), i * 23, 0, 23, 16));
        firecat = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 5; i < 12; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("firecat"), i * 23, 0, 23, 16));
        firecatJump = new Animation(0.1f, frames);
        frames.clear();

        firecatStand = new TextureRegion(screen.getAtlas().findRegion("firecat"), 0, 0, 23, 16);

        defineMario();
        setBounds(0, 0, 16 / ElementalRun.PPM, 16 / ElementalRun.PPM);
        setRegion(firecatStand);
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
            case FALLING:
                region = isOrange() ? firecatJump.getKeyFrame(stateTimer, true) : watercatJump.getKeyFrame(stateTimer, true);
                break;
            case RUNNING:
                region = isOrange() ? firecat.getKeyFrame(stateTimer, true) : watercat.getKeyFrame(stateTimer, true);
                break;
            case STANDING:
            default:
                region = isOrange() ? firecatStand : watercatStand;
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
