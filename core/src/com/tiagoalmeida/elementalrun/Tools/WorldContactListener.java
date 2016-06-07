package com.tiagoalmeida.elementalrun.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.tiagoalmeida.elementalrun.FutureRun;
import com.tiagoalmeida.elementalrun.Sprites.Items.InteractiveTileObject;
import com.tiagoalmeida.elementalrun.Sprites.Player;

/**
 * World contact listener class. This class deals with all collisions.
 */
public class WorldContactListener implements ContactListener {

    /**
     * Override of beginContact method. Checks which collision is happening and deals with it accordingly.
     * @param contact Contact object containing all the information about the colision.
     */
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int coDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (coDef) {
            case FutureRun.PLAYER_BIT | FutureRun.ORANGE_DIAMOND_BIT:
                if(fixA.getFilterData().categoryBits == FutureRun.ORANGE_DIAMOND_BIT)
                    ((InteractiveTileObject)fixA.getUserData()).use();
                else
                    ((InteractiveTileObject)fixB.getUserData()).use();
                break;

            case FutureRun.PLAYER_BIT | FutureRun.BLUE_DIAMOND_BIT:
                if(fixA.getFilterData().categoryBits == FutureRun.BLUE_DIAMOND_BIT)
                    ((InteractiveTileObject)fixA.getUserData()).use();
                else
                    ((InteractiveTileObject)fixB.getUserData()).use();
                break;

            case FutureRun.PLAYER_BIT | FutureRun.PORTAL_BIT:
                if(fixA.getFilterData().categoryBits == FutureRun.PLAYER_BIT)
                    ((Player)fixA.getUserData()).setGameOver(true);
                else
                    ((Player)fixB.getUserData()).setGameOver(true);
                break;

            case FutureRun.PLAYER_BIT | FutureRun.END_OF_WORLD:
                if(fixA.getFilterData().categoryBits == FutureRun.PLAYER_BIT)
                    ((Player)fixA.getUserData()).setGameOver(false);
                else
                    ((Player)fixB.getUserData()).setGameOver(false);
                break;
        }
    }

    /**
     * Overrides endContact method. Does nothing.
     * @param contact
     */
    @Override
    public void endContact(Contact contact) {
    }

    /**
     * Overrides preSolve method. Does nothing.
     * @param contact
     */
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    /**
     * Overrides postSolve method. Does nothing.
     * @param contact
     */
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
