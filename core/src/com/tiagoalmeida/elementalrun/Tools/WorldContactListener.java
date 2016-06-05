package com.tiagoalmeida.elementalrun.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.tiagoalmeida.elementalrun.ElementalRun;
import com.tiagoalmeida.elementalrun.Sprites.Items.InteractiveTileObject;
import com.tiagoalmeida.elementalrun.Sprites.Player;


public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int coDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (coDef) {
            case ElementalRun.PLAYER_BIT | ElementalRun.ORANGE_DIAMOND_BIT:
                if(fixA.getFilterData().categoryBits == ElementalRun.ORANGE_DIAMOND_BIT)
                    ((InteractiveTileObject)fixA.getUserData()).use();
                else
                    ((InteractiveTileObject)fixB.getUserData()).use();
                break;

            case ElementalRun.PLAYER_BIT | ElementalRun.BLUE_DIAMOND_BIT:
                if(fixA.getFilterData().categoryBits == ElementalRun.BLUE_DIAMOND_BIT)
                    ((InteractiveTileObject)fixA.getUserData()).use();
                else
                    ((InteractiveTileObject)fixB.getUserData()).use();
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
